package com.example.el.sendmethere2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class CustomerHome extends AppCompatActivity {

    private Firebase mref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_home);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button myRequests = (Button) findViewById(R.id.myRequests);
        Button mbuttonNew = (Button) findViewById(R.id.buttonNew);
        // final TextView mTextCondition = (TextView) findViewById(R.id.textViewDashboard);                  //Text and button declarations

        mref = new Firebase("https://incandescent-heat-5066.firebaseio.com/request");                      //Links our database
        Query query = mref.orderByKey().equalTo("5");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("Request DataSnap:" + dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mref.addValueEventListener(new ValueEventListener() {                                       //Event listener will notify us of changes from database

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Fetches the information in database
                /*String newCondition = (String) dataSnapshot.getValue();
                mTextCondition.setText(newCondition); */
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mbuttonNew.setOnClickListener(new View.OnClickListener() {                                 //mbuttonNew button set value "Rainy" to database
            @Override
            public void onClick(View v) {
                //mref.setValue("Create new request");
                Intent intent2 = new Intent(getApplicationContext(), NewRequest.class);
                startActivity(intent2);
            }

        });

        myRequests.setOnClickListener(new View.OnClickListener() {                                 //mButtonSunny button set value "Sunny" to database
            @Override
            public void onClick(View v) {
                // mref.setValue("View your requests");
                Intent intent2 = new Intent(getApplicationContext(), UserRequestListActivity.class);
                startActivity(intent2);
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    boolean canAddItem = false;

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //When about action item is clicked
        if (id == R.id.profile) {
            //Create Intent for Profile Activity
            Intent profileIntent = new Intent(this,Profile.class);
            //Start Profile Activity
            startActivity(profileIntent);
            return true;
        }
        //When about action item is clicked
        if (id == R.id.about) {
            //Create Intent for about Activity
            Intent aboutIntent = new Intent(this,About.class);
            //Start About Activity
            startActivity(aboutIntent);
            return true;
        }

        //When logout action item is clicked
        if (id == R.id.logout) {
            //Create Intent for logout Activity
            Intent productIntent = new Intent(this,Login_Page.class);
            //Start logout Activity
            startActivity(productIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(canAddItem){
            menu.getItem(0).setIcon(R.drawable.menu);
        }
        else{
            menu.getItem(0).setIcon(R.drawable.home);
            canAddItem = true;
        }

        return super.onPrepareOptionsMenu(menu);
    }
}
