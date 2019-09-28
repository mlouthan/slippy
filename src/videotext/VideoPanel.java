package videotext;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import videotext.textanimation.PagedText;
import videotext.textanimation.TextAnimator;

public class VideoPanel extends Stage {

    private final Group videoRoot;

    private double xOffset = 0;
    private double yOffset = 0;

    public VideoPanel() {
        videoRoot = new Group();

        Scene videoScene = new Scene(videoRoot, Config.VIDEO_WIDTH, Config.VIDEO_HEIGHT);
        videoScene.setOnMousePressed(ev -> {
                xOffset = ev.getSceneX();
                yOffset = ev.getSceneY();
        });
        videoScene.setOnMouseDragged(ev -> {
                setX(ev.getScreenX() - xOffset);
                setY(ev.getScreenY() - yOffset);
        });
        videoScene.setFill(Paint.valueOf(Config.DEFAULT_SCREEN_COLOR));
        setScene(videoScene);
    }

    public void setBackground(final String color) {
        videoRoot.getScene().setFill(Paint.valueOf(color));
    }

    public void play(final TextVideo textVideo, final String textToDisplay) {
        videoRoot.getChildren().clear();
        MediaView mediaView = textVideo.getMediaView();
        mediaView.getMediaPlayer().setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                videoRoot.getChildren().clear();
            }
        });
        Text text = textVideo.getText();
        videoRoot.getChildren().add(mediaView);
        videoRoot.getChildren().add(text);
        text.setText("");
        TextAnimator animator = new TextAnimator(textVideo.getVideoConfig(), mediaView.getMediaPlayer(), text);
        PagedText pagedText = new PagedText(textToDisplay);
        animator.animateText(pagedText);
    }
}
