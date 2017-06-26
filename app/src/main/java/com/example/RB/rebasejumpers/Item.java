package com.example.RB.rebasejumpers;

/**
 * Created by andrey on 6/21/17.
 */
public class Item {

    /**
     * Stores item name.
     */
    private String itemName;
    /**
     * Stores name.
     */
    private String name;

    /**
     * Instantiates a new Item.
     *
     * @param itemNameParam the item name
     * @param nameParam     the name
     */
    public Item(final String itemNameParam, final String nameParam) {
        this.itemName = itemNameParam;
        this.name = nameParam;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        if (itemName == null) {
            return "System";
        }
        return itemName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param nameParam the name
     */
    public void setName(final String nameParam) {
        this.name = nameParam;
    }
}
