package com.example.memo_line.ui.addeditmemo

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.memo_line.R
import com.example.memo_line.util.replaceFragmentInActivity
import com.example.memo_line.util.setupActionBar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.shared_toolbar.*


class AddEditMemoActivity : DaggerAppCompatActivity() {

    companion object {
        const val REQUEST_ADD_MEMO = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_memo)
        val memoId = intent.getStringExtra(AddEditMemoFragment.ARGUMENT_SHOW_MEMO_ID)

        /**
         * 툴바
         */
        setupActionBar(R.id.toolbar) {
            setTitle(if (memoId == null) R.string.add_memo else R.string.nothing)
            setDisplayHomeAsUpEnabled(true);
            setDisplayShowHomeEnabled(true);
        }

        supportFragmentManager.findFragmentById(R.id.contentFrame) as AddEditMemoFragment?
            ?: AddEditMemoFragment.newInstance(memoId).also {
                replaceFragmentInActivity(it, R.id.contentFrame)
            }

    }

    /**
     * 뒤로가기 버튼 이벤트
     */
    override fun onBackPressed() {
        if (toolbar.title == getString(R.string.edit_memo)) {
            val showFg = supportFragmentManager.findFragmentById(R.id.contentFrame)
                    as AddEditMemoFragment
            showFg.onShow()
        } else {
            super.onBackPressed()
        }
    }


}
