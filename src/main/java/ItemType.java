/**
 * @author Tyler Krawec
 */
public enum ItemType {

    AUDIO("AU"),
    VISUAL("VI"),
    AUDIO_MOBILE("AM"),
    VISUAL_MOBILE("VM");

    public final String code;

    ItemType(String itemCode) {
        code = itemCode;
    }

    public String getItemCode() {
        return code;
    }

}

