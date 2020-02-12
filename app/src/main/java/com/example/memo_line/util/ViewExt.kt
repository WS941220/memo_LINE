package com.example.memo_line.util

import com.google.android.material.snackbar.Snackbar
import android.view.View

fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}