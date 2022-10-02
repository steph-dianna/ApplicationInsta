package com.example.applicationinsta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterGridView extends ArrayAdapter<Post> {

    public AdapterGridView(@NonNull Context context, ArrayList<Post> posts){
        super(context,0,posts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.griditem,parent,false);

        Post post = getItem(position);
        ImageView ivgView= view.findViewById(R.id.ivgView);
        Glide.with(getContext()).load(post.getImage().getUrl()).into(ivgView);

        return  view;

    }
}
