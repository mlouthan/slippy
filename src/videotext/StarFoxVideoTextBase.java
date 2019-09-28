package videotext;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public abstract class StarFoxVideoTextBase implements TextVideo {

    private static final String FONT_FILE = "resources/CHMCPixel.ttf";

    private static final int FONT_SIZE = 59;

    private static final int TEXT_X = 260;
    private static final int TEXT_Y = 90;
    private static final int TEXT_WIDTH = 680;
    private static final String TEXT_STYLE = "-fx-fill: white;";

    protected MediaView mediaView;
    protected Text text;
    protected Font font;
    protected VideoConfig videoConfig;

    protected final ResourceLoader resourceLoader;

    public StarFoxVideoTextBase() {
        resourceLoader = new ResourceLoader();
    }

    @Override
    public abstract List<FrameRate> getPossibleFramerates();

    @Override
    public abstract FrameRate getDefaultFramerate();

    @Override
    public abstract String getVideoName();

    @Override
    public MediaView getMediaView() {
        return mediaView;
    }

    @Override
    public Text getText() {
        return text;
    }

    @Override
    public VideoConfig getVideoConfig() {
        return videoConfig;
    }

    @Override
    public void loadMedia(final FrameRate frameRate) {
        mediaView = createMediaView(frameRate);
        font = createSlippyFont(FONT_SIZE);
        text = createSlippyText();
        text.setFont(font);
        videoConfig = createVideoConfig();
    }

    protected abstract MediaView createMediaView(final FrameRate frameRate);

    protected MediaView loadMediaView(final String videoPath) {
        Media media = resourceLoader.loadMedia(videoPath);
        MediaPlayer slippyMediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(slippyMediaPlayer);
        slippyMediaPlayer.setAutoPlay(false);
        return mediaView;
    }

    protected abstract VideoConfig createVideoConfig();

    private Font createSlippyFont(final int fontSize) {
        return resourceLoader.loadFont(FONT_FILE, fontSize);
    }

    private Text createSlippyText() {
        Text slippyText = new Text();
        slippyText.setText("");
        slippyText.setStyle(TEXT_STYLE);
        slippyText.setLayoutX(TEXT_X);
        slippyText.setLayoutY(TEXT_Y);
        slippyText.setWrappingWidth(TEXT_WIDTH);
        return slippyText;
    }
}
