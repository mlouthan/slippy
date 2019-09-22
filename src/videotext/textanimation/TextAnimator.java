package videotext.textanimation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;
import videotext.VideoConfig;

public class TextAnimator {

    private final VideoConfig videoConfig;
    private final MediaPlayer mediaPlayer;
    private final Text text;

    public TextAnimator(final VideoConfig videoConfig, final MediaPlayer mediaPlayer, final Text text) {
        this.videoConfig = videoConfig;
        this.mediaPlayer = mediaPlayer;
        this.text = text;
    }

    public void animateText(final PagedText textToDisplay) {
        Timeline timeline = createTimeline(textToDisplay);
        mediaPlayer.stop();
        mediaPlayer.play();
        try {
            Thread.sleep(videoConfig.getVideoIntroMillis());
        } catch(Exception e) {}
        timeline.play();
    }

    private Timeline createTimeline(final PagedText textToDisplay) {
        final IntegerProperty counter = new SimpleIntegerProperty(0);
        final IntegerProperty stringPosition = new SimpleIntegerProperty(0);
        final IntegerProperty page = new SimpleIntegerProperty(0);
        final IntegerProperty delay = new SimpleIntegerProperty(0);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(videoConfig.getAnimationTimeDelayMillis()),
                event -> {
                    boolean pageDone = stringPosition.get() > textToDisplay.length(page.get());
                    boolean morePages = page.get() < textToDisplay.pageCount() - 1;
                    boolean slippyPaused = mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED;

                    if (!slippyPaused &&
                            counter.get() * videoConfig.getAnimationTimeDelayMillis() >
                                    mediaPlayer.getMedia().getDuration().toMillis() - videoConfig.getVideoOutroMillis()) {
                        mediaPlayer.pause();
                    }
                    
                    if (delay.get() > 0) {
                        delay.setValue(delay.get() - 1);
                        if (delay.get() == 0) {
                            text.setText("");
                            page.setValue(page.get() + 1);
                            stringPosition.setValue(0);
                        }
                    } else if (pageDone && !morePages && slippyPaused) {
                        try {
                            Thread.sleep(videoConfig.getTimeToReadTextMillis());
                        } catch(Exception e) {}
                        mediaPlayer.play();
                        text.setText("");
                        timeline.stop();
                    } else if (pageDone && morePages) {
                        delay.setValue(videoConfig.getPageTimeDelayMillis() / videoConfig.getAnimationTimeDelayMillis());
                    } else if (stringPosition.get() > textToDisplay.length(page.get())) {
                        //do nothing
                    } else {
                        text.setText(textToDisplay.substring(page.get(), 0, stringPosition.get()));
                        stringPosition.set(stringPosition.get() + 1);
                    }
                    counter.set(counter.get() + 1);
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;
    }

    //20 character limit for the big font
}
