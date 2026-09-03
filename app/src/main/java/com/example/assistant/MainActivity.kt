package com.example.assistant

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assistant.utils.AppLauncher
import com.example.assistant.utils.CallHelper
import com.example.assistant.utils.MediaControlManager

class MainActivity : AppCompatActivity() {

    private lateinit var appLauncher: AppLauncher
    private lateinit var callHelper: CallHelper
    private lateinit var mediaControl: MediaControlManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appLauncher = AppLauncher(this)
        callHelper = CallHelper(this)
        mediaControl = MediaControlManager(this)

        val inputField = findViewById<EditText>(R.id.inputText)
        val executeButton = findViewById<Button>(R.id.btnExecute)

        executeButton.setOnClickListener {
            val command = inputField.text.toString().trim()
            processCommand(command)
        }
    }

    private fun processCommand(command: String) {
        when {
            command.startsWith("חייג ל") -> {
                val name = command.removePrefix("חייג ל").trim()
                val success = callHelper.makeCallToContact(name)
                if (!success) showToast("איש הקשר לא נמצא")
            }
            command.startsWith("פתוח ") || command.startsWith("פתח ") -> {
                val appName = command.replace("פתוח ", "").replace("פתח ", "").trim()
                val success = appLauncher.openAppByName(appName)
                if (!success) showToast("האפליקציה לא נמצאה")
            }
            command == "נגן" || command == "עצור" -> {
                mediaControl.togglePlayPause()
            }
            command == "הבא" -> {
                mediaControl.nextTrack()
            }
            else -> {
                val launched = appLauncher.openAppByName(command)
                if (!launched) showToast("פקודה לא מוכרת")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
