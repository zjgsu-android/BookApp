package com.zjgsu.bookapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookService {
    @GET("books")
    Call<List<Book>> listBooks();
}
