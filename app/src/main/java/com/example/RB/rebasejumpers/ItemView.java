package com.example.RB.rebasejumpers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
@SuppressWarnings("ALL")
public class ItemView extends AppCompatActivity {

    /**
     * The activityItemView.
     */
    private ListView activityItemView;
    /**
     * Error tag.
     */
    private static final String TAG = "ItemView";
    private String isFound;


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

        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (checkBox.isChecked()) {
                    isFound = "true";
                } else {
                    isFound = "false";
                }
            }
        });

        // Grab items
        mReference.orderByKey();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                ArrayList<Item> itemList = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    for(DataSnapshot a: postSnapshot.getChildren()) {
                        String itemName = a.child("itemName").getValue().toString();
                        String name = a.child("name").getValue().toString();
                        Object checked = a.child("checked").getValue();
                        if (checked != null) {
                            itemList.add(new Item(itemName, name, (Boolean) checked));
                        } else {
                            itemList.add(new Item(itemName, name, false));
                        }
                    }
                }
                activityItemView = (ListView) findViewById(R.id.list_view);
                activityItemView.setAdapter(
                        new ItemArrayAdapter(ItemView.this, itemList));
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
