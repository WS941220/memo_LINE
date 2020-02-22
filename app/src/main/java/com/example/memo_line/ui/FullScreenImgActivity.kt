package com.example.memo_line.ui

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.memo_line.R
import com.example.memo_line.ui.addeditmemo.AddEditMemoFragment
import com.example.memo_line.util.setupActionBar
import com.github.chrisbanes.photoview.PhotoView
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_full_screen_img.*
import java.io.File
import android.animation.ObjectAnimator.ofFloat as ofFloat1

class FullScreenImgActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_img)

        val uri = intent.getParcelableExtra<Uri>("uri")

        setupActionBar(R.id.toolbar) {
            setTitle(R.string.nothing)
            setDisplayHomeAsUpEnabled(true);
            setDisplayShowHomeEnabled(true);
        }

        val actionBar = findViewById<AppBarLayout>(R.id.actionBar).apply {
            bringToFront()
        }

        Glide.with(this).load(uri?.toString()).error(R.drawable.ic_not_found).centerCrop().into(fullScreenImg)

    }

    /**
     * 뒤로가기
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
