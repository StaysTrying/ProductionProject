/**
 * @author Tyler Krawec
 */
public class Screen implements ScreenSpec {

    String resolution;
    int refreshRate;
    int responseTime;

    public Screen() {
        this.resolution = null;
        this.refreshRate = 0;
        this.responseTime = 0;
    }

    public Screen(String resolution, int refreshRate, int responseTime) {
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.responseTime = responseTime;

    }

    @Override
    public String getResolution() {
        return resolution;
    }

    @Override
    public int getRefreshRate() {
        return refreshRate;
    }

    @Override
    public int getResponseTime() {
        return responseTime;
    }

    public String toString() {
        return "\n" + "Resolution: " + resolution + "\n"
                + "Refresh rate: " + refreshRate + "\n"
                + "Response time: " + responseTime;
    }
}
