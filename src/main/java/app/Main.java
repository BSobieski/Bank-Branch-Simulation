package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        primaryStage.setResizable(false);
        Scene mainScene = new Scene(root, 1200, 800);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Bank Simulation Project");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
