package com.example.applicationinsta;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

@ParseClassName("_User")
public class User  extends ParseUser {
    public static final String KEY_PHOTO_USER = "photoUser";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    public String getKeyUsername() {
        return getString(KEY_USERNAME);
    }

    public void setKeyUsername(String username) {
        put(KEY_USERNAME, username);
    }

    public String getKeyPassword() {
        return getString(KEY_PASSWORD);
    }

    public void setKeyPassword(String password) {
        put(KEY_PASSWORD, password);
    }

    public ParseFile getPhotoUser() {
        return getParseFile(KEY_PHOTO_USER);
    }
}