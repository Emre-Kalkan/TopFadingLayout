package com.example.topfadinglayout

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout

class TopFadingLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val rectTop = Rect()
    private val paintTop = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    }
    private val fadeColors = intArrayOf(Color.TRANSPARENT, Color.BLACK)

    override fun dispatchDraw(canvas: Canvas) {
        initTopGradient()
        val count = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
        super.dispatchDraw(canvas)
        canvas.apply {
            drawRect(rectTop, paintTop)
            restoreToCount(count)
        }
    }

    private fun initTopGradient() {
        // Fade size can be custom
        val fadeSize = height.div(2).toFloat()
        val top = paddingTop
        val left = paddingLeft
        val right = width.minus(paddingRight)
        val bottom = top.plus(fadeSize)
        rectTop.set(left, top, right, bottom.toInt())
        // ignore horizontal bounds since its a vertical fade.
        val horizontalBound = left.toFloat()
        paintTop.shader = LinearGradient(
            horizontalBound,
            top.toFloat(),
            horizontalBound,
            fadeSize,
            fadeColors,
            null,
            Shader.TileMode.CLAMP
        )
    }
}