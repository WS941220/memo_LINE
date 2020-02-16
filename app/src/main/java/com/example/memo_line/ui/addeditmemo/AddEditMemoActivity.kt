package com.example.memo_line.ui.addeditmemo

import android.os.Bundle
import com.example.memo_line.R
import com.example.memo_line.util.replaceFragmentInActivity
import com.example.memo_line.util.setupActionBar
import dagger.android.support.DaggerAppCompatActivity


class AddEditMemoActivity : DaggerAppCompatActivity() {

    companion object {
        const val REQUEST_ADD_MEMO = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_memo)
        val memoId = intent.getStringExtra(AddEditMemoFragment.ARGUMENT_EDIT_MEMO_ID)

        /**
         * 툴바
         */
        setupActionBar(R.id.toolbar) {
            setTitle(R.string.add_memo)
            setDisplayHomeAsUpEnabled(true);
            setDisplayShowHomeEnabled(true);
        }

        supportFragmentManager.findFragmentById(R.id.contentFrame) as AddEditMemoFragment?
            ?: AddEditMemoFragment.newInstance(memoId).also {
                replaceFragmentInActivity(it, R.id.contentFrame)
            }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
