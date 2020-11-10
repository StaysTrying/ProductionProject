import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import static java.lang.Integer.parseInt;
import static javafx.collections.FXCollections.observableArrayList;


public class PpController {

    @FXML
    private Tab tbProductionLine;

    @FXML
    private GridPane prodLineInput;

    @FXML
    private TableView<Product> prodLineTblView;

    @FXML
    private TableColumn<?,?> nameCol;

    @FXML
    private TableColumn<?,?> typeCol;

    @FXML
    private TableColumn<?,?> manufacturerCol;

    @FXML
    private TextField prodName;

    @FXML
    private TextField prodManufacturer;

    @FXML
    private ChoiceBox<String> choiceItemType;

    @FXML
    private Button buttonAddItem;

    @FXML
    private Tab tbProduce;

    @FXML
    private ListView<Product> prodListView;

    @FXML
    private Button buttonRecProd;

    @FXML
    private ComboBox<String> cmbQuantity;

    @FXML
    private Tab tbProductionLog;

    @FXML
    private TextArea prodLogTxt;

    @FXML
    void addItem(ActionEvent event) {
        connectToDB();
        updateProduct();
        closeDBConn();
    }


    @FXML
    void record(ActionEvent event) {
        connectToDB();
        recordProduction();
        closeDBConn();
    }

    //Global fields for SQL integration
    private static Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    ObservableList<Product> productLine = FXCollections.observableArrayList();

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
            stmt = conn.createStatement();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void closeDBConn(){

        try{
            pstmt.close(); 
            stmt.close();
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        connectToDB();
        setUpTblView();
        populateProdLine();
        updateListView();
        populateColumn();
        populateCmbBox();
        populateTypeBox();
        //closeDBConn();
    }

    public void populateProdLine(){
        try {
            //Create SQL Statement
            stmt = conn.createStatement();

            String sql = "SELECT * FROM Product";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Product prod = new Widget(rs.getString(2), ItemType.valueOf(rs.getString(3)), rs.getString(4));
                productLine.add(prod);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setUpTblView(){

        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory("type"));
        manufacturerCol.setCellValueFactory(new PropertyValueFactory("manufacturer"));

        prodLineTblView.setItems(productLine);

        prodListView.setItems(productLine);

    }

    public void populateColumn(){

        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory("type"));
        manufacturerCol.setCellValueFactory(new PropertyValueFactory("manufacturer"));

        prodLineTblView.setItems(productLine);

    }

    public void populateTypeBox(){
        choiceItemType.setValue(String.valueOf(ItemType.AUDIO));
        for ( ItemType itemCode: ItemType.values()) {
            choiceItemType.getItems().add(String.valueOf(itemCode));
        }
    }

    public void populateCmbBox(){
        cmbQuantity.setValue("1");
        cmbQuantity.setEditable(true);
        cmbQuantity.getSelectionModel().selectFirst();
        for (int count = 1; count <= 10; count++) {
            cmbQuantity.getItems().add(String.valueOf(count));
        }

    }

    public void recordProduction(){

        int quantity = parseInt(cmbQuantity.getValue());

        Product prod = prodListView.getSelectionModel().getSelectedItem();

        for (int count = 1; count <= quantity; count++) {
            ProductionRecord pr = new ProductionRecord(prod);

            prodLogTxt.appendText(pr.toString() + "\n") ;
        }

    }

    public void printProd() {

        try {
            //STEP 3: Execute a query
            stmt = conn.createStatement();

            String sql = "SELECT * FROM Product";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct() {

        try {
            //Create a prepared statement
            String sql = "INSERT INTO Product(name, manufacturer, type) " +
                    "VALUES (?,?,?)";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, prodName.getText());
            pstmt.setString(2, prodManufacturer.getText());
            pstmt.setString(3, String.valueOf(choiceItemType.getValue()));

            pstmt.executeUpdate(); // Execute the query

            Product prod = new Widget(prodName.getText(), ItemType.valueOf(choiceItemType.getValue()) , prodManufacturer.getText());

            productLine.add(prod);

            printProd();
            updateListView();

        } catch (IllegalStateException e){
            System.out.println("Print stuff if bad " + e);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateListView(){
        prodListView.setItems(productLine);
    }
}

