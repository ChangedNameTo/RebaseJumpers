package com.example.RB.rebasejumpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Item array adapter.
 */
class ItemArrayAdapter extends BaseAdapter {

    private LayoutInflater inflater = null;
    private final List<Item> list;
    private final List<Item> searchList = null;

    /**
     * Instantiates a new Item array adapter.
     *
     * @param context      the context
     * @param listParam    the list
     */
    ItemArrayAdapter(Context context, final List<Item> listParam) {
        //noinspection AssignmentToCollectionOrArrayFieldFromParameter
        this.list = listParam;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Gets the count of the list size
     * @return the count
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Gets the item's position
     * @param position the position number
     * @return the object at that position
     */
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    /**
     * Gets the item's id
     * @param position the position number
     * @return the item's id in long form
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Gets the list
     * @return the list of items
     */
    public List<Item> getList() {
        return list;
    }


    /**
     * Gets the view of the items
     * @param position the position number
     * @param convertView the view that is converted
     * @param parent the ViewGroup parent
     * @return the view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.list_item, parent, false);
        }
        TextView text = (TextView) vi.findViewById(R.id.list_item);
        TextView name = (TextView) vi.findViewById(R.id.item_name);
        CheckBox checkbox = (CheckBox) vi.findViewById(R.id.checkBox);
        Item item = list.get(position);
        //
        text.setText(item.getItemName());
        name.setText(item.getName());
        checkbox.setChecked(item.isFound());

        checkbox.setTag(position);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                int position = (int) checkBox.getTag();
                final Item item = list.get(position);
                item.setIsFound(checkBox.isChecked());
                FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                if (mUser != null) {
                    String name = mUser.getEmail();
                    name = name.replace(".", "");
                    DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
                    mReference.child("items").child(name).orderByChild("itemName").equalTo
                            (item.getItemName()).addListenerForSingleValueEvent(new
                                ValueEventListener() {
                                    /**
                                     * The onDataChange method to get the data from Firebase
                                     * @param dataSnapshot each snapshot of data from Firebase
                                     */
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                postSnapshot.getRef().child("found").setValue(item.isFound());
                            }
                        }

                                    /**
                                     * The onCancelled method
                                     * @param databaseError to catch the database error
                                     */
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        return vi;
    }

    /**
     * Filters through the items
     * @param charText takes in the user's characters they type to search through the app
     * @return the list of items corresponding to what the user typed in
     */
    List<Item> filter(CharSequence charText) {
        List<Item> returnList = new ArrayList<>();
        if ("".equals(charText)){
            returnList.addAll(list);
        } else {
            if (!list.isEmpty()) {
                for (Item item : list) {
                    if (item.getItemName().contains(charText)) {
                        returnList.add(item);
                    }
                }
            }
        }

        return returnList;
    }
}
