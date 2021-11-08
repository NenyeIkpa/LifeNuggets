package com.example.lifequotes.util


const val BASE_URL = "https://jsonplaceholder.typicode.com/"
fun generateUserId(id: Int): Int {
    return id % 10
}
fun generateUserImage(position: Int): Int {
    return position % 10
}
