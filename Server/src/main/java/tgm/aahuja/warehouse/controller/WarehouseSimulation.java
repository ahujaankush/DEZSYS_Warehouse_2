package tgm.aahuja.warehouse.controller;

import tgm.aahuja.warehouse.model.Warehouse.Product;
import tgm.aahuja.warehouse.model.Warehouse.WarehouseData;

public class WarehouseSimulation {

  private int getRandomInt(int inMinimum, int inMaximum) {

    double number = (Math.random() * ((inMaximum - inMinimum) + 1)) + inMinimum;
    return (int)number;
  }

  public WarehouseData getData(String inID) {

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

    Product[] products = new Product[20];
    for (int i = 0; i < 20; ++i) {
      products[i] = Product.newBuilder()
                        .setProductID("00-" + getRandomInt(10000, 99999))
                        .setProductName(productNames[i][0])
                        .setProductCategory(productNames[i][1])
                        .setProductQuantity(getRandomInt(9, 100))
                        .build();
    }

    WarehouseData data = WarehouseData.newBuilder()
                             .setWarehouseID(inID)
                             .setWarehouseName("Linz Bahnhof")
                             .addProductData(products[0])
                             .addProductData(products[1])
                             .addProductData(products[2])
                             .addProductData(products[3])
                             .addProductData(products[4])
                             .addProductData(products[5])
                             .addProductData(products[6])
                             .addProductData(products[7])
                             .addProductData(products[8])
                             .addProductData(products[9])
                             .addProductData(products[10])
                             .addProductData(products[11])
                             .addProductData(products[12])
                             .addProductData(products[13])
                             .addProductData(products[14])
                             .addProductData(products[15])
                             .addProductData(products[16])
                             .addProductData(products[17])
                             .addProductData(products[18])
                             .addProductData(products[19])
                             .build();

    return data;
  }
}
