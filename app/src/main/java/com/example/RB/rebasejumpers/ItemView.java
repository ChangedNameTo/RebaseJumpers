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

    /**
     * The onCreate method
     * @param savedInstanceState the Bundle of the instance state
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        search_bar = (EditText) findViewById(R.id.search_bar);

        search_bar.addTextChangedListener(new TextWatcher() {

            /**
             * The afterTextChanged method
             * @param arg0 the arguments from the user
             */
            @Override
            public void afterTextChanged(Editable arg0) {
                setItemList();
            }

            /**
             * The beforeTextChanged
             * @param arg0 the 0th argument
             * @param arg1 the 1st argument
             * @param arg2 the 2nd argument
             * @param arg3 the 3rd argument
             */
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                setItemList();
            }

            /**
             * The onTextChanged method
             * @param arg0 the 0th argument
             * @param arg1 the 1st argument
             * @param arg2 the 2nd argument
             * @param arg3 the 3rd argument
             */
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
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
                    for(DataSnapshot a : postSnapshot.getChildren()) {

                        DataSnapshot itemSnapshot = a.child("itemName");
                        Object itemSnapshotValue = itemSnapshot.getValue();
                        String itemName = itemSnapshotValue.toString();

                        DataSnapshot nameSnapshot = a.child("name");
                        Object nameValue = nameSnapshot.getValue();
                        String name = nameValue.toString();

                        DataSnapshot checkedSnapshot = a.child("checked");
                        Object checked = checkedSnapshot.getValue();

                        Object latitude = a.child("latitude").getValue();
                        Object longitude = a.child("longitude").getValue();

                        if (checked != null) {
                            itemList.add(new Item(itemName, name, (Boolean) checked,
                                    (double) latitude, (double) longitude));
                        } else {
                            itemList.add(new Item(itemName, name, false, (double) latitude, (double) longitude));
                        }
                    }
                }
                // Grabs the view
                activityItemView = (ListView) findViewById(R.id.list_view);

                // Grabs the filter string (can be null)
                Editable filterEditable = search_bar.getText();
                String filterString = filterEditable.toString();
                filterString = filterString.toLowerCase();

                // Creates me adapters
                firebaseAdapter = new ItemArrayAdapter(ItemView.this, itemList);
                firebaseAdapter = new ItemArrayAdapter(ItemView.this,
                        firebaseAdapter.filter(filterString));

                // Attaches the list
                activityItemView.setAdapter(firebaseAdapter);
            }

            /**
             * The onCancelled method
             * @param databaseError the DatabaseError message
             */
            @Override
            public void onCancelled(final DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}



