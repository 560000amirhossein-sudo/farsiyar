
package com.farsiyar.app

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private val captureRequest = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(28, 28, 28, 28)
            setBackgroundColor(Color.rgb(20, 15, 35))
        }

        val title = TextView(this).apply {
            text = "فارسی‌یار"
            textSize = 32f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val description = TextView(this).apply {
            text = "ترجمهٔ متن بازی به فارسی"
            textSize = 18f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 20, 0, 30)
        }

        val startButton = Button(this).apply {
            text = "آماده‌سازی ترجمه"
            setOnClickListener {
                val manager =
                    getSystemService(MEDIA_PROJECTION_SERVICE)
                        as MediaProjectionManager

                startActivityForResult(
                    manager.createScreenCaptureIntent(),
                    captureRequest
                )
            }
        }

        layout.addView(title)
        layout.addView(description)
        layout.addView(
            startButton,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        setContentView(layout)
    }

    @Deprecated("Legacy activity result callback")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == captureRequest) {
            if (resultCode == RESULT_OK && data != null) {
                Toast.makeText(
                    this,
                    "مجوز تصویر دریافت شد؛ مرحلهٔ بعدی ساخت ترجمه است.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "مجوز تصویر داده نشد.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
