package com.dicoding.picodiploma.mycamera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.dicoding.picodiploma.mycamera.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari Intent
        val result = intent.getStringExtra(EXTRA_RESULT)
        val confidence = intent.getDoubleExtra(EXTRA_CONFIDENCE, 0.0)
        val imageUri = intent.getStringExtra(EXTRA_IMAGE_URI)

        // Tampilkan data
        binding.imageView.setImageURI(imageUri?.toUri())
        binding.textViewResult.text = result
        binding.textViewConfidence.text = String.format("Confidence: %.2f%%", confidence)
    }

    companion object {
        const val EXTRA_RESULT = "extra_result"
        const val EXTRA_CONFIDENCE = "extra_confidence"
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
}
