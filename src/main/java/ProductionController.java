import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;

import static java.lang.Integer.parseInt;

/**
 * @author Tyler Krawec
 * <p>
 * Controller class to control my Production stage and related fxml file.
 * Connects to my database and allows for user entered input.
 */
public class ProductionController {

  //Region for implemented javafx elements
  @FXML
  private TableView<Product> prodLineTblView;

  @FXML
  private TableColumn<?, ?> nameCol;

  @FXML
  private TableColumn<?, ?> typeCol;

  @FXML
  private Rectangle invalidName;

  @FXML
  private Rectangle invalidManufacturer;

  @FXML
  private TableColumn<?, ?> manufacturerCol;

  @FXML
  private TextField prodName;

  @FXML
  private TextField prodManufacturer;

  @FXML
  private ChoiceBox<String> choiceItemType;

  @FXML
  private ListView<Product> prodListView;

  @FXML
  private ComboBox<String> cmbQuantity;

  @FXML
  private TextArea prodLogTxt;

  /**
   * Method for handling the addItem button click.
   */
  @FXML
  void addItem(ActionEvent event) {
    connectToDB();
    updateProduct();
  }

  /**
   * Method for handling the record production button click.
   */
  @FXML
  void record(ActionEvent event) {
    connectToDB();
    recordProduction();

  }

  //Global fields for SQL integration
  private Connection conn;
  private Statement stmt;
  private PreparedStatement pstmt;
  private ResultSet rs;

  //Observable lists of objects utilized in my GUI
  ObservableList<Product> productLine = FXCollections.observableArrayList();
  ObservableList<ProductionRecord> productionRun = FXCollections.observableArrayList();

  /**
   * Method that runs at program start to set up visual elements of my GUI.
   */
  public void initialize() {

    invalidName.setOpacity(0);
    invalidManufacturer.setOpacity(0);

    fillProdLine();
    fillProdRun();

    populateTblView();
    populateListView();

    populateTxtArea();
    populateCmbBox();
    populateTypeBox();
  }

  /**
   * Opens a connection to my database files
   */
  public void connectToDB() {

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/ProdProj";

    //  Database credentials
    final String USER = "";
    final String PASS = "";

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Fills ProdLine observable list with data from my Product table
   */
  public void fillProdLine() {
    connectToDB();
    try {
      //Create SQL Statement
      stmt = conn.createStatement();

      String sql = "SELECT * FROM Product";

      rs = stmt.executeQuery(sql);

      while (rs.next()) {
        Product prod = new Widget(rs.getInt(1), rs.getString(2), ItemType.valueOf(rs.getString(3)),
            rs.getString(4));
        productLine.add(prod);
      }

      stmt.close();
      conn.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Fills ProdRun observable list with data from my ProductionRecord table
   */
  public void fillProdRun() {

    connectToDB();

    productionRun.clear();

    try {

      //Create SQL Statement
      stmt = conn.createStatement();

      String sql = "SELECT * FROM PRODUCTIONRECORD";

      rs = stmt.executeQuery(sql);

      while (rs.next()) {
        ProductionRecord pr = new ProductionRecord(rs.getInt(1), rs.getInt(2), rs.getString(3),
            rs.getDate(4));

        if (pr.getSerialNum().substring(3, 5).equals("AU") || pr.getSerialNum().substring(3, 5)
            .equals("AM")) {
          ProductionRecord.audCount++;
        } else if (pr.getSerialNum().substring(3, 5).equals("VI") || pr.getSerialNum()
            .substring(3, 5).equals("VM")) {
          ProductionRecord.visCount++;
        }

        productionRun.add(pr);

      }

      stmt.close();
      conn.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Fills table view on my GUI with Products saved in prodLine observable list
   */
  public void populateTblView() {

    nameCol.setCellValueFactory(new PropertyValueFactory("name"));
    typeCol.setCellValueFactory(new PropertyValueFactory("type"));
    manufacturerCol.setCellValueFactory(new PropertyValueFactory("manufacturer"));

    prodLineTblView.setItems(productLine);

  }

  /**
   * Fills list view on my GUI with Products saved in the Product table
   */
  public void populateListView() {
    prodListView.setItems(productLine);
  }

  /**
   * Populates the text area in my GUI with data from my ProductionRecord table.
   */
  public void populateTxtArea() {
    prodLogTxt.setEditable(false);
    prodLogTxt.clear();
    for (int i = 0; i < productionRun.size(); i++) {
      prodLogTxt.appendText(productionRun.get(i).toString());
    }
  }

  /**
   * Populates the combo box on my GUI with the numbers 1-10
   */
  public void populateCmbBox() {
    cmbQuantity.setValue("1");
    cmbQuantity.setEditable(true);
    cmbQuantity.getSelectionModel().selectFirst();
    for (int count = 1; count <= 10; count++) {
      cmbQuantity.getItems().add(String.valueOf(count));
    }
  }

  /**
   * Populates the choice box on my GUI with the possible item types from the ItemType enum
   */
  public void populateTypeBox() {
    choiceItemType.setValue(String.valueOf(ItemType.AUDIO));
    for (ItemType itemCode : ItemType.values()) {
      choiceItemType.getItems().add(String.valueOf(itemCode));
    }
  }

  /**
   * Adds user entered data to my Product table in my database
   */
  public void updateProduct() {
    connectToDB();
    invalidName.setOpacity(0);
    invalidManufacturer.setOpacity(0);
    try {
      //Create a prepared statement
      String sql = "INSERT INTO Product(name, manufacturer, type) " +
          "VALUES (?,?,?)";

      String name = prodName.getText();
      String manufacturer = prodManufacturer.getText();
      String type = String.valueOf(choiceItemType.getValue());

      if(name.equals("")){
        prodName.setText("Add Name");
        invalidName.setOpacity(1);
      }else if (manufacturer.equals("")){
        prodManufacturer.setText("Add Manufacturer");
        invalidManufacturer.setOpacity(1);
      }
      else{
        pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, name);
        pstmt.setString(2, manufacturer);
        pstmt.setString(3, type);

        pstmt.executeUpdate(); // Execute the query

        Product prod = new Widget(name, ItemType.valueOf(type), manufacturer);
        productLine.add(prod);

      }

      pstmt.close();
      conn.close();
    } catch (IllegalStateException e) {
      System.out.println("Print stuff if bad " + e);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Selects a product from my list view and quantity from my combo box to to create production
   * records for any product saved in the Product table and saves data to the ProductionRecord
   * table
   */
  public void recordProduction() {
    connectToDB();
    int quantity = parseInt(cmbQuantity.getValue());
    Product prod = prodListView.getSelectionModel().getSelectedItem();

    try {
      //Create a prepared statement
      String sql = "INSERT INTO ProductionRecord(product_id, serial_num, date_produced) " +
          "VALUES (?,?,?)";

      pstmt = conn.prepareStatement(sql);

      for (int count = 1; count <= quantity; count++) {

        ProductionRecord pr = new ProductionRecord(prod);

        int productId = prod.getId();
        String serialNum = pr.getSerialNum();
        Timestamp ts = new Timestamp(pr.getProdDate().getTime());

        pstmt.setString(1, String.valueOf(productId));
        pstmt.setString(2, serialNum);
        pstmt.setString(3, String.valueOf(ts));

        pstmt.executeUpdate(); // Execute the query

      }

      pstmt.close();
      conn.close();

      fillProdRun();
      populateTxtArea();


    } catch (IllegalStateException e) {
      System.out.println("Print stuff if bad " + e);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}

