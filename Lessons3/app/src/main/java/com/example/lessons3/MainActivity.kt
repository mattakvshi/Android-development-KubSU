package com.example.lessons3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.lessons3.data.Student
import com.example.lessons3.data.University
import com.example.lessons3.fragments.FacultyListFragment
import com.example.lessons3.fragments.GroupsFragment
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
        const val groupID = 2
        const val studentID = 3
    }

    private var _miNewUniversity: MenuItem? =null
    private var _miUpdateUniversity: MenuItem? =null
    private var _miDeleteUniversity: MenuItem? =null

    private var _miNewGroup: MenuItem? =null
    private var _miUpdateGroup: MenuItem? =null
    private var _miDeleteGroup: MenuItem? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onBackPressedDispatcher.addCallback(this){
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
                when(currentFragmentID){
                    universityID -> {
                        finish()
                    }
                    facultyID ->{
                        currentFragmentID= universityID
                        updateTitle("Список университетов")
                    }
                    else->{}
                }
                updateMenuView()
            }
            else{
                finish()
            }
        }
        setFragment(universityID)

//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.fcvMain, UniversityListFragment.getInstance())
//            .commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        _miNewUniversity = menu?.findItem(R.id.miNewUniversity)
        _miUpdateUniversity = menu?.findItem(R.id.miUpdateUniversity)
        _miDeleteUniversity = menu?.findItem(R.id.miDeleteUniversity)

        _miNewGroup = menu?.findItem(R.id.miNewGroup)
        _miUpdateGroup = menu?.findItem(R.id.miUpdateGroup)
        _miDeleteGroup = menu?.findItem(R.id.miDeleteGroup)
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
            R.id.miNewGroup -> {
                val fedit : Edit = GroupsFragment.getInstance()
                fedit.append()
                true
            }
            R.id.miUpdateGroup -> {
                val fedit : Edit = GroupsFragment.getInstance()
                fedit.update()
                true
            }
            R.id.miDeleteGroup -> {
                val fedit : Edit = GroupsFragment.getInstance()
                fedit.delete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

//    override fun onStop(){
//        DataRepository.getInstatnce().saveData()
//        super.onStop()
//    }

//    override fun onDestroy() {
//        DataRepository.getInstatnce().saveData()
//        super.onDestroy()
//    }

    override fun updateTitle(newTitle : String){
      title = newTitle
    }

    private var currentFragmentID = -1

    override fun setFragment(fragmentId: Int, student: Student?) {
        currentFragmentID = fragmentId
        when (fragmentId){
            universityID -> {setFragment(UniversityListFragment.getInstance())}
            facultyID -> {setFragment(FacultyListFragment.getInstance())}
            groupID -> {setFragment(GroupsFragment.getInstance())}
        }
    }

    private fun updateMenuView(){
        _miUpdateUniversity?.isVisible=currentFragmentID== universityID
        _miDeleteUniversity?.isVisible=currentFragmentID== universityID
        _miNewUniversity?.isVisible=currentFragmentID== universityID
        _miUpdateGroup?.isVisible=currentFragmentID== groupID
        _miDeleteGroup?.isVisible=currentFragmentID== groupID
        _miNewGroup?.isVisible=currentFragmentID== groupID
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcvMain, fragment)
            .addToBackStack(null)
            .commit()
        updateMenuView()
    }


}