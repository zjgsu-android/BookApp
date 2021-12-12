package com.zjgsu.bookapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> mBooks;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleView;
        TextView mAuthorView;
        TextView mPriceView;

        public ViewHolder(@NonNull View view) {
            super(view);
            mTitleView = view.findViewById(R.id.book_title);
            mAuthorView = view.findViewById(R.id.book_author);
            mPriceView = view.findViewById(R.id.book_price);
        }
    }

    public BookAdapter(List<Book> books) {
        mBooks = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = mBooks.get(position);
        holder.mTitleView.setText(book.getTitle());
        holder.mAuthorView.setText(book.getAuthor());
        holder.mPriceView.setText(String.valueOf(book.getPrice()));
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }
}
