package com.saransh.todolist.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TodoData {

    private static final TodoData instance = new TodoData();
    private static final String filename = "TodoListItems.txt";
    private ObservableList<TodoItem> todoItems;
    private final DateTimeFormatter formatter;

    public static TodoData getInstance() {
        return instance;
    }

    private TodoData() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public ObservableList<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void addTodoItem(TodoItem item) {
        todoItems.add(item);
    }

    public void deleteTodoItem(TodoItem item) {
        todoItems.remove(item);
    }

    public void updateTodoItem(TodoItem oldItem, TodoItem updatedItem) {
        int oldItemIndex = todoItems.indexOf(oldItem);
        todoItems.set(oldItemIndex, updatedItem);
    }

    public void loadTodoItems() throws IOException {

        todoItems = FXCollections.observableArrayList();
        Path path = Paths.get(filename);

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String input;
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split("\t");

                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                LocalDate date = LocalDate.parse(dateString, formatter);
                TodoItem todoItem = new TodoItem(shortDescription, details, date);
                todoItems.add(todoItem);
            }
        }
    }

    public void storeTodoItems() throws IOException {

        Path path = Paths.get(filename);
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (TodoItem item : todoItems) {
                bw.write(String.format("%s\t%s\t%s",
                        item.getShortDescription(),
                        item.getDetails(),
                        item.getDeadline().format(formatter)));
                bw.newLine();
            }
        }
    }
}
