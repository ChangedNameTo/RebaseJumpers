package com.example.RB.rebasejumpers;


/**
 * The type Item.
 */
public class Item {
    private final String itemName;
    private String name;
    private Boolean found;
    private double latitude;
    private double longitude;

    /**
     * Instantiates a new Item.
     *
     * @param itemName the item name
     * @param name     the name
     */
    public Item(String itemName, String name, Boolean found, double latitude, double longitude) {
        this.itemName = itemName;
        this.name = name;
        this.found = found;
        this.latitude = latitude;
        this.longitude = longitude;
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

    /**
     * Gets name.
     *
     * @return the name
     */
    String getItemName() {
        return itemName;
    }

    public Boolean isFound() {
        return found;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
