package com.example.lifenuggets.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifenuggets.R
import com.example.lifenuggets.model.data.CommentResponse
import com.example.lifenuggets.model.adapter.CommentsAdapter
import com.example.lifenuggets.utils.ApiUserClientService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CommentDialogBox:AppCompatDialogFragment() {

    lateinit var commentsAdapter: CommentsAdapter
    lateinit var commentsRV: RecyclerView
    lateinit var progressIndicator: ProgressBar
    private lateinit var addCommentName: EditText
    private lateinit var addCommentBody: EditText
    private lateinit var newCommentAddButton: ImageView
    lateinit var newComment: CommentResponse


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         val dialogBox = Dialog(requireContext())
        dialogBox.setContentView(R.layout.dialog_custom_viewp)

        val postId = UserPost.id


        commentsRV = dialogBox.findViewById(R.id.rv_commentOnPost)!!
        progressIndicator = dialogBox.findViewById(R.id.alertDialog_progressBar)
        addCommentName = dialogBox.findViewById(R.id.et_addCommentName)
        addCommentBody= dialogBox.findViewById(R.id.et_addCommentBody)
        newCommentAddButton = dialogBox.findViewById(R.id.addComment_button)
        commentsAdapter = CommentsAdapter()

        thirdNetworkCall(postId)

        commentsRV.adapter = commentsAdapter
        commentsRV.layoutManager = LinearLayoutManager(requireContext())

        dialogBox.show()

        newCommentAddButton.setOnClickListener {
            if (addCommentName.text.isNotBlank() && addCommentBody.text.isNotBlank()) {
                val count = commentsAdapter.itemCount
                newComment = CommentResponse(
                    postId = postId.toInt(),
                    id = count + 1,
                    body = addCommentBody.text.toString(),
                    name = addCommentName.text.toString()
                )

                makeFakeCommentToAPI(newComment)
                addCommentName.text.clear()
                addCommentBody.text.clear()


//                Toast.makeText(
//                    this,
//                    "click Successful count is ${UserPost.postAdapter.itemCount}",
//                    Toast.LENGTH_SHORT
//                ).show()


            }
        }
    }

    private fun thirdNetworkCall(postId: String) {
        progressIndicator.visibility = View.VISIBLE
        ApiUserClientService.retrofitService.loadComments(postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<List<CommentResponse>> {
                override fun onSubscribe(d: Disposable?) {

                }

                override fun onNext(t: List<CommentResponse>?) {
                    if (t != null) {
                        commentsAdapter.setComments(t)
                        Log.d("comment", "listcomments: $t")
                    }
                    progressIndicator.visibility = View.GONE
                    commentsRV.visibility = View.VISIBLE
//                    commentsRV.adapter = commentsAdapter
//                    Log.d("commentRV", "listcomments: $t")
//                    commentsRV.layoutManager = LinearLayoutManager(requireContext())
                }

                override fun onError(e: Throwable?) {
                    Toast.makeText(requireContext(), "Error retrieving comments", Toast.LENGTH_LONG).show()
                }

                override fun onComplete() {
                    Toast.makeText(requireContext(), "Comments retrieval complete", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun makeFakeCommentToAPI(comment: CommentResponse) {
        ApiUserClientService.retrofitService.pushComment(comment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<CommentResponse> {
                override fun onSubscribe(d: Disposable?) {

                }

                override fun onNext(t: CommentResponse?) {
                    newComment = t!!
                    commentsAdapter.addNewComment(t)
                }

                override fun onError(e: Throwable?) {
                    Log.d("comment push error", "Error uploading comment")
                }

                override fun onComplete() {
                    Log.d("comment push", "Comment uploaded!")
                }

            })
}}