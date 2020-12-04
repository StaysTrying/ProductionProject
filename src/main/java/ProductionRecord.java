import java.util.Date;

/**
 * @author Tyler Krawec
 */
public class ProductionRecord {
    int productID;
    int productionNum;
    String serialNum;
    Date prodDate;
    int prodCount;
    static int visCount;
    static int visMobCount;
    static int audCount;
    static int audMobCount;

    public ProductionRecord() { }

    public ProductionRecord(int productId) {
        this.productID = productId;
        this.productionNum = 0;
        this.serialNum = "0";
        this.prodDate = new Date();
    }

    public ProductionRecord(int productionNumber,int productId, String serialNumber, Date dateProduced) {
        this.productionNum = productionNumber;
        this.productID = productId;
        this.serialNum = serialNumber;
        this.prodDate = dateProduced;
    }

    public ProductionRecord(Product product) {
        this.prodDate = new Date();
        this.productID = product.getId();

       /* switch(product.type) {
            case AUDIO:
                generateSerialNum(product, audCount);
                audCount++;
                break;
            case AUDIO_MOBILE:
                generateSerialNum(product, audMobCount);
                audMobCount++;
                break;
            case VISUAL:
                generateSerialNum(product, visCount);
                visCount++;
                break;
            case VISUAL_MOBILE:
                generateSerialNum(product, visMobCount);
                visMobCount++;
            default :
                System.out.println("Invalid Item Type");
        }*/

        if (product.type == ItemType.AUDIO || product.type == ItemType.AUDIO_MOBILE) {
            audCount++;
            generateSerialNum(product, audCount);
        } else if (product.type == ItemType.VISUAL || product.type == ItemType.VISUAL_MOBILE) {
            visCount++;
            generateSerialNum(product, visCount);
        }
    }

    public void generateSerialNum(Product product, int enumCount) {

        String serialCount = String.format("%05d", enumCount);

        setSerialNum(product.getManufacturer().substring(0, 3).toUpperCase() + product.type.getItemCode().substring(0, 2) + serialCount);
    }

    public int getProductionNum() {
        return productionNum;
    }

    public void setProductionNum(int productionNum) {
        this.productionNum = productionNum;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public int getProdCount() {
        return prodCount;
    }

    public void setProdCount(int prodCount) {
        this.prodCount = prodCount;
    }

    public String toString() {
        return "Prod. Num: " + productionNum + " Product ID: " + productID + " Serial Num: " + serialNum + " Date: " + prodDate + "\n";
    }
}
