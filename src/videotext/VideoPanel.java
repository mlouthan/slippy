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

    public VideoPanel() {
        videoRoot = new Group();

        Scene videoScene = new Scene(videoRoot, Config.VIDEO_WIDTH, Config.VIDEO_HEIGHT);
        videoScene.setFill(Paint.valueOf(Config.SCREEN_COLOR));
        setScene(videoScene);
    }

    public void play(final TextVideo textVideo, final FrameRate frameRate, final String textToDisplay) {
        videoRoot.getChildren().clear();
        MediaView mediaView = textVideo.getMediaView(frameRate);
        Text text = textVideo.getText(textToDisplay);
        videoRoot.getChildren().add(mediaView);
        videoRoot.getChildren().add(text);
        text.setText("");
        TextAnimator animator = new TextAnimator(textVideo.getVideoConfig(), mediaView.getMediaPlayer(), text);
        PagedText pagedText = new PagedText(textToDisplay);
        animator.animateText(pagedText);
    }
}
