package com.example.maddy.maddylogin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deanghorbanian on 6/23/17.
 */

public class Model {
    /** Singleton instance */
    private static final Model _instance = new Model();
    public static Model getInstance() { return _instance; }

    private List<Item> items;

    public Model() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }
}
