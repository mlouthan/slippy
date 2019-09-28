package videotext.falco;

import javafx.scene.media.MediaView;
import videotext.FrameRate;
import videotext.StarFoxVideoTextBase;
import videotext.VideoConfig;

import java.util.Arrays;
import java.util.List;

public class FalcoTextVideo extends StarFoxVideoTextBase {

    private static final String FALCO_VIDEO_30_FPS = "resources/Falco_Blank_30fps.mp4";
    private static final String FALCO_VIDEO_60_FPS = "resources/Falco_Blank_60fps.mp4";

    public FalcoTextVideo() {
        super();
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
    protected MediaView createMediaView(final FrameRate frameRate) {
        switch (frameRate) {
            case THIRTYFPS:
                return loadMediaView(FALCO_VIDEO_30_FPS);

            case SIXTYFPS:
                return loadMediaView(FALCO_VIDEO_60_FPS);

            default:
                throw new RuntimeException("Framerate " + frameRate.getName() + " not supported");
        }
    }

    @Override
    protected VideoConfig createVideoConfig() {
        return new FalcoVideoConfig();
    }
}
