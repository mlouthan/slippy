package videotext;

public interface VideoConfig {

    int getAnimationTimeDelayMillis();

    int getVideoIntroMillis();

    int getVideoOutroMillis();

    int getSpeakingAnimationTimeMillis();

    int getTimeToReadTextMillis();

    int getPageTimeDelayMillis();
}
