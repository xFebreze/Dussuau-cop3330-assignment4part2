/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Alek Dussuau
 */
package ucf.assignments;

import org.junit.jupiter.api.Test;


import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemControllerTest {

    @Test
    public void addItemTest(){
        //Make a new item model
        //call addItem
        //get display info
        //assert equal w/ input values

        ItemController AppTest = new ItemController();

        AppTest.addItem("Item 1","2001-02-01");
        AppTest.toDoList.get(0).getDescription();
        assertEquals("Item 1", AppTest.toDoList.get(0).getDescription());
        assertEquals("2001-02-01", AppTest.toDoList.get(0).getDueDate());
        assertEquals(false, AppTest.toDoList.get(0).getComplete());
    }

    @Test
    public void removeItemTest(){
        //make a new item model
        //add two items
        //remove one item
        //check if list size is 1

        ItemController AppTest = new ItemController();

        AppTest.addItem("Item1","2001-02-01");
        AppTest.addItem("Item2","2002-02-01");
        AppTest.removeItem(AppTest.toDoList.get(1));
        assertEquals(1,AppTest.toDoList.size());
    }

    @Test
    public void clearItemsTest(){
        //make a new item model
        //add two items
        //call clear items
        //check if list size is 0

        ItemController AppTest = new ItemController();

        AppTest.addItem("Item1","2001-02-01");
        AppTest.addItem("Item2","2002-02-01");
        AppTest.clearItems();
        assertEquals(0,AppTest.toDoList.size());
    }

    @Test
    public void editItemDescriptionTest(){
        //make new item model
        //add an item to todolist
        //call edit item description on said item
        //assert equals item description and input value

        ItemController AppTest = new ItemController();

        AppTest.addItem("Item1","2001-02-01");
        AppTest.editItemDescription("new name", AppTest.toDoList.get(0));
        assertEquals("new name", AppTest.toDoList.get(0).getDescription());
    }

    @Test
    public void editItemDueDateTest(){
        //make new item model
        //add an item to todolist
        //call edit item due date on said item
        //assert equals item Due date and input value

        ItemController AppTest = new ItemController();

        AppTest.addItem("Item1","2001-02-01");
        AppTest.editItemDueDate("2020-20-20", AppTest.toDoList.get(0));
        assertEquals("2020-20-20", AppTest.toDoList.get(0).getDueDate());
    }

    @Test
    public void markItemCompletionTestTrue(){
        //make new item model
        //add an item to todolist
        //call mark item completion on said item
        //assert equals new value and true

        ItemController AppTest = new ItemController();

        AppTest.addItem("Item1","2001-02-01");
        AppTest.alterItemComplete(AppTest.toDoList.get(0));
        assertEquals(true, AppTest.toDoList.get(0).getComplete());
    }

    @Test
    public void markItemCompletionTestFalse(){
        //make new item model
        //add an item to todolist
        //call mark item completion on said item twice
        //assert equals new value and false

        ItemController AppTest = new ItemController();

        AppTest.addItem("Item1","2001-02-01");
        AppTest.alterItemComplete(AppTest.toDoList.get(0));
        AppTest.alterItemComplete(AppTest.toDoList.get(0));
        assertEquals(false, AppTest.toDoList.get(0).getComplete());
    }

    @Test
    public void DisplayTest(){

        //all of my displays are in one function that operates via checkboxes
        //I can't figure out how to do checkboxes as test

    }

    @Test
    public void LoadTest(){
        //Make new item controller
        //Load a list from a resource folder
        //Check items of todolist to make sure they are identical to whats in txt file

        ItemController AppTest = new ItemController();

        AppTest.loadList(new File("resources/TestLoad.txt"));
        assertEquals("Item1", AppTest.toDoList.get(0).getDescription());
        assertEquals("2021-07-11", AppTest.toDoList.get(0).getDueDate());
        assertEquals(false, AppTest.toDoList.get(0).getComplete());
        assertEquals("Item2", AppTest.toDoList.get(1).getDescription());
        assertEquals("2021-07-12", AppTest.toDoList.get(1).getDueDate());
        assertEquals(false, AppTest.toDoList.get(1).getComplete());
        assertEquals("Item3", AppTest.toDoList.get(2).getDescription());
        assertEquals("2021-07-12", AppTest.toDoList.get(2).getDueDate());
        assertEquals(true, AppTest.toDoList.get(2).getComplete());
        assertEquals("Item4", AppTest.toDoList.get(3).getDescription());
        assertEquals("2021-07-14", AppTest.toDoList.get(3).getDueDate());
        assertEquals(false, AppTest.toDoList.get(3).getComplete());
    }

    @Test
    public void SaveTest(){
        //make new item controller
        //add item to the list
        //save list to resource folder
        //clear list
        //load list
        //assert equals with know item data

        ItemController AppTest = new ItemController();

        AppTest.addItem("SaveFileCheck","1212-12-12");
        AppTest.saveList(new File("resources/saveFile.txt"));
        AppTest.clearItems();
        AppTest.loadList(new File("resources/saveFile.txt"));
        assertEquals("SaveFileCheck", AppTest.toDoList.get(0).getDescription());
        assertEquals("1212-12-12", AppTest.toDoList.get(0).getDueDate());
        assertEquals(false, AppTest.toDoList.get(0).getComplete());

    }
}
