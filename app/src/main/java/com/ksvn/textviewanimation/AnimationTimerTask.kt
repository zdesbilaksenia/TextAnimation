package com.ksvn.textviewanimation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.animation.doOnEnd
import java.util.*


class AnimationTimerTask(
    private val textView: TextView,
    private val layout: LinearLayout,
    private val activity: Activity,
    private var set: AnimatorSet
) :
    TimerTask() {

    private var yDownAnimator: ValueAnimator? = null
    private var yUpAnimator: ValueAnimator? = null

    override fun run() {
        activity.runOnUiThread(Runnable {
            setAnimations(textView.y)

            set.play(yUpAnimator).after(yDownAnimator)
            set.start()
            set.doOnEnd {
                setAnimations(0f)
                set.play(yUpAnimator).after(yDownAnimator)
                set.start()
            }
        })
    }

    private fun setAnimations(topStartPoint: Float) {
        yDownAnimator =
            ObjectAnimator.ofFloat(
                topStartPoint,
                (layout.height - textView.height).toFloat()
            )
        yDownAnimator?.addUpdateListener { textView.y = yDownAnimator?.animatedValue as Float }
        yDownAnimator?.interpolator = LinearInterpolator()
        yDownAnimator?.duration = 5000

        yUpAnimator =
            ObjectAnimator.ofFloat(
                (layout.height - textView.height).toFloat(),
                0f
            )
        yUpAnimator?.addUpdateListener { textView.y = yUpAnimator?.animatedValue as Float }
        yUpAnimator?.interpolator = LinearInterpolator()
        yUpAnimator?.duration = 5000
    }

    fun stopAnimation() {
        set.removeAllListeners()
        yUpAnimator?.cancel()
        yDownAnimator?.cancel()
        set.cancel()
    }
}