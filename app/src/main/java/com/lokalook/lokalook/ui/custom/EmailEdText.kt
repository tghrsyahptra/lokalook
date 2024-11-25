package com.lokalook.lokalook.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.lokalook.lokalook.R

class EmailEdText : AppCompatEditText {

    private lateinit var emailIcon: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        emailIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_email_24) as Drawable
        addTextChangedListener(createEmailTextWatcher())
    }

    private fun createEmailTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // No action required
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            handleEmailValidation(s)
            showEmailIcon()
        }

        override fun afterTextChanged(s: Editable) {
            // No action required
        }
    }

    private fun handleEmailValidation(text: CharSequence) {
        error = when {
            text.isEmpty() -> null
            !text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) -> context.getString(R.string.label_invalid_email)
            else -> null
        }
    }

    private fun showEmailIcon() {
        setCompoundDrawablesWithIntrinsicBounds(emailIcon, null, null, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        context.apply {
            setTextColor(ContextCompat.getColor(this, R.color.black))
            hint = getString(R.string.hint_email)
            textSize = 16f
            setHintTextColor(ContextCompat.getColor(this, R.color.gray_800))
            background = ContextCompat.getDrawable(this, R.drawable.form_input)
        }
        isSingleLine = true
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
}