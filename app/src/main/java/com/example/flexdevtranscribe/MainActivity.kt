package com.example.flexdevtranscribe

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import com.example.flexdevtranscribe.databinding.ActivityMainBinding
import java.util.*

fun convertStringToAnnotatedString(text: String): AnnotatedString {
    return buildAnnotatedString {
        append(text)
    }
}

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding // Declare a binding variable

    private val speechRecognitionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    if (result.resultCode == RESULT_OK) {
        val data: Intent? = result.data
        val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        // Process the speech recognition results here
        val spokenText = results?.firstOrNull() ?: "No speech recognized"
        // Update your UI, e.g., set text to a TextView
        binding.tvResult.text = convertStringToAnnotatedString(spokenText) // Assuming tvResult is your TextView
    }
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inflate the binding class
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Set the root view of the binding as the content view
        setContentView(binding.root)

        binding.btnRecord.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...")
            }
            speechRecognitionLauncher.launch(intent)
        }
    }
}
