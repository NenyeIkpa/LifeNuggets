package com.example.lifequotes.model

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifequotes.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class PostViewModel(private val repository: Repository): ViewModel() {

    private val _recyclerPostResponse: MutableLiveData<List<Post>> = MutableLiveData()
    val recyclerPostResponse: LiveData<List<Post>> = _recyclerPostResponse

    private val _myCustomPost: MutableLiveData<List<Post>> = MutableLiveData()
    val myCustomPost: LiveData<List<Post>> = _myCustomPost

    private val _recyclerCommentResponse: MutableLiveData<List<Comment>> = MutableLiveData()
    val recyclerCommentResponse: LiveData<List<Comment>> = _recyclerCommentResponse

    private val _addPostResponse: MutableLiveData<Post> = MutableLiveData()
    val addPostResponse: LiveData<Post> = _addPostResponse

    private val _addCommentResponse: MutableLiveData<List<Comment>> = MutableLiveData()
    val addCommentResponse: LiveData<List<Comment>> = _addCommentResponse

    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost()
            if (response.isSuccessful) {
                _recyclerPostResponse.postValue(response.body())
                Log.d("post", "getPost: ${response.body()} ")
            }
        }
    }

    fun getComments(id: String) {
        viewModelScope.launch {
            val response = repository.getComments(id)
            if (response.isSuccessful) {
                _recyclerCommentResponse.postValue(response.body())
            }
        }
    }


    fun pushPost(userId: Int, id: Int, title: String, body: String) {
        viewModelScope.launch {
            val response = repository.pushPost(userId, id, title, body)
            _addPostResponse.postValue(response.body())
        }
    }

    fun pushComment(postId: Int, id: Int, name: String, body: String) {
        viewModelScope.launch {
            val response= repository.pushComment(postId, id, name, body)
            _addCommentResponse.postValue(response.body())
        }
    }

}