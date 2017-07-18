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
    String getItemName() {
        return itemName;
    }

    /**
     * Gets the isFound boolean
     * @return the boolean
     */
    Boolean isFound() {
        return checked;
    }

    /**
     * Sets the boolean checked.
     * @param checked the boolean we are setting
     */
    void setIsFound(Boolean checked) {
        this.checked = checked;
    }

}
