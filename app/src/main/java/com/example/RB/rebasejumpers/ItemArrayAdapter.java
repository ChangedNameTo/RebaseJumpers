package com.example.RB.rebasejumpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Item array adapter.
 */
class ItemArrayAdapter extends BaseAdapter {

    /**
     * inflater.
     */
    private static LayoutInflater inflater = null;
    /**
     * list.
     */
    private final List<Item> list;

    /**
     * Instantiates a new Item array adapter.
     *
     * @param context the context
     * @param list    the list
     */
    ItemArrayAdapter(Context context, ArrayList<Item> list) {
        Context context1 = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.list_item, parent, false);
        }
        TextView text = (TextView) vi.findViewById(R.id.list_item);
        text.setText(list.get(position).getName());
        return vi;
    }
}