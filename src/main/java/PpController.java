import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class PpController {

    @FXML
    private Tab tbProductionLine;

    @FXML
    private GridPane prodLineInput;

    @FXML
    private TextField prodName;

    @FXML
    private TextField prodManuf;

    @FXML
    private ComboBox<String> itemType;

    @FXML
    private Button buttonAddItem;

    @FXML
    private Tab tbProduce;

    @FXML
    private Button buttonRecProd;

    @FXML
    private ComboBox<String> cmbQuantity;

    @FXML
    private Tab tbProductionLog;

    @FXML
    void addItem(ActionEvent event) {
        updateProduct();
    }

    @FXML
    void record(ActionEvent event) {

    }

    public void initialize() {

        cmbQuantity.setEditable(true);

        cmbQuantity.getSelectionModel().selectFirst();

        for( int count = 1; count <=10; count++ ){
            cmbQuantity.getItems().add(String.valueOf(count));
        }
    }


    public void updateProduct() {
        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:./res/ProdProj";


        //  Database credentials

        final String USER = "";
        final String PASS = "";

        Connection conn = null;
        Statement stmt = null;


        String name = prodName.getText();
        String manufacturer = prodManuf.getText();

        try {

            // STEP 1: Register JDBC driver

            Class.forName(JDBC_DRIVER);


            //STEP 2: Open a connection

            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            //STEP 3: Execute a query

            stmt = conn.createStatement();


            String sql = "INSERT INTO Product(type, manufacturer, name) " +
                    "VALUES ( 'Audio', '" + manufacturer + "' , '" + name + "' )";


            stmt.executeUpdate(sql);


            System.out.println("Select * FROM Products");

            // STEP 4: Clean-up environment

            stmt.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}

