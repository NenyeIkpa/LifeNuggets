package com.example.lifenuggets.model.network

import com.example.lifenuggets.model.data.CommentResponse
import com.example.lifenuggets.model.data.PostResponse
import com.example.lifenuggets.model.ResponseItem
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*


interface ApiUser {

    //Describe the request types and their relative URl
    @GET("users")
    fun loadUser(): Observable<ArrayList<ResponseItem>>

    @GET("users/{userId}/posts")
    fun loadPost(@Path("userId") userId: String): Observable<List<PostResponse>>

    @GET("posts/{id}/comments")
    fun loadComments(@Path("id") id: String): Observable<List<CommentResponse>>

    @FormUrlEncoded
    @POST("users/userId/posts")
    fun pushPost(@Body post: PostResponse): Observable<PostResponse>

//    @POST("posts")
//    @FormUrlEncoded
//    suspend fun pushPost(@Body post: PostResponse): Observable<PostResponse>


    @POST("comments")
    fun pushComment(@Body comment: CommentResponse): Observable<CommentResponse>
}


