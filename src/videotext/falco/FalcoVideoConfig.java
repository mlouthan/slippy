package videotext.falco;

import videotext.VideoConfig;

public class FalcoVideoConfig implements VideoConfig {

    private static final int ANIMATION_TIME_DELAY_MILLIS = 50;
    private static final int VIDEO_INTRO_MILLIS = 800;
    private static final int VIDEO_OUTRO_MILLIS = 1650;
    private static final int SPEAKING_ANIMATION_TIME_MILLIS = 900;
    private static final int TIME_TO_READ_TEXT_MILLIS = 1000;
    private static final int PAGE_TIME_TO_READ_MILLIS = 1000;
    private static final String SCREEN_COLOR = "#4CF947";

    @Override
    public int getAnimationTimeDelayMillis() {
        return ANIMATION_TIME_DELAY_MILLIS;
    }

    @Override
    public int getVideoIntroMillis() {
        return VIDEO_INTRO_MILLIS;
    }

    @Override
    public int getVideoOutroMillis() {
        return VIDEO_OUTRO_MILLIS;
    }

    @Override
    public int getSpeakingAnimationTimeMillis() {
        return SPEAKING_ANIMATION_TIME_MILLIS;
    }

    @Override
    public int getTimeToReadTextMillis() {
        return TIME_TO_READ_TEXT_MILLIS;
    }

    @Override
    public int getPageTimeDelayMillis() {
        return PAGE_TIME_TO_READ_MILLIS;
    }

    @Override
    public  String getScreenColor() {
        return SCREEN_COLOR;
    }
}
