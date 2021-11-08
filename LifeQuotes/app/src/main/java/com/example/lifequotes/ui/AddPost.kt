package com.example.lifequotes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.lifequotes.R
import com.example.lifequotes.adapter.PostRecyclerAdapter
import com.example.lifequotes.model.MainViewModelFactory
import com.example.lifequotes.model.Post
import com.example.lifequotes.model.PostViewModel
import com.example.lifequotes.repository.Repository
import com.example.lifequotes.util.generateUserId


class AddPost : AppCompatActivity(), PostRecyclerAdapter.CommentLayoutClickEvent {

    private lateinit var viewModel: PostViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    private lateinit var addPostTitle: EditText
    private lateinit var addPostBody: EditText
    private lateinit var addPostButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        val repository = Repository()
        viewModelFactory =MainViewModelFactory(repository)
        addPostButton = findViewById(R.id.addPost_send)
        addPostTitle = findViewById(R.id.addPost_postTitle)
        addPostBody  =findViewById(R.id.addPost_postBody)

        viewModel = ViewModelProvider(this, viewModelFactory) [PostViewModel::class.java]

        addPostButton.setOnClickListener {
            if (addPostTitle.text.isNotBlank() && addPostBody.text.isNotBlank()){
                val newPost = Post(
                    userId = generateUserId(PostActivity.postAdapter.itemCount + 1),
                    id = PostActivity.postAdapter.itemCount + 1,
                    body = addPostBody.text.toString(),
                    title = addPostTitle.text.toString()
                )

                PostActivity.postAdapter.addNewPost(newPost)
                finish()

                Toast.makeText(this,"click Successful count is ${ PostActivity.postAdapter.itemCount}", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun addPost(userId: Int, id:Int, title: String, body: String): PostViewModel {
        viewModel.pushPost(userId, id, title, body)
        viewModel.addPostResponse.observe(this, {

        })
        return viewModel
    }

    override fun onClickOfCommentLayout(id: Int, title: String, postBody: String) {

    }
}