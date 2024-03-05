package com.example.lessons3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lessons3.R
import com.example.lessons3.data.University
import com.example.lessons3.databinding.FragmentUniversityListBinding
import java.lang.Exception
import java.lang.reflect.Executable

class UniversityListFragment : Fragment() {

    companion object {
        private var INSTANCE : UniversityListFragment? = null
        fun getInstance() : UniversityListFragment{
            if (INSTANCE==null) INSTANCE= UniversityListFragment()
            return INSTANCE ?: throw Exception("UniversityListFragment не создан")
        }
    }

    private lateinit var viewModel: UniversityListViewModel

    private lateinit var _binding : FragmentUniversityListBinding
    val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_university_list, container, false)
        _binding = FragmentUniversityListBinding.inflate(inflater, container, false)

        binding.rvUniversity.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UniversityListViewModel::class.java)
        viewModel.universityList.observe(viewLifecycleOwner){
            binding.rvUniversity.adapter=UniversityAdapter(it!!.items)
        }
    }

    private inner class UniversityAdapter(private val items: List<University>)
        : RecyclerView.Adapter<UniversityAdapter.ItemHolder>(){

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): UniversityAdapter.ItemHolder {
            val view = layoutInflater.inflate(R.layout.element_university_list, parent, false)
            return ItemHolder(view)
        }

        override fun getItemCount(): Int = items.size
        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            holder.bind(viewModel.universityList.value!!.items[position])
        }

        private inner class ItemHolder(view: View)
            : RecyclerView.ViewHolder(view) {
                private lateinit var university: University

                fun bind(university: University){
                    this.university=university
                    val tv = itemView.findViewById<TextView>(R.id.tvUniversity)
                    tv.text = university.name
                    val tvc = itemView.findViewById<TextView>(R.id.tvCity)
                    tvc.text = university.city
                }
            }
    }

}