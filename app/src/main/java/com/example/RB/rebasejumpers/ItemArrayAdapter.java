package com.example.RB.rebasejumpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The type Item array adapter.
 */
public class ItemArrayAdapter extends BaseAdapter {

    /**
     * The List.
     */
    private ArrayList<Item> list;

    /**
     * The LayoutInflater.
     */
    private static LayoutInflater inflater = null;

    /**
     * Instantiates a new Item array adapter.
     *
     * @param contextParam the context
     * @param listParam    the list
     */
    public ItemArrayAdapter(final Context contextParam,
                            final ArrayList<Item> listParam) {
        this.list = listParam;
        inflater = (LayoutInflater)
                contextParam.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(final int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position,
                        final View convertView,
                        final ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.list_item, null);
        }
        TextView text = (TextView) vi.findViewById(R.id.list_item);
        text.setText(list.get(position).getName());
        return vi;
    }
}
