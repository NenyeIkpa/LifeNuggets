package com.example.lifenuggets.model.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifenuggets.R
import com.example.lifenuggets.model.data.ListOfProfilePics
import com.example.lifenuggets.model.ResponseItem
import com.example.lifenuggets.model.data.UserProfilePic
import com.squareup.picasso.Picasso

class RVAdapter(var userClick: OnClickUser): RecyclerView.Adapter<RVAdapter.LifeUserViewHolder>() {

    private var users: ArrayList<ResponseItem>  = ArrayList()
    val profilePic:List<UserProfilePic> = ListOfProfilePics.list

    inner class LifeUserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val userProfilePic: ImageView = itemView.findViewById(R.id.userPageImage)
        val id: TextView = itemView.findViewById(R.id.rv_tv_id)
        val name: TextView = itemView.findViewById(R.id.rv_tv_name)
        val username: TextView = itemView.findViewById(R.id.rv_tv_username)

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val pos = adapterPosition
            var id = users[pos].id
            if (pos != RecyclerView.NO_POSITION) {
                userClick.onClick(id)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LifeUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_recycler, parent, false)
        return LifeUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: LifeUserViewHolder, position: Int) {
        val viewPos = users[position]

        Log.d("CHECKINGOUT", "GETS CALLED $position times")
        holder.apply {
            id.text = viewPos.id.toString()
            name.text = viewPos.name
            username.text = viewPos.username

        }

        Picasso.get()
            .load(profilePic[position].image)
            .into(holder.userProfilePic)
    }

    override fun getItemCount(): Int {
        Log.d("CHECKINGOUT", "USERS COUNT ${users.count()}")
        return users.size
    }

    fun setData(userList: ArrayList<ResponseItem>) {
        users = userList
        notifyDataSetChanged()
    }
}