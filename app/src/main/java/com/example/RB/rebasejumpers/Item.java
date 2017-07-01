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
        if (itemName == null) {
            return "System";
        }
        return itemName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isChecked() {
        return checked;
    }
}
