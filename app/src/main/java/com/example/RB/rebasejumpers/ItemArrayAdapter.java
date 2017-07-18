package com.example.RB.rebasejumpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Item array adapter.
 */
public class ItemArrayAdapter extends BaseAdapter {

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
        Checkable checkbox = (CheckBox) vi.findViewById(R.id.checkBox);
        Item item = list.get(position);
        text.setText(item.getItemName());
        name.setText(item.getName());
        checkbox.setChecked(item.isFound());
        return vi;
    }

    List<Item> filter(CharSequence charText) {
        List<Item> returnList = new ArrayList<>();
        if ("".equals(charText)){
            returnList.addAll(list);
        } else {
            if (!list.isEmpty()) {
                for (Item item : list) {
                    String itemName = item.getItemName();
                    if (itemName.contains(charText)) {
                        returnList.add(item);
                    }
                }
            }
        }

        return returnList;
    }
}
