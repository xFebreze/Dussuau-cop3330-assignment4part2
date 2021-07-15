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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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

        //Check to see if the date picker field is null
        //Convert text field and date picker to strings
        //check if string meets requirements if not show alert
        //call add item w/ strings
        //clear fields for typing

        if(DueDate.getValue() == null){
            alerts("Warning","Please use the date picker to pick a date");
            return;
        }

        String date = DueDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String str = DescriptionField.getText();

        if(str.length() < 1 || str.length() > 256){
            DescriptionField.clear();
            alerts("Warning", "Please make sure you have a description between 1-256 characters");
            return;
        }

        addItem(str, date);
        DescriptionField.clear();
        DueDate.getEditor().clear();

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
        //Check if string is valid
        //get selected Item
        //call editItemDescription with str
        //clear description field

        String str = DescriptionField.getText();
        if(str.length() < 1 || str.length() > 256){
            DescriptionField.clear();
            alerts("Warning", "Please make sure you have a description between 1-256 characters");
            return;
        }
        Item temp = TableDisplay.getSelectionModel().getSelectedItem();
        editItemDescription(str, temp);
        DescriptionField.clear();

    }

    @FXML
    public void editItemDueDateButtonClicked(ActionEvent actionevent){

        //check if dueDate is not null
        //convert dueDate to date
        //get selected Item
        //call editItemDueDate with date
        //clear datePicker

        if(DueDate.getValue() == null ){
            alerts("Warning","Please use the date picker to pick a date");
            return;
        }
        String date = DueDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Item temp = TableDisplay.getSelectionModel().getSelectedItem();
        editItemDueDate(date, temp);
        DueDate.getEditor().clear();
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
        //prompt a file chooser to save a file
        //use saveList with the saved file from file chooser


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save List");
        fileChooser.setInitialFileName("myList");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text files", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());

        saveList(file);
    }

    @FXML
    public void loadListButtonClicked(ActionEvent actionevent){
        //prompt a file chooser to save a file
        //use loadList with the saved file from file chooser

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open txt file");
        File file = fileChooser.showOpenDialog(new Stage());
        loadList(file);
    }

    @FXML
    public void helpButtonClicked(ActionEvent actionEvent){
        //Prompts the help screen
        alerts("Help","(This is dedicated to Rey)\n\n" +
                "Add Item: to add an item to the list type your description\n" +
                "and selected a due date then click the add item button\n\n" +
                "Remove Item: to remove an item selected an item from the list\n" +
                "then click the remove item button\n\n" +
                "Edit Description: first select the item you want to edit\n" +
                "after type in the description box what you want your new\n" +
                "description to say then click the edit description button\n\n" +
                "Edit DueDate: first select the item you want to edit\n" +
                "after select a new date that you would like to change to\n" +
                "then click the edit due date button\n\n" +
                "Toggle completion: first select the item from the list you\n" +
                "would like to mark then click the toggle completion button\n\n");
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

    public void saveList(File file){

        //create a fileChooser
        //set data for said fileChooser
        //make a file to store for fileChooser
        //write to created file


        FileWriter fileWrite = null;
        String fileData = "";

        try
        {
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

    public void loadList(File file){

        //call read file to get the data from the file
        //clear the current toDoList
        //store data from file into toDolist
        //updateDisplay

        List<String> data = readFile(file);
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

    public List<String> readFile(File file){

        //Create a fileChooser
        //set data to fileChooser
        //make a scanner to read the selected file
        //Store the file data into an arrayList
        //return list to loadFile method

        List<String> ret = new ArrayList<>();

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

    public void alerts(String title, String msg){
        //used for alerts when user does something wrong or needs help etc.
        Stage temp = new Stage();

        temp.initModality(Modality.APPLICATION_MODAL);
        temp.setTitle(title);
        temp.setMinWidth(250);

        Label label = new Label();
        label.setText(msg);
        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(event -> temp.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        temp.setScene(scene);
        temp.showAndWait();
    }

}
