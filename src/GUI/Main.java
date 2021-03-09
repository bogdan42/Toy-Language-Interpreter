package GUI;

import GUI.Controller.InterpreterController;
import GUI.Controller.mainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/GUI/startWindow.fxml"));
        Parent root = loader.load();
        mainWindowController controller = loader.getController();
        controller.setStartWindow(primaryStage);
        Scene primaryScene = new Scene(root, 800, 600);
        primaryScene.getStylesheets().add(getClass().getResource("/GUI/startWindow.css").toExternalForm());
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("ToyLanguage");
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
