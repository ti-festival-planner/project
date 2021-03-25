package GUI;

import Logic.ActivityController;
import Logic.ScheduleController;
import Util.Activity;
import Util.Groep;
import Util.Guard;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;


public class Gui extends Application {


    private Stage mainStage;
    private Stage editStage;

    private VBox mainPaine = new VBox();
    private VBox AddPane = new VBox();
    private TableView<Activity> table;
    private ComboBox<String> activityComboBox = new ComboBox<>();
    private ComboBox<Guard> guardComboBox = new ComboBox<>();
    private ComboBox<Groep> groupComboBox = new ComboBox<>();

    private TextField hourStart = new TextField();
    private TextField hourEnd = new TextField();

    private Button deleteButton = new Button("Delete");
    private Button editButton = new Button("Edit");
    private Button addButton = new Button("Add");

    private ActivityController activityController;
    private ScheduleController scheduleController = new ScheduleController();

    public void start(Stage mainWindow){
        mainStage = mainWindow;
        HBox addActivityBox = getHbox();
        MenuBar menuBar = getMenuBar();
        TableView table = getTable();
        this.activityController = new ActivityController(table);
        mainPaine.getChildren().addAll(menuBar,addActivityBox,table);

        mainWindow.setScene(new Scene(mainPaine));
        mainWindow.setTitle("Schedule");
        mainWindow.show();
    }

