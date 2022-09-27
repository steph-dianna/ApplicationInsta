package com.example.applicationinsta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    public TextView utilisateur;
    public ImageView photo;
    public TextView description;
    public ImageView photoUser;
    public TextView heure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        utilisateur = findViewById(R.id.utilisateur);
        description = findViewById(R.id.description);
        heure = findViewById(R.id.heure);
        photo = findViewById(R.id.photo);
        photoUser = findViewById(R.id.photoUser);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));



        utilisateur.setText(post.getUser().getUsername());
        description.setText(post.getDescription());
        heure.setText(TimeFormatter.getTimeStamp(post.getCreatedAt().toString()));


        ParseFile imaj = post.getImage();

        if (imaj != null) {

            Glide.with(DetailActivity.this)
                    .load(imaj.getUrl())
                    .fitCenter()
                    .into(photo);

        }

        Glide.with(DetailActivity.this).load(post.getUser().getParseFile(User.KEY_PHOTO_USER)
                .getUrl()).into(photoUser);
    }
}