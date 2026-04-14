package com.example.edustay.AdapterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.edustay.Comman.Urls;
import com.example.edustay.Model.Book;
import com.example.edustay.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> list;
    private Context context;

    public BookAdapter(List<Book> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mybook_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = list.get(position);
        holder.t1.setText(book.getTitle());
        holder.t2.setText(book.getAuthor());


        String url = Urls.urlMyBookPic + book.getImage();
        Glide.with(context).load(url).into(holder.img);
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView t1, t2; ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.bookTitle);
            t2 = itemView.findViewById(R.id.bookAuthor);
            img = itemView.findViewById(R.id.bookImage);
        }
    }
}

