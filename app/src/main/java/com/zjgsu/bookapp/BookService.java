package com.zjgsu.bookapp;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BookService {
    @GET("books")
    Call<List<Book>> listBooks();

    @GET("books/{id}")
    Call<Book> getBook(@Path("id") int id);

    @POST("books")
    Call<Book> createBook(@Body Book book);

    @PUT("books/{id}")
    Call<Book> modifyBook(@Body Book book, @Path("id") int id);

    @DELETE("books/{id}")
    Call<ResponseBody> deleteBook(@Path("id") int id);
}
