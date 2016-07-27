package main;


import mvc.SetController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Initializer extends Application {

    @Override
    public void start (Stage s) {

        s.setTitle("SET");
        Group root = new Group();
        Scene scene = new Scene(root, 1000, 650);
        s.setScene(scene);
        s.show();
        SetController setController = new SetController(root);
        //setController.startGame();
        
    }

    /**
     * Begins the simulation. Called in Main.java.
     * 
     * @param args Input from command line if run from a terminal.
     */
    public void begin(String[] args) {
        launch(args);
    }
}