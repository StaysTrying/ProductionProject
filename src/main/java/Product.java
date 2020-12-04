
/**
 * @author Tyler Krawec
 */
public abstract class Product implements Item {

    int id;
    String name;
    ItemType type;
    String manufacturer;

    public Product() {// Default Constructor
        this.name = null;
        this.type = null;
        this.manufacturer = null;
    }

    public Product(String name, ItemType type, String manufacturer) {  // Product Constructor
        this.name = name;
        this.type = type;
        this.manufacturer = manufacturer;
    }

    public Product(int id, String name, ItemType type, String manufacturer) {  // Product Constructor
        this.id = id;
        this.name = name;
        this.type = type;
        this.manufacturer = manufacturer;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String toString() {
        return "Name: " + name + "\n"
                + "Manufacturer: " + manufacturer + "\n"
                + "Type: " + type.getItemCode();
    }
}

/*class Widget extends Product{

    public Widget(String name, ItemType type, String manufacturer){
        super(name, type, manufacturer);
    }
}*/

