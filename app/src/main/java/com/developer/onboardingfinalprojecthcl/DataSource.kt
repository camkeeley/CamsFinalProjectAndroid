package com.developer.onboardingfinalprojecthcl

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.ArrayList

class DataSource(resources: Resources) {

    private val initialProfileList = profileList(resources)
    //var profile = Profiles()
    //private val initialProfileList = profile.getProfiles()

    private val profilesLiveData = initialProfileList

    fun getProfileList(): MutableLiveData<ArrayList<User>>? {
        return profilesLiveData
    }

    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource( resources: Resources): DataSource {
            return synchronized((DataSource::class)) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}