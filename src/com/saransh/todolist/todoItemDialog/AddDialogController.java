package com.saransh.todolist.todoItemDialog;

import com.saransh.todolist.datamodel.TodoData;
import com.saransh.todolist.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddDialogController implements Initializable {

    @FXML
    private TextField shortDescriptionField;

    @FXML
    private TextArea detailsArea;

    @FXML
    private DatePicker deadlinePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deadlinePicker.setValue(LocalDate.now());
    }

    public TodoItem processResult() {
        String shortDescription = shortDescriptionField.getText().trim();
        String details = detailsArea.getText().trim();
        LocalDate deadline = deadlinePicker.getValue();

        if(shortDescription.isEmpty() || details.isEmpty()) {
            return null;
        } else {
            TodoItem newItem = new TodoItem(shortDescription, details, deadline);
            TodoData.getInstance().addTodoItem(newItem);
            return newItem;
        }
    }
}
