package com.example.lifenuggets.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifenuggets.R
import com.example.lifenuggets.model.data.ListOfProfilePics
import com.example.lifenuggets.model.data.PostResponse
import com.example.lifenuggets.model.data.UserProfilePic
import com.squareup.picasso.Picasso

class UserPostAdapter(val listener: CommentLayoutClickEvent) : RecyclerView.Adapter<UserPostAdapter.UserPostViewHolder>() {
    var post: ArrayList<PostResponse> = arrayListOf()
    val profilePic:List<UserProfilePic> = ListOfProfilePics.list

    inner class UserPostViewHolder(postView: View): RecyclerView.ViewHolder(postView),
        View.OnClickListener {
        val userId: TextView = postView.findViewById(R.id.tv_post_userId)
        val postId: TextView = postView.findViewById(R.id.tv_post_id)
        val postTitle: TextView = postView.findViewById(R.id.tv_post_title)
        val postBody: TextView = postView.findViewById(R.id.tv_post_body)
        val images:ImageView = postView.findViewById(R.id.rv_userImage)
        val commentBody:LinearLayout = postView.findViewById(R.id.rv_commentLayout)

        init {
            commentBody.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            listener.onClickOfCommentLayout(position)
        }
    }
    interface CommentLayoutClickEvent{
        fun onClickOfCommentLayout(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_user_post, parent, false)
        return UserPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserPostViewHolder, position: Int) {
        val postPos = post[position]
        holder.apply {
            userId.text = postPos.userId.toString()
            postId.text = postPos.id.toString()
            postTitle.text = postPos.title
            postBody.text = postPos.body
        }
        val rnds = (0..8).random()
        Picasso.get()
            .load(profilePic[rnds].image)
            .into(holder.images)
    }

    override fun getItemCount(): Int {
      return post.count()
    }

    fun setUserPostData(userPost: List<PostResponse>){
        post.addAll(userPost)
        notifyDataSetChanged()
    }

    fun addNewPost(newPost: PostResponse){
        post.add(newPost)
        notifyDataSetChanged()
    }
}