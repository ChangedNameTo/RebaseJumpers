package com.example.RB.rebasejumpers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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


    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        //Firebase
        FirebaseDatabase mDatabase;
        DatabaseReference mReference;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        // Initialize Authentication
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("items");

        Button newItemButton = (Button) findViewById(R.id.new_item);
        newItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(ItemView.this, NewItemActivity.class));
            }
        });

        // Grab items
        mReference.orderByKey();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ArrayList<Item> itemList = new ArrayList<Item>();
                    for(DataSnapshot a: postSnapshot.getChildren()) {
                        System.out.println(a.getKey());
                        String name = a.child("name").getValue().toString();
                        String email = a.child("email").getValue().toString();
                        itemList.add(new Item(name, email));
                    }
                    activityItemView = (ListView) findViewById(R.id.list_view);
                    activityItemView.setAdapter(
                            new ItemArrayAdapter(ItemView.this, itemList));
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}



