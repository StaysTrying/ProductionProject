/**
 * @author Tyler Krawec
 * <p>
 * An enumeration for the possible ItemTypes for any item created with my
 * production app
 */
public enum ItemType {

  AUDIO("AU"),
  VISUAL("VI"),
  AUDIO_MOBILE("AM"),
  VISUAL_MOBILE("VM");

  public final String code;

  /**
   * Sets the code for an Item to an abbreviation of its type
   *
   * @param itemCode = abbreviation for ItemType to be used in my production app
   */
  ItemType(String itemCode) {
    code = itemCode;
  }

  /**
   * Returns an item's code
   */
  public String getItemCode() {
    return code;
  }

}

