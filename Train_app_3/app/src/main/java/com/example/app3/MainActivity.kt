package com.example.app3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.app3.data.Student
import com.example.app3.fragments.FacultyListFragment
import com.example.app3.fragments.GroupsFragment
import com.example.app3.fragments.StudentFragment
import com.example.app3.fragments.UniversityListFragment
import com.example.app3.repository.DataRepository

class MainActivity : AppCompatActivity(), ActivityInterface {
    private var _miNewUniversity: MenuItem? = null
    private var _miUpdateUniversity: MenuItem? = null
    private var _miDeleteUniversity: MenuItem? = null
    private var _miNewFaculty: MenuItem? = null
    private var _miUpdateFaculty: MenuItem? = null
    private var _miDeleteFaculty: MenuItem? = null
    private var _miNewGroup: MenuItem? = null
    private var _miUpdateGroup: MenuItem? = null
    private var _miDeleteGroup: MenuItem? = null

    interface Edit {
        fun append()
        fun update()
        fun delete()
    }

    companion object {
        const val universityId = 0
        const val facultyId = 1
        const val groupId = 2
        const val studentId = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onBackPressedDispatcher.addCallback(this) {
            onBack()
        }
        setFragment(universityId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        _miNewUniversity = menu?.findItem(R.id.miNewUniversity)
        _miUpdateUniversity = menu?.findItem(R.id.miUpdateUniversity)
        _miDeleteUniversity = menu?.findItem(R.id.miDeleteUniversity)
        _miNewFaculty = menu?.findItem(R.id.miNewFaculty)
        _miUpdateFaculty = menu?.findItem(R.id.miUpdateFaculty)
        _miDeleteFaculty = menu?.findItem(R.id.miDeleteFaculty)
        _miNewGroup = menu?.findItem(R.id.miNewGroup)
        _miUpdateGroup = menu?.findItem(R.id.miUpdateGroup)
        _miDeleteGroup = menu?.findItem(R.id.miDeleteGroup)

        updateMenuView()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miNewUniversity -> {
                val fedit: Edit = UniversityListFragment.getInstance()
                fedit.append()
                true
            }
            R.id.miUpdateUniversity -> {
                val fedit: Edit = UniversityListFragment.getInstance()
                fedit.update()
                true
            }
            R.id.miDeleteUniversity -> {
                val fedit: Edit = UniversityListFragment.getInstance()
                fedit.delete()
                true
            }
            R.id.miNewFaculty -> {
                val fedit: Edit = FacultyListFragment.getInstance()
                fedit.append()
                true
            }
            R.id.miUpdateFaculty -> {
                val fedit: Edit = FacultyListFragment.getInstance()
                fedit.update()
                true
            }
            R.id.miDeleteFaculty -> {
                val fedit: Edit = FacultyListFragment.getInstance()
                fedit.delete()
                true
            }
            R.id.miNewGroup -> {
                val fedit: Edit = GroupsFragment.getInstance()
                fedit.append()
                true
            }
            R.id.miUpdateGroup -> {
                val fedit: Edit = GroupsFragment.getInstance()
                fedit.update()
                true
            }
            R.id.miDeleteGroup -> {
                val fedit: Edit = GroupsFragment.getInstance()
                fedit.delete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*override fun onStop() {
        DataRepository.getInstance().saveData()
        super.onStop()
    }*/

    override fun updateTitle(newTitle: String) {
        title = newTitle
    }

    private var currentFragmentId = -1

    override fun setFragment(fragmentId: Int, student: Student?) {
        currentFragmentId = fragmentId
        when (fragmentId) {
            universityId -> {setFragment(UniversityListFragment.getInstance())}
            facultyId -> {setFragment(FacultyListFragment.getInstance())}
            groupId -> {setFragment(GroupsFragment.getInstance())}
            studentId -> {setFragment(StudentFragment.newInstance(
                DataRepository.getInstance().group.value!!, student))}
        }
        updateMenuView()
    }

    override fun onBack() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            when (currentFragmentId) {
                universityId -> {
                    finish()
                }
                facultyId -> {
                    currentFragmentId = universityId
                    updateTitle("Список университетов")
                }
                groupId -> {
                    currentFragmentId = facultyId
                    updateTitle("Факультеты ${DataRepository.getInstance().university.value!!.name}")
                }
                studentId -> {
                    currentFragmentId = groupId
                    updateTitle("Факультет ${DataRepository.getInstance().faculty.value!!.name}")
                }
                else -> {}
            }
            updateMenuView()
        }
        else {
            finish()
        }
    }

    private fun updateMenuView() {
        _miUpdateUniversity?.isVisible = currentFragmentId == universityId
        _miDeleteUniversity?.isVisible = currentFragmentId == universityId
        _miNewUniversity?.isVisible = currentFragmentId == universityId
        _miUpdateFaculty?.isVisible = currentFragmentId == facultyId
        _miDeleteFaculty?.isVisible = currentFragmentId == facultyId
        _miNewFaculty?.isVisible = currentFragmentId == facultyId
        _miNewGroup?.isVisible = currentFragmentId == groupId
        _miUpdateGroup?.isVisible = currentFragmentId == groupId
        _miDeleteGroup?.isVisible = currentFragmentId == groupId
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcvMain, fragment)
            .addToBackStack(null)
            .commit()
        updateMenuView()
    }
}