package com.example.lifequotes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifequotes.R
import com.example.lifequotes.adapter.PostRecyclerAdapter
import com.example.lifequotes.model.MainViewModelFactory
import com.example.lifequotes.model.Post
import com.example.lifequotes.model.PostViewModel
import com.example.lifequotes.repository.Repository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PostActivity : AppCompatActivity(), PostRecyclerAdapter.CommentLayoutClickEvent {
    private lateinit var viewModel: PostViewModel
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var recyclerView: RecyclerView
    private lateinit var addPostPage: FloatingActionButton
    lateinit var searchText:EditText
    private lateinit var searchButton:Button
    companion object{
        lateinit var postAdapter: PostRecyclerAdapter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val repository = Repository()
        viewModelFactory =MainViewModelFactory(repository)
        postAdapter = PostRecyclerAdapter(this)
        recyclerView = findViewById(R.id.rv_post)
        addPostPage = findViewById(R.id.floatingActionButton)
        searchText = findViewById(R.id.searchView)
        searchButton = findViewById(R.id.search_button)

        viewModel = ViewModelProvider(this, viewModelFactory) [PostViewModel::class.java]


        setUpRecyclerView()
         loadPost()


        addPostPage.setOnClickListener{
           loadPostAddPage()
        }
        searchButton.setOnClickListener {
            if (searchText.text.isNotBlank() && searchText.text.contains("^[0-9]*$".toRegex())){
                loadPostSearch()
            }
        }



    }
    //set up recycler view
    private fun setUpRecyclerView() {
        recyclerView.adapter = postAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
    //create function to observe any changes in post and update recycler view accordingly in real-time
     fun loadPost(): PostViewModel {
        viewModel.getPost()
        viewModel.recyclerPostResponse.observe(this, Observer {response ->
            if(response != null) {
                Log.d("res", "loadPost: $response")
                postAdapter.setPostData(response)
            }else {
                Toast.makeText(this, "Error fetching post", Toast.LENGTH_LONG).show()
            }
        })
        return viewModel
    }

    private fun loadPostSearch(): PostViewModel {
        viewModel.getPost()
        viewModel.recyclerPostResponse.observe(this, Observer {response ->
            if(response != null) {

                val newList = mutableListOf<Post>()
                for(i in response){
                    if (i.userId == searchText.text.toString().toInt()){
                        newList.add(i)
                    }
                }
                val responseBody:List<Post> = newList
                postAdapter.setPostData(responseBody)
            }else {
                Toast.makeText(this, "Error fetching post", Toast.LENGTH_LONG).show()
            }
        })
        return viewModel
    }

    //create function to implement search filters
//    fun loadCustomPost(userId: Int, sort: String, order: String): PostViewModel {
//        viewModel.getCustomPost()
//        viewModel.myCustomPost.observe(this, {response ->
//            if(response != null) {
//                postAdapter.setPostData(response)
//            }else {
//                Toast.makeText(this, "Error fetching post", Toast.LENGTH_LONG).show()
//            }
//        })
//        return viewModel
//    }
    fun search() {
       // searchView.setQuery()
    }
    private fun loadPostAddPage() {
        val intent = Intent(this, AddPost::class.java)
        startActivity(intent)
    }

    //use intent to pass data from PostActivity to CommentActivity
    override fun onClickOfCommentLayout(id: Int, title: String, postBody: String) {
        val intent = Intent(this, CommentActivity::class.java)
        intent.putExtra("postId", id.toString())
        intent.putExtra("title", title)
        intent.putExtra("postBody", postBody)
        startActivity(intent)
    }
}