package com.cpsc4340.rockCity.view;

/*
 * Graphical User Interface
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class RockCityView extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(RockCityView.class);
    private static RockCityView instance;
    private final Stage selectionStage = new Stage();
    private Stage window;
    private Scene startScene;
    private EventHandler<ActionEvent> saveHandler;
    private EventHandler<ActionEvent> findHandler;
    private EventHandler<ActionEvent> updateHandler;
    private EventHandler<ActionEvent> deleteHandler;
    private EventHandler<ActionEvent> reportHandler;
    private String tableName;

    public String getReport() {
        return report;
    }

    private String report;
    public List<TextField> eventList = new LinkedList<>();
    public List<TextField> locationList = new LinkedList<>();
    public List<TextField> inOutList = new LinkedList<>();
    public List<TextField> venueList = new LinkedList<>();
    public List<TextField> dateList = new LinkedList<>();
    public List<TextField> showList = new LinkedList<>();
    public List<TextField> dateSpList = new LinkedList<>();
    public List<TextField> cashList = new LinkedList<>();
    public List<TextField> inKindList = new LinkedList<>();

    private TextField nameText = new TextField();
    private Label addressLabel = new Label("Address: ");
    private TextField addressText = new TextField();
    private Label emailLabel = new Label("Email: ");
    private TextField emailText = new TextField();
    private Label siteLabel = new Label("Web Site: ");
    private TextField siteText = new TextField();
    private Label phoneLabel = new Label("Phone: ");
    private TextField phoneText = new TextField();
    private Label faxLabel = new Label("Fax: ");
    private TextField faxText = new TextField();
    private Label cNameLabel = new Label("Contact Name: ");
    private TextField cNameText = new TextField();
    private Label cNumberLabel = new Label("Contact Number: ");
    private TextField cNumberText = new TextField();
    private Label attendeeLabel = new Label("Attendee Number: ");
    private Label managerLabel = new Label("Manager Name: ");
    private TextField managerText = new TextField();
    private Label hostNameLabel = new Label("Host Name: ");
    private TextField hostNameText = new TextField();
    private TextField attendeeText = new TextField();
    private Label targetLabel = new Label("Target Audiences: ");
    private TextField targetText = new TextField();
    private Label startLabel = new Label("Start Date: ");
    private TextField startText = new TextField();
    private Label endLabel = new Label("End Date: ");
    private TextField endText = new TextField();
    private Label prefLabel = new Label("Preferred Area: ");
    private TextField prefText = new TextField();
    private Label sTypeLabel = new Label("Show Type: ");
    private TextField sTypeText = new TextField();
    private Label orgNameLabel = new Label("Organization Name: ");
    private TextField orgNameText = new TextField();
    private Button goBack = new Button("Go Back");
    private Button saveBtn = new Button("Save");
    private Button updateBtn = new Button("Update");
    private Button deleteBtn = new Button("Delete");

    public RockCityView() {
        instance = this;
    }

    static synchronized RockCityView getInstance() {
        if (instance == null) {
            Thread thread = new Thread(() -> Application.launch(RockCityView.class));
            thread.start();
        }
        while (instance == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return instance;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public void setReportHandler(EventHandler<ActionEvent> reportHandler) {
        this.reportHandler = reportHandler;
    }

    public TextField getManagerText() {
        return managerText;
    }

    public TextField getHostNameText() {
        return hostNameText;
    }

    public TextField getAttendeeText() {
        return attendeeText;
    }

    public TextField getTargetText() {
        return targetText;
    }

    public TextField getStartText() {
        return startText;
    }

    public TextField getEndText() {
        return endText;
    }

    public TextField getPrefText() {
        return prefText;
    }

    public TextField getsTypeText() {
        return sTypeText;
    }

    public TextField getOrgNameText() {
        return orgNameText;
    }

    public TextField getNameText() {
        return nameText;
    }

    public TextField getAddressText() {
        return addressText;
    }

    public TextField getEmailText() {
        return emailText;
    }

    public TextField getSiteText() {
        return siteText;
    }

    public TextField getPhoneText() {
        return phoneText;
    }

    public TextField getFaxText() {
        return faxText;
    }

    public TextField getcNameText() {
        return cNameText;
    }

    public TextField getcNumberText() {
        return cNumberText;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Database Project");

        Group group = new Group();
        startScene = new Scene(group, 800, 400);
        startScene.setFill(Color.BEIGE);
        window.setScene(startScene);

        //Welcome Text
        Text welcomeText = new Text();
        welcomeText.setFont(new Font(45));
        welcomeText.setUnderline(true);
        welcomeText.setX(150);
        welcomeText.setY(120);
        welcomeText.setText("Welcome to Rock City!");
        group.getChildren().add(welcomeText);

        //MenuButton Selection
        HBox menuBtnBox = new HBox();
        menuBtnBox.setPadding(new Insets(200, 300, 500, 75));

        MenuButton organizerMenuBtn = new MenuButton("Organizer");
        MenuItem addOrganizer = new MenuItem("Add Organizer");
        MenuItem findOrganizer = new MenuItem("Find Organizer");
        organizerMenuBtn.getItems().addAll(addOrganizer, findOrganizer);
        organizerMenuBtn.getItems().forEach(i -> i.setOnAction(e -> handleSelection(e)));
        organizerMenuBtn.setPadding(new Insets(20, 20, 20, 20));

        MenuButton sponsorMenuBtn = new MenuButton("Sponsor");
        MenuItem addSponsor = new MenuItem("Add Sponsor");
        MenuItem findSponsor = new MenuItem("Find Sponsor");
        sponsorMenuBtn.getItems().addAll(addSponsor, findSponsor);
        sponsorMenuBtn.getItems().forEach(i -> i.setOnAction(e -> handleSelection(e)));
        sponsorMenuBtn.setPadding(new Insets(20, 20, 20, 20));

        MenuButton hostMenuBtn = new MenuButton("Host");
        MenuItem addHost = new MenuItem("Add Host");
        MenuItem findHost = new MenuItem("Find Host");
        hostMenuBtn.getItems().addAll(addHost, findHost);
        hostMenuBtn.getItems().forEach(i -> i.setOnAction(e -> handleSelection(e)));
        hostMenuBtn.setPadding(new Insets(20, 20, 20, 20));

        MenuButton showMenuBtn = new MenuButton("Show");
        MenuItem addShow = new MenuItem("Add Show");
        MenuItem findShow = new MenuItem("Find Show");
        showMenuBtn.getItems().addAll(addShow, findShow);
        showMenuBtn.getItems().forEach(i -> i.setOnAction(this::handleSelection));
        showMenuBtn.setPadding(new Insets(20, 20, 20, 20));

        MenuButton venueMenuBtn = new MenuButton("Venue");
        MenuItem addVenue = new MenuItem("Add Venue");
        MenuItem findVenue = new MenuItem("Find Venue");
        venueMenuBtn.getItems().addAll(addVenue, findVenue);
        venueMenuBtn.getItems().forEach(i -> i.setOnAction(this::handleSelection));
        venueMenuBtn.setPadding(new Insets(20, 20, 20, 20));

        menuBtnBox.getChildren().addAll(organizerMenuBtn, sponsorMenuBtn,
                hostMenuBtn, showMenuBtn, venueMenuBtn);
        menuBtnBox.setSpacing(20);
        group.getChildren().add(menuBtnBox);

        window.show();
    }

    private void handleSelection(ActionEvent event) {
        clearFields();
        Scene scene = null;
        String itemText = ((MenuItem) event.getSource()).getText();

        switch (itemText) {
            case "Add Organizer":
                scene = new Scene(addOrganizer(), 800, 400);
                break;
            case "Find Organizer":
                scene = new Scene(findOrganizer(), 800, 400);
                break;
            case "Add Sponsor":
                scene = new Scene(addSponsor(), 800, 400);
                break;
            case "Find Sponsor":
                scene = new Scene(findSponsor(), 800, 400);
                break;
            case "Add Host":
                scene = new Scene(addHost(), 800, 400);
                break;
            case "Find Host":
                scene = new Scene(findHost(), 800, 400);
                break;
            case "Add Show":
                scene = new Scene(addShow(), 800, 400);
                break;
            case "Find Show":
                scene = new Scene(findShow(), 800, 400);
                break;
            case "Add Venue":
                scene = new Scene(addVenue(), 800, 400);
                break;
            case "Find Venue":
                scene = new Scene(findVenue(), 800, 400);
                break;
            default:
                break;
        }
        if (scene != null) {
            try {
                selectionStage.initModality(Modality.APPLICATION_MODAL);
                selectionStage.initOwner(this.window);
            } catch (IllegalStateException e) {
                //do Nothing
            } finally {
                selectionStage.setScene(scene);
                selectionStage.show();
            }
        }
    }

    public void setSaveHandler(EventHandler<ActionEvent> saveHandler) {
        this.saveHandler = saveHandler;
    }

    public void setFindHandler(EventHandler<ActionEvent> findHandler) {
        this.findHandler = findHandler;
    }

    public void setUpdateHandler(EventHandler<ActionEvent> updateHandler) {
        this.updateHandler = updateHandler;
    }

    public void setDeleteHandler(EventHandler<ActionEvent> deleteHandler) {
        this.deleteHandler = deleteHandler;
    }

    private GridPane addOrganizer() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        Label nameLabel = new Label("Organizer Name: ");
        setTableName("Organizer");
        saveBtn.setOnAction(saveHandler);
        goBack.setOnAction(event -> goBack());
        gridPane.add(nameLabel, 1, 1);
        gridPane.add(nameText, 2, 1);
        gridPane.add(addressLabel, 3, 1);
        gridPane.add(addressText, 4, 1);
        gridPane.add(emailLabel, 1, 2);
        gridPane.add(emailText, 2, 2);
        gridPane.add(siteLabel, 3, 2);
        gridPane.add(siteText, 4, 2);
        gridPane.add(phoneLabel, 1, 3);
        gridPane.add(phoneText, 2, 3);
        gridPane.add(faxLabel, 3, 3);
        gridPane.add(faxText, 4, 3);
        gridPane.add(cNameLabel, 1, 4);
        gridPane.add(cNameText, 2, 4);
        gridPane.add(cNumberLabel, 3, 4);
        gridPane.add(cNumberText, 4, 4);
        gridPane.add(saveBtn, 3, 6);
        gridPane.add(goBack, 3, 7);

        return gridPane;
    }

    private GridPane findOrganizer() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(30);
        gridPane.setAlignment(Pos.CENTER);
        Label label = new Label("Organizer Name: ");
        setTableName("Organizer");
        gridPane.add(label, 0, 1);
        gridPane.add(nameText, 1, 1);
        Button find = new Button("Find Organizer");
        find.setOnAction(findHandler);
        gridPane.add(find, 2, 1);
        goBack.setOnAction(event -> goBack());
        gridPane.add(goBack, 3, 1);

        return gridPane;
    }

    private GridPane addSponsor() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        Label nameLabel = new Label("Sponsor Name: ");
        setTableName("Sponsor");
        goBack.setOnAction(event -> goBack());
        saveBtn.setOnAction(saveHandler);
        gridPane.add(nameLabel, 1, 1);
        gridPane.add(nameText, 2, 1);
        gridPane.add(addressLabel, 3, 1);
        gridPane.add(addressText, 4, 1);
        gridPane.add(emailLabel, 1, 2);
        gridPane.add(emailText, 2, 2);
        gridPane.add(siteLabel, 3, 2);
        gridPane.add(siteText, 4, 2);
        gridPane.add(phoneLabel, 1, 3);
        gridPane.add(phoneText, 2, 3);
        gridPane.add(faxLabel, 3, 3);
        gridPane.add(faxText, 4, 3);
        gridPane.add(cNameLabel, 1, 4);
        gridPane.add(cNameText, 2, 4);
        gridPane.add(cNumberLabel, 3, 4);
        gridPane.add(cNumberText, 4, 4);
        gridPane.add(saveBtn, 3, 6);
        gridPane.add(goBack, 3, 7);

        return gridPane;
    }

    private GridPane findSponsor() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(30);
        gridPane.setAlignment(Pos.CENTER);
        Label label = new Label("Sponsor Name: ");
        setTableName("Sponsor");
        gridPane.add(label, 0, 1);
        gridPane.add(nameText, 1, 1);
        Button find = new Button("Find Sponsor");
        find.setOnAction(findHandler);
        gridPane.add(find, 2, 1);
        Button goBack = new Button("Go Back");
        goBack.setOnAction(event -> goBack());
        gridPane.add(goBack, 3, 1);

        return gridPane;
    }

    private GridPane addHost() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        Label nameLabel = new Label("Host Name: ");
        setTableName("Host");
        goBack.setOnAction(event -> goBack());
        saveBtn.setOnAction(saveHandler);
        gridPane.add(nameLabel, 1, 1);
        gridPane.add(nameText, 2, 1);
        gridPane.add(addressLabel, 3, 1);
        gridPane.add(addressText, 4, 1);
        gridPane.add(emailLabel, 1, 2);
        gridPane.add(emailText, 2, 2);
        gridPane.add(siteLabel, 3, 2);
        gridPane.add(siteText, 4, 2);
        gridPane.add(phoneLabel, 1, 3);
        gridPane.add(phoneText, 2, 3);
        gridPane.add(faxLabel, 3, 3);
        gridPane.add(faxText, 4, 3);
        gridPane.add(cNameLabel, 1, 4);
        gridPane.add(cNameText, 2, 4);
        gridPane.add(cNumberLabel, 3, 4);
        gridPane.add(cNumberText, 4, 4);
        gridPane.add(saveBtn, 3, 6);
        gridPane.add(goBack, 3, 7);

        return gridPane;
    }

    private GridPane findHost() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(30);
        gridPane.setAlignment(Pos.CENTER);
        Label label = new Label("Host Name: ");
        setTableName("Host");
        gridPane.add(label, 0, 1);
        gridPane.add(nameText, 1, 1);
        Button find = new Button("Find Host");
        find.setOnAction(findHandler);
        gridPane.add(find, 2, 1);
        Button goBack = new Button("Go Back");
        goBack.setOnAction(event -> goBack());
        gridPane.add(goBack, 3, 1);

        return gridPane;
    }

    private GridPane addShow() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        Label nameLabel = new Label("Show Name: ");
        setTableName("Show");
        goBack.setOnAction(event -> goBack());
        saveBtn.setOnAction(saveHandler);
        gridPane.add(nameLabel, 1, 1);
        gridPane.add(nameText, 2, 1);
        gridPane.add(attendeeLabel, 3, 1);
        gridPane.add(attendeeText, 4, 1);
        gridPane.add(targetLabel, 1, 2);
        gridPane.add(targetText, 2, 2);
        gridPane.add(startLabel, 3, 2);
        gridPane.add(startText, 4, 2);
        gridPane.add(endLabel, 1, 3);
        gridPane.add(endText, 2, 3);
        gridPane.add(prefLabel, 3, 3);
        gridPane.add(prefText, 4, 3);
        gridPane.add(sTypeLabel, 1, 4);
        gridPane.add(sTypeText, 2, 4);
        gridPane.add(orgNameLabel, 3, 4);
        gridPane.add(orgNameText, 4, 4);
        gridPane.add(saveBtn, 3, 6);
        gridPane.add(goBack, 3, 7);

        return gridPane;
    }

    private GridPane findShow() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(30);
        gridPane.setAlignment(Pos.CENTER);
        Label label = new Label("Show Name: ");
        setTableName("Show");
        gridPane.add(label, 0, 1);
        gridPane.add(nameText, 1, 1);
        Button find = new Button("Find Show");
        find.setOnAction(findHandler);
        gridPane.add(find, 2, 1);
        goBack.setOnAction(event -> goBack());
        gridPane.add(goBack, 3, 1);

        return gridPane;
    }

    private GridPane addVenue() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        Label nameLabel = new Label("Venue Name: ");
        setTableName("Venue");
        goBack.setOnAction(event -> goBack());
        saveBtn.setOnAction(saveHandler);
        gridPane.add(nameLabel, 1, 1);
        gridPane.add(nameText, 2, 1);
        gridPane.add(addressLabel, 3, 1);
        gridPane.add(addressText, 4, 1);
        gridPane.add(emailLabel, 1, 2);
        gridPane.add(emailText, 2, 2);
        gridPane.add(managerLabel, 3, 2);
        gridPane.add(managerText, 4, 2);
        gridPane.add(phoneLabel, 1, 3);
        gridPane.add(phoneText, 2, 3);
        gridPane.add(hostNameLabel, 3, 3);
        gridPane.add(hostNameText, 4, 3);
        gridPane.add(saveBtn, 3, 6);
        gridPane.add(goBack, 3, 7);

        return gridPane;
    }

    private GridPane findVenue() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(30);
        gridPane.setAlignment(Pos.CENTER);
        Label label = new Label("Venue Name: ");
        setTableName("Venue");
        gridPane.add(label, 0, 1);
        gridPane.add(nameText, 1, 1);
        Button find = new Button("Find Venue");
        find.setOnAction(findHandler);
        gridPane.add(find, 2, 1);
        goBack.setOnAction(event -> goBack());
        gridPane.add(goBack, 3, 1);

        return gridPane;
    }

    public void goBack() {
        window.close();
        window.setScene(startScene);
        window.show();
    }

    public void displayFinding() {
        String table = getTableName();
        GridPane gridPane = null;
        if ("Organizer".equals(table)) {
            gridPane = addOrganizer();
        } else if ("Sponsor".equals(table)) {
            gridPane = addSponsor();
            Button managementReport = new Button("Management Report");
            gridPane.add(managementReport, 2, 5);
            managementReport.setOnAction(reportHandler);
        } else if ("Show".equals(table)) {
            gridPane = addShow();
            Button eventSchedule = new Button("Event Schedule");
            gridPane.add(eventSchedule, 2, 5);
            eventSchedule.setOnAction(reportHandler);
            Button showReport = new Button("Show Report");
            gridPane.add(showReport, 3, 5);
            showReport.setOnAction(reportHandler);
        } else if ("Venue".equals(table)) {
            gridPane = addVenue();
        } else if ("Host".equals(table)) {
            gridPane = addHost();
        }
        if (gridPane != null) {
            gridPane.add(updateBtn, 3, 6);
            gridPane.add(deleteBtn, 4, 6);
            updateBtn.setOnAction(updateHandler);
            deleteBtn.setOnAction(deleteHandler);
            Scene scene = new Scene(gridPane, 800, 400);
            final Stage stage = new Stage();
            try {
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(this.window);
            } catch (IllegalStateException e) {
                //do Nothing
            } finally {
                selectionStage.hide();
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    public void showReport() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        Text text = new Text();
        text.setFont(Font.font(40));
        if ("EventSchedule".equals(report)) {
            text.setText("Event Schedule");
            Label label = new Label("Events:");
            gridPane.add(label, 0, 1);
            final int[] col = {1};
            eventList.forEach(event -> {
                gridPane.add(event, col[0], 1);
                col[0] += 1;
            });

            Label location = new Label("Location: ");
            gridPane.add(location, 0, 2);
            final int[] col1 = {1};
            locationList.forEach(loc -> {
                gridPane.add(loc, col1[0], 2);
                col1[0] += 1;
            });

            Label inOutL = new Label("Indoor/Outdoor: ");
            gridPane.add(inOutL, 0, 3);
            final int[] col2 = {1};
            inOutList.forEach(inOut-> {
                gridPane.add(inOut, col2[0], 3);
                col2[0] += 1;
            });

            Label venueL = new Label("Venue: ");
            gridPane.add(venueL, 0, 4);
            final int[] col3 = {1};
            venueList.forEach(venue -> {
                gridPane.add(venue, col3[0], 4);
                col3[0] += 1;
            });

        } else if ("ManagementReport".equals(report)) {
            text.setText("Management Report");
            Label showL = new Label("Show: ");
            gridPane.add(showL, 0, 1);
            final int[] col = {1};
            showList.forEach(show -> {
                gridPane.add(show, col[0], 1);
                col[0] += 1;
            });

            Label dateL = new Label("Date: ");
            gridPane.add(dateL, 0, 2);
            final int[] col1 = {1};
            dateSpList.forEach(date -> {
                gridPane.add(date, col1[0], 2);
                col1[0] += 1;
            });

            Label cashL = new Label("Cash: ");
            gridPane.add(cashL, 0, 3);
            final int[] col2 = {1};
            cashList.forEach(cash -> {
                gridPane.add(cash, col2[0], 3);
                col2[0] += 1;
            });

            Label inKindL = new Label("In Kind: ");
            gridPane.add(inKindL, 0, 4);
            final int[] col3 = {1};
            inKindList.forEach(inKind -> {
                gridPane.add(inKind, col3[0], 4);
                col3[0] += 1;
            });

        } else if ("ShowReport".equals(report)) {
            text.setText("Show Report");

        }

        gridPane.add(text, 1, 0);
        Scene scene = new Scene(gridPane, 800, 400);
        final Stage stage = new Stage();
        try {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.window);
        } catch (IllegalStateException e) {
            //do Nothing
        } finally {
            stage.setScene(scene);
            stage.show();
        }
    }


    public void displayResult(String content) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(this.window);
        VBox dialogVbox = new VBox(20);
        Text text = new Text(content);
        text.setFont(new Font(15));
        dialogVbox.getChildren().add(text);
        Scene dialogScene = new Scene(dialogVbox, 500, 100);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void clearFields() {
        getNameText().clear();
        getAddressText().clear();
        getEmailText().clear();
        getSiteText().clear();
        getPhoneText().clear();
        getFaxText().clear();
        getcNameText().clear();
        getcNumberText().clear();
        getAttendeeText().clear();
        getTargetText().clear();
        getStartText().clear();
        getEndText().clear();
        getPrefText().clear();
        getsTypeText().clear();
        getOrgNameText().clear();
        getManagerText().clear();
        getHostNameText().clear();
    }
}