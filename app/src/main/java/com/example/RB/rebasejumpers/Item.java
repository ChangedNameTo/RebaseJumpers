package com.example.RB.rebasejumpers;


/**
 * The type Item.
 */
class Item {
    private final String itemName;
    private String name;

    /**
     * Instantiates a new Item.
     *
     * @param itemName the item name
     * @param name     the name
     */
    Item(String itemName, String name) {
        this.itemName = itemName;
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public CharSequence getName() {
        if (itemName == null) {
            return "System";
        }
        return itemName;
    }

    public void setName(String name) {
        this.name = name;
    }
}
