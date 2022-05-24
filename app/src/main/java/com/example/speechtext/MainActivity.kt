package com.example.speechtext

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import org.w3c.dom.Text
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_SPEECH_INPUT = 100
    private lateinit var button: Button
    private lateinit var resTextView: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)

        resTextView = findViewById(R.id.textView)

        button.setOnClickListener {
            SpeechFunction()
        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun SpeechFunction() {
//        val intent = Instant(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Kuch bool")

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (exp: ActivityNotFoundException) {
            Toast.makeText(applicationContext, "Speech Not Supported ...", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK || null != data) {
                val res: ArrayList<String> =
                    data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                resTextView.text = res[0]
                readTextPass()
            }
        }
    }
    fun readTextPass(){

        val getText = findViewById<TextView>(R.id.textView)

        var text = getText.text.toString()

        if (text !== "") {
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("text", text.toString())
            startActivity(intent)
        } else {
            Toast.makeText(this,    "Error", Toast.LENGTH_SHORT).show()
        }
    }

}