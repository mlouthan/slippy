package slippy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VideoPanel extends Stage {

    private final Group videoRoot;

    private final MediaView shortSlippyVideo;

    private final Text slippyText;

    private final Font smallSlippyFont;
    private final Font largeSlippyFont;

    public VideoPanel() {
        videoRoot = new Group();
        shortSlippyVideo = createSlippyVideo(VideoConfig.SHORT_SLIPPY_VIDEO);
        smallSlippyFont = createSlippyFont(VideoConfig.SMALL_FONT_SIZE);
        largeSlippyFont = createSlippyFont(VideoConfig.LARGE_FONT_SIZE);
        slippyText = createSlippyTextBox();

        Scene videoScene = new Scene(videoRoot, VideoConfig.VIDEO_WIDTH, VideoConfig.VIDEO_HEIGHT);
        videoScene.setFill(Paint.valueOf(VideoConfig.SCREEN_COLOR));
        setScene(videoScene);
    }

    public void play(final String textToDisplay) {
        videoRoot.getChildren().clear();
        if (textToDisplay.length() > 60) {
            slippyText.setFont(smallSlippyFont);
        } else {
            slippyText.setFont(largeSlippyFont);
        }
        videoRoot.getChildren().add(shortSlippyVideo);
        videoRoot.getChildren().add(slippyText);
        slippyText.setText("");
        typeText(textToDisplay, shortSlippyVideo);
    }

    private MediaView createSlippyVideo(final String videoFile) {
        try {
            Media media = new Media(getClass().getResource(videoFile).toURI().toString());
            MediaPlayer slippyMediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(slippyMediaPlayer);
            slippyMediaPlayer.setAutoPlay(false);
            return mediaView;
        } catch (final Exception e) {
            throw new RuntimeException("Couldn't load video");
        }
    }

    private Font createSlippyFont(final int fontSize) {
        try {
            return javafx.scene.text.Font.loadFont(
                    getClass().getResource(VideoConfig.SLIPPY_FONT_FILE).toURI().toString(),
                    fontSize
            );
        } catch (final Exception e) {
            throw new RuntimeException("Couldn't load font");
        }

    }

    private Text createSlippyTextBox() {
        Text slippyText = new Text();
        slippyText.setText("");
        slippyText.setStyle(VideoConfig.TEXT_STYLE);
        slippyText.setLayoutX(VideoConfig.TEXT_X);
        slippyText.setLayoutY(VideoConfig.TEXT_Y);
        slippyText.setWrappingWidth(VideoConfig.TEXT_WIDTH);
        return slippyText;
    }

    private void typeText(final String text, final MediaView mediaView) {
        final IntegerProperty i = new SimpleIntegerProperty(0);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(VideoConfig.ANIMATION_TIME_DELAY_MILLIS),
                event -> {
                    boolean textDone = i.get() > text.length();
                    boolean slippyPaused = mediaView.getMediaPlayer().getStatus() == MediaPlayer.Status.PAUSED;
                    if (textDone && slippyPaused) {
                        try {
                            Thread.sleep(VideoConfig.TIME_TO_READ_TEXT_MILLIS);
                        } catch(Exception e) {}
                        mediaView.getMediaPlayer().play();
                        slippyText.setText("");
                        timeline.stop();
                    } else if (!slippyPaused &&
                            i.get() * VideoConfig.ANIMATION_TIME_DELAY_MILLIS >
                            mediaView.getMediaPlayer().getMedia().getDuration().toMillis() - VideoConfig.VIDEO_OUTRO_MILLIS) {
                        mediaView.getMediaPlayer().pause();
                    } else if (i.get() > text.length()) {
                        //do nothing
                    } else {
                        slippyText.setText(text.substring(0, i.get()));
                    }
                    i.set(i.get() + 1);
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        mediaView.getMediaPlayer().stop();
        mediaView.getMediaPlayer().play();
        try {
            Thread.sleep(VideoConfig.VIDEO_INTRO_MILLIS);
        } catch(Exception e) {}
        timeline.play();
    }
}
