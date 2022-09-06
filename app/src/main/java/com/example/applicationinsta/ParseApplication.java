package com.example.applicationinsta;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("hm5BujvyJ2vslWySafa4I2ASCewDQ5Q9m6T4YZJi")
                .clientKey("MEo4hzFYHH1QE1lbug3fFP1NXYqjsYJgcsSXZduz")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
