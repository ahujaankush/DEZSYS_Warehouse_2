package tgm.aahuja.warehouse.model;

import java.io.Serializable;

public class Product implements Serializable {

  String productID;
  String productName;
  String productCategory;
  int productQuantity;

  public Product(String productID, String productName, String productCategory,
                 int productQuantity) {
    this.productID = productID;
    this.productName = productName;
    this.productCategory = productCategory;
    this.productQuantity = productQuantity;
  }

  public String getProductID() { return productID; }

  public void setProductID(String productID) { this.productID = productID; }

  public String getProductName() { return productName; }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductCategory() { return productCategory; }

  public void setProductCategory(String productCategory) {
    this.productCategory = productCategory;
  }

  public int getProductQuantity() { return productQuantity; }

  public void setProductQuantity(int productQuantity) {
    this.productQuantity = productQuantity;
  }
}
