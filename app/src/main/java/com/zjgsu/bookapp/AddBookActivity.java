package com.zjgsu.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        EditText etTitle = findViewById(R.id.edit_book_title);
        EditText etAuthor = findViewById(R.id.edit_book_author);
        EditText etPages = findViewById(R.id.edit_book_pages);
        EditText etPrice = findViewById(R.id.edit_book_price);

        Button saveBtn = findViewById(R.id.save_book);
        saveBtn.setOnClickListener((View v) -> {
            Intent intent = new Intent();
            intent.putExtra("title", etTitle.getText().toString());
            intent.putExtra("author", etAuthor.getText().toString());
            intent.putExtra("pages", Integer.parseInt(etPages.getText().toString()));
            intent.putExtra("price", Float.parseFloat(etPrice.getText().toString()));
            setResult(RESULT_OK, intent);
            finish();
        });

        Button cancelBtn = findViewById(R.id.cancel_book);
        cancelBtn.setOnClickListener((View v) -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });
    }
}