    private TableView getTable() {
        TableColumn<Activity, Integer> columnStartTime = new TableColumn<>("Starting time");
        columnStartTime.setCellValueFactory(new PropertyValueFactory<Activity,Integer>("hourStart"));

        TableColumn<Activity, Integer> columnEndTime = new TableColumn<>("End time");
        columnEndTime.setCellValueFactory(new PropertyValueFactory<Activity,Integer>("hourEnd"));

        TableColumn<Activity, String> columnName = new TableColumn<>("Activiy");
        columnName.setCellValueFactory(new PropertyValueFactory<Activity,String>("name"));

        TableColumn<Activity, String> columnGuards = new TableColumn<>("Guard");
        columnGuards.setCellValueFactory(new PropertyValueFactory<Activity,String>("Guard"));

        TableColumn<Activity, String> columnGroups = new TableColumn<>("Groep");
        columnGroups.setCellValueFactory(new PropertyValueFactory<Activity,String>("Groep"));

        table = new TableView<>();

        table.getColumns().addAll(columnStartTime,columnEndTime,columnName,columnGuards,columnGroups);

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);

            }
        });

        return table;
    }

    private MenuBar getMenuBar() {
        Menu fileMenu = new Menu("File");
        Menu simMenu = new Menu("Simulate");

        addMenuItems(fileMenu,simMenu);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, simMenu);
        return menuBar;
    }

    private void addMenuItems(Menu fileMenu, Menu simMenu) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DAT files (*.dat)", "*.dat");
        fileChooser.getExtensionFilters().add(extFilter);

        MenuItem openFile = new MenuItem("Open...");
        MenuItem saveFile = new MenuItem("Save as...");
        openFile.setOnAction(event -> { activityController.getSelectedFile(fileChooser); });
        saveFile.setOnAction(event -> { activityController.saveSelectedFile(fileChooser); });
        fileMenu.getItems().addAll(openFile, saveFile);

        MenuItem startSim = new MenuItem("Start");
        startSim.setOnAction(event -> { try{ launchSim(); } catch (Exception e){ Alert error = new Alert(Alert.AlertType.ERROR); error.setContentText(e.getMessage()); }});
        simMenu.getItems().addAll(startSim);
    }

    private void launchSim() throws Exception {
        Simulator sim = new Simulator();
        sim.start(new Stage());
    }

    private void clearInput(){
        activityComboBox.getSelectionModel().clearSelection();
        guardComboBox.getSelectionModel().clearSelection();
        groupComboBox.getSelectionModel().clearSelection();
        hourStart.clear();
        hourEnd.clear();
    }

    private void fillBox(){
        activityComboBox.setPromptText("Select activity");
        activityComboBox.getItems().addAll(scheduleController.getActivityNames());

        guardComboBox.setPromptText("Select guard");
        guardComboBox.getItems().addAll(scheduleController.getGuards());

        groupComboBox.setPromptText("Select group");
        groupComboBox.getItems().addAll(scheduleController.getPrisonGroeps());
        activityComboBox.isFocused();
    }

    private void setActions(){
        addButton.setOnAction(e-> {
            try {
                activityController.addItem(Integer.parseInt(hourStart.getText()),
                        Integer.parseInt(hourEnd.getText()),
                        activityComboBox.getValue(),
                        guardComboBox.getValue(),
                        groupComboBox.getValue());
                clearInput();
            } catch (Exception ne){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Er is wat fout gegaan bij het opslaan:");
                error.setTitle("Error!");
                error.setContentText("Details error: \n"+ne.getMessage());
                error.showAndWait();
            }
        });
        deleteButton.setOnAction(e-> {deleteButtonClicked();});
        editButton.setOnAction(e -> {editButtonClicked();});

    }

    private HBox getHbox() {
        HBox addActivityBox = new HBox();
        fillBox();
        setActions();
        addActivityBox.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    activityController.addItem(Integer.parseInt(hourStart.getText()),
                            Integer.parseInt(hourEnd.getText()),
                            activityComboBox.getValue(),
                            guardComboBox.getValue(),
                            groupComboBox.getValue());
                    clearInput();
                } catch (Exception ne) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setHeaderText("Er is wat fout gegaan bij het opslaan:");
                    error.setTitle("Error!");
                    error.setContentText("Details error: \n" + ne.getMessage());
                    error.showAndWait();
                }
            }
        });
        editButton.setDisable(true);
        deleteButton.setDisable(true);

        activityComboBox.setMinWidth(50);
        guardComboBox.setMinWidth(50);
        groupComboBox.setMinWidth(50);
        hourStart.setMinWidth(50);
        hourEnd.setMinWidth(50);

        hourStart.setPromptText("Start hour");
        hourEnd.setPromptText("End hour");


        addActivityBox.setPadding(new Insets(10,10,10,10));
        addActivityBox.setSpacing(10);

        addActivityBox.getChildren().addAll(
                activityComboBox,
                guardComboBox,
                groupComboBox,
                hourStart,
                hourEnd,
                addButton,
                editButton,
                deleteButton);
        addActivityBox.setAlignment(Pos.TOP_CENTER);

        return addActivityBox;
    }

    private void editButtonClicked() {

        if (table.getSelectionModel().getSelectedItem() != null) {
            Activity activity = table.getSelectionModel().getSelectedItem();

            editStage = new Stage();
            editStage.initModality(Modality.WINDOW_MODAL);

            ComboBox<String> editactivityComboBox = new ComboBox<>();
            editactivityComboBox.setPromptText("Select activity");
            editactivityComboBox.getItems().addAll(scheduleController.getActivityNames());
            editactivityComboBox.setValue(activity.getName());

            ComboBox<Guard> editguardComboBox = new ComboBox<>();
            editguardComboBox.setPromptText("Select guard");
            editguardComboBox.getItems().addAll(scheduleController.getGuards());
            editguardComboBox.setValue(activity.getGuard());

            ComboBox<Groep> editgroupComboBox = new ComboBox<>();
            editgroupComboBox.setPromptText("Select group");
            editgroupComboBox.getItems().addAll(scheduleController.getPrisonGroeps());
            editgroupComboBox.setValue(activity.getGroep());

            TextField edithourStart = new TextField(""+activity.getHourStart());
            TextField edithourEnd = new TextField(""+activity.getHourEnd());

            Button cancelButton = new Button("cancel");
            Button confirmButton = new Button("confirm");

            cancelButton.setOnAction(e -> {editStage.close();});
            confirmButton.setOnAction(e -> {
                Activity activityOld = activity;
                Activity activityNew = new Activity();
                activityNew.setName(editactivityComboBox.getValue());
                activityNew.setGuard(editguardComboBox.getValue());
                activityNew.setGroep(editgroupComboBox.getValue());
                activityNew.setHourStart(Integer.parseInt(edithourStart.getText()));
                activityNew.setHourEnd(Integer.parseInt(edithourEnd.getText()));
                activityController.editItem(activityOld, activityNew);
                editStage.close();
            });

            editStage.setScene(
                new Scene(
                    new VBox(20,
                        new Text("Edit prisoner"),
                        new HBox(20, new Label("Activity: "),   editactivityComboBox),
                        new HBox(20, new Label("Guard: "),      editguardComboBox),
                        new HBox(20, new Label("Group: "),      editgroupComboBox),
                        new HBox(20, new Label("Start time: "), edithourStart),
                        new HBox(20, new Label("End time: "),   edithourEnd),
                        new HBox(20, cancelButton, confirmButton)
                    ), 300, 400)
            );
            editStage.showAndWait();
        } else {
            Alert alertNoSel = new Alert(Alert.AlertType.INFORMATION);
            alertNoSel.setTitle("Geen activiteit geselecteerd");
            alertNoSel.setHeaderText("Je hebt hebt geen activiteit geselecteerd!");
            alertNoSel.showAndWait();
        }
    }

    private void deleteButtonClicked() {

        if (table.getSelectionModel().getSelectedItem() != null) {
            Activity activity = table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Weet je het zeker?");
            alert.setHeaderText("Je staat op het punt de volgende activiteit te verwijderen:");
            alert.setContentText("Activiteit: "+activity.getName()+"\n"+
                                 "Groep: "+activity.getGroep()+"\n"+
                                 "Guard: "+activity.getGuard()+"\n"+
                                 "Start uur: "+activity.getHourStart()+"\n"+
                                 "Eind uur: "+activity.getHourEnd()+"\n");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().add(buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                activityController.deleteItem(activity);
            } else {
                Alert alertCancel = new Alert(Alert.AlertType.INFORMATION);
                alertCancel.setTitle("Verwijderen geannuleerd");
                alertCancel.setHeaderText("Je hebt het verwijderen van de activiteit geannuleerd!");
                alertCancel.showAndWait();
            }
        } else {
            Alert alertNoSel = new Alert(Alert.AlertType.INFORMATION);
            alertNoSel.setTitle("Geen activiteit geselecteerd");
            alertNoSel.setHeaderText("Je hebt hebt geen activiteit geselecteerd!");
            alertNoSel.showAndWait();
        }
    }


    public ObservableList<Activity> getActivity(){
        ObservableList<Activity> activities = FXCollections.observableArrayList();
        table.setItems(activities);
        System.out.println(activities);
        return activities;
    }
}
