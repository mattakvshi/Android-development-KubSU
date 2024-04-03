package com.example.lessons3.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lessons3.ActivityInterface
import com.example.lessons3.R
import com.example.lessons3.data.Faculty
import com.example.lessons3.data.University
import com.example.lessons3.databinding.FragmentFacultyListBinding
import com.example.lessons3.databinding.FragmentUniversityListBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class FacultyListFragment : Fragment() {

    companion object {
        private var INSTANCE : FacultyListFragment? = null
        fun getInstance() : FacultyListFragment{
            if (INSTANCE==null) INSTANCE= FacultyListFragment()
            return INSTANCE ?: throw Exception("FacultyListFragment не создан")
        }
    }

    private lateinit var viewModel: FacultyListViewModel

    private lateinit var _binding : FragmentFacultyListBinding
    val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_faculty_list, container, false)
        _binding = FragmentFacultyListBinding.inflate(inflater, container, false)

        binding.rvFacultyList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        

        viewModel.facultyList.observe(viewLifecycleOwner){
            binding.rvFacultyList.adapter=FacultyAdapter(it)
        }
        binding.fabAppend.setOnClickListener{
            newFaculty()
        }
    }


    private inner class FacultyAdapter(private val items: List<Faculty>)
        : RecyclerView.Adapter<FacultyAdapter.ItemHolder>(){

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): FacultyAdapter.ItemHolder {
            val view = layoutInflater.inflate(R.layout.element_faculty_list, parent, false)
            return ItemHolder(view)
        }

        override fun getItemCount(): Int = items.size
        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            holder.bind(viewModel.facultyList.value!![position])
        }


        private var lastView : View? =null
        private fun updateCurrentView(view : View){
            val ll = lastView?.findViewById<LinearLayout>(R.id.llButtons)
            ll?.visibility=View.INVISIBLE
            ll?.layoutParams=ll?.layoutParams.apply{this?.width=1}

            lastView?.findViewById<ConstraintLayout>(R.id.clFacultyElement)?.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.white)
            )
            view.findViewById<ConstraintLayout>(R.id.clFacultyElement).setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.mu_blue)
            )
            lastView=view
        }

        private inner class ItemHolder(view: View)
            : RecyclerView.ViewHolder(view) {
            private lateinit var faculty: Faculty

            fun bind(faculty: Faculty){
                if(faculty==viewModel.faculty)
                    updateCurrentView(itemView)
                this.faculty=faculty
                val tv = itemView.findViewById<TextView>(R.id.tvName)
                tv.text = faculty.name


                val c_l = itemView.findViewById<ConstraintLayout>(R.id.clFacultyElement)

                itemView.findViewById<ImageButton>(R.id.ibEdit).setOnClickListener{
                    updateFaculty()
                }

                itemView.findViewById<ImageButton>(R.id.ibDelete).setOnClickListener{
                    deleteFaculty()
                }


                val llb = itemView.findViewById<LinearLayout>(R.id.llButtons)
                llb.visibility=View.INVISIBLE
                llb?.layoutParams=llb?.layoutParams.apply { this?.width=1 }

                 val cl =  View.OnClickListener{
                    viewModel.setCurrentFaculty(faculty)
                    updateCurrentView(itemView)
                }

                c_l.setOnClickListener(cl)
                tv.setOnClickListener (cl)

                val lcl = View.OnLongClickListener{
                    c_l.callOnClick()
                    llb.visibility=View. VISIBLE
                    MainScope().launch {
                        val lp = llb?.layoutParams
                        lp?.width = 1

                        while (lp?.width!!<350){
                            lp?.width = lp?.width!! + 35
                            llb?.layoutParams = lp
                            delay(50)
                        }
                    }
                    true
                }

                c_l.setOnLongClickListener(lcl)
                tv.setOnLongClickListener(lcl)

            }
        }
    }

    private fun newFaculty() {
        val mDialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_university_edit, null)
        val inputName = mDialogView.findViewById<EditText>(R.id.edUniversityName)

        mDialogView.findViewById<EditText>(R.id.edUniversityCity).visibility=View.GONE
        mDialogView.findViewById<TextView>(R.id.tv_City).visibility=View.GONE

        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Информация о факультете")
            .setView(mDialogView)
            .setPositiveButton("добавить") { _, _ ->
                if (inputName.text.isNotBlank() ) {
                    viewModel.appendFaculty(inputName.text.toString())
                }
            }
            .setNegativeButton( "отмена", null)
            .setCancelable(true)
            .create()
            .show()
    }

    private fun updateFaculty() {
        val mDialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_university_edit, null)
        val inputName = mDialogView.findViewById<EditText>(R.id.edUniversityName)

        mDialogView.findViewById<EditText>(R.id.edUniversityCity).visibility=View.GONE
        mDialogView.findViewById<TextView>(R.id.tv_City).visibility=View.GONE

        inputName.setText(viewModel.faculty?.name)

        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Информация о факультете")
            .setView(mDialogView)
            .setPositiveButton("изменить") { _, _ ->
                if (inputName.text.isNotBlank() ) {
                    viewModel.updateFaculty(inputName.text.toString())
                }
            }
            .setNegativeButton( "отмена", null)
            .setCancelable(true)
            .create()
            .show()
    }

    private fun deleteFaculty() {
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление!")
            .setMessage("Вы действительно хотите удалить факультет ${viewModel.faculty?.name ?: ""}?")
            .setPositiveButton("ДА"){ _, _ ->
                viewModel.deleteFaculty()
            }
            .setNegativeButton("НЕТ", null)
            .setCancelable(true)
            .create()
            .show()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(FacultyListViewModel::class.java)
        (context as ActivityInterface).updateTitle("Факультеты ${viewModel.university?.name}")
    }

}