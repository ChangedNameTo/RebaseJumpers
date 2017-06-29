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
 * The type New item activity.
 */
@SuppressWarnings("ALL")
public class NewItemActivity extends AppCompatActivity {

    /**
     * UI reference.
     */
    private EditText mItemName;

    /**
     * The M reference.
     */
    private final DatabaseReference mReference = FirebaseDatabase
            .getInstance().getReference();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item_creation);

        mItemName = (EditText) findViewById(R.id.item_name);

        Button newItemButton = (Button)
                findViewById(R.id.submit_new_item_buttom);
        newItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                newItem();
            }
        });
    }

    /**
     * Puts a new item in the firebase db.
     */
    private void newItem() {
        /*
      The M auth.
     */
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        /*
      The M user.
     */
        FirebaseUser mUser = mAuth.getCurrentUser();

        String name = null;
        if (mUser != null) {
            name = mUser.getDisplayName();
        }
        if (name == null) {
            name = "System";
        }
        String itemName = mItemName.getText().toString();

        Item newItem = new Item(itemName, name);

        DatabaseReference newReference =  mReference.child("items").child(name).push();

        newReference.setValue(newItem);

        startActivity(new Intent(NewItemActivity.this, ItemView.class));
    }
}
