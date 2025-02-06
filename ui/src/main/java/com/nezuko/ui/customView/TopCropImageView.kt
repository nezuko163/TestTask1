package com.nezuko.ui.customView

import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class TopCropImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        applyTopCrop()
    }

    private fun applyTopCrop() {
        val drawable = drawable ?: return

        val imageWidth = drawable.intrinsicWidth.toFloat()
        val imageHeight = drawable.intrinsicHeight.toFloat()
        val scale = width / imageWidth

        val matrix = Matrix()
        matrix.setScale(scale, scale) // Масштабируем по ширине
        matrix.postTranslate(0f, imageHeight) // Сдвигаем к верхнему краю

        imageMatrix = matrix
    }
}