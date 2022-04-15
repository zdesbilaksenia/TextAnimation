package com.ksvn.textviewanimation

import android.animation.AnimatorSet
import android.os.Bundle
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var mainLayout: LinearLayout? = null
    private var textView: TextView? = null
    private var x = 0
    private var y = 0
    private var timer = Timer()
    private var set = AnimatorSet()
    private var animationTimerTask: AnimationTimerTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainLayout = findViewById(R.id.layout)

        mainLayout?.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                mainLayout?.removeAllViews()

                x = motionEvent.x.toInt()
                y = motionEvent.y.toInt()

                moveTextView()
                setTimer()
            }

            true
        }
    }

    private fun moveTextView() {
        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(x, y, 0, 0)

        textView = TextView(this)
        textView?.text = resources.getString(R.string.greeting)
        textView?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f);
        textView?.layoutParams = layoutParams

        if (Locale.getDefault().displayLanguage == "English") {
            textView?.setTextColor(ContextCompat.getColor( this,R.color.red))
        } else {
            textView?.setTextColor(ContextCompat.getColor(this, R.color.blue))
        }

        mainLayout?.addView(textView)
    }

    private fun setTimer() {
        timer.cancel()
        timer = Timer()

        animationTimerTask?.stopAnimation()
        set = AnimatorSet()
        animationTimerTask = AnimationTimerTask(textView!!, mainLayout!!, this, set)

        textView?.setOnClickListener {
            animationTimerTask?.stopAnimation()
        }

        timer.schedule(animationTimerTask, 5000);
    }
}