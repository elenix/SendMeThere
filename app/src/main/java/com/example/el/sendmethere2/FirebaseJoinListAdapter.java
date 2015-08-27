package com.example.el.sendmethere2;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kiwi on 25/8/2015.
 */
public abstract class FirebaseJoinListAdapter<T> extends BaseAdapter {

    private Firebase mRef;
    private Query mKeyRef;
    private Class<T> mModelClass;
    private int mLayout;
    private LayoutInflater mInflater;
    private List<T> mModels;
    private List<String> mKeys;
    private ChildEventListener mListener;
    private T temptModel;
    private String tempKey;


    /**
     * @param mRef        The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                    combination of <code>limit()</code>, <code>startAt()</code>, and <code>endAt()</code>,
     * @param mModelClass Firebase will marshall the data at a location into an instance of a class that you provide
     * @param mLayout     This is the mLayout used to represent a single list item. You will be responsible for populating an
     *                    instance of the corresponding view with the data from an instance of mModelClass.
     * @param activity    The activity containing the ListView
     */
    public FirebaseJoinListAdapter(Query mKeyRef, Firebase mRef, Class<T> mModelClass, int mLayout, Activity activity) {
        this.mRef = mRef;
        this.mKeyRef = mKeyRef;
        this.mModelClass = mModelClass;
        this.mLayout = mLayout;
        mInflater = activity.getLayoutInflater();
        mModels = new ArrayList<T>();
        mKeys = new ArrayList<String>();
        // Look for all child events. We will then map them to our own internal ArrayList, which backs ListView
        mListener = this.mKeyRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Query queryRef = FirebaseJoinListAdapter.this.mRef.orderByKey().equalTo(dataSnapshot.getKey());
                final String pChild = previousChildName;
                queryRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                        System.out.println("Value of DataSnapshot:" + dataSnapshot.getValue());
                        tempKey = dataSnapshot.getKey();
                        temptModel = dataSnapshot.getValue(FirebaseJoinListAdapter.this.mModelClass);
                        T model = temptModel;
                        String key = tempKey;

                        // Insert into the correct location, based on previousChildName
                        if (pChild == null) {
                            mModels.add(0, model);
                            mKeys.add(0, key);
                        } else {
                            int previousIndex = mKeys.indexOf(pChild);
                            int nextIndex = previousIndex + 1;
                            if (nextIndex == mModels.size()) {
                                mModels.add(model);
                                mKeys.add(key);
                            } else {
                                mModels.add(nextIndex, model);
                                mKeys.add(nextIndex, key);
                            }
                        }

                        notifyDataSetChanged();
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
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // One of the mModels changed. Replace it in our list and name mapping
                Query queryRef = FirebaseJoinListAdapter.this.mRef.orderByKey().equalTo(dataSnapshot.getKey());
                queryRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                        System.out.println("Value of DataSnapshot:" + dataSnapshot.getValue());
                        tempKey = dataSnapshot.getKey();
                        temptModel = dataSnapshot.getValue(FirebaseJoinListAdapter.this.mModelClass);
                        T newModel = temptModel;
                        String key = tempKey;
                        int index = mKeys.indexOf(key);

                        mModels.set(index, newModel);

                        notifyDataSetChanged();
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
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                // A model was removed from the list. Remove it from our list and the name mapping
                Query queryRef = FirebaseJoinListAdapter.this.mRef.orderByKey().equalTo(dataSnapshot.getKey());
                queryRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                        System.out.println("Value of DataSnapshot:" + dataSnapshot.getValue());
                        tempKey = dataSnapshot.getKey();
                        temptModel = dataSnapshot.getValue(FirebaseJoinListAdapter.this.mModelClass);
                        String key = tempKey;
                        int index = mKeys.indexOf(key);

                        mKeys.remove(index);
                        mModels.remove(index);

                        notifyDataSetChanged();
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
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

                // A model changed position in the list. Update our list accordingly
                Query queryRef = FirebaseJoinListAdapter.this.mRef.orderByKey().equalTo(dataSnapshot.getKey());
                final String pChild = previousChildName;
                queryRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                        System.out.println("Value of DataSnapshot:" + dataSnapshot.getValue());
                        tempKey = dataSnapshot.getKey();
                        temptModel = dataSnapshot.getValue(FirebaseJoinListAdapter.this.mModelClass);
                        T newModel = temptModel;
                        String key = tempKey;
                        int index = mKeys.indexOf(key);
                        mModels.remove(index);
                        mKeys.remove(index);
                        if (pChild == null) {
                            mModels.add(0, newModel);
                            mKeys.add(0, key);
                        } else {
                            int previousIndex = mKeys.indexOf(pChild);
                            int nextIndex = previousIndex + 1;
                            if (nextIndex == mModels.size()) {
                                mModels.add(newModel);
                                mKeys.add(key);
                            } else {
                                mModels.add(nextIndex, newModel);
                                mKeys.add(nextIndex, key);
                            }
                        }
                        notifyDataSetChanged();
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
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("FirebaseListAdapter", "Listen was cancelled, no more updates will occur");
            }

        });
    }

    public void cleanup() {
        // We're being destroyed, let go of our mListener and forget about all of the mModels
        mRef.removeEventListener(mListener);
        mModels.clear();
        mKeys.clear();
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @Override
    public Object getItem(int i) {
        return mModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(mLayout, viewGroup, false);
        }

        T model = mModels.get(i);
        // Call out to subclass to marshall this model into the provided view
        populateView(view, model);
        return view;
    }

    /**
     * Each time the data at the given Firebase location changes, this method will be called for each item that needs
     * to be displayed. The arguments correspond to the mLayout and mModelClass given to the constructor of this class.
     * <p/>
     * Your implementation should populate the view using the data contained in the model.
     *
     * @param v     The view to populate
     * @param model The object containing the data used to populate the view
     */
    protected abstract void populateView(View v, T model);
}