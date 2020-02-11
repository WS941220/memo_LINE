package com.example.memo_line.ui.addeditmemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.memo_line.R
import com.example.memo_line.di.DaggerActivityComponent
import com.example.practice_test.di.module.ActivityModule

class AddEditMemoActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_ADD_MEMO = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_memo)
        injectDependency()
    }

    /**
     * Dagger 의존성 주입
     */
    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build()
        activityComponent.inject(this)
    }
}
