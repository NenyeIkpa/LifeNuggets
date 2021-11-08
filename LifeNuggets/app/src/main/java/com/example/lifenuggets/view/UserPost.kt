package com.example.lifenuggets.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifenuggets.R
import com.example.lifenuggets.utils.ApiUserClientService
import com.example.lifenuggets.model.data.PostResponse
import com.example.lifenuggets.model.adapter.UserPostAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class UserPost : AppCompatActivity(), UserPostAdapter.CommentLayoutClickEvent {


    lateinit var postRV: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var addPost: FloatingActionButton
    lateinit var addPostTitle: EditText
    lateinit var addPostBody: EditText
    lateinit var addPostButton: ImageView
    lateinit var newPost: PostResponse

    companion object{
        lateinit var id:String
        lateinit var postAdapter: UserPostAdapter
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_post)

        id = intent.getStringExtra("id")!!
        //commentIcon = findViewById(R.id.rv_commentIcon)
        postRV = findViewById(R.id.rv_userPost)
        postAdapter = UserPostAdapter(this)
        progressBar = findViewById(R.id.progressBar)
        addPost = findViewById(R.id.floatingActionButton)

        addPost.setOnClickListener {
            addPostDialog(id)
        }

        secondNetworkCall(id)
    }
    private fun secondNetworkCall(userId: String) {
        progressBar.visibility = View.VISIBLE
        ApiUserClientService.retrofitService.loadPost(userId)
            .subscribeOn(Schedulers.io())       //subscribe to the observer away from the main thread
            .observeOn(AndroidSchedulers.mainThread())      //send the observable's notification to the main UI
            .subscribe(object: Observer<List<PostResponse>>{
                override fun onSubscribe(d: Disposable?) {

                }

                override fun onNext(t: List<PostResponse>?) {
                    if (t != null) {
                        postAdapter.setUserPostData(t)
                    }
                    progressBar.visibility = View.INVISIBLE
                    postRV.visibility = View.VISIBLE
                    postRV.adapter = postAdapter
                    //use layout manager to position items
                    postRV.layoutManager = LinearLayoutManager(this@UserPost)
                }

                override fun onError(e: Throwable?) {
                    //display error message in case of an exception
                    Toast.makeText(this@UserPost, "Error retrieving posts", Toast.LENGTH_LONG).show()
                }

                override fun onComplete() {
                    //display message that post retrieval has been completed
                    Toast.makeText(this@UserPost, "Post retrieval complete", Toast.LENGTH_LONG).show()
                }
            })
    }



//write function to create and display dialog box for adding posts
    private fun addPostDialog(id: String) {

        val alertDialog = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.add_post, null)
        alertDialog.setView(dialogView)

        addPostTitle = dialogView.findViewById(R.id.addPost_postTitle)
        addPostBody = dialogView.findViewById(R.id.addPost_postBody)
        addPostButton =dialogView.findViewById(R.id.addPost_send)


        alertDialog.create()
        // dialog.window!!.getAttributes().windowAnimations = R.style.PauseDialogAnimation
        alertDialog.show()

        addPostButton.setOnClickListener {
            if (addPostTitle.text.isNotBlank() && addPostBody.text.isNotBlank()) {
                val count = postAdapter.itemCount
                 newPost = PostResponse(
                    userId = id.toInt(),
                    id = count + 1,
                    body = addPostBody.text.toString(),
                    title = addPostTitle.text.toString()
                )
                makeFakePostToAPI(newPost)
                dialogView.isGone

                Toast.makeText(
                    this,
                    "click Successful count is ${postAdapter.itemCount}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //make fake call to API to push
    private fun makeFakePostToAPI(post: PostResponse) {
        ApiUserClientService.retrofitService.pushPost(post)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<PostResponse>{
                override fun onSubscribe(d: Disposable?) {

                }

                override fun onNext(t: PostResponse?) {
                    newPost = t!!
                    postAdapter.addNewPost(t)
                }

                override fun onError(e: Throwable?) {
                    Toast.makeText(baseContext, "Error uploading post", Toast.LENGTH_SHORT).show()
                }
                override fun onComplete() {
                    Toast.makeText(baseContext, "Post uploaded successfully", Toast.LENGTH_SHORT).show()
                }

            })
    }


    override fun onClickOfCommentLayout(position: Int) {
        val dialogueBoxInstance = CommentDialogBox()
        dialogueBoxInstance.show(supportFragmentManager,"Show Add Post Dialogue")
    }

}