package videotext;

public enum FrameRate {
    THIRTYFPS("30 FPS"),
    SIXTYFPS("60 FPS");

    private final String name;

    FrameRate(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
