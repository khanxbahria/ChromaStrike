package chromastrike.profile;

public class ChromaProfile {
    public String shortcutKey = null;
    public String name = null;
    public final double originOffsetX;
    public final double originOffsetY;
    public final double ratioWidth;
    public final double ratioHeight;
    public final int mousePressDuration;
    public final int intervalDelay;
    public final int checkDelay;
    public final int repeatCount;

    public ChromaProfile(double originOffsetX, double originOffsetY, double ratioWidth, double ratioHeight,
                         int mousePressDuration, int intervalDelay, int checkDelay, int repeatCount) {
        this.originOffsetX = originOffsetX;
        this.originOffsetY = originOffsetY;
        this.ratioWidth = ratioWidth;
        this.ratioHeight = ratioHeight;
        this.mousePressDuration = mousePressDuration;
        this.intervalDelay = intervalDelay;
        this.checkDelay = checkDelay;
        this.repeatCount = repeatCount;
    }

    public ChromaProfile(String name, String shortcutKey,
                         double originOffsetX, double originOffsetY, double ratioWidth, double ratioHeight,
                         int mousePressDuration, int intervalDelay, int checkDelay, int repeatCount,
                         double minimumRatioPurplePixels) {
        this(originOffsetX, originOffsetY, ratioWidth, ratioHeight,
                mousePressDuration, intervalDelay, checkDelay, repeatCount);
        this.shortcutKey = shortcutKey;
        this.name = name;
    }


}
