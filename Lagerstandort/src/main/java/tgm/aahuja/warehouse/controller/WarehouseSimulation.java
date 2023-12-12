package tgm.aahuja.warehouse.controller;

import tgm.aahuja.warehouse.model.Product;
import tgm.aahuja.warehouse.model.WarehouseData;

public class WarehouseSimulation {

  private int getRandomInt(int inMinimum, int inMaximum) {

    double number = (Math.random() * ((inMaximum - inMinimum) + 1)) + inMinimum;
    return (int)number;
  }

  public WarehouseData getData(String inID) {

    WarehouseData data = new WarehouseData();
    data.setWarehouseID(inID);
    data.setWarehouseName("Linz Bahnhof");

    // Code responsible for generating warehouse

    String[] randomWords = new String[] {
        "Saft",  "Apfel", "Orange", "Mix",     "Super", "Waschmittel", "Zucker",
        "Stift", "Torte", "Sauer",  "Kirsche", "Skyr",  "Protein",     "Gym"};
    String[][] productNames = new String[20][2];
    for (int i = 0; i < productNames.length; i++) {
      productNames[i] = new String[] {
          randomWords[getRandomInt(0, randomWords.length - 1)] + " " +
              randomWords[getRandomInt(0, randomWords.length - 1)],
          randomWords[getRandomInt(0, randomWords.length - 1)]};
    }

    int warehouseCapacity = productNames.length;
    for (int i = 0; i < warehouseCapacity; i++) {
      data.getProductData().add(
          new Product("00-" + getRandomInt(10000, 99999), productNames[i][0],
                      productNames[i][1], getRandomInt(0, 100)));
    }

    return data;
  }
}
