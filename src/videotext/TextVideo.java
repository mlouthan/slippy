package videotext;

import javafx.scene.media.MediaView;
import javafx.scene.text.Text;

import java.util.List;

public interface TextVideo {

    List<FrameRate> getPossibleFramerates();

    FrameRate getDefaultFramerate();

    String getVideoName();

    void loadMedia(final FrameRate frameRate);

    MediaView getMediaView();

    Text getText(final String textToDisplay);

    VideoConfig getVideoConfig();
}
