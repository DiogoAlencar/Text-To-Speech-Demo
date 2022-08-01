package com.example.texttospeechdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.texttospeechdemo.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityMainBinding
    private var textToSpeech : TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        textToSpeech = TextToSpeech(this, this)

        binding.buttonTextToSpeech.setOnClickListener { view ->
            if (binding.editTextTexto.text!!.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please type something",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                speakOut(binding.editTextTexto.text.toString())

            }
        }

    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech!!.setLanguage(Locale.getDefault())

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language not supported")
            } else {
                Log.e("TTS", "Initialization failed")
            }
        }

    }

    private fun speakOut(text : String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()

        if (textToSpeech != null) {
            textToSpeech?.stop()
            textToSpeech?.shutdown()
        }

    }
}