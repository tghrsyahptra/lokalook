package com.lokalook.lokalook.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.lokalook.lokalook.R

class UsernameEdText : AppCompatEditText, View.OnTouchListener {

    private lateinit var clearButtonDrawable: Drawable
    private lateinit var inputIconDrawable: Drawable

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize()
    }

    private fun initialize() {
        clearButtonDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24) as Drawable
        inputIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_person_24) as Drawable
        setOnTouchListener(this)
        addTextChangedListener(createTextWatcher())
    }

    private fun createTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.isNotEmpty()) showClearButton() else hideClearButton()
        }
        override fun afterTextChanged(s: Editable) {}
    }

    private fun showClearButton() {
        setCompoundDrawablesWithIntrinsicBounds(inputIconDrawable, null, clearButtonDrawable, null)
    }

    private fun hideClearButton() {
        setCompoundDrawablesWithIntrinsicBounds(inputIconDrawable, null, null, null)
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            if (isClearButtonPressed(event)) {
                return handleClearButtonAction(event)
            }
        }
        return false
    }

    private fun isClearButtonPressed(event: MotionEvent): Boolean {
        val clearButtonStart: Float
        val clearButtonEnd: Float
        return if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            clearButtonEnd = (clearButtonDrawable.intrinsicWidth + paddingStart).toFloat()
            event.x < clearButtonEnd
        } else {
            clearButtonStart = (width - paddingEnd - clearButtonDrawable.intrinsicWidth).toFloat()
            event.x > clearButtonStart
        }
    }

    private fun handleClearButtonAction(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                showClearButton()
                return true
            }
            MotionEvent.ACTION_UP -> {
                clearText()
                hideClearButton()
                return true
            }
        }
        return false
    }

    private fun clearText() {
        text?.clear()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setupTextStyle()
    }

    private fun setupTextStyle() {
        context.apply {
            setTextColor(ContextCompat.getColor(this, R.color.black))
            hint = getString(R.string.hint_username)
            textSize = 16f
            setHintTextColor(ContextCompat.getColor(this, R.color.gray_800))
            background = ContextCompat.getDrawable(this, R.drawable.form_input)
        }
        isSingleLine = true
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
}