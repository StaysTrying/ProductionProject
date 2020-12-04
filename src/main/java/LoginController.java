
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField userTxt;

    @FXML
    private PasswordField passField;

    @FXML
    private TextArea AccInfoTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private PasswordField newPass;

    @FXML
    private PasswordField checkNewPass;

    Stage popupStage = new Stage();
    Stage invalidStage = new Stage();
    Stage productionStage = new Stage();

    //Global fields for SQL integration
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public void initialize(){
        connectToDB();
        setUpPopupStage();
        setUpProductionStage();
        setUpInvalidCredStage();
    }

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

    public void setUpInvalidCredStage(){
        try {
            Parent invalidCred = FXMLLoader.load(getClass().getResource("InvalidLogin.fxml"));
            Scene popup = new Scene(invalidCred, 385, 171);
            invalidStage.setScene(popup);
            invalidStage.initModality(Modality.APPLICATION_MODAL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUpPopupStage(){
        Parent popupRoot;
            try {
                popupRoot = FXMLLoader.load(getClass().getResource("ErrorPopup.fxml"));
                Scene popup = new Scene(popupRoot, 385, 171);
                popupStage.setScene(popup);
                popupStage.initModality(Modality.APPLICATION_MODAL);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void setUpProductionStage(){
        Parent productionRoot;
        try {
            productionRoot = FXMLLoader.load(getClass().getResource("Production.fxml"));
            Scene production = new Scene(productionRoot, 435, 340);
            productionRoot.getStylesheets().add("ProductionProj.css");
            productionStage.setScene(production);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void checkCredentials(ActionEvent event) {
        connectToDB();

        String username = userTxt.getText();
        String password = passField.getText();

        try {
            //Create SQL Statement
            stmt = conn.createStatement();

            String sql = "SELECT * FROM EMPLOYEES";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String validUsername = rs.getString("USERNAME");
                String validPass = rs.getString("PASSWORD");
                if(username.equals(validUsername) && password.equals(validPass)){
                    productionStage.show();
                    break;
                } else invalidStage.show();
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void newEmployee(ActionEvent event) {
        connectToDB();

        String name = nameTxt.getText();
        String password = newPass.getText();
        String passMatch = checkNewPass.getText();

        if ((passwordsMatch(password,passMatch) && checkName(name) && verifyPassword(password)) == true) {

            Employee emp = new Employee(name, password);

            try {
                //Create a prepared statement
                String sql = "INSERT INTO EMPLOYEES(name, username, password, email) " +
                        "VALUES (?,?,?,?)";

                pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, name);
                pstmt.setString(2, emp.getUsername());
                pstmt.setString(3, password);
                pstmt.setString(4, emp.getEmail());

                pstmt.executeUpdate(); // Execute the query

                AccInfoTxt.clear();
                AccInfoTxt.setText(emp.toString());

                pstmt.close();
                conn.close();

            } catch (IllegalStateException e) {
                System.out.println("Print stuff if bad " + e);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else { popupStage.show(); }
    }

    public boolean passwordsMatch(String password,String passMatch){
        if(password.equals(passMatch)){
            return true;
        } else return false;
    }

    public boolean checkName(String name){
        if(name.contains(" ")){
            return true;
        } else return false;
    }

    public Boolean verifyPassword(String password){
        if(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{3,}$")){
            return true;
        } else return false;
    }

}
