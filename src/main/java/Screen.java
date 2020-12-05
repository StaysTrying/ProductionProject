/**
 * @author Tyler Krawec
 * <p>
 *   Class for constructing Screen objects and implementing ScreenSpec to describe them
 */
public class Screen implements ScreenSpec {

  String resolution;
  int refreshRate;
  int responseTime;

  /**
   * Default constructor for a Screen object
   */
  public Screen() {
    this.resolution = null;
    this.refreshRate = 0;
    this.responseTime = 0;
  }
  /**
   * Constructor for a Product object
   *
   * @param resolution = resolution of Screen object
   * @param refreshRate = refresh rate of Screen object
   * @param responseTime = response time of Screen object
   */
  public Screen(String resolution, int refreshRate, int responseTime) {
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;

  }
  /**
   * Method to return a Screen objects resolution
   */
  @Override
  public String getResolution() {
    return resolution;
  }
  /**
   * Method to return a Screen objects refreshRate
   */
  @Override
  public int getRefreshRate() {
    return refreshRate;
  }
  /**
   * Method to return a Screen objects responseTime
   */
  @Override
  public int getResponseTime() {
    return responseTime;
  }
  /**
   * Method return the description of a Screen object
   */
  public String toString() {
    return "\n" + "Resolution: " + resolution + "\n"
        + "Refresh rate: " + refreshRate + "\n"
        + "Response time: " + responseTime;
  }
}
