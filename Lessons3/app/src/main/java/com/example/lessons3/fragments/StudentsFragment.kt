package com.example.lessons3.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lessons3.R

class StudentsFragment : Fragment() {
    private lateinit var group: Group
    companion object {
        fun newInstance(group: Group): StudentsFragment {
            return StudentsFragment().apply { this.group = group }
        }
    }

    private lateinit var viewModel: FacultyListViewModel

    private lateinit var _binding: FragmentStudentsBinding
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentsBinding.inflate(inflater, container, false)
        binding.rvStudents.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)
        viewModel.set_Group(group)
        viewModel.studentList.observe(viewLifecycleOwner) {
            binding.rvStudents.adapter = StudentAdapter(it)
        }
        binding.fabStudents.setOnClickListener {
            editStudent(Student().apply { groupID = viewModel.group!!.id })
        }
    }

    private fun deleteDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление")
            .setMessage("Вы действительно хотите удалить студента ${viewModel.student?.shortName ?: ""}?")
            .setPositiveButton("Да") { _, _ ->
                viewModel.deleteStudent()
            }
            .setNegativeButton("Нет", null)
            .setCancelable(true)
            .create()
            .show()
    }

    private fun editStudent(student: Student) {
        (requireActivity() as ActivityInterface).setFragment(MainActivity.studentID, student)
        (requireActivity() as ActivityInterface).updateTitle("Группа ${viewModel.group!!.name}")
    }

    private inner class StudentAdapter(private val items: List<Student>): RecyclerView.Adapter<StudentAdapter.ItemHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): StudentAdapter.ItemHolder {
            TODO("Not yet implemented")
        }
    }
}