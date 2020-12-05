/**
 * @author Tyler Krawec
 * <p>
 * Gives functionality to a MoviePlayer and implements MultimediaControl
 */
public class MoviePlayer extends Product implements MultimediaControl {

  //Fields for MoviePlayer
  Screen screen;
  MonitorType monitorType;

  /**
   * Default constructor for a MoviePlayer
   */
  public MoviePlayer() {
    this.screen = null;
    this.monitorType = null;
  }

  /**
   * Constructor for MoviePlayer that takes in the following parameters
   *
   * @param name         = Name of MoviePlayer
   * @param manufacturer = Manufacturer of MoviePlayer
   * @param screen       = Screen type of MoviePlayer
   * @param monitorType  = MonitorType of MoviePlayer
   */
  public MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, ItemType.VISUAL, manufacturer);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  /**
   * Method for simulating a movie playing
   */
  @Override
  public void play() {
    System.out.println("Playing movie");
  }

  /**
   * Method for simulating a movie stopping
   */
  @Override
  public void stop() {
    System.out.println("Stopping movie");
  }

  /**
   * Method for simulating a MoviePlayer switching to previous movie
   */
  @Override
  public void previous() {
    System.out.println("Previous movie");
  }

  /**
   * Method for simulating a MoviePlayer switching to next movie
   */
  @Override
  public void next() {
    System.out.println("Next movie");
  }

  /**
   * Method for printing details about a MoviePlayer
   */
  public String toString() {
    return "Name: " + name + "\n"
        + "Manufacturer: " + manufacturer + "\n"
        + "Type: " + type + "\n"
        + "Screen: " + screen + "\n"
        + "Monitor Type: " + monitorType;
  }
}
