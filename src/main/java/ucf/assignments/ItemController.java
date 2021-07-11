package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


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
        TableDisplay.getItems().removeAll(tempItem);

    }

    public void clearItems(){

        //clear all items from list
        //clear all items from display

        toDoList.clear();
        TableDisplay.getItems().clear();
        System.out.println(toDoList.size());
    }

    public void editItemDescription(String tempDescription, Item tempItem){

        //find list index
        //edit item on list
        //edit item on table display

        int tempIndex = toDoList.indexOf(tempItem);
        toDoList.set(tempIndex, new Item (tempDescription, tempItem.getDueDate(), tempItem.getComplete()));
        TableDisplay.getItems().set(tempIndex, new Item (tempDescription, tempItem.getDueDate(), tempItem.getComplete()));
    }

    public void editItemDueDate(String tempDueDate, Item tempItem){

        //find list index
        //edit item on list
        //edit item on table display

        int tempIndex = toDoList.indexOf(tempItem);
        toDoList.set(tempIndex, new Item (tempItem.getDescription(), tempDueDate, tempItem.getComplete()));
        TableDisplay.getItems().set(tempIndex, new Item (tempItem.getDescription(), tempDueDate, tempItem.getComplete()));
    }

    public void alterItemComplete(Item tempItem){

        //call Item.updateCompletion(Item)
        //This function should switch the completion on or off

        int tempIndex = toDoList.indexOf(tempItem);

        if(!tempItem.getComplete()){
            toDoList.set(tempIndex, new Item (tempItem.getDescription(), tempItem.getDueDate(), true));
            TableDisplay.getItems().set(tempIndex, new Item (tempItem.getDescription(), tempItem.getDueDate(), true));
        }else{
            toDoList.set(tempIndex, new Item (tempItem.getDescription(), tempItem.getDueDate(), false));
            TableDisplay.getItems().set(tempIndex, new Item (tempItem.getDescription(), tempItem.getDueDate(), false));
        }

    }

    public void saveList(){

        //loop through currently selected list
        //store items of list into txt file

    }

    public void loadList(){

        //open txt file
        //read txt file and store into list of items

    }

    public void displayItems(){

        //clear all items from list
        //check to see what display is selected
        //display based on selected display

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
        }else if(incompleteItems.isSelected()){
            for(int i = 0; i < toDoList.size(); i++){
                if((!toDoList.get(i).getComplete())){
                    TableDisplay.getItems().add(toDoList.get(i));
                }
            }
        }

    }

}
