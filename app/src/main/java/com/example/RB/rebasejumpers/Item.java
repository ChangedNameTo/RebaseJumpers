package com.example.RB.rebasejumpers;


/**
 * The type Item.
 */
class Item {
    private final String itemName;

    /**
     * Instantiates a new Item.
     *
     * @param itemName the item name
     * @param name     the name
     */
    Item(String itemName, String name) {
        this.itemName = itemName;
        String name1 = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
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
