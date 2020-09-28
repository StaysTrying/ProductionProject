public enum ItemType {
    Audio("AU"),
    Visual("VI"),
    AudioMobile("AM"),
    VisualMobile("VM");

    public final String code;

    ItemType(String itemCode){
        code = itemCode;
    }
}

