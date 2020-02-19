package com.example.memo_line.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.memo_line.R
import com.github.chrisbanes.photoview.PhotoView
import kotlinx.android.synthetic.main.activity_full_screen_img.*

class FullScreenImgActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_img)

        val uri = intent.getParcelableExtra<Uri>("uri")

        Glide.with(this).load(uri).centerCrop().into(fullScreenImg)
    }
}
