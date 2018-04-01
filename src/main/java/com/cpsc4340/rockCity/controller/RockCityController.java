package com.cpsc4340.rockCity.controller;

import com.cpsc4340.rockCity.model.RockCityModel;
import com.cpsc4340.rockCity.view.RockCityView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public class RockCityController {

    private RockCityModel model;
    private RockCityView view;

    public RockCityController(RockCityModel model, RockCityView view) {
        this.model = model;
        this.view = view;
        this.view.setSaveHandler(new SaveHandler());
        this.view.setFindHandler(new FindHandler());
        this.view.setUpdateHandler(new UpdateHandler());
        this.view.setDeleteHandler(new DeleteHandler());
        this.view.setReportHandler(new ReportHandler());
    }

    class SaveHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String tableName = view.getTableName();
            if ("Show".equals(tableName)) {
                insertShow();
            } else if ("Venue".equals(tableName)) {
                insertVenue();
            } else {
                insert(tableName);
            }
        }

        private void insert(String table) {
            String name = view.getNameText().getText();
            String address = view.getAddressText().getText();
            String email = view.getEmailText().getText();
            String site = view.getSiteText().getText();
            String phone = view.getPhoneText().getText();
            String fax = view.getFaxText().getText();
            String cName = view.getcNameText().getText();
            String cNumber = view.getcNumberText().getText();

            if (name.isEmpty() || address.isEmpty() || email.isEmpty() || site.isEmpty()
                    || phone.isEmpty() || fax.isEmpty() || cName.isEmpty()
                    || cNumber.isEmpty()) {
                view.displayResult("All Fields Are Required!");
            } else {
                boolean result = model.insert(table, name, address, email, site, phone,
                        fax, cName, cNumber);
                if (!result) {
                    view.displayResult("Error: Operation Failed!");
                } else {
                    view.goBack();
                    view.clearFields();
                    view.displayResult(name + " was successfully added!");
                }
            }
        }

        private void insertShow() {
            String name = view.getNameText().getText();
            String attendee = view.getAttendeeText().getText();
            String target = view.getTargetText().getText();
            String start = view.getStartText().getText();
            String end = view.getEndText().getText();
            String prefText = view.getPrefText().getText();
            String type = view.getsTypeText().getText();
            String orgName = view.getOrgNameText().getText();
            if (name.isEmpty() || attendee.isEmpty() || target.isEmpty()
                    || start.isEmpty() || end.isEmpty() || prefText.isEmpty()
                    || type.isEmpty() || orgName.isEmpty()) {
                view.displayResult("All Fields Are Required!");
            } else {
                boolean result = model.insert("Show", name, attendee, target, start,
                        end, prefText, type, orgName);
                if (!result) {
                    view.displayResult("Error: Operation Failed!");
                } else {
                    view.goBack();
                    view.clearFields();
                    view.displayResult(name + " was successfully added!");
                }
            }

        }

        private void insertVenue() {
            String name = view.getNameText().getText();
            String address = view.getAddressText().getText();
            String email = view.getEmailText().getText();
            String manager = view.getManagerText().getText();
            String phone = view.getPhoneText().getText();
            String hostName = view.getHostNameText().getText();

            if (name.isEmpty() || address.isEmpty() || email.isEmpty()
                    || manager.isEmpty() || phone.isEmpty() || hostName.isEmpty()) {
                view.displayResult("All Fields Are Required!");
            } else {
                boolean result = model.insert("Venue", name, address, email,
                        manager, phone, hostName);
                if (!result) {
                    view.displayResult("Error: Operation Failed!");
                } else {
                    view.goBack();
                    view.clearFields();
                    view.displayResult(name + " was successfully added!");
                }
            }

        }
    }

    class FindHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String tableName = view.getTableName();
            String name = view.getNameText().getText();
            HashMap<String, String> result = model.find(tableName, name);
            if (result.isEmpty()) {
                view.displayResult("Error: Not Found!");
            } else if ("Show".equals(tableName)) {
                view.getNameText().setText(result.get("SName"));
                view.getAttendeeText().setText(result.get("Attendees-num"));
                view.getTargetText().setText(result.get("Target-audiences"));
                view.getStartText().setText(result.get("Start_date"));
                view.getEndText().setText(result.get("End_date"));
                view.getPrefText().setText(result.get("Preferred_area"));
                view.getsTypeText().setText(result.get("S-type"));
                view.getOrgNameText().setText(result.get("OrgName1"));
                view.displayFinding();
            } else if ("Venue".equals(tableName)) {
                view.getNameText().setText(result.get("VName"));
                view.getAddressText().setText(result.get("Address"));
                view.getEmailText().setText(result.get("Email"));
                view.getManagerText().setText(result.get("manager-name"));
                view.getPhoneText().setText(result.get("phone"));
                view.getHostNameText().setText(result.get("HName1"));
                view.displayFinding();
            } else if ("Organizer".equals(tableName) || "Host".equals(tableName) ||
                    "Sponsor".equals(tableName)) {
                String n = ("Organizer".equals(tableName)) ? "OrgName": null;
                n = ("Host".equals(tableName)) ? "HName": n;
                n = ("Sponsor".equals(tableName)) ? "SpName" : n;
                view.getNameText().setText(result.get(n));
                view.getAddressText().setText(result.get("Address"));
                view.getEmailText().setText(result.get("Email"));
                view.getSiteText().setText(result.get("Web site"));
                view.getPhoneText().setText(result.get("phone"));
                view.getFaxText().setText(result.get("fax"));
                view.getcNameText().setText(result.get("contact-name"));
                view.getcNumberText().setText(result.get("Contact-Num"));
                view.displayFinding();
            }
        }
    }

    class UpdateHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String tableName = view.getTableName();
            if ("Show".equals(tableName)) {
                updateShow();
            } else if ("Venue".equals(tableName)) {
                updateVenue();
            } else if ("ShowReport".equals(tableName) || "ManagementReport".equals
                    (tableName) || "EventSchedule".equals(tableName)) {
                updateReport(tableName);
            } else {
                update(tableName);
            }
        }

        private void update(String table) {
            String name = view.getNameText().getText();
            String address = view.getAddressText().getText();
            String email = view.getEmailText().getText();
            String site = view.getSiteText().getText();
            String phone = view.getPhoneText().getText();
            String fax = view.getFaxText().getText();
            String cName = view.getcNameText().getText();
            String cNumber = view.getcNumberText().getText();

            if (name.isEmpty() || address.isEmpty() || email.isEmpty() || site.isEmpty()
                    || phone.isEmpty() || fax.isEmpty() || cName.isEmpty()
                    || cNumber.isEmpty()) {
                view.displayResult("All Fields Are Required!");
            } else {
                boolean result = model.update(table, address, email, site, phone,
                        fax, cName, cNumber, name);
                if (!result) {
                    view.displayResult("Error: Operation Failed!");
                } else {
                    view.goBack();
                    view.clearFields();
                    view.displayResult(name + " was successfully updated!");
                }
            }
        }

        private void updateShow() {
            String name = view.getNameText().getText();
            String attendee = view.getAttendeeText().getText();
            String target = view.getTargetText().getText();
            String start = view.getStartText().getText();
            String end = view.getEndText().getText();
            String prefText = view.getPrefText().getText();
            String type = view.getsTypeText().getText();
            String orgName = view.getOrgNameText().getText();

            if (name.isEmpty() || attendee.isEmpty() || target.isEmpty()
                    || start.isEmpty() || end.isEmpty() || prefText.isEmpty()
                    || type.isEmpty() || orgName.isEmpty()) {
                view.displayResult("All Fields Are Required!");
            } else {
                boolean result = model.update("Show",  attendee, target, start,
                        end, prefText, type, orgName, name);
                if (!result) {
                    view.displayResult("Error: Operation Failed!");
                } else {
                    view.goBack();
                    view.clearFields();
                    view.displayResult(name + " was successfully updated!");

                }
            }
        }

        private void updateVenue() {
            String name = view.getNameText().getText();
            String address = view.getAddressText().getText();
            String email = view.getEmailText().getText();
            String manager = view.getManagerText().getText();
            String phone = view.getPhoneText().getText();
            String hostName = view.getHostNameText().getText();

            if (name.isEmpty() || address.isEmpty() || email.isEmpty()
                    || manager.isEmpty() || phone.isEmpty() || hostName.isEmpty()) {
                view.displayResult("All Fields Are Required!");
            } else {
                boolean result = model.update("Venue", address, email,
                        manager, phone, hostName, name);
                if (!result) {
                    view.displayResult("Error: Operation Failed!");
                } else {
                    view.goBack();
                    view.clearFields();
                    view.displayResult(name + " was successfully updated!");
                }
            }
        }

        private void updateReport(String table) {

        }
    }

    class DeleteHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String tableName = view.getTableName();
            String name = view.getNameText().getText();
            boolean result = model.delete(tableName, name);
            if (!result) {
                view.displayResult("Error: Operation Failed!");
            } else {
                view.goBack();
                view.clearFields();
                view.displayResult(name + " was successfully deleted!");
            }
        }
    }

    class ReportHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
           Button button = (Button) event.getSource();
            String table = button.getText();
            String name = view.getNameText().getText();
            view.setReport(table.replaceAll("\\s",""));
            ConcurrentHashMap<String, String> hashMap = model.getReport(view.getReport(),
                    name);
            if (hashMap.isEmpty()) {
                view.displayResult("Error: Could Not Load Report!");
            } else {
                if ("Event Schedule".equals(table)) {
                    hashMap.forEach((key, value) -> {
                        if (key.startsWith("EName")) {
                            TextField tf = new TextField();
                            tf.setText(value);
                            view.eventList.add(tf);
                        }

                        if (key.startsWith("LName")) {
                            TextField tf = new TextField();
                            tf.setText(value);
                            view.locationList.add(tf);
                        }

                        if (key.startsWith("InOut")) {
                            TextField tf = new TextField();
                            tf.setText(value);
                            view.inOutList.add(tf);
                        }

                        if (key.startsWith("VName")) {
                            TextField tf = new TextField();
                            tf.setText(value);
                            view.venueList.add(tf);
                        }
                    });
                } else if ("Management Report".equals(table)) {
                    hashMap.forEach((key, value) -> {
                        if (key.startsWith("SName")) {
                            TextField tf = new TextField();
                            tf.setText(value);
                            view.showList.add(tf);
                        }

                        if (key.startsWith("Date")) {
                            TextField tf = new TextField();
                            tf.setText(value);
                            view.dateSpList.add(tf);
                        }

                        if (key.startsWith("Cash")) {
                            TextField tf = new TextField();
                            tf.setText(value);
                            view.cashList.add(tf);
                        }

                        if (key.startsWith("In-kind")) {
                            TextField tf = new TextField();
                            tf.setText(value);
                            view.inKindList.add(tf);
                        }

                    });

                } else if ("Show Report".equals(table)) {

                }
                view.showReport();
            }
        }
    }
}
