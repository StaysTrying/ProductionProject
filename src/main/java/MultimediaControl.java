/**
 * @author Tyler Krawec
 * <p>
 * Interface that assigns methods that must be implemented to an object that
 * has multimedia control capabilities
 */
public interface MultimediaControl {

  /**
   * Method for simulating play
   */
  public void play();

  /**
   * Method for simulating a stop
   */
  public void stop();

  /**
   * Method for simulating a switch to previous media
   */
  public void previous();

  /**
   * Method for simulating a switch to next media
   */
  public void next();
}
