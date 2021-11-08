package com.example.lifequotes.network

import com.example.lifequotes.model.Comment
import com.example.lifequotes.model.Post
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("posts")
    suspend fun getPost(
        @Query("_sort") sort: String = "id",
        @Query("_order") order: String = "desc"
    ): Response<List<Post>>


    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id")id: String): Response<List<Comment>>


    @GET("posts")
    suspend fun getCustomPosts(
    @Query("userId") userId: Int,
    @Query("_sort") sort: String,
    @Query("_order") order: String): Response<List<Post>>

    @FormUrlEncoded
    @POST("posts")
    suspend fun pushPost(
        @Field("userId")userId: Int,
        @Field("id")id: Int,
        @Field("title")title: String,
        @Field("body")body: String
    ): Response<Post>


    @FormUrlEncoded
    @POST("comments")
    suspend fun pushComment(
        @Field("postId") postId: Int,
        @Field("id") id: Int,
        @Field("name") name: String,
        @Field("body") body: String
    ): Response<List<Comment>>


}