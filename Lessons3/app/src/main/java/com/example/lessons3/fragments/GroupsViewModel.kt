package com.example.lessons3.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lessons3.data.Group
import com.example.lessons3.repository.DataRepository

class GroupsViewModel : ViewModel() {
    var groupList: MutableLiveData<List<Group>> = MutableLiveData()
    private var _group: Group? = null
    val group get() = _group

    init {
        DataRepository.getInstance().listOfGroup.observeForever {
            groupList.postValue(
                it.filter { it.facultyID == DataRepository.getInstance().faculty.value?.id }.sortedBy { it.name } as MutableList<Group>
            )
        }

        DataRepository.getInstance().group.observeForever {
            _group = it
        }

        DataRepository.getInstance().faculty.observeForever {
            groupList.postValue(
                DataRepository.getInstance().listOfGroup.value?.filter
                { it.facultyID == DataRepository.getInstance().faculty.value?.id }?.sortedBy { it.name } as MutableList<Group>
            )
        }
    }

    fun deleteGroup() {
        if (group != null)
            DataRepository.getInstance().deleteGroup(group!!)
    }

    val faculty get() = DataRepository.getInstance().faculty.value

    fun appendGroup(groupName: String) {
        val group = Group()
        group.name = groupName
        group.facultyID = faculty?.id
        DataRepository.getInstance().addGroup(group)
    }

    fun updateGroup(groupName: String) {
        if (_group != null) {
            _group!!.name = groupName
            DataRepository.getInstance().updateGroup(_group!!)
        }
    }

    fun setCurrentGroup(position: Int) {
        if ((groupList.value?.size ?: 0) > position)
            groupList.value?.let { DataRepository.getInstance().setCurrentGroup(it.get(position)) }
    }

    fun setCurrentGroup(group: Group) {
        DataRepository.getInstance().setCurrentGroup(group)
    }

    val getGroupListPosition get() = groupList.value?.indexOfFirst { it.id == group?.id } ?: -1
}