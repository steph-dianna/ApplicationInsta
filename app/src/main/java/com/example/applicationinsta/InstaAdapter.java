package com.example.applicationinsta;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class InstaAdapter extends RecyclerView.Adapter<InstaAdapter.ViewHolder> {
    public static final String TAG = "InstaAdapter";
    public static Context context;
    List<Post> postList;
    private static ArrayList<String> likes;
    public int nbrsLikes;
    ParseUser parseUser;

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

        ImageView photo,photo_User,message;
        ImageButton ivCoeur;
        TextView utilisateur, description,heure,nbrLike;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            utilisateur = itemView.findViewById(R.id.utilisateur);
            description = itemView.findViewById(R.id.description);
            heure = itemView.findViewById(R.id.heure);
            nbrLike = itemView.findViewById(R.id.nbrLike);

            photo = itemView.findViewById(R.id.photo);
            photo_User = itemView.findViewById(R.id.photo_User);
            ivCoeur = itemView.findViewById(R.id.ivCoeur);
            message = itemView.findViewById(R.id.message);
            container = itemView.findViewById(R.id.container);


        }

        public void bind(Post post) {
            utilisateur.setText(post.getUser().getUsername());
            description.setText(post.getDescription());

            heure.setText(TimeFormatter.getTimeStamp(post.getCreatedAt().toString()));

            parseUser = ParseUser.getCurrentUser();
            try {
                likes = Post.fromJsonArray(post.getListLike());
            }catch (JSONException e){
                e.printStackTrace();
            }

            try {
                if (likes.contains(parseUser.getObjectId())){
                    Drawable drawable = ContextCompat.getDrawable(context,R.drawable.coeur2);
                    ivCoeur.setImageDrawable(drawable);
                }else {
                    Drawable drawable = ContextCompat.getDrawable(context,R.drawable.coeur);
                    ivCoeur.setImageDrawable(drawable);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }

            ivCoeur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nbrsLikes = post.getLike();
                    int index;

                    if (! likes.contains(parseUser.getObjectId())){
                        Drawable drawable = ContextCompat.getDrawable(context,R.drawable.coeur2);
                        ivCoeur.setImageDrawable(drawable);
                        nbrsLikes++;
                        index = -1;

                    }else {
                        Drawable drawable = ContextCompat.getDrawable(context,R.drawable.coeur);
                        ivCoeur.setImageDrawable(drawable);
                        nbrsLikes--;
                        index = likes.indexOf(parseUser.getObjectId());
                    }
                    nbrLike.setText(String.valueOf(nbrsLikes) + "likes");
                    SaveLikes(post,nbrsLikes,index,parseUser);
                }
            });

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



            photo_User.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FragmentManager fragmentManager= ((FragmentActivity)context).getSupportFragmentManager();
                    fragment_Account account =fragment_Account.newInstance("");

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("post",Parcels.wrap(post));

                    account.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.Frame,account).commit();

                }
            });


            ParseFile imaj = post.getImage();

            if (imaj != null) {

                Glide.with(context)
                        .load(imaj.getUrl())
                        .fitCenter()
                        .into(photo);

            }

            Glide.with(context).load(post.getUser().getParseFile(User.KEY_PHOTO_USER).getUrl()
                    ).transform(new RoundedCorners(150)).into(photo_User);

        }

        private void SaveLikes(Post post, int nbrsLikes, int index, ParseUser parseUser) {
            post.setLike(nbrsLikes);

            if(index == -1){
                post.setListLIKE(parseUser);
                likes.add(parseUser.getObjectId());
            }else {
                likes.remove(index);
                post.removeListLIKE(likes);
            }
        }


    }
}
