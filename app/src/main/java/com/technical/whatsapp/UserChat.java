package com.technical.whatsapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserChat extends AppCompatActivity {

        EditText chatText;
        static ArrayList<String> chat;
        static ArrayAdapter adapter;
        static String usern;
        String current;
        Runnable r;
        ListView chatListView;



        @Override
        protected void onCreate(Bundle savedInstanceState) {


                final Handler handler = new Handler(){

                        @Override
                        public void handleMessage(Message msg) {

                        //change the interface

                                UserChat.chat.add("->"+current);
                                UserChat.adapter.notifyDataSetChanged();

                        }
                };


                 r = new Runnable() {
                        @Override
                        public void run() {


                                        synchronized (this){

                                                try{
                                                     //   wait(2000);

                                                        for(;;) {


                                                                ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Messages");
                                                                query3.whereEqualTo("sender", UserChat.usern);
                                                                query3.whereEqualTo("reciever", ParseUser.getCurrentUser().getUsername());

                                                                query3.orderByAscending("createdAt");
                                                                query3.whereEqualTo("screenValue", "0");

                                                                query3.findInBackground(new FindCallback<ParseObject>() {
                                                                        @Override
                                                                        public void done(List<ParseObject> objects, ParseException e) {

                                                                                if (e == null) {

                                                                                        for (ParseObject msg : objects) {

                                                                                                current = msg.getString("message");
                                                                                                msg.put("screenValue", "1");
                                                                                                msg.saveInBackground();

                                                                                                handler.sendEmptyMessage(0);


                                                                                                Log.i("Service", "Message Shown");

                                                                                        }

                                                                                } else {

                                                                                        Log.i("Info", e.getMessage());
                                                                                        e.printStackTrace();

                                                                                }
                                                                        }
                                                                });

                                                                wait(2000);
                                                        }



                                                }catch (Exception e){
                                                        e.printStackTrace();
                                                }


                                        }








                        }
                };




                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_user_chat);

                getWindow().setBackgroundDrawableResource(R.drawable.geometry);

                usern =  getIntent().getStringExtra("username");
                setTitle(usern);


                //Intent updateMes = new Intent(this,UpdateMessage.class);
                //startService(updateMes);


                chatText = (EditText)findViewById(R.id.chatText);
                 chatListView = (ListView)findViewById(R.id.chatListView);
                chat = new ArrayList<>();





                adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,chat);
                chatListView.setAdapter(adapter);


                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Messages");
                query1.whereEqualTo("sender",ParseUser.getCurrentUser().getUsername());
                query1.whereEqualTo("reciever",usern);


                ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Messages");
                query2.whereEqualTo("sender",usern);
                query2.whereEqualTo("reciever",ParseUser.getCurrentUser().getUsername());


                List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
                queries.add(query1);
                queries.add(query2);

                ParseQuery<ParseObject> query =  ParseQuery.or(queries);
                query.orderByAscending("createdAt");


                query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {

                                if(e==null){

                                        for( ParseObject msg : objects){


                                                if(msg.getString("sender").equals(usern)){

                                                        chat.add("->"+msg.getString("message"));

                                                        msg.put("screenValue","1");
                                                        msg.saveInBackground();

                                                }
                                                else{
                                                        chat.add(msg.getString("message"));
                                                }

                                                adapter.notifyDataSetChanged();

                                        }

                                        Thread thread = new Thread(r);
                                        thread.start();



                                }
                                else{

                                        Log.i("Info",e.getMessage());
                                        e.printStackTrace();

                                }

                        }
                });



        }



        public void sendClick(View view) {

                chat.add(chatText.getText().toString());
                adapter.notifyDataSetChanged();

                ParseObject obj = new ParseObject("Messages");
                obj.put("sender", ParseUser.getCurrentUser().getUsername());
                obj.put("reciever",usern);
                obj.put("message",chatText.getText().toString());
                obj.put("screenValue","0");
                obj.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                                if(e==null){

                                        Log.i("Info","Sent Successfully");
                                        Toast.makeText(UserChat.this,"Sent Successfully",Toast.LENGTH_LONG).show();

                                }
                                else{
                                        Log.i("Info",e.getMessage());
                                        Toast.makeText(UserChat.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                }

                        }
                });
                chatText.setText("");
        }

        public  void refresh(View vi){





        }




}
