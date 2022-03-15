package com.developer.onboardingfinalprojecthcl

import android.app.NotificationManager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech.STOPPED
import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

const val PROFILE_NAME = "profile name"
const val PROFILE_EMAIL = "profile email"
const val PROFILE_ID = "profile id"
const val PROFILE_IMAGE_URL = "profile image url"
const val ADDRESS_STREET = "street address"
const val ADDRESS_CITY = "city address"
const val ADDRESS_STRING = "address string"


lateinit var lastActivity : String

const val IMAGE = "image"
lateinit var user: GoogleSignInAccount
var userObject = User(id = "", name = "", email = "", imageURL = null, address = null, address_String = "")

class AllProfiles : NavigationPane(), LifecycleObserver {

    private val profilesListViewModel by viewModels<AllProfilesViewModel> {
        AllProfilesViewModelFactory( this )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //prevIntent = intent
        lastActivity = "allProfiles"

        setContentView(R.layout.activity_allprofiles)

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        val mDrawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        onCreateDrawer( mDrawerLayout )

        user = GoogleSignIn.getLastSignedInAccount(this)!!

        userObject.id = user?.id.toString()
        userObject.email = user?.email.toString()
        userObject.address = Address(city = "", street = "")
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val profileAdapter = ProfileAdapter { profile -> adapterOnClick(profile) }



        recyclerView.apply {
            layoutManager = GridLayoutManager( applicationContext, 3 )
            recyclerView.adapter = profileAdapter

        }


        /*
        class User (
            val id: String,
            val name: String,
            val email: String,
            var imageURL: String,
            val address: Address
        */



        //profilesListViewModel.addUser( user )


        //val data2 = profilesListViewModel.profilesLiveData?.value

        profilesListViewModel.profilesLiveData?.observe( this, {
            it?.let {
                profileAdapter.submitList(it as MutableList<User>)

            }
        })


    }


    fun sendData() {


    }

    override fun onResume() {
        super.onResume()

        user = GoogleSignIn.getLastSignedInAccount(this)!!

        println("UserName " + userObject.name)
        lastActivity = "allProfiles"

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val profileAdapter = ProfileAdapter { profile -> adapterOnClick(profile) }



        recyclerView.apply {
            layoutManager = GridLayoutManager( applicationContext, 3 )
            recyclerView.adapter = profileAdapter

        }


        sendData()
        /*
        class User (
            val id: String,
            val name: String,
            val email: String,
            var imageURL: String,
            val address: Address
        */



        //profilesListViewModel.addUser( user )

        //val data2 = profilesListViewModel.profilesLiveData?.value

        profilesListViewModel.profilesLiveData?.observe( this, {
            it?.let {
                profileAdapter.submitList(it as MutableList<User>)

            }
        })
    }


        private fun adapterOnClick(profile: User) {

        val intent = Intent(this, ProfileDetailActivity::class.java)
        //profileData = intent?.getParcelableExtra<User>(profile)
        //val bundle = Bundle()
        //bundle.putSerializable("PROFILE", profile)
        intent.putExtra(PROFILE_NAME, profile.name)
        intent.putExtra(PROFILE_EMAIL, profile.email)
        intent.putExtra(PROFILE_ID, profile.id)
        intent.putExtra(PROFILE_IMAGE_URL, profile.imageURL)
        intent.putExtra(ADDRESS_STREET, profile.address?.street)
        intent.putExtra(ADDRESS_CITY, profile.address?.city)
        intent.putExtra(ADDRESS_STRING, profile.address?.street + ", " + profile.address?.city)


        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()



        if (user != null) {
            val personName = user.displayName
            val personGivenName = user.givenName
            val personFamilyName = user.familyName
            val personEmail = user.email
            val personId = user.id
            val personPhoto: Uri? = user.photoUrl
        }



    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppClose() {

        println(lastActivity)

        if( lastActivity.equals( "allProfiles") ) {

            val intent = Intent( this, AllProfiles::class.java)
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notifications = NotificationBuilder()
            notifications.makeNotification( intent, this, notificationManager )
        }



    }
    override fun onStop() {
        super.onStop()


        /*
        if( !ProcessLifecycleOwner.get().lifecycle.currentState.equals(Lifecycle.State.STARTED)  ) {

            println(ProcessLifecycleOwner.get().lifecycle.currentState.toString())

            val intent = Intent(this, AllProfiles::class.java)
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notifications = NotificationBuilder()
            notifications.makeNotification( intent, this, notificationManager )


        }
         */




    }

    override fun onDestroy() {
        println("Destroyed!")
        super.onDestroy()


    }

}

//Client ID: 547057850921-b8780t8rt1ehn4smtdhhtn3vqtabgokq.apps.googleusercontent.com
//CLient secret: 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
