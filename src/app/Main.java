package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private Scene mainScene;
    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        primaryStage.setResizable(false);
        mainScene = new Scene(root, 1200, 800);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Projekt Banku");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
