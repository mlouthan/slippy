package slippy;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SlippyApp extends Application {

    private TextArea slippyTextInput;

    public TextArea getSlippyTextInput() {
        return slippyTextInput;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        VideoPanel videoStage = new VideoPanel();
        videoStage.initStyle(StageStyle.UNDECORATED);
        videoStage.setTitle("Slippy");
        videoStage.show();

        Group inputRoot = new Group();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        slippyTextInput = new TextArea();
        slippyTextInput.setPrefRowCount(3);
        slippyTextInput.setPrefColumnCount(20);
        slippyTextInput.setWrapText(true);
        slippyTextInput.setPromptText("Slippy says");
        grid.add(new Label("Slippy says:"), 0, 0);
        grid.add(slippyTextInput, 1, 0, 1, 2);
        Button playButton = new Button("Play");
        grid.add(playButton, 0, 1);
        inputRoot.getChildren().add(grid);
        Scene inputScene = new Scene(inputRoot, 400, 75);
        primaryStage.setScene(inputScene);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("What does Slippy say?");
        primaryStage.show();

        EventHandler<ActionEvent> event = e -> videoStage.play(slippyTextInput.getText());
        playButton.setOnAction(event);

        primaryStage.setOnCloseRequest(event1 -> videoStage.close());
        videoStage.setOnCloseRequest(event1 -> primaryStage.close());
    }


    public static void main(String[] args) {
        launch(args);
    }

}
