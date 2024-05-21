package com.example.app3.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.app3.ActivityInterface
import com.example.app3.R
import com.example.app3.data.Group
import com.example.app3.data.Student
import com.example.app3.databinding.FragmentStudentBinding
import java.text.SimpleDateFormat
import java.util.Date


class StudentFragment : Fragment() {

    private lateinit var group: Group
    private var student: Student? = null
    private lateinit var sexArray: Array<String>

    companion object {
        fun newInstance(group: Group, student: Student?): StudentFragment {
            return StudentFragment().apply {
                this.group = group
                this.student = student
            }
        }
    }

    private lateinit var viewModel: StudentViewModel
    private lateinit var _binding: FragmentStudentBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentBinding.inflate(inflater, container, false)

        sexArray = resources.getStringArray(R.array.SEX)
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, sexArray)

        binding.spSex.adapter = adapter

        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        viewModel.set_Group(group)
        viewModel.setStudent(student)
        if (viewModel.student != null)
            loadForm()

        var sex: Int = 0
        binding.spSex.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {

                val selectedSex = parent.getItemAtPosition(position).toString()
                sex = when (selectedSex) {
                    getString(R.string.man) -> R.string.man
                    getString(R.string.woman) -> R.string.woman
                    else -> 0
                }

                Log.d("StudentFragment", sex.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        var birthDay: Date = Date()
        binding.cvBirthDate.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val customDate = dateFormat.parse("${year}-${month}-${dayOfMonth}")
            birthDay = customDate!!
            Log.d("StudentFragment", birthDay.toString())
        }

        binding.btnSave.setOnClickListener {
            if (viewModel.student == null){
                viewModel.appendStudent(binding.etLastName.text.toString(),
                    binding.etFirstName.text.toString(),
                    binding.etMiddleName.text.toString(),
                    birthDay!!,
                    binding.etPhone.text.toString(),
                    sex)
            }
            else {
                viewModel.updateStudent(binding.etLastName.text.toString(),
                    binding.etFirstName.text.toString(),
                    binding.etMiddleName.text.toString(),
                    birthDay!!,
                    binding.etPhone.text.toString(),
                    sex)
            }
            (requireActivity() as ActivityInterface).onBack()
        }

    }

    private fun loadForm(){
        binding.etLastName.setText(viewModel.student!!.lastName)
        binding.etFirstName.setText(viewModel.student!!.firstName)
        binding.etMiddleName.setText(viewModel.student!!.middleName)
        binding.etPhone.setText(viewModel.student!!.phone)
        val position = when (viewModel.student!!.sex){
            R.string.man -> 0
            R.string.woman -> 1
            else -> 0
        }
        binding.spSex.setSelection(position)
        binding.cvBirthDate.setDate(viewModel.student!!.birthDate.time)
        binding.btnSave.text = "Изменить"
    }


}