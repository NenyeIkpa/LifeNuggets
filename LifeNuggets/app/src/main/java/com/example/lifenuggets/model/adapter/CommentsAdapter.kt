package com.example.lifenuggets.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifenuggets.R
import com.example.lifenuggets.model.data.CommentResponse

class CommentsAdapter: RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    private var comment: ArrayList<CommentResponse> = arrayListOf()

    inner class CommentsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.tv_comment_id)
        val postId: TextView = view.findViewById(R.id.tv_comment_postId)
        val name: TextView = view.findViewById(R.id.tv_comment_name)
        val body: TextView = view.findViewById(R.id.tv_comment_body)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_comments, parent, false)
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val commentPos = comment[position]
        holder.apply {
            postId.text = commentPos.postId.toString()
            name.text = commentPos.name
            body.text = commentPos.body
            id.text = commentPos.id.toString()
        }
    }

    override fun getItemCount(): Int {
        return comment.size
    }

    fun setComments(comments: List<CommentResponse>) {
        comment.addAll(comments)
        notifyDataSetChanged()

    }

    fun addNewComment(newComment: CommentResponse){
        comment.add(newComment)
        notifyDataSetChanged()
    }
}