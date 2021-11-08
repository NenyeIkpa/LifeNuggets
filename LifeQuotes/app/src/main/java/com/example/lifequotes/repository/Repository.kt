package com.example.lifequotes.repository

import android.icu.text.Transliterator
import com.example.lifequotes.model.Comment
import com.example.lifequotes.model.Post
import com.example.lifequotes.network.RetrofitInstance
import retrofit2.Response

class Repository {

    suspend fun getPost(): Response<List<Post>> {
        return RetrofitInstance.api.getPost()
    }

    suspend fun getComments(id: String): Response<List<Comment>> {
        return RetrofitInstance.api.getComments(id)
    }

    suspend fun getCustomPost(userId: Int, sort: String, order: String): Response<List<Post>> {
        return RetrofitInstance.api.getCustomPosts(userId, sort, order)
    }

    suspend fun pushPost(userId: Int, id: Int, title: String, body: String): Response<Post> {
        return RetrofitInstance.api.pushPost(userId, id, title, body)
    }

    suspend fun pushComment(postId: Int, id: Int, name: String, body: String): Response<List<Comment>> {
        return RetrofitInstance.api.pushComment(postId, id, name, body)
    }
}