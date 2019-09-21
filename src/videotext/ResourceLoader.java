package videotext;

import javafx.scene.media.Media;
import javafx.scene.text.Font;

public class ResourceLoader {

    public Media loadMedia(final String file) {
        try {
            Media media = new Media(getClass().getResource(file).toURI().toString());
            return media;
        } catch (final Exception e) {
            throw new RuntimeException("Couldn't load video", e);
        }
    }

    public Font loadFont(final String file, final int fontSize) {
        try {
            return javafx.scene.text.Font.loadFont(
                    getClass().getResource(file).toURI().toString(),
                    fontSize
            );
        } catch (final Exception e) {
            throw new RuntimeException("Couldn't load font");
        }
    }
}
