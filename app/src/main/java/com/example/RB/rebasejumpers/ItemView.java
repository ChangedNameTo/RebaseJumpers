package com.example.RB.rebasejumpers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * The type Item view.
 */
public class ItemView extends AppCompatActivity {

    /**
     * The activityItemView.
     */
    private ListView activityItemView;
    /**
     * Error tag.
     */
    private static final String TAG = "ItemView";

    private EditText search_bar;

    private ItemArrayAdapter firebaseAdapter;

    //Firebase
    private final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference mReference = mDatabase.getReference("items");

    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        search_bar = (EditText) findViewById(R.id.search_bar);

        search_bar.addTextChangedListener(new TextWatcher() {

                                              @Override
                                              public void afterTextChanged(Editable arg0) {
                                                  setItemList();
                                              }

                                              @Override
                                              public void beforeTextChanged(CharSequence
                                                                                    arg0,
                                                                            int arg1,
                                                                            int arg2,
                                                                            int arg3) {
                                                  setItemList();
                                              }

                                              @Override
                                              public void onTextChanged(CharSequence arg0,
                                                                        int arg1,
                                                                        int arg2,
                                                                        int arg3) {
                                                  setItemList();
                                              }

                                          });

        Button newItemButton = (Button) findViewById(R.id.new_item);
        newItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(ItemView.this, NewItemActivity.class));
            }
        });

        setItemList();
    }

    private void setItemList() {
        mReference.orderByKey();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                ArrayList<Item> itemList = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    for(DataSnapshot a: postSnapshot.getChildren()) {
                        String itemName = a.child("itemName").getValue().toString();
                        String name = a.child("name").getValue().toString();
                        itemList.add(new Item(itemName, name));
                    }
                }
                // Grabs the view
                activityItemView = (ListView) findViewById(R.id.list_view);

                // Grabs the filter string (can be null)
                String filterString = search_bar.getText().toString().toLowerCase();

                // Creates me adapters
                firebaseAdapter = new ItemArrayAdapter(ItemView.this, itemList);
                firebaseAdapter = new ItemArrayAdapter(ItemView.this, firebaseAdapter.filter(filterString));

                // Attaches the list
                activityItemView.setAdapter(firebaseAdapter);
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}



