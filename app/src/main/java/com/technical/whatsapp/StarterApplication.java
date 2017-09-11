package com.technical.whatsapp;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class StarterApplication extends Application {

        @Override
        public void onCreate() {
                super.onCreate();

                Parse.enableLocalDatastore(this);

                Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                        .applicationId("db806ec71a24c85939dc563fdb675fc0f332552c")
                        .clientKey("3a18b773d598ec328f836afd24ae31f992496395")
                        .server("http://ec2-13-126-42-92.ap-south-1.compute.amazonaws.com:80/parse/")
                        .build()
                );





               // ParseUser.enableAutomaticUser();
                ParseACL defaultACL = new ParseACL();
                defaultACL.setPublicReadAccess(true);
                defaultACL.setPublicWriteAccess(true);
                ParseACL.setDefaultACL(defaultACL, true);



        }
}
