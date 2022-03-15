package com.developer.onboardingfinalprojecthcl

import android.os.Parcelable
import java.io.Serializable

/*
class User: Serializable {
    var id = Int
    lateinit var name: String
    lateinit var address: String
    lateinit var email: String
    var imageURL = Int

    fun returnName(): String {
        return name
    }
}
*/


class User (
    var id: String?,
    var name: String?,
    var email: String?,
    var imageURL: String?,
    var address: Address?,
    var address_String: String?
    )
