# Week 4
[Raoul de Heer - 2165787](https://github.com/raouldeheer)
## Deze week op de agenda

-   [Edit button toegevoegd](#Edit-button-toegevoegd)

## Edit button toegevoegd
Ik heb de edit button deze week toegevoegd op deze manier:
```java
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
            activity.setName(editactivityComboBox.getValue());
            activity.setGuard(editguardComboBox.getValue());
            activity.setGroep(editgroupComboBox.getValue());
            activity.setHourStart(Integer.parseInt(edithourStart.getText()));
            activity.setHourEnd(Integer.parseInt(edithourEnd.getText()));
            activityController.editItem(activityOld, activity);
            int i = table.getItems().indexOf(activityOld);
            table.getItems().remove(i);
            table.getItems().add(i, activity);
            editStage.close();
        });

        editStage.setScene(
            new Scene(
                new VBox(20,
                    new Text("Edit prisoner"),
                    new HBox(20, new Label("Activity: "),   activityComboBox),
                    new HBox(20, new Label("Guard: "),      guardComboBox),
                    new HBox(20, new Label("Group: "),      groupComboBox),
                    new HBox(20, new Label("Start time: "), hourStart),
                    new HBox(20, new Label("End time: "),   hourEnd),
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
```
Door een popup stage te maken en die weer te geven.  
Deze code was nog niet bug vrij maar die zijn er op een latere datum uitgehaald.  
Zie commit [bcfd351](https://github.com/ti-festival-planner/project/commit/bcfd35128ec28a1e480f4012e3c49e69e344ba08)  
