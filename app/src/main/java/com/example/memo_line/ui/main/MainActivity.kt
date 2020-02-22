package com.example.memo_line.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.memo_line.R
import com.example.memo_line.util.replaceFragmentInActivity
import com.example.memo_line.util.setupActionBar
import com.google.android.material.navigation.NavigationView

import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.shared_toolbar.*


class MainActivity : DaggerAppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 툴바
         */
        setupActionBar(R.id.toolbar) {
            setDisplayShowCustomEnabled(false)
            setCustomView(R.layout.actionbar_checkbox)
            setTitle(R.string.title)
            // 네비 on
//            setHomeAsUpIndicator(R.drawable.ic_menu)
//            setDisplayHomeAsUpEnabled(true)
        }

//        /**
//         * 네비게이션 on
//         */
//        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout)).apply {
//            setStatusBarBackground(R.color.colorPrimaryDark)
//        }
//        setupDrawerContent(findViewById(R.id.nav_view))

        supportFragmentManager.findFragmentById(R.id.contentFrame)
                as MainFragment? ?: MainFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

    }

//    /**
//     * 네비 오픈
//     */
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == android.R.id.home) {
//            drawerLayout.openDrawer(GravityCompat.START)
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }
//    /**
//    * 네비 close
//    */
//    private fun setupDrawerContent(navigationView: NavigationView) {
//        navigationView.setNavigationItemSelectedListener { menuItem ->
//            menuItem.isChecked = true
//            drawerLayout.closeDrawers()
//            true
//        }
//    }

    /**
     * 뒤로가기 버튼 이벤트
     */
    override fun onBackPressed() {
        if (toolbar.title.equals(getString(R.string.nothing))) {
            val mainFg = supportFragmentManager.findFragmentById(R.id.contentFrame)
                    as MainFragment
            mainFg.showMain()
        }
        else {
            super.onBackPressed()
        }
    }


}

