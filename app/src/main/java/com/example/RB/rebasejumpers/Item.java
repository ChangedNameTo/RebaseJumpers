package com.example.RB.rebasejumpers;

/**
 * Created by andrey on 6/21/17.
 */

public class Item {
    private String itemName;
    private String name;

    public Item(String itemName, String name) {
        this.itemName = itemName;
        this.name = name;
    }

    public String getName() {
        if(itemName==null) {
            return "System";
        }
        return itemName;
    }

    public String getEmail() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
