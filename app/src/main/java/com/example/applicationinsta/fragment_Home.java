package com.example.applicationinsta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class fragment_Home extends Fragment {

    public static final String TAG = "fragment_Home";
    RecyclerView recycleview;
    protected List<Post> postList;
    protected InstaAdapter adapter;
    private Context context;


    public fragment_Home(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycleview = view.findViewById(R.id.recycleview);
        postList = new ArrayList<>();

        //Create the adapter
        adapter = new InstaAdapter(getContext(), postList);

        //Set the adapter on the recyclerView
        recycleview.setAdapter(adapter);

        // Set The layout manager on the recyclerView
        recycleview.setLayoutManager(new LinearLayoutManager(context));

        queryPost();

    }

    protected void queryPost() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting Posts", e);
                    Toast.makeText(getContext(), "Issue with getting Posts", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (Post post : posts){
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }

                postList.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }

}