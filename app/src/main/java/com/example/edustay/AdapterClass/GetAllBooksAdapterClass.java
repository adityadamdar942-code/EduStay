package com.example.edustay.AdapterClass;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.edustay.POJOClass.GetAllBooksPOJOClass;
import com.example.edustay.R;

import java.util.List;

public class GetAllBooksAdapterClass extends BaseAdapter {

    List<GetAllBooksPOJOClass> getAllBooksPOJOClasses;
    Activity activity;

    public GetAllBooksAdapterClass(List<GetAllBooksPOJOClass> getAllBooksPOJOClasses, Activity activity) {
        this.getAllBooksPOJOClasses = getAllBooksPOJOClasses;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return getAllBooksPOJOClasses.size();
    }

    @Override
    public Object getItem(int position) {
        return getAllBooksPOJOClasses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(activity)
                    .inflate(R.layout.custom_book_list, parent, false);

            holder = new ViewHolder();
            holder.ivBookImage = convertView.findViewById(R.id.ivBookImage);
            holder.tvBookName = convertView.findViewById(R.id.tvBookName);
            holder.tvAuthorName = convertView.findViewById(R.id.tvAuthorName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        GetAllBooksPOJOClass obj = getAllBooksPOJOClasses.get(position);


        holder.tvBookName.setText(obj.getBookName());
        holder.tvAuthorName.setText(obj.getBookAuthor());


        String imageName = obj.getBookImage() == null ? "" : obj.getBookImage().trim();
        Log.e("BOOK_DEBUG", "imageName = " + imageName);

        if (TextUtils.isEmpty(imageName)) {


            holder.ivBookImage.setImageResource(R.drawable.image_not_found);

        } else {


            String imageUrl = "http://10.159.20.239/EduStayAPI/images/" + imageName;
            Log.e("BOOK_DEBUG", "imageUrl = " + imageUrl);


            Glide.with(activity)
                    .load(imageUrl)
                    .placeholder(R.drawable.image_not_found)
                    .error(R.drawable.image_not_found)
                    .into(holder.ivBookImage);
        }

        return convertView;
    }


    static class ViewHolder {
        ImageView ivBookImage;
        TextView tvBookName, tvAuthorName;
    }
}
