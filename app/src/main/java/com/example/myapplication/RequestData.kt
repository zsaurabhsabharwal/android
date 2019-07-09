package com.example.myapplication

import io.reactivex.Observable
import retrofit2.http.GET


interface RequestData {
    @GET("/photos")
    fun getData() : Observable<List<Result>>
}