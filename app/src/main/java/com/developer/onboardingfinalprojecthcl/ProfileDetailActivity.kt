package com.developer.onboardingfinalprojecthcl

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.squareup.picasso.Picasso

lateinit var new_intent: Intent
class ProfileDetailActivity : NavigationPane(), LifecycleObserver {

    lateinit var imgURL: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lastActivity = "profileDetails"


        setContentView(R.layout.activity_profile_detail)
        val mDrawerLayout: DrawerLayout = findViewById(R.id.drawer_layout_details)
        onCreateDrawer(mDrawerLayout)

        //val bData = intent.getSerializableExtra(PROFILE) as User


        imgURL = intent.getStringExtra(PROFILE_IMAGE_URL).toString()

        Picasso.get()
            .load(imgURL)
            .resize(500, 500)
            .centerCrop()
            .into(findViewById<ImageView>(R.id.detailImage))

        findViewById<TextView>(R.id.name).setText(intent.getStringExtra(PROFILE_NAME))
        findViewById<TextView>(R.id.email).setText(intent.getStringExtra(PROFILE_EMAIL))



        if( intent.getStringExtra(PROFILE_ID) == user.id )
        {
            allowEdits()
            findViewById<TextView>(R.id.address).setText(userObject.address_String)
            findViewById<TextView>(R.id.name).setText(userObject.name)


        }
        else {
            findViewById<TextView>(R.id.address).setText(intent.getStringExtra(ADDRESS_STRING))
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppClose() {


        println(ProcessLifecycleOwner.get().lifecycle.currentState.toString())

        if( lastActivity.equals("profileDetails" ) ) {
            val intent = new_intent


            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notifications = NotificationBuilder()
            notifications.makeNotification( intent, this, notificationManager )
        }



    }

    override fun onResume() {
        super.onResume()
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)

        new_intent = intent

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

    }
    override fun onStop() {
        super.onStop()




    }

    fun allowEdits()
    {
        val saveButton: Button = findViewById<Button>(R.id.saveButton)
        saveButton.visibility = View.VISIBLE
        findViewById<TextView>(R.id.name).inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        findViewById<TextView>(R.id.email).inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        findViewById<TextView>(R.id.address).inputType = InputType.TYPE_CLASS_TEXT

        saveButton.setOnClickListener{ makeChanges() }

    }

    fun makeChanges()
    {
        userObject.name = findViewById<TextView>(R.id.name).text.toString()
        userObject.email = findViewById<TextView>(R.id.email).text.toString()
        userObject.address_String = findViewById<TextView>(R.id.address).text.toString()

    }


}