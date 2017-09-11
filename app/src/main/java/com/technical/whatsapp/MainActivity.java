package com.technical.whatsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

        EditText usernameText,passwordText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                setTitle("PrivChat : Login");





                if(ParseUser.getCurrentUser()!=null){

                        reDirect();

                }

                ParseObject object = new ParseObject("fdsa");
                object.put("myNumber", "110919978");
                object.put("myString", "ashu");

                object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException ex) {
                                if (ex == null) {
                                        Log.i("Parse Result", "Successful!");
                                } else {
                                        Log.i("Parse Result", "Failed " + ex.toString());
                                }
                        }
                });


                 usernameText = (EditText)findViewById(R.id.usernameText);
                 passwordText = (EditText)findViewById(R.id.passwordText);





                ParseAnalytics.trackAppOpenedInBackground(getIntent());
        }


        // For Login
        public void loginClick(View view){

                if(checkForEmpty()){

                        ParseUser.logInInBackground(usernameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {

                                        if(e==null){

                                                Log.i("Info","Logged In Successfullly ");
                                                reDirect();
                                        }
                                        else{

                                                Log.i("Info",e.getMessage());
                                                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                                        }

                                }
                        });

                }
                else{

                        Toast.makeText(this,"Enter Username or Password",Toast.LENGTH_LONG).show();

                }


        }



        // FOr sign Up
        public void signupClick(View vewi){

                if(checkForEmpty()){

                        ParseUser user = new ParseUser();
                        user.setUsername(usernameText.getText().toString());
                        user.setPassword(passwordText.getText().toString());

                        user.signUpInBackground(new SignUpCallback() {
                                @Override
                                public void done(ParseException e) {


                                        if(e==null){

                                                Log.i("Info","Signed Up Successfullly ");
                                                Toast.makeText(MainActivity.this,"Signed Up Successfully",Toast.LENGTH_LONG).show();
                                                reDirect();

                                        }
                                        else{

                                                Log.i("Info",e.getMessage());
                                                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                                        }


                                }
                        });


                }
                else{

                        Toast.makeText(this,"Enter Username or Password",Toast.LENGTH_LONG).show();

                }

        }




        public boolean checkForEmpty(){

                if(usernameText.getText().toString().trim()=="" || passwordText.getText().toString().trim()=="" )
                {
                        return false;

                }
                else {

                        return true;
                }


        }

        public void reDirect(){


                Intent intent = new Intent(this,UserList.class);
                startActivity(intent);


        }





}
