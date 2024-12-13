from flask import Flask, request, jsonify
from google.cloud import storage
import pandas as pd
from sklearn.preprocessing import LabelEncoder
import os
import io
from flask_cors import CORS  # Import CORS

app = Flask(__name__)

# Konfigurasi CORS
CORS(app)

# Set credential Google Cloud sebelum menjalankan
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "./credentials.json"

# Konfigurasi bucket Google Cloud Storage
BUCKET_NAME = "wisata-data"
FILE_NAME = "objek_wisata.csv"

# Fungsi untuk membaca file CSV dari bucket
def load_data_from_bucket():
    client = storage.Client()
    bucket = client.get_bucket(BUCKET_NAME)
    blob = bucket.blob(FILE_NAME)
    data = blob.download_as_text()
    df = pd.read_csv(io.StringIO(data))
    # Label encoding kategori
    label_encoder = LabelEncoder()
    df["kategori_encoded"] = label_encoder.fit_transform(df["kategori"])
    return df

# Fungsi rekomendasi
def rekomendasi_wisata(df, budget, num_people):
    hasil = []
    total_budget_per_person = budget / num_people

    for i, row in df.iterrows():
        total_biaya = row["tiket"] + row["makanan"]
        if total_biaya <= total_budget_per_person:
            hasil.append({
                "destinasi": row["nama"],
                "gambar": row["image_url"],
                "total_biaya": total_biaya
            })

    if not hasil:
        return {"message": "Tidak ada destinasi yang sesuai dengan budget Anda."}
    return hasil

# Endpoint untuk root
@app.route('/', methods=['GET'])
def home():
    return jsonify({"message": "API Smart Budgeting berjalan dengan baik!"})

# Endpoint untuk rekomendasi
@app.route('/rekomendasi', methods=['GET'])
def get_rekomendasi():
    try:
        # Ambil parameter dari request
        budget = int(request.args.get('budget', 0))
        num_people = int(request.args.get('num_people', 1))

        # Load data
        df = load_data_from_bucket()

        # Dapatkan rekomendasi
        result = rekomendasi_wisata(df, budget, num_people)
        return jsonify(result)

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=True)
