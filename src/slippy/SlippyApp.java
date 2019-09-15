package slippy;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

        VideoPanel videoStage = new VideoPanel();
        videoStage.initStyle(StageStyle.UNDECORATED);
        videoStage.setTitle("Slippy");
        videoStage.show();

        Scene inputScene = createSlippyInputScene(videoStage);
        primaryStage.setScene(inputScene);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("What does Slippy say?");
        primaryStage.show();

        primaryStage.setOnCloseRequest(event1 -> videoStage.close());
        videoStage.setOnCloseRequest(event1 -> primaryStage.close());
    }

    private Scene createSlippyInputScene(final VideoPanel videoPanel) {
        slippyTextInput = new TextArea();
        slippyTextInput.setPrefRowCount(3);
        slippyTextInput.setPrefColumnCount(20);
        slippyTextInput.setWrapText(true);
        slippyTextInput.setPromptText("Slippy says");

        Button playButton = new Button("Play");
        playButton.setDefaultButton(true);

        slippyTextInput.addEventFilter(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                if (ev.isShiftDown()) {
                    slippyTextInput.appendText("\n");
                } else {
                    playButton.fire();
                }
                ev.consume();
            }
        });
        playButton.setOnAction(e -> videoPanel.play(slippyTextInput.getText()));

        Group inputRoot = new Group();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Slippy says:"), 0, 0);
        grid.add(slippyTextInput, 1, 0, 1, 2);
        grid.add(playButton, 0, 1);
        inputRoot.getChildren().add(grid);

        return new Scene(inputRoot, 400, 75);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
