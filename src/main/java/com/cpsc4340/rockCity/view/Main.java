package com.cpsc4340.rockCity.view;
import com.cpsc4340.rockCity.controller.RockCityController;
import com.cpsc4340.rockCity.model.RockCityModel;
import org.apache.log4j.BasicConfigurator;

public class Main {


    public static void main(String[] args) {
        BasicConfigurator.configure();
        RockCityView view = RockCityView.getInstance();
        RockCityModel model = new RockCityModel();
        new RockCityController(model, view);

    }
}
