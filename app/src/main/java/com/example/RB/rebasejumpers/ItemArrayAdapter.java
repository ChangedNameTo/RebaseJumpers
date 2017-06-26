package com.example.RB.rebasejumpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by andrey on 6/21/17.
 */

public class ItemArrayAdapter extends BaseAdapter {

    Context context;
    ArrayList<Item> list;
    private static LayoutInflater inflater = null;

    public ItemArrayAdapter(Context context, ArrayList<Item> list) {
        this.context = context;
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
            vi = inflater.inflate(R.layout.list_item, null);
        }
        TextView text = (TextView) vi.findViewById(R.id.list_item);
        text.setText(list.get(position).getName());
        return vi;
    }
}