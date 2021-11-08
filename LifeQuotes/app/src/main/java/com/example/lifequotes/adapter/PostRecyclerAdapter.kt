package com.example.lifequotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifequotes.R
import com.example.lifequotes.model.Post
import com.example.lifequotes.model.ProfilePics
import com.example.lifequotes.model.UserProfilePic
import com.example.lifequotes.util.generateUserId
import com.example.lifequotes.util.generateUserImage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

//create adapter for the recycle view that will hold fetched posts
class PostRecyclerAdapter(val listener: CommentLayoutClickEvent): RecyclerView.Adapter<PostRecyclerAdapter.PostViewHolder>() {
    //
    var post: MutableList<Post> = mutableListOf()
    private val profilePic:List<UserProfilePic> = ProfilePics.list

    //create view holder and initialize variables that will hold view
    inner class PostViewHolder(postView: View): RecyclerView.ViewHolder(postView),
        View.OnClickListener {
        val userId: TextView = postView.findViewById(R.id.tv_post_userId)
        val postId: TextView = postView.findViewById(R.id.tv_post_id)
        val postTitle: TextView = postView.findViewById(R.id.tv_post_title)
        val postBody: TextView = postView.findViewById(R.id.tv_post_body)
        val images: ImageView = postView.findViewById(R.id.rv_userImage)
       private val commentBody: LinearLayout = postView.findViewById(R.id.rv_commentLayout)

        //set listener to the comment tab
        init {
            commentBody.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            val id = post[position].id

            val title = post[position].title
            val postBody = post[position].body
            listener.onClickOfCommentLayout( id, title, postBody)
        }
    }

        interface CommentLayoutClickEvent {
            fun onClickOfCommentLayout(id: Int, title: String, postBody: String)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_recycler_view, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postPos = post[position]
        holder.apply {
            userId.text = postPos.userId.toString()
            postId.text = postPos.id.toString()
            postTitle.text = postPos.title
            postBody.text = postPos.body
        }

        Picasso.get()
            .load(profilePic[generateUserImage(position)].image)
            .into(holder.images)
    }



    override fun getItemCount(): Int {
        return post.size
    }

    fun setPostData(userPost: List<Post>){
        post.clear()
        post.addAll(userPost)
        notifyDataSetChanged()
    }

    fun addNewPost(userPost:Post){
        post.add(0, userPost)
        notifyDataSetChanged()
    }
}