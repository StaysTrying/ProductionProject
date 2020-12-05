import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Tyler Krawec
 * <p>
 * Main process for my program that runs on program start
 */
public class Main extends Application {

  /**
   * Launches my program
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Sets up first GUI stage for user to interact with
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent loginRoot = FXMLLoader.load(getClass().getResource("EmployeeLogin.fxml"));

    Scene login = new Scene(loginRoot, 429, 336);

    primaryStage.setTitle("Production Project");
    primaryStage.setScene(login);
    primaryStage.show();


  }
}
