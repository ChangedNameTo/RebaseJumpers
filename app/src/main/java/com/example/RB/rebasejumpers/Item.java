package com.example.RB.rebasejumpers;


/**
 * The type Item.
 */
class Item {
    private final String itemName;
    private String name;
    private Boolean checked;

    /**
     * Instantiates a new Item.
     *
     * @param itemName the item name
     * @param name     the name
     */
    Item(String itemName, String name, Boolean checked) {
        this.itemName = itemName;
        this.name = name;
        this.checked = checked;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public CharSequence getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getItemName() {
        return itemName;
    }
    /**
     * Gets name.
     *
     * @return the name
     */

    Boolean isFound() {
        return checked;
    }
}
