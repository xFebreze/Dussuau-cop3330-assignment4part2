/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Alek Dussuau
 */
package ucf.assignments;

public class Item {

    private String description;
    private String dueDate;
    private Boolean complete;

    //constructor
    public Item(String description, String dueDate, Boolean complete) {
        this.description = description;
        this.dueDate = dueDate;
        this.complete = complete;
    }

    //getters
    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public Boolean getComplete() {
        return complete;
    }


}
