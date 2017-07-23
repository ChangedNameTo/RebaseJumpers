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
     * @return the name
     */
    public CharSequence getName() {
        return name;
    }

    /**
     * Sets name.
     * @param name the name that we want to set the item to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the item's name.
     * @return the name of the item
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Gets the isFound boolean
     * @return the boolean
     */
    public Boolean isFound() {
        return found;
    }

    /**
     * Gets the latitude of the item
     * @return the boolean
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets the longitude of the item
     * @return the boolean
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the boolean checked.
     * @param found the boolean we are setting
     */
    void setIsFound(Boolean found) {
        this.found = found;
    }

}
