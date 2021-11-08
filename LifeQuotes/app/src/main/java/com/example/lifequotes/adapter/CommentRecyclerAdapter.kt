package com.example.lifequotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifequotes.R
import com.example.lifequotes.model.Comment
import com.example.lifequotes.model.Post

class CommentRecyclerAdapter: RecyclerView.Adapter<CommentRecyclerAdapter.CommentsViewHolder>() {

    private var comment: MutableList<Comment> = mutableListOf()

    class CommentsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.tv_comment_id)
        val postId: TextView = view.findViewById(R.id.tv_comment_postId)
        val name: TextView = view.findViewById(R.id.tv_comment_name)
        val body: TextView = view.findViewById(R.id.tv_comment_body)


        fun bind(com: Comment) {
            postId.text = com.postId.toString()
            name.text = com.name
            body.text = com.body
            id.text = com.id.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_recycler_view, parent, false)
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val commentPos = comment[position]
        holder.bind(commentPos)
    }

    override fun getItemCount(): Int {
        return comment.size
    }

    fun setComments(comments: List<Comment>) {
        comment.addAll(comments)
        notifyDataSetChanged()

    }

    fun addNewComment(userComment: Comment){
        comment.add(userComment)
        notifyDataSetChanged()
    }
}