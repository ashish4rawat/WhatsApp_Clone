package com.technical.whatsapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UpdateMessage extends Service {




        public UpdateMessage() {
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
                Log.i("Info","onStartCommand method called");



                Runnable r = new Runnable() {
                        @Override
                        public void run() {

                                for(;;){

                                        synchronized (this){

                                                try{

                                                        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Messages");
                                                        query3.whereEqualTo("sender",UserChat.usern);
                                                        query3.whereEqualTo("reciever", ParseUser.getCurrentUser().getUsername());

                                                        query3.orderByAscending("createdAt");
                                                        query3.whereEqualTo("screenValue","0");

                                                        query3.findInBackground(new FindCallback<ParseObject>() {
                                                                @Override
                                                                public void done(List<ParseObject> objects, ParseException e) {

                                                                        if(e==null){

                                                                                for( ParseObject msg : objects){

                                                                                        msg.put("screenValue","1");
                                                                                        msg.saveInBackground();

                                                                                        UserChat.chat.add("->"+msg.getString("message"));
                                                                                        UserChat.adapter.notifyDataSetChanged();

                                                                                        Log.i("Service","Message Shown");

                                                                                }

                                                                        }
                                                                        else{

                                                                                Log.i("Info",e.getMessage());
                                                                                e.printStackTrace();

                                                                        }
                                                                }
                                                        });





                                                        wait(2000);
                                                }catch (Exception e){
                                                e.printStackTrace();
                                                }


                                        }


                                }


                        }
                };

                Thread t = new Thread(r);
                t.start();
                return  Service.START_STICKY;

        }

        @Override
        public void onDestroy() {

                Log.i("Info","onDestroy method called");
                super.onDestroy();
        }

        @Override
        public IBinder onBind(Intent intent) {
                // TODO: Return the communication channel to the service.
               // throw new UnsupportedOperationException("Not yet implemented");
                return null;
        }
}
