package com.example.lifenuggets.view

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.lifenuggets.R
import com.example.lifenuggets.model.data.PostResponse
import com.example.lifenuggets.view.UserPost.Companion.postAdapter

class AddPostDialog: AppCompatDialogFragment() {

    lateinit var addPostTitle: EditText
    lateinit var addPostBody: EditText
    private lateinit var addPostButton: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dialogBox = Dialog(requireContext())
        dialogBox.setContentView(R.layout.add_post)

        addPostTitle = dialogBox.findViewById(R.id.addPost_postTitle)
        addPostBody = dialogBox.findViewById(R.id.addPost_postBody)
        addPostButton = dialogBox.findViewById(R.id.addPost_send)


        addPostButton.setOnClickListener {
            if (addPostTitle.text.isNotBlank() && addPostBody.text.isNotBlank()) {
                var count = postAdapter.itemCount * id
                val newPost = PostResponse(
                    userId = UserPost.id.toInt(),
                    id = count ++,
                    body = addPostBody.text.toString(),
                    title = addPostTitle.text.toString()
                )

                postAdapter.addNewPost(newPost)
                dialogBox.dismiss()


                Toast.makeText(
                    requireContext(),
                    "click Successful count is ${UserPost.postAdapter.itemCount}",
                    Toast.LENGTH_SHORT
                ).show()


            }
        }
    }
}