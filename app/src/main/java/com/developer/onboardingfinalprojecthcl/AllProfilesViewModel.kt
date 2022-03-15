package com.developer.onboardingfinalprojecthcl

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.ArrayList

class AllProfilesViewModel(val dataSource: DataSource) : ViewModel() {

    val profilesLiveData = dataSource.getProfileList()

    fun addUser(user: User){
        val data = profilesLiveData?.value
        data?.add(user)
        profilesLiveData?.postValue(data)
    }
}

class AllProfilesViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllProfilesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AllProfilesViewModel(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}