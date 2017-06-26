package com.example.RB.rebasejumpers;


class Item {
    private final String itemName;

    Item(String itemName, String name) {
        this.itemName = itemName;
        String name1 = name;
    }

    public String getName() {
        if(itemName==null) {
            return "System";
        }
        return itemName;
    }

    /*public String getEmail() {
        return name;
    }*/

    /*public void setName(String name) {
        this.name = name;
    }*/
}
