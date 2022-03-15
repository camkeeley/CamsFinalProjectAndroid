package com.developer.onboardingfinalprojecthcl

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView

const val CHANNEL_ID = "15"
//lateinit var prevIntent: Intent
open class NavigationPane : AppCompatActivity() {


    private lateinit var mToggle:ActionBarDrawerToggle
    fun onCreateDrawer(mDrawerLayout: DrawerLayout) {


        //toggle = ActionBarDrawerToggle(this, drawerLayout)

        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        val headerView = navigationView.getHeaderView(0)

        //val mDrawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)

        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            mDrawerLayout.closeDrawers()

            when( menuItem.itemId ) {
                R.id.signOut ->
                {
                    val signOutIntent = Intent(this, SignInActivity::class.java)
                    signOutIntent.putExtra( SIGN_OUT, true)
                    println("DOG")
                    startActivity( signOutIntent )
                }
                
            }

            true
        }

    }


    override fun onStop() {
        super.onStop()
        //sendNotification()
    }





    override fun onOptionsItemSelected( item: MenuItem): Boolean {
        if( mToggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}
