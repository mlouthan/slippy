package videotext;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        typeText(textToDisplay, textVideo, mediaView, text);
    }

    private void typeText(final String textToDisplay, final TextVideo textVideo, final MediaView mediaView, final Text text) {
        final IntegerProperty i = new SimpleIntegerProperty(0);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(textVideo.getVideoConfig().getAnimationTimeDelayMillis()),
                event -> {
                    boolean textDone = i.get() > textToDisplay.length();
                    boolean slippyPaused = mediaView.getMediaPlayer().getStatus() == MediaPlayer.Status.PAUSED;
                    if (textDone && slippyPaused) {
                        try {
                            Thread.sleep(textVideo.getVideoConfig().getTimeToReadTextMillis());
                        } catch(Exception e) {}
                        mediaView.getMediaPlayer().play();
                        text.setText("");
                        timeline.stop();
                    } else if (!slippyPaused &&
                            i.get() * textVideo.getVideoConfig().getAnimationTimeDelayMillis() >
                            mediaView.getMediaPlayer().getMedia().getDuration().toMillis() - textVideo.getVideoConfig().getVideoOutroMillis()) {
                        mediaView.getMediaPlayer().pause();
                    } else if (i.get() > textToDisplay.length()) {
                        //do nothing
                    } else {
                        text.setText(textToDisplay.substring(0, i.get()));
                    }
                    i.set(i.get() + 1);
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        mediaView.getMediaPlayer().stop();
        mediaView.getMediaPlayer().play();
        try {
            Thread.sleep(textVideo.getVideoConfig().getVideoIntroMillis());
        } catch(Exception e) {}
        timeline.play();
    }
}
