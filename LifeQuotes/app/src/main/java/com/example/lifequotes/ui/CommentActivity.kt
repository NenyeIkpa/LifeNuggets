package com.example.lifequotes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.bind
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifequotes.R
import com.example.lifequotes.adapter.CommentRecyclerAdapter
import com.example.lifequotes.model.Comment
import com.example.lifequotes.model.MainViewModelFactory
import com.example.lifequotes.model.PostViewModel
import com.example.lifequotes.repository.Repository

class CommentActivity : AppCompatActivity() {

    private lateinit var postTitle: TextView
    private lateinit var post: TextView
    private lateinit var commentsRV: RecyclerView
    private lateinit var commentAdapter: CommentRecyclerAdapter
    private lateinit var viewModel: PostViewModel
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var addCommentButton: ImageButton
    private lateinit var addCommentName: EditText
    private lateinit var addCommentBody: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val repository = Repository()
        viewModelFactory = MainViewModelFactory(repository)

        val id = intent.getStringExtra("postId")
        val title = intent.getStringExtra("title")
        val postBody = intent.getStringExtra("postBody")

        postTitle = findViewById(R.id.comment_PostTitle)
        post = findViewById(R.id.comment_postBody)
        commentsRV = findViewById(R.id.comment_recycler)
        commentAdapter = CommentRecyclerAdapter()
        addCommentButton = findViewById(R.id.postCommentButton)
        addCommentName = findViewById(R.id.etComment_name)
        addCommentBody = findViewById(R.id.etComment_body)

        postTitle.text = title
        post.text = postBody

        viewModel = ViewModelProvider(this, viewModelFactory)[PostViewModel::class.java]

        setUpRecyclerView()
        id?.let { loadComments(it) }

        addCommentButton.setOnClickListener {
            if (addCommentName.text.isNotBlank() && addCommentBody.text.isNotBlank()) {
                val newComment = Comment(
                    id!!.toInt(),
                    (id.toInt() * commentAdapter.itemCount) + 1,
                       addCommentName.text.toString(),
                        addCommentBody.text.toString())
                    commentAdapter.addNewComment(newComment)
                Toast.makeText(applicationContext, "Comment successfully added", Toast.LENGTH_LONG).show()
                addCommentName.text.clear()
                addCommentBody.text.clear()
            } else {

                }

            }

        }

    //set up recycler view
    private fun setUpRecyclerView() {
        commentsRV.adapter = commentAdapter
        commentsRV.layoutManager = LinearLayoutManager(this)
    }

    private fun loadComments(id: String): PostViewModel {
        viewModel.getComments(id)
        viewModel.recyclerCommentResponse.observe(this, {
            if (it != null) {
                commentAdapter.setComments(it)
            } else {
                Toast.makeText(this, "Error fetching comments", Toast.LENGTH_SHORT).show()
            }

        })
        return viewModel
    }

    private fun pushComment(postId: Int, id:Int, title: String, body: String): PostViewModel {
        viewModel.pushComment(postId, id, title, body)
        viewModel.addCommentResponse.observe(this, {

        })
        return viewModel
    }
}