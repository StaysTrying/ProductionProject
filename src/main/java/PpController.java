import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
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
    private ChoiceBox<String> itemType;

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

        itemType.setValue("Audio");
        for ( ItemType itemCode: ItemType.values()) {
            itemType.getItems().add(itemCode.toString());
        }

        cmbQuantity.setValue("Choose Quantity");
        cmbQuantity.setEditable(true);
        cmbQuantity.getSelectionModel().selectFirst();
        for (int count = 1; count <= 10; count++) {
            cmbQuantity.getItems().add(String.valueOf(count));
        }
    }

    public void printProd() {
        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:./res/ProdProj";

        //  Database credentials
        final String USER = "";
        final String PASS = "";
        Connection conn = null;
        Statement stmt = null;

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            stmt = conn.createStatement();

            String sql = "SELECT * FROM Product";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
            }

            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
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

        String type = itemType.getValue();
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
                    "VALUES ( '" + type + "', '" + manufacturer + "' , '" + name + "' )";

            stmt.executeUpdate(sql);

            printProd();

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

