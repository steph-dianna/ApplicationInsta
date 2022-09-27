package com.example.applicationinsta;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class InstaAdapter extends RecyclerView.Adapter<InstaAdapter.ViewHolder> {
    public static final String TAG = "InstaAdapter";
    public static Context context;
    List<Post> postList;

    public InstaAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_insta_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstaAdapter.ViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.bind(post);
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }
    // Method to clean all elements of the recycler
    public void clear(){
        postList.clear();
        notifyDataSetChanged();
    }

    // Method to add a list of Posts -- change to type used
    public void addAll(List<Post> postList){
        postList.addAll(postList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView photo,photo_User;
        TextView utilisateur, description,heure;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            photo_User = itemView.findViewById(R.id.photo_User);
            utilisateur = itemView.findViewById(R.id.utilisateur);
            description = itemView.findViewById(R.id.description);
            heure = itemView.findViewById(R.id.heure);
            container = itemView.findViewById(R.id.container);


        }

        public void bind(Post post) {
            utilisateur.setText(post.getUser().getUsername());
            description.setText(post.getDescription());
            heure.setText(TimeFormatter.getTimeStamp(post.getCreatedAt().toString()));

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context,DetailActivity.class);
                    intent.putExtra("post", Parcels.wrap(post));

                    ActivityOptions options = ActivityOptions.
                            makeSceneTransitionAnimation((Activity)context, utilisateur, "detail");
                    context.startActivity(intent, options.toBundle());


                }
            });




            ParseFile imaj = post.getImage();

            if (imaj != null) {

                Glide.with(context)
                        .load(imaj.getUrl())
                        .fitCenter()
                        .into(photo);

            }

            Glide.with(context).load(post.getUser().getParseFile(User.KEY_PHOTO_USER)
                    .getUrl()).into(photo_User);
        }

    }
}
