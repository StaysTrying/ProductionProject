import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;


public class Controller {

    @FXML
    private Tab tab1;

    @FXML
    private Button buttonAddItem;

    @FXML
    private Tab tab2;

    @FXML
    private Button buttonRecProd;

    @FXML
    private Tab tab3;

    @FXML
    void event(ActionEvent event) {
    System.out.println("Event Handled.");
    }

    @FXML
    void record(ActionEvent event) {
    System.out.println("Product Recorded.");
    }
}
