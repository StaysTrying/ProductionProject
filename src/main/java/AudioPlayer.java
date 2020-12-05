/**
 * @author Tyler Krawec
 * <p> Method that constructs AudioPlayer objects and implements MultimediaControl functionality
 */
public class AudioPlayer extends Product implements MultimediaControl {

  String supportedAudioFormats;
  String supportedPlaylistFormats;

  /**
   * Default AudioPlayer Constructor
   */
  public AudioPlayer() {

  }

  /**
   * AudioPlayer Constructor takes in specifications for an AudioPlayer and constructs it
   *
   * @param manufacturer             product manufacturer extended from Product
   * @param name                     product name extended from Product
   * @param supportedAudioFormats    represents supportedAudioFormats for device
   * @param supportedPlaylistFormats represents supportedPlaylistFormats for device
   */
  public AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, ItemType.AUDIO, manufacturer);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  /**
   * Method for simulating Play
   */
  @Override
  public void play() {
    System.out.println("Playing");
  }

  /**
   * Method for simulating Stop
   */
  @Override
  public void stop() {
    System.out.println("Stopping");
  }

  /**
   * Method for simulating Previous
   */
  @Override
  public void previous() {
    System.out.println("Previous");
  }

  /**
   * Method for simulating Next
   */
  @Override
  public void next() {
    System.out.println("Next");
  }

  /**
   * Method for printing a description of an AudioPlayer
   */
  public String toString() {
    return "Name: " + name + "\n"
        + "Manufacturer: " + manufacturer + "\n"
        + "Type: " + type + "\n"
        + "Supported Audio Formats: " + supportedAudioFormats + "\n"
        + "Supported Playlist Formats: " + supportedPlaylistFormats;
  }

}

