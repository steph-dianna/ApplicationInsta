package com.example.applicationinsta;

import androidx.annotation.NonNull;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED = "createdAt";
    public static final String KEY_LIKE = "Likes";
    public static final String KEY_Lis_LIKE = "listLikes";


    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION,description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE,parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER,user);
    }

    public  int getLike(){
        return getInt(KEY_LIKE);
    }

    public  void setLike(int like){
        put(KEY_LIKE,like);
    }

    public JSONArray getListLike(){
        return getJSONArray(KEY_Lis_LIKE);
    }

    public void  setListLIKE(ParseUser parseUser){
        add(KEY_Lis_LIKE,parseUser);
    }

    public void removeListLIKE(List<String> stringList) {
        remove(KEY_Lis_LIKE);
        put(KEY_Lis_LIKE,stringList);
    }

    public static ArrayList<String> fromJsonArray(JSONArray jsonArray) throws JSONException {
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            for (int i = 0; i<jsonArray.length(); i++)
                arrayList.add(jsonArray.getJSONObject(i).getString("objectId"));
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return arrayList;
    }
}
