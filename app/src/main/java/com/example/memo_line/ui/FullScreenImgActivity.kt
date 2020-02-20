package com.example.memo_line.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.memo_line.R
import com.github.chrisbanes.photoview.PhotoView
import kotlinx.android.synthetic.main.activity_full_screen_img.*
import java.io.File

class FullScreenImgActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_img)

        val uri = intent.getParcelableExtra<Uri>("uri")

        Glide.with(this).load(uri?.toString()).error(R.drawable.ic_launcher_background).override(200, 200).centerCrop().into(fullScreenImg)

    }
}
