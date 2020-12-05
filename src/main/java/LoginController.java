
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

/**
 * @author Tyler
 * <p>
 *   Class used for handling user input in order to verifying their credentials for login or
 *   create new accounts for Employees and saving credentials to the Employee table.
 */
public class LoginController {

  //region for my JavaFX elements
  @FXML
  private TextField userTxt;

  @FXML
  private PasswordField passField;

  @FXML
  private TextArea AccInfoTxt;

  @FXML
  private TextField nameTxt;

  @FXML
  private Label invalidMsg;

  @FXML
  private Label invalidMsg2;

  @FXML
  private PasswordField newPass;

  @FXML
  private PasswordField checkNewPass;


  // Initializes stages to be used throughout the use of my application
  Stage popupStage = new Stage();
  Stage invalidStage = new Stage();
  Stage productionStage = new Stage();

  //Global fields for SQL integration
  private Connection conn;
  private Statement stmt;
  private PreparedStatement pstmt;
  private ResultSet rs;

  /**
   * Method to initialize methods that must run once to set up structures in my production app
   */
  public void initialize() {
    connectToDB();
    setUpPopupStage();
    setUpProductionStage();
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
   * Sets up popup stage for when a user enters invalid information when creating a new Employee
   */
  public void setUpPopupStage() {
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

  /**
   * Sets up the stage for my production app once a user logs in using correct information
   */
  public void setUpProductionStage() {
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

  /**
   * Action event to handle the login button click. Opens a database connection and searches
   * Employee table for the username and password entered, then checks to see if information is
   * valid. If invalid, then an popup window will show with instructions.
   */
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
        if (username.equals(validUsername) && password.equals(validPass)) {

          productionStage.show();

          break;
        } else {
          invalidMsg.setText("Incorrect Email or Password");
          invalidMsg2.setText("(If you do not have an account, click the tab above to get started)");
        }
      }

      stmt.close();
      conn.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Action event to handle the create account button click. Checks to see if input is valid, then
   * connects and inputs the information into the Employee table of my database. If invalid, a popup
   * window will show with instructions.
   */
  @FXML
  void newEmployee(ActionEvent event) {
    connectToDB();

    String name = nameTxt.getText();
    String password = newPass.getText();
    String passMatch = checkNewPass.getText();

    if ((passwordsMatch(password, passMatch) && checkName(name) && verifyPassword(password))
        == true) {

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
    } else {
      popupStage.show();
    }
  }

  /**
   * Method to check if the user entered their password correctly.
   *
   * @param password  = A user entered password pulled from GUI.
   * @param passMatch = A check to verify the user entered the correct password.
   */
  public boolean passwordsMatch(String password, String passMatch) {
    if (password.equals(passMatch)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method to check that the user entered their name in the correct format.
   *
   * @param name = User entered name to be checked by checkName
   */
  public boolean checkName(String name) {
    if (name.contains(" ")) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method for verifying the the user entered password meets requirements.
   *
   * @param password = User entered password to be checked by verifyPassword
   */
  public Boolean verifyPassword(String password) {
    if (password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{3,}$")) {
      return true;
    } else {
      return false;
    }
  }

}
