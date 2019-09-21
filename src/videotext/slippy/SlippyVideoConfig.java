package videotext.slippy;

import videotext.VideoConfig;

public class SlippyVideoConfig implements VideoConfig {

    private static final int ANIMATION_TIME_DELAY_MILLIS = 50;
    private static final int VIDEO_INTRO_MILLIS = 500;
    private static final int VIDEO_OUTRO_MILLIS = 1300;
    private static final int TIME_TO_READ_TEXT_MILLIS = 1000;

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
    public int getTimeToReadTextMillis() {
        return TIME_TO_READ_TEXT_MILLIS;
    }
}
