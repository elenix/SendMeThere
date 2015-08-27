package com.example.el.sendmethere2;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class RequestListActivity extends AppCompatActivity {
    ListView listView ;

    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private RequestListAdapter mRequestListAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
        listView = (ListView) findViewById(R.id.requestList);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        listView = (ListView) findViewById(R.id.requestList);
        // Tell our list adapter that we only want 50 messages at a time
        mFirebaseRef = new Firebase( "https://incandescent-heat-5066.firebaseio.com/");
        AuthData authData = mFirebaseRef.getAuth();
        if(authData == null) {
            Intent intent1 = new Intent(getApplicationContext(), DriverMenu.class);
            startActivity(intent1);

            Toast.makeText(getApplicationContext(),
                    "Please Login First", Toast.LENGTH_LONG).show();
        } else {
            mFirebaseRef = mFirebaseRef.child("request");
            mRequestListAdapter = new RequestListAdapter(mFirebaseRef, this, R.layout.request);
            listView.setAdapter(mRequestListAdapter);
            mRequestListAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    listView.setSelection(mRequestListAdapter.getCount() - 1);
                }
            });
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Request r = (Request)parent.getAdapter().getItem(position);
                // When clicked, show a toast with the TextView text
                Intent intent = new Intent(getApplicationContext(), Activity_driver.class);
                intent.putExtra("rid", r.getRid());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.homemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home) {
            Intent homeIntent = new Intent(this,CustomerHome.class);
            startActivity(homeIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
