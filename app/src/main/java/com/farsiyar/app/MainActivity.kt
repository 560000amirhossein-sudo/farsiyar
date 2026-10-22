
package com.farsiyar.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private val overlayRequest = 2001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 48, 32, 32)
            setBackgroundColor(0xFF171321.toInt())
        }

        val title = TextView(this).apply {
            text = "فارسی‌یار"
            textSize = 30f
            setTextColor(0xFFFFFFFF.toInt())
        }

        val description = TextView(this).apply {
            text = "دکمهٔ شناور برای ترجمهٔ متن بازی"
            textSize = 16f
            setTextColor(0xFFD8C8FF.toInt())
            setPadding(0, 20, 0, 24)
        }

        val button = Button(this).apply {
            text = "فعال‌کردن دکمهٔ شناور"
            setOnClickListener {
                if (Settings.canDrawOverlays(this@MainActivity)) {
                    startFloatingService()
                } else {
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:$packageName")
                    )
                    startActivityForResult(intent, overlayRequest)
                }
            }
        }

        layout.addView(title)
        layout.addView(description)
        layout.addView(button)
        setContentView(layout)
    }

    private fun startFloatingService() {
        startService(Intent(this, FloatingService::class.java))
        Toast.makeText(
            this,
            "دکمهٔ شناور فعال شد",
            Toast.LENGTH_SHORT
        ).show()
    }

    @Deprecated("Deprecated by Android")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == overlayRequest) {
            if (Settings.canDrawOverlays(this)) {
                startFloatingService()
            } else {
                Toast.makeText(
                    this,
                    "اجازهٔ نمایش روی برنامه‌ها لازم است",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
