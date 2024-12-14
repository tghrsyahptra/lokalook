import logging
from flask import Flask, request, jsonify
from tensorflow.keras.preprocessing import image
import numpy as np
import tensorflow as tf
from google.cloud import storage
from io import BytesIO
import os
import uuid
from datetime import datetime
from flask_cors import CORS

# Daftar label kelas dengan indeks numerik
destination_labels = {
    'ayam bakar': 0,
    'bakmie': 1,
    'buntil': 2,
    'gethuk': 3,
    'jalabia': 4,
    'jenang jaket': 5,
    'klanting': 6,
    'kraca': 7,
    'mendoan': 8,
    'nopia': 9,
    'soto sokaraja': 10
}

# Membalik dictionary untuk mendapatkan label dari indeks
class_labels = {v: k for k, v in destination_labels.items()}

CONFIDENCE_THRESHOLD = 0.85

# Setup logging
logging.basicConfig(level=logging.DEBUG)

# Fungsi untuk memuat dan memproses gambar
def preprocess_image(img_file):
    try:
        from PIL import Image
        img = Image.open(BytesIO(img_file.read()))
        img_size = (299, 299)
        img = img.resize(img_size)
        img_array = np.array(img).astype(np.float32) / 255.0
        img_array = np.expand_dims(img_array, axis=0)
        return img_array
    except Exception as e:
        logging.error(f"Error preprocessing image: {str(e)}")
        raise ValueError(f"Error preprocessing image: {str(e)}")

# Fungsi untuk memuat model TFLite dari GCS
def load_model_from_gcs(bucket_name, model_path):
    try:
        storage_client = storage.Client()
        bucket = storage_client.get_bucket(bucket_name)
        blob = bucket.blob(model_path)
        model_data = BytesIO()
        blob.download_to_file(model_data)
        model_data.seek(0)
        interpreter = tf.lite.Interpreter(model_content=model_data.read())
        interpreter.allocate_tensors()
        return interpreter
    except Exception as e:
        logging.error(f"Error loading model from GCS: {str(e)}")
        raise ValueError(f"Error loading model from GCS: {str(e)}")

# Fungsi untuk melakukan prediksi dengan model TFLite
def predict_with_tflite(model, img_array):
    try:
        input_details = model.get_input_details()
        output_details = model.get_output_details()
        model.set_tensor(input_details[0]['index'], img_array)
        model.invoke()
        output_data = model.get_tensor(output_details[0]['index'])
        return output_data
    except Exception as e:
        logging.error(f"Error during prediction: {str(e)}")
        raise ValueError(f"Error during prediction: {str(e)}")

app = Flask(__name__)
CORS(app)

# Pastikan kredensial GCP tersedia
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "./credentials.json"

# Mencoba untuk memuat model dengan pengecekan error handling
try:
    model = load_model_from_gcs('lokalook-fd', 'exception_Banyumas_food_88.tflite')
except ValueError as ve:
    logging.error(f"Model loading failed: {str(ve)}")
    model = None  # Pastikan untuk tidak melanjutkan jika model gagal dimuat

@app.route('/predict', methods=['POST'])
def predict():
    if model is None:
        logging.error("Model is not loaded, cannot process the prediction.")
        return jsonify({"message": "Model loading failed, try again later."}), 500

    try:
        img_file = request.files.get('image')
        if not img_file:
            logging.error("No image file uploaded.")
            return jsonify({"message": "No image file uploaded"}), 400
        
        # Preprocess input image
        img_array = preprocess_image(img_file)
        
        # Perform prediction
        prediction = predict_with_tflite(model, img_array)
        
        # Process prediction result
        predicted_class_index = np.argmax(prediction)
        predicted_class = class_labels.get(predicted_class_index, "Unknown")
        confidence = float(np.max(prediction))
        is_above_threshold = confidence > CONFIDENCE_THRESHOLD
        
        response_data = {
            "message": "Model is predicted successfully.",
            "data": {
                "id": str(uuid.uuid4()).replace('-', '')[:16],
                "result": predicted_class,
                "confidenceScore": round(confidence * 100, 8),
                "isAboveThreshold": is_above_threshold,
                "createdAt": datetime.utcnow().isoformat()[:-3] + "Z"
            }
        }
        
        return jsonify(response_data)
    
    except ValueError as ve:
        logging.error(f"Error processing image or prediction: {str(ve)}")
        return jsonify({"message": str(ve)}), 400
    except Exception as e:
        logging.error(f"Unexpected error: {str(e)}")
        return jsonify({"message": f"Unexpected error: {str(e)}"}), 500

@app.route('/', methods=['GET'])
def get_status():
    try:
        return jsonify({
            "status": "API is running",
            "message": "Model and server are running successfully."
        })
    except Exception as e:
        logging.error(f"Unexpected error during status check: {str(e)}")
        return jsonify({"message": f"Unexpected error: {str(e)}"}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=True)
