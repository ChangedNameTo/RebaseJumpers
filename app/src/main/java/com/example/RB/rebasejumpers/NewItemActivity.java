package com.example.RB.rebasejumpers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by will on 6/23/17.
 */
public class NewItemActivity extends AppCompatActivity {

    // UI references
    private EditText mItemName;

    // Database references
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item_creation);

        // Initialize Authentication
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mItemName = (EditText) findViewById(R.id.item_name);

        Button newItemButton = (Button) findViewById(R.id.submit_new_item_buttom);
        newItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                newItem();
            }
        });
    }

    private void newItem() {
        String name = mUser.getDisplayName();
        if(name == null) {
            name = "System";
        }
        String itemName = mItemName.getText().toString();

        Item newItem = new Item(itemName, name);

        mReference.child("items").child(name).setValue(newItem);

        startActivity(new Intent(NewItemActivity.this, ItemView.class));
    }
}
