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
    private static final String SLIPPY_VIDEO = "resources/Slippy_Short_Blue.mp4";

    private static final int SMALL_FONT_SIZE = 31;
    private static final int LARGE_FONT_SIZE = 59;
    private static final int LARGE_FONT_CHAR_LIMIT = 60;

    private static final int TEXT_X = 260;
    private static final int TEXT_Y = 160;
    private static final int TEXT_WIDTH = 650;
    private static final String TEXT_STYLE = "-fx-fill: white;";

    private final MediaView slippy60FPSMediaView;
    private final Text slippyText;
    private final Font smallSlippyFont;
    private final Font largeSlippyFont;
    private final VideoConfig slippyVideoConfig;

    private final ResourceLoader resourceLoader;

    public SlippyTextVideo() {
        resourceLoader = new ResourceLoader();
        slippy60FPSMediaView = createSlippyMediaView();
        slippyText = createSlippyText();
        smallSlippyFont = createSlippyFont(SMALL_FONT_SIZE);
        largeSlippyFont = createSlippyFont(LARGE_FONT_SIZE);
        slippyVideoConfig = new SlippyVideoConfig();
    }

    @Override
    public List<FrameRate> getPossibleFramerates() {
        return Arrays.asList(/*FrameRate.THIRTYFPS,*/ FrameRate.SIXTYFPS);
    }

    @Override
    public FrameRate getDefaultFramerate() {
        return FrameRate.SIXTYFPS;
    }

    @Override
    public String getVideoName() {
        return "Slippy";
    }

    @Override
    public MediaView getMediaView(FrameRate frameRate) {
        switch (frameRate) {
            case SIXTYFPS :
                return slippy60FPSMediaView;

            default :
                throw new RuntimeException("Slippy only has 60FPS video");
        }
    }

    @Override
    public Text getText(String textToDisplay) {
        if (textToDisplay.length() > LARGE_FONT_CHAR_LIMIT) {
            slippyText.setFont(smallSlippyFont);
        } else {
            slippyText.setFont(largeSlippyFont);
        }
        return slippyText;
    }

    @Override
    public VideoConfig getVideoConfig() {
        return slippyVideoConfig;
    }

    private MediaView createSlippyMediaView() {
        //Media media = new Media(getClass().getResource(SLIPPY_VIDEO).toURI().toString());
        Media media = resourceLoader.loadMedia(SLIPPY_VIDEO);
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