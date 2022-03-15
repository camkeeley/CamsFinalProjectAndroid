package com.developer.onboardingfinalprojecthcl

import android.provider.ContactsContract

class Feed( val users: List<User> ) {
    val userData = users
}



class Address( val street: String,
               val city: String)

class Geo( val lat: Float,
           val lng: Float )