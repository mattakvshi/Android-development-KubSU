package com.example.lessons3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.lessons3.data.University
import com.example.lessons3.fragments.FacultyListFragment
import com.example.lessons3.fragments.UniversityListFragment
import com.example.lessons3.repository.DataRepository

class MainActivity : AppCompatActivity(), ActivityInterface {

    interface Edit {
        fun append()
        fun update()
        fun delete()
    }

    companion object{
        const val universityID = 0
        const val facultyID = 1
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
            R.id.miUpdateUniversity -> {
                val fedit : Edit = UniversityListFragment.getInstance()
                fedit.update()
                true
            }
            R.id.miDeleteUniversity -> {
                val fedit : Edit = UniversityListFragment.getInstance()
                fedit.delete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onStop(){
        DataRepository.getInstatnce().saveData()
        super.onStop()
    }

//    override fun onDestroy() {
//        DataRepository.getInstatnce().saveData()
//        super.onDestroy()
//    }

    override fun updateTitle(newTitle : String){
      title = newTitle
    }

    override fun setFragment(fragmentId: Int) {
        when (fragmentId){
            universityID -> {setFragment(UniversityListFragment.getInstance())}
            facultyID -> {setFragment(FacultyListFragment.getInstance())}
        }
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcvMain, fragment)
            .commit()
    }


}