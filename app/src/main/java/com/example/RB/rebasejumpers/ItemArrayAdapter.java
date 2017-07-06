package com.example.RB.rebasejumpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Item> getList() {
        return list;
    }


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
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            long count = dataSnapshot.getChildrenCount();
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                postSnapshot.getRef().child("found").setValue(item.isFound());
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        return vi;
    }

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
