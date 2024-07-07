package com.example.skyfinder.presentation.ui

import android.text.InputFilter
import android.text.Spanned

class CyrillicInputFilter : InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        for (i in start until end) {
            if (!Character.UnicodeBlock.of(source[i]).equals(Character.UnicodeBlock.CYRILLIC)) {
                return ""
            }
        }
        return null
    }
}