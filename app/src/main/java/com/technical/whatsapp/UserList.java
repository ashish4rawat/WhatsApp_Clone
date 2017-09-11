package com.technical.whatsapp;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {

        ArrayList<String> users;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_user_list);

                setTitle("Users");

                final ListView userListView = (ListView)findViewById(R.id.userListView);
                 users = new ArrayList<String>();

                ParseQuery<ParseUser> query =  ParseUser.getQuery();
                query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
                query.orderByAscending("username");


                query.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {

                                if(e==null){



                                        for(ParseUser user : objects){

                                              users.add(user.getUsername());

                                        }

                                        ArrayAdapter adapter = new ArrayAdapter(UserList.this,android.R.layout.simple_list_item_1,users);
                                        userListView.setAdapter(adapter);


                                }
                                else{

                                        Log.i("Info",e.getMessage());
                                        e.printStackTrace();


                                }

                        }
                });




                userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                Intent newIntent = new Intent(UserList.this,UserChat.class);
                                newIntent.putExtra("username",users.get(i));
                                startActivity(newIntent);



                        }
                });



        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.main_menu,menu);


                return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

                if(item.getItemId()==R.id.settings){



                }

                if(item.getItemId()==R.id.logout){

                        ParseUser.getCurrentUser().logOut();

                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);

                }

                return super.onOptionsItemSelected(item);
        }



}
