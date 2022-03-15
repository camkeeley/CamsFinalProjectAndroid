package com.developer.onboardingfinalprojecthcl

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

const val SIGN_OUT = "sign out"
val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    .requestEmail()
    .build()

lateinit var mGoogleSignInClient: GoogleSignInClient

class SignInActivity : AppCompatActivity() {



    val RC_SIGN_IN = 9001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        /*
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)
        */

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<SignInButton>(R.id.signInBtn).setOnClickListener{ signIn() }


    }

    override fun onResume() {
        super.onResume()

        if( intent.getBooleanExtra(SIGN_OUT, false) ) {
            signOut()
        }
        else {
            val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
            updateUI( account )
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            println("SUCCESS")
            // Signed in successfully, show authenticated UI.
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(ContentValues.TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    fun signIn()
    {
        val intent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult( intent, RC_SIGN_IN)
    }

    fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)

            }
        println("SIGNED OUT")
    }

    fun updateUI( account: GoogleSignInAccount?)
    {
        if( account != null )
        {
            val intent = Intent(this, AllProfiles::class.java )
            startActivity( intent )
        }
    }
}