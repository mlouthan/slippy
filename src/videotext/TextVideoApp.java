package videotext;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import videotext.slippy.SlippyTextVideo;

import java.util.Arrays;
import java.util.List;

public class TextVideoApp extends Application {

    private TextArea slippyTextInput;

    public TextArea getSlippyTextInput() {
        return slippyTextInput;
    }

    private final List<TextVideo> textVideos = Arrays.asList(new SlippyTextVideo());
    private TextVideo selectedVideo = null;
    private FrameRate selectedFramerate = null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        VideoPanel videoStage = new VideoPanel();
        videoStage.initStyle(StageStyle.UNDECORATED);
        videoStage.show();

        MenuBar menuBar = createMenuBar(textVideos);

        Scene inputScene = createSlippyInputScene(videoStage);
        ((Group)inputScene.getRoot()).getChildren().add(menuBar);
        primaryStage.setScene(inputScene);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("What does Slippy say?");
        primaryStage.show();

        primaryStage.setOnCloseRequest(event1 -> videoStage.close());
        videoStage.setOnCloseRequest(event1 -> primaryStage.close());
    }

    private MenuBar createMenuBar(final List<TextVideo> textVideos) {
        MenuBar menuBar = new MenuBar();
        menuBar.setUseSystemMenuBar(true);
        Menu videoMenu = new Menu("Video");

        ToggleGroup framerateGroup = new ToggleGroup();
        if (textVideos.size() == 1) {
            TextVideo selected = textVideos.get(0);
            Menu framerateMenu = new Menu("Frame Rate");
            createFramerateMenu(selected, framerateGroup, framerateMenu, true);
            videoMenu.getItems().add(framerateMenu);
        } else if (textVideos.size() > 1) {
            boolean isSelected = true;
            for (TextVideo textVideo : textVideos) {
                Menu menu = new Menu(textVideo.getVideoName());
                createFramerateMenu(textVideo, framerateGroup, menu, isSelected);
                videoMenu.getItems().add(menu);
                isSelected = false;
            }
        }
        menuBar.getMenus().add(videoMenu);
        return menuBar;
    }

    private void createFramerateMenu(final TextVideo textVideo, final ToggleGroup framerateGroup, final Menu framerateMenu, boolean isSelected) {
        for (FrameRate frameRate : FrameRate.values()) {
            RadioMenuItem FPSItem = new RadioMenuItem(frameRate.getName());
            FPSItem.setOnAction(ev -> {
                this.selectedVideo = textVideo;
                this.selectedFramerate = frameRate;
            });
            if (!textVideo.getPossibleFramerates().contains(frameRate)) {
                FPSItem.setDisable(true);
            }
            if (frameRate == textVideo.getDefaultFramerate() && isSelected) {
                FPSItem.setSelected(true);
                this.selectedVideo = textVideo;
                this.selectedFramerate = frameRate;
            }
            FPSItem.setToggleGroup(framerateGroup);
            framerateMenu.getItems().addAll(FPSItem);
        }
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
        playButton.setOnAction(e -> videoPanel.play(this.selectedVideo, this.selectedFramerate, slippyTextInput.getText()));

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
