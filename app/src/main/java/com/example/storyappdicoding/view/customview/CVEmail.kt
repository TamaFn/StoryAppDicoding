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


class CVEmail: AppCompatEditText, View.OnTouchListener {

    private var isValid = false
    private var isTaken = false
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
                validateEmail()
                if(isTaken){
                    validateEmailHasTaken()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun validateEmail() {
        val inputText = text.toString().trim()
        isValid = Patterns.EMAIL_ADDRESS.matcher(inputText).matches()
        error = if (!inputText.isNullOrEmpty() && !isValid) {
            resources.getString(R.string.email_wrong)
        } else {
            null
        }
    }

    private fun validateEmailHasTaken() {
        error = if (isTaken && text.toString().trim() == isSame) {
            resources.getString(R.string.email_taken)
        } else {
            null
        }
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }

}