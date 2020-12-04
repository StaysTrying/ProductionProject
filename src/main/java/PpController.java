import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;

import static java.lang.Integer.parseInt;

/**
 * @author Tyler Krawec
 */
public class PpController {

    //Region for implemented javafx elements
    @FXML
    private TableView<Product> prodLineTblView;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> typeCol;

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

    @FXML
    void addItem(ActionEvent event) {
        connectToDB();
        updateProduct();
    }

    @FXML
    void record(ActionEvent event) {
        connectToDB();
        recordProduction();

    }

    //Global fields for SQL integration
    private  Connection conn;
    private  Statement stmt;
    private  PreparedStatement pstmt;
    private  ResultSet rs;

    ObservableList<Product> productLine = FXCollections.observableArrayList();
    ObservableList<ProductionRecord> productionRun = FXCollections.observableArrayList();

    //Code that runs once every time
    public void initialize() {
        connectToDB();
        fillProdLine();
        fillProdRun();
        populateTblView();
        populateListView();
        populateTxtArea();
        populateCmbBox();
        populateTypeBox();
    }

    //Method for connected to ProdProj DB
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

    //Populating the ProdLine ObservableList
    public void fillProdLine() {
        try {
            //Create SQL Statement
            stmt = conn.createStatement();

            String sql = "SELECT * FROM Product";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Product prod = new Widget(rs.getInt(1), rs.getString(2), ItemType.valueOf(rs.getString(3)), rs.getString(4));
                productLine.add(prod);
            }

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Populating the ProdLine ObservableList
    public void fillProdRun() {
        productionRun.clear();

        try {
            //Create SQL Statement
            stmt = conn.createStatement();

            String sql = "SELECT * FROM PRODUCTIONRECORD";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ProductionRecord pr = new ProductionRecord(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4));

                if(pr.getSerialNum().substring(3,5).equals("AU") || pr.getSerialNum().substring(3,5).equals("AM")){
                    ProductionRecord.audCount++;
                } else if(pr.getSerialNum().substring(3,5).equals("VI") || pr.getSerialNum().substring(3,5).equals("VM"))
                { ProductionRecord.visCount++;}

                productionRun.add(pr);

            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Set up table columns and populates it with the ObservableList
    public void populateTblView() {

        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory("type"));
        manufacturerCol.setCellValueFactory(new PropertyValueFactory("manufacturer"));

        prodLineTblView.setItems(productLine);

    }

    public void populateListView(){
        prodListView.setItems(productLine);
    }

    public void populateTxtArea(){
        prodLogTxt.setEditable(false);
        prodLogTxt.clear();
        for(int i = 0; i < productionRun.size(); i++ ){
            prodLogTxt.appendText(productionRun.get(i).toString());
        }
    }

    public void populateCmbBox() {
        cmbQuantity.setValue("1");
        cmbQuantity.setEditable(true);
        cmbQuantity.getSelectionModel().selectFirst();
        for (int count = 1; count <= 10; count++) {
            cmbQuantity.getItems().add(String.valueOf(count));
        }
    }

    public void populateTypeBox() {
        choiceItemType.setValue(String.valueOf(ItemType.AUDIO));
        for (ItemType itemCode : ItemType.values()) {
            choiceItemType.getItems().add(String.valueOf(itemCode));
        }
    }

    public void updateProduct() {

        try {
            //Create a prepared statement
            String sql = "INSERT INTO Product(name, manufacturer, type) " +
                    "VALUES (?,?,?)";

            String name = prodName.getText();
            String manufacturer = prodManufacturer.getText();
            String type = String.valueOf(choiceItemType.getValue());

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setString(2, manufacturer);
            pstmt.setString(3, type);

            pstmt.executeUpdate(); // Execute the query

            Product prod = new Widget(name, ItemType.valueOf(type), manufacturer);
            productLine.add(prod);

            pstmt.close();
            conn.close();

        } catch (IllegalStateException e) {
            System.out.println("Print stuff if bad " + e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void recordProduction() {

        int quantity = parseInt(cmbQuantity.getValue());
        Product prod = prodListView.getSelectionModel().getSelectedItem();

        try {
            //Create a prepared statement
            String sql = "INSERT INTO ProductionRecord(product_id, serial_num, date_produced) " +
                    "VALUES (?,?,?)";

            stmt = conn.createStatement();
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

            connectToDB();
            fillProdRun();
            populateTxtArea();

            pstmt.close();
            conn.close();

        } catch (IllegalStateException e) {
            System.out.println("Print stuff if bad " + e);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

