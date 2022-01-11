package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Task;
import ui.EditTask;
import ui.ListView;
import ui.PomoTodoApp;
import utility.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private File todoBarPopUpFxmlFile = new File(todoActionsPopUpFXML);
    private File todoOptionsPopUpFxmlFile = new File(todoOptionsPopUpFXML);
    
    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;

    private JFXPopup todobarPopUp;
    private JFXPopup viewPopUp;
    
    private Task task;
    
    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTodobarPopUp();
        loadTodobarPopUpActionListener();
        loadTodoOptionsPopUp();
        loadTodoOptionsPopUpActionListener();
    }

    // EFFECTS: load to-do bar pop up (to do, up next, in progress, done, pomodoro!)
    private void loadTodobarPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoBarPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodobarPopUpController());
            todobarPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: load option pop up (edit, delete)
    private void loadTodoOptionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoOptionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodoOptionsPopUpController());
            viewPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: show option selector pop up when its icon is clicked
    private void loadTodoOptionsPopUpActionListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                viewPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15);
            }
        });
    }

    // EFFECTS: show to-do bar pop up when its icon is clicked
    private void loadTodobarPopUpActionListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todobarPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        12,
                        15);
            }
        });
    }

    // Inner class: to-do bar pop up controller
    class TodobarPopUpController {
        @FXML
        private JFXListView<?> actionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();
            todobarAction(selectedIndex);
            todobarPopUp.hide();
        }

        @FXML
        private void todobarAction(int selectedIndex) {
            if (selectedIndex == 0) {
                Logger.log("TodobarActionsPopUpController", "To Do is not supported in this version");
            } else if (selectedIndex == 1) {
                Logger.log("TodobarActionsPopUpController", "Up Next is not supported in this version!");
            } else if (selectedIndex == 2) {
                Logger.log("TodobarActionsPopUpController", "In Progress is not supported in this version!");
            } else if (selectedIndex == 3) {
                Logger.log("TodobarActionsPopUpController", "Done is not supported in this version!");
            } else if (selectedIndex == 4) {
                Logger.log("TodobarActionsPopUpController", "Pomodoro! is not supported in this version!");
            } else {
                Logger.log("TodobarActionsPopUpController", "No action is implemented for the selected option");
            }
        }
    }

    // Inner class: to-do option pop up controller
    class TodoOptionsPopUpController {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("TodobarOptionsPopUpController", "Edit selected task");
                    PomoTodoApp.setScene(new EditTask(task));
                    break;
                case 1:
                    Logger.log("TodobarOptionsPopUpController", "Delete selected task");
                    PomoTodoApp.getTasks().remove(task);
                    PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks()));
                    break;
                default:
                    Logger.log("TodobarOptionsPopUpController", "No action is implemented for the selected option");
            }
            viewPopUp.hide();
        }
    }
}
