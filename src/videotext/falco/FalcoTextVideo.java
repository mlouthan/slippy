package videotext.falco;

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

public class FalcoTextVideo implements TextVideo {

    private static final String FALCO_FONT_FILE = "resources/CHMCPixel.ttf";
    private static final String FALCO_VIDEO_30_FPS = "resources/Falco_Blank_30fps.mp4";
    private static final String FALCO_VIDEO_60_FPS = "resources/Falco_Blank_60fps.mp4";

    private static final int SMALL_FONT_SIZE = 31;
    private static final int LARGE_FONT_SIZE = 59;
    private static final int LARGE_FONT_CHAR_LIMIT = 60;

    private static final int TEXT_X = 260;
    private static final int TEXT_Y = 90;
    private static final int TEXT_WIDTH = 680;
    private static final String TEXT_STYLE = "-fx-fill: white;";

    private MediaView falcoMediaView;
    private Text falcoText;
    private Font falcoFont;
    private VideoConfig falcoVideoConfig;

    private final ResourceLoader resourceLoader;

    public FalcoTextVideo() {
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
        return "Falco";
    }

    @Override
    public void loadMedia(final FrameRate frameRate) {
        switch (frameRate) {
            case THIRTYFPS:
                falcoMediaView = createFalcoMediaView(FALCO_VIDEO_30_FPS);
                break;

            case SIXTYFPS:
                falcoMediaView = createFalcoMediaView(FALCO_VIDEO_60_FPS);
                break;

            default:
                throw new RuntimeException("Framerate " + frameRate.getName() + " not supported");
        }
        falcoFont = createFalcoFont(LARGE_FONT_SIZE);
        falcoText = createFalcoText();
        falcoText.setFont(falcoFont);
        falcoVideoConfig = new FalcoVideoConfig();
    }

    @Override
    public MediaView getMediaView() {
        return falcoMediaView;
    }

    @Override
    public Text getText(String textToDisplay) {
        return falcoText;
    }

    @Override
    public VideoConfig getVideoConfig() {
        return falcoVideoConfig;
    }

    private MediaView createFalcoMediaView(final String falcoVideoPath) {
        Media media = resourceLoader.loadMedia(falcoVideoPath);
        MediaPlayer falcoMediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(falcoMediaPlayer);
        falcoMediaPlayer.setAutoPlay(false);
        return mediaView;
    }

    private Font createFalcoFont(final int fontSize) {
        return resourceLoader.loadFont(FALCO_FONT_FILE, fontSize);
    }

    private Text createFalcoText() {
        Text falcoText = new Text();
        falcoText.setText("");
        falcoText.setStyle(TEXT_STYLE);
        falcoText.setLayoutX(TEXT_X);
        falcoText.setLayoutY(TEXT_Y);
        falcoText.setWrappingWidth(TEXT_WIDTH);
        return falcoText;
    }
}
