/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Alek Dussuau
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;


public class ItemController implements Initializable {

    ObservableList <Item> toDoList = FXCollections.observableArrayList();

    @FXML TextField DescriptionField;
    @FXML DatePicker DueDate;
    @FXML TableView<Item> TableDisplay;
    @FXML TableColumn<Item, String> TableDescription;
    @FXML TableColumn<Item, String> TableDueDate;
    @FXML TableColumn<Item, String> TableCompletion;
    @FXML CheckBox completeItems;
    @FXML CheckBox incompleteItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
        TableDueDate.setCellValueFactory(new PropertyValueFactory<Item, String>("dueDate"));
        TableCompletion.setCellValueFactory(new PropertyValueFactory<Item, String>("complete"));
    }

    @FXML
    public void addItemButtonClicked(ActionEvent actionevent){

        //Convert text field and date picker to strings
        //call add item w/ strings

        String date = DueDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String str = DescriptionField.getText();
        addItem(str, date);
    }

    @FXML
    public void removeItemButtonClicked(ActionEvent actionevent){

        //grab the selected item
        //call remove item given selected item

        Item temp = TableDisplay.getSelectionModel().getSelectedItem();
        removeItem(temp);
    }

    @FXML
    public void clearItemsButtonCLicked(ActionEvent actionevent){

        //call ClearItems

        clearItems();
    }

    @FXML
    public void editItemDescriptionButtonClicked(ActionEvent actionevent){

        //convert textField to str
        //get selected Item
        //call editItemDescription

        String str = DescriptionField.getText();
        Item temp = TableDisplay.getSelectionModel().getSelectedItem();
        editItemDescription(str, temp);
    }

    @FXML
    public void editItemDueDateButtonClicked(ActionEvent actionevent){

        //convert dueDate to date
        //get selected Item
        //call editItemDueDate

        String date = DueDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Item temp = TableDisplay.getSelectionModel().getSelectedItem();
        editItemDueDate(date, temp);
    }

    @FXML
    public void alterCompletionButtonClicked(ActionEvent actionevent){

        //gets selected Item
        //changes the completion status

        Item temp = TableDisplay.getSelectionModel().getSelectedItem();
        alterItemComplete(temp);
    }

    @FXML
    public void DisplayItemsButtonClicked(ActionEvent actionevent){
        //when display items button is clicked
        displayItems();
    }

    @FXML
    public void saveListButtonClicked(ActionEvent actionevent){
        //when save list button is clicked
        saveList();
    }

    @FXML
    public void loadListButtonClicked(ActionEvent actionevent){
        //when load list button is clicked
        loadList();
    }

    public void addItem(String tempDescription, String tempDueDate){

        //add item to list
        //add item to Display

        toDoList.add(new Item (tempDescription,tempDueDate,false));
        displayItems();
    }

    public void removeItem(Item tempItem){

        //remove item from list
        //remove item from display

        toDoList.remove(tempItem);
        displayItems();
    }

    public void clearItems(){

        //clear all items from list
        //clear all items from display

        toDoList.clear();
        displayItems();
    }

    public void editItemDescription(String tempDescription, Item tempItem){

        //find list index
        //edit item on list
        //edit item on table display

        int tempIndex = toDoList.indexOf(tempItem);
        toDoList.set(tempIndex, new Item (tempDescription, tempItem.getDueDate(), tempItem.getComplete()));
        displayItems();
    }

    public void editItemDueDate(String tempDueDate, Item tempItem){

        //find list index
        //edit item on list
        //edit item on table display

        int tempIndex = toDoList.indexOf(tempItem);
        toDoList.set(tempIndex, new Item (tempItem.getDescription(), tempDueDate, tempItem.getComplete()));
        displayItems();
    }

    public void alterItemComplete(Item tempItem){

        //call Item.updateCompletion(Item)
        //This function should switch the completion on or off

        int tempIndex = toDoList.indexOf(tempItem);

        if(!tempItem.getComplete()){
            toDoList.set(tempIndex, new Item (tempItem.getDescription(), tempItem.getDueDate(), true));
            displayItems();
        }else{
            toDoList.set(tempIndex, new Item (tempItem.getDescription(), tempItem.getDueDate(), false));
            displayItems();
        }

    }

    public void saveList(){

        //create a fileChooser
        //set data for said fileChooser
        //make a file to store for fileChooser
        //write to created file

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save List");
        fileChooser.setInitialFileName("myList");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text files", "*.txt"));

        FileWriter fileWrite = null;
        String fileData = "";

        try
        {
            File file = fileChooser.showSaveDialog(new Stage());
            fileWrite = new FileWriter(file);
            for(int i = 0; i < toDoList.size(); i++){
                fileData += toDoList.get(i).getDescription() + "\n" +
                            toDoList.get(i).getDueDate() + "\n" +
                            toDoList.get(i).getComplete() + "\n\n";
            }

            fileWrite.write(fileData);
            fileWrite.close();
        }
        catch (Exception ex)
        {

        }

    }

    public void loadList(){

        //call read file to get the data from the file
        //clear the current toDoList
        //store data from file into toDolist
        //updateDisplay

        List<String> data = readFile();
        toDoList.clear();
        Boolean temp = null;

        for(int i = 0; i < data.size(); i += 4){
            System.out.println(data.get((i+3)));
            if(data.get(i+2).equals("true")){
                temp = true;
            }else{
                temp = false;
            }
            toDoList.add(new Item(data.get(i),data.get(i+1),temp));
        }

        displayItems();
    }

    public List<String> readFile(){

        //Create a fileChooser
        //set data to fileChooser
        //make a scanner to read the selected file
        //Store the file data into an arrayList
        //return list to loadFile method

        List<String> ret = new ArrayList<>();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open txt file");
        File file = fileChooser.showOpenDialog(new Stage());
        Scanner reader = null;

        try
        {
            reader = new Scanner(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        while(reader.hasNextLine()){
            String fileLine = reader.nextLine();
            ret.add(fileLine);
        }


        return ret;
    }

    public void displayItems(){

        //clear all items from list
        //check to see what display is selected
        //display based on selected display
        if(this.TableDisplay != null){
            TableDisplay.getItems().clear();
            if(completeItems.isSelected() && incompleteItems.isSelected()){
                for(int i = 0; i < toDoList.size(); i++){
                    TableDisplay.getItems().add(toDoList.get(i));
                }
            }else if(completeItems.isSelected()){
                for(int i = 0; i < toDoList.size(); i++){
                    if((toDoList.get(i).getComplete())){
                        TableDisplay.getItems().add(toDoList.get(i));
                    }
                }
            }else if(incompleteItems.isSelected()) {
                for (int i = 0; i < toDoList.size(); i++) {
                    if ((!toDoList.get(i).getComplete())) {
                        TableDisplay.getItems().add(toDoList.get(i));
                    }
                }
            }
        }

    }

}
