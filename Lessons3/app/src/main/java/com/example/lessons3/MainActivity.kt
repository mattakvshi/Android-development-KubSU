package com.example.lessons3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.lessons3.fragments.UniversityListFragment

class MainActivity : AppCompatActivity() {

    interface Edit {
        fun append()
        fun update()
        fun delete()
    }

    private var _miNewUniversity: MenuItem? =null
    private var _miUpdateUniversity: MenuItem? =null
    private var _miDeleteUniversity: MenuItem? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcvMain, UniversityListFragment.getInstance())
            .commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        _miNewUniversity = menu?.findItem(R.id.miNewUniversity)
        _miUpdateUniversity = menu?.findItem(R.id.miUpdateUniversity)
        _miDeleteUniversity = menu?.findItem(R.id.miDeleteUniversity)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.miNewUniversity -> {
                val fedit : Edit = UniversityListFragment.getInstance()
                fedit.append()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}