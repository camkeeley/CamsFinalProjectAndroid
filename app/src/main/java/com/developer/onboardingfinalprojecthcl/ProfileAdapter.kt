package com.developer.onboardingfinalprojecthcl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ProfileAdapter(private val onClick: (User) -> Unit):
    ListAdapter<User, ProfileAdapter.ProfileViewHolder>(ProfileDiffCallback){

    class ProfileViewHolder(itemView: View, val onClick: (User) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val profileImageView: ImageView = itemView.findViewById<ImageView>(R.id.profileImage)
        private val profileName: TextView = itemView.findViewById<TextView>(R.id.name)
        private val profileEmail: TextView = itemView.findViewById<TextView>(R.id.email)
        private val profileImage: ImageView = itemView.findViewById<ImageView>(R.id.profileImage)
        private val profileImageUrl: TextView = itemView.findViewById<TextView>(R.id.imageURL)
        private val profileAddress: TextView = itemView.findViewById<TextView>(R.id.address)

        private var currentProfile: User? = null
        init {
            itemView.setOnClickListener {
                currentProfile?.let {
                    onClick(it)
                }
            }
        }

        fun bind( profile: User ) {
            currentProfile = profile

            if(profile.id == user.id) {
                println("USERNAME " + profile.name)
            }

            profileName.text = profile.name
            profileEmail.text = profile.email
            //profileAddress.text = profile.address.street

            val url = profile.imageURL


            profileImageUrl.text = url
            Picasso.get()
                .load(url)
                .resize(250, 250)
                .centerCrop()
                .into(profileImage)

            /*
            if( profile.image != null )
            {
                profileImageView.setImageResource( profile.image )

            }

             */
        }

        fun addUser( user: User )
        {
            bind(user)
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): ProfileViewHolder {
        val view = LayoutInflater.from( parent.context )
            .inflate(R.layout.activity_card_view, parent, false)
        return ProfileViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profile = getItem(position)
        holder.bind(profile)
    }





}

object ProfileDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.email == newItem.email
    }
}