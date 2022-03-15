package com.developer.onboardingfinalprojecthcl;

import android.content.res.Resources;
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData

lateinit var userList: List<User>

fun profileList(resources:Resources): MutableLiveData<ArrayList<User>>{

    var feed: Feed?
    val get = GetData()

    return get.retrieveData()
    //return userList




}
/*
    fun setProfiles( userData: List<User> )
    {
        userList = userData
    }

    fun getProfiles(): List<User>
    {
        return userList
    }

}

*/