import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Tyler Krawec
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent loginRoot = FXMLLoader.load(getClass().getResource("EmployeeLogin.fxml"));

        Scene login = new Scene(loginRoot, 429, 336);

        primaryStage.setTitle("Production Project");
        primaryStage.setScene(login);
        primaryStage.show();


    }
}
