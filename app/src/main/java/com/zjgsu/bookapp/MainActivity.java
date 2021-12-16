package com.zjgsu.bookapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String BASE_URL = "http://192.168.88.8:8080/"; // 把这里换成服务器的地址
    private BookService mBookService;
    private RecyclerView mBookListView;
    private final List<Book> mBooks = new ArrayList<>();
    private BookAdapter mAdapter;
    private ActivityResultLauncher<Intent> mAddActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup book list
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBookListView = findViewById(R.id.book_list);
        mBookListView.setLayoutManager(layoutManager);
        mAdapter = new BookAdapter(mBooks);
        mBookListView.setAdapter(mAdapter);

        // Update book list
        Button updateBtn = findViewById(R.id.update);
        updateBtn.setOnClickListener((View v) -> updateBooks());

        // Add book
        mAddActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        addBook(result.getData());
                    }
                });
        Button addBtn = findViewById(R.id.add);
        addBtn.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, AddBookActivity.class);
            mAddActivityResultLauncher.launch(intent);
        });

        Button deleteBtn = findViewById(R.id.delete);
        deleteBtn.setOnClickListener((View v) -> removeBook(2));


        // Build retrofit book service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mBookService = retrofit.create(BookService.class);

        // Update books for first time
        updateBooks();
    }

    void updateBooks() {
        mBookService.listBooks().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                mBooks.clear();
                mBooks.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    void addBook(Intent intent) {
        Book book = new Book(intent.getStringExtra("title"),
                intent.getStringExtra("author"),
                intent.getIntExtra("pages", 0),
                intent.getFloatExtra("price", 0.0f));

        mBookService.createBook(book).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                Toast.makeText(MainActivity.this, "Add book " + book.getTitle() + " succeed!", Toast.LENGTH_LONG).show();
                mBooks.add(book);
                mAdapter.notifyItemInserted(mBooks.size() - 1);
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    void removeBook(int pos) {
        Book book = mBooks.get(pos);
        Log.d(TAG, "removeBook: ");

        mBookService.deleteBook(book.getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: remove book with id = " + book.getId());
                mBooks.remove(pos);
                mAdapter.notifyItemRemoved(pos);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.w(TAG, "onFailure: delete book " + book.getTitle() + " failed");
            }
        });
    }
}
