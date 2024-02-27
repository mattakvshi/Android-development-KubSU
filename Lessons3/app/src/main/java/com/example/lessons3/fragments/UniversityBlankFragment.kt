package com.example.lessons3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lessons3.R

class UniversityBlankFragment : Fragment() {

    companion object {
        fun newInstance() = UniversityBlankFragment()
    }

    private lateinit var viewModel: UniversityBlankViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_university_blank, container, false)
    }
    

}