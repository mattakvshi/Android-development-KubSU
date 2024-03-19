package com.example.lessons3.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lessons3.R
import com.example.lessons3.databinding.FragmentFacultyListBinding
import com.example.lessons3.databinding.FragmentUniversityListBinding
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
}