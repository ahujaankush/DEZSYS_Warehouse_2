package tgm.aahuja.warehouse.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WarehouseData implements Serializable {

  private String warehouseID;
  private String warehouseName;
  private String timestamp;

  private ArrayList<Product> productData;

  /**
   * Constructor
   */
  public WarehouseData() {

    this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    productData = new ArrayList<>();
  }

  /**
   * Setter and Getter Methods
   */
  public String getWarehouseID() {
    return warehouseID;
  }

  public void setWarehouseID(String warehouseID) {
    this.warehouseID = warehouseID;
  }

  public String getWarehouseName() {
    return warehouseName;
  }

  public void setWarehouseName(String warehouseName) {
    this.warehouseName = warehouseName;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public ArrayList<Product> getProductData() {
    return productData;
  }

  public void setProductData(ArrayList<Product> productData) {
    this.productData = productData;
  }

  /**
   * Methods
   */
  @Override
  public String toString() {
    String info = String.format("Warehouse Info: ID = %s, timestamp = %s",
        warehouseID, timestamp);
    return info;
  }
}
