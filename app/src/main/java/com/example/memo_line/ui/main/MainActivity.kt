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


class MainActivity : DaggerAppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 툴바
         */
        setupActionBar(R.id.toolbar) {
            setTitle(R.string.title)
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
        }

        /**
         * 네비게이션
         */
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout)).apply {
            setStatusBarBackground(R.color.colorPrimaryDark)
        }
        setupDrawerContent(findViewById(R.id.nav_view))

        supportFragmentManager.findFragmentById(R.id.contentFrame)
                as MainFragment? ?: MainFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

    }

    /**
     * 네비 오픈
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            //            if (menuItem.itemId == R.id.statistics_navigation_menu_item) {
//                val intent = Intent(this@TasksActivity, StatisticsActivity::class.java)
//                startActivity(intent)
//            }
            // Close the navigation drawer when an item is selected.
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            true
        }
    }

}

