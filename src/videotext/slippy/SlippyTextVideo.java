package videotext.slippy;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import videotext.FrameRate;
import videotext.ResourceLoader;
import videotext.TextVideo;
import videotext.VideoConfig;

import java.util.Arrays;
import java.util.List;

public class SlippyTextVideo implements TextVideo {

    private static final String SLIPPY_FONT_FILE = "resources/CHMCPixel.ttf";
    private static final String SLIPPY_VIDEO_30_FPS = "resources/Slippy_Blank_30fps.mp4";
    private static final String SLIPPY_VIDEO_60_FPS = "resources/Slippy_Blank_60fps.mp4";

    private static final int SMALL_FONT_SIZE = 31;
    private static final int LARGE_FONT_SIZE = 59;
    private static final int LARGE_FONT_CHAR_LIMIT = 60;

    private static final int TEXT_X = 260;
    private static final int TEXT_Y = 90;
    private static final int TEXT_WIDTH = 680;
    private static final String TEXT_STYLE = "-fx-fill: white;";

    private MediaView slippyMediaView;
    private Text slippyText;
    private Font slippyFont;
    private VideoConfig slippyVideoConfig;

    private final ResourceLoader resourceLoader;

    public SlippyTextVideo() {
        resourceLoader = new ResourceLoader();
    }

    @Override
    public List<FrameRate> getPossibleFramerates() {
        return Arrays.asList(FrameRate.THIRTYFPS, FrameRate.SIXTYFPS);
    }

    @Override
    public FrameRate getDefaultFramerate() {
        return FrameRate.THIRTYFPS;
    }

    @Override
    public String getVideoName() {
        return "Slippy";
    }

    @Override
    public void loadMedia(final FrameRate frameRate) {
        switch (frameRate) {
            case THIRTYFPS:
                slippyMediaView = createSlippyMediaView(SLIPPY_VIDEO_30_FPS);
                break;

            case SIXTYFPS:
                slippyMediaView = createSlippyMediaView(SLIPPY_VIDEO_60_FPS);
                break;

            default:
                throw new RuntimeException("Framerate " + frameRate.getName() + " not supported");
        }
        slippyFont = createSlippyFont(LARGE_FONT_SIZE);
        slippyText = createSlippyText();
        slippyText.setFont(slippyFont);
        slippyVideoConfig = new SlippyVideoConfig();
    }

    @Override
    public MediaView getMediaView() {
        return slippyMediaView;
    }

    @Override
    public Text getText(String textToDisplay) {
        return slippyText;
    }

    @Override
    public VideoConfig getVideoConfig() {
        return slippyVideoConfig;
    }

    private MediaView createSlippyMediaView(final String slippyVideoPath) {
        Media media = resourceLoader.loadMedia(slippyVideoPath);
        MediaPlayer slippyMediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(slippyMediaPlayer);
        slippyMediaPlayer.setAutoPlay(false);
        return mediaView;
    }

    private Font createSlippyFont(final int fontSize) {
        return resourceLoader.loadFont(SLIPPY_FONT_FILE, fontSize);
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
