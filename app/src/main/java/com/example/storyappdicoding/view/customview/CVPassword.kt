package com.example.storyappdicoding.view.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.example.storyappdicoding.R

class CVPassword: AppCompatEditText, View.OnTouchListener {

    private var isValid = false
    private lateinit var isSame: String

    constructor(
        context: Context
    ) : super(context) {
        init()
    }
    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        init()
    }
    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword()
            }

            override fun afterTextChanged(p0: Editable?) {
                //Do nothing
            }

        })
    }

    private fun validatePassword() {
        isValid = (text?.length ?: 0) >= 8
        error = if (!isValid) {
            resources.getString(R.string.password_less)
        } else {
            null
        }
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }

}