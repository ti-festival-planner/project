import Data.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Gui extends Application {
    private Schedule schedule = new Schedule();
    private VBox mainPaine = new VBox();
    private VBox AddPane = new VBox();
    private TableView<Activity> table;
    private ComboBox<String> activityComboBox = new ComboBox<>();
    private ComboBox<Area> areaComboBox = new ComboBox<>();
    private ComboBox<Guard> guardComboBox = new ComboBox<>();
    private ComboBox<Groep> groupComboBox = new ComboBox<>();

    private TextField hourStart = new TextField();
    private TextField hourEnd = new TextField();

    private Button deleteButton = new Button("Delete");
    private Button addButton = new Button("Add");



    public void start(Stage mainWindow){
        HBox addActivityBox = getHbox();
        MenuBar menuBar = getMenuBar();
        TableView table = getTable();

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

        TableColumn<Activity, String> columnArea = new TableColumn<>("Area");
        columnArea.setCellValueFactory(new PropertyValueFactory<Activity,String>("Area"));

        TableColumn<Activity, String> columnGuards = new TableColumn<>("Guard");
        columnGuards.setCellValueFactory(new PropertyValueFactory<Activity,String>("Guard"));

        TableColumn<Activity, String> columnGroups = new TableColumn<>("Groep");
        columnGroups.setCellValueFactory(new PropertyValueFactory<Activity,String>("Groep"));

        table = new TableView<>();

        table.getColumns().addAll(columnStartTime,columnEndTime,columnName,columnArea,columnGuards,columnGroups);

        return table;
    }

    private MenuBar getMenuBar() {
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");

        addMenuItems(fileMenu, editMenu);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu,editMenu);
        return menuBar;
    }

    private void addMenuItems(Menu fileMenu, Menu editMenu) {
        FileChooser fileChooser = new FileChooser();

        fileMenu.getItems().add(new MenuItem("New..."));

        MenuItem openFile = new MenuItem("Open...");
        openFile.setOnAction(event -> { getSelectedFile(fileChooser); });

        fileMenu.getItems().add(openFile);
        MenuItem saveFile = new MenuItem("Save as...");
        fileMenu.getItems().add(saveFile);
        saveFile.setOnAction(event -> saveSelectedFile(fileChooser));
        fileMenu.getItems().add(new MenuItem("Exit"));

        editMenu.getItems().add(new MenuItem("Schedule"));
        MenuItem addPane = new Menu("Add...");
        editMenu.getItems().add(addPane);
    }

    private void saveSelectedFile(FileChooser fileChooser) {
        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DAT files (*.dat)", "*.dat");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            JavaIO.writeData(file, this.schedule);
//            saveTextToFile(sampleText, file);
        } else {
            System.out.println("Cancelled");
        }
    }

    private void getSelectedFile(FileChooser fileChooser) {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DAT files (*.dat)", "*.dat");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null){
            System.out.println(selectedFile);
            clearSchedule();
            for(Activity activity: JavaIO.readData(selectedFile)){
                addItem(activity);
            }
        } else {
            System.out.println("File is not valid");
        }
    }

    private HBox getHbox() {
        HBox addActivityBox = new HBox();

        activityComboBox.setPromptText("Select activity");
        activityComboBox.getItems().addAll(schedule.activityNames);

        areaComboBox.setPromptText("Select area");
        areaComboBox.getItems().addAll(schedule.areas);

        guardComboBox.setPromptText("Select guard");
        guardComboBox.getItems().addAll(schedule.guards);

        groupComboBox.setPromptText("Select group");
        groupComboBox.getItems().addAll(schedule.prisonGroeps);

        addButton.setOnAction(e-> addButtonClicked());
        deleteButton.setOnAction(e-> deleteButtonClicked());

        activityComboBox.setMinWidth(50);
        areaComboBox.setMinWidth(50);
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
                areaComboBox,
                guardComboBox,
                groupComboBox,
                hourStart,
                hourEnd,
                addButton,
                deleteButton);
        addActivityBox.setAlignment(Pos.TOP_CENTER);

        return addActivityBox;
    }

    private void deleteButtonClicked() {
    }

    private void addItem(Activity activity){
        table.getItems().add(activity);
        schedule.addActivity(activity);
    }

    private void clearSchedule(){
        table.getItems().clear();
        schedule.clearActivities();
    }



    private void addButtonClicked() {
        Activity activity = new Activity();
        activity.setSecurityLevel(0);
        activity.setHourStart(Integer.parseInt(hourStart.getText()));
        activity.setHourEnd(Integer.parseInt(hourEnd.getText()));
        activity.setName(activityComboBox.getValue());
        activity.setGuard(guardComboBox.getValue());
        activity.setGroep(groupComboBox.getValue());
        activity.setArea(areaComboBox.getValue());
        addItem(activity);
        System.out.println(activity);
    }

    public ObservableList<Activity> getActivity(){
        ObservableList<Activity> activities = FXCollections.observableArrayList();
//        activities.add(new Activity(0,5,9,"OOM",guardComboBox.getValue(),groupComboBox.getValue(),areaComboBox.getValue()));
        table.setItems(activities);
        System.out.println(activities);
        return activities;
    }
}