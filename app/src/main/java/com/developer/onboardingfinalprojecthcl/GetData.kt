package com.developer.onboardingfinalprojecthcl

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class GetData: AppCompatActivity() {



    lateinit var input: ArrayList<User>
    lateinit var userList: List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //fetchJson()

    }

    /*

     fun retrieveData(): List<User>? {
        lateinit var userList: List<User>
        GlobalScope.launch {
            fetchData()
            userList = fetchData()
        }
        return userList

    }

        suspend fun fetchData(): List<User> {

            val request = Request.Builder()
                .url("https://jsonplaceholder.typicode.com/users")
                .build()

            val client = OkHttpClient()
            client.newCall(request).execute().use {
                    response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                var body1 = response.body!!.toString()
                val gson = GsonBuilder().create()
                input = gson.fromJson(body1, Array<User>::class.java )


            }
            return input.toList()



        }
    */
    fun retrieveData(): MutableLiveData<ArrayList<User>> {

        val request = Request.Builder()
            .url("https://jsonplaceholder.typicode.com/users")
            .build()
        println("retriever called")
        val client = OkHttpClient()
        var liveData: MutableLiveData<ArrayList<User>> = MutableLiveData()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    "call failed"
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")

                        println("Test worked")
                        val body1 = response.body!!.string()
                        val gson = GsonBuilder().create()
                        input = gson.fromJson(body1, Array<User>::class.java).toCollection(ArrayList())
                        println("TEST SUCCEEDED!" + body1)

                        //val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this@GetData)
                        //val user = User(id = account?.id.toString(), name = "Your Account", email = account?.email.toString(), imageURL = null, address = null)

                        input.add(userObject)


                        for( each in input ) {

                            each.imageURL = getImageUrl()
                        }
                        //liveData = LiveData(input.toList())
                        liveData.postValue(input)

                        runOnUiThread {
                            //findViewById<TextView>(R.id.view_data).setText(body1)

                        }
                    }
                }

            })
        return liveData
    }

    fun getImageUrl(): String {
        val random: String = List(16) {
            ( ('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
        }.joinToString("")
        val url: String = "https://robohash.org/" + random

        return url

    }

    fun returnData(): List<User>
    {
        return input.toList()

    }
}












