package com.example.RB.rebasejumpers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by andrey on 6/21/17.
 */
public class ItemView extends AppCompatActivity {

    private ListView activityItemView;
    private static final String TAG = "ItemView";

    // Firebase
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        // Initialize Authentication
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("items");
        mAuth = FirebaseAuth.getInstance();

        Button newItemButton = (Button) findViewById(R.id.new_item);
        newItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(ItemView.this, NewItemActivity.class));
            }
        });

        // Grab items
        mReference.orderByKey();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ArrayList<Item> itemList = new ArrayList<Item>();

                    String name = postSnapshot.child("name").getValue().toString();
                    String email = postSnapshot.child("email").getValue().toString();
                    itemList.add(new Item(name, email));

                    activityItemView = (ListView) findViewById(R.id.list_view);
                    activityItemView.setAdapter(new ItemArrayAdapter(ItemView.this, itemList));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
