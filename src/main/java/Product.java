
/**
 * @author Tyler Krawec
 * <p> Abstract class to create Product objects with user input from the GUI
 */
public abstract class Product implements Item {

  int id;
  String name;
  ItemType type;
  String manufacturer;

  /**
   * Default constructor for a Product object
   */
  public Product() {
    this.name = null;
    this.type = null;
    this.manufacturer = null;
  }

  /**
   * Constructor for a product object that receives user input from the GUI
   *
   * @param name         = Product object's name received from the GUI
   * @param type         = Product object's ItemType received from the GUI
   * @param manufacturer = Product object's manufacturer received from GUI
   */
  public Product(String name, ItemType type, String manufacturer) {  // Product Constructor
    this.name = name;
    this.type = type;
    this.manufacturer = manufacturer;
  }

  /**
   * Overloaded constructor for a product object that receives user input from the GUI
   *
   * @param id           = Product object's id generated as it was added to the database
   * @param name         = Product object's name received from the GUI
   * @param type         = Product object's ItemType received from the GUI
   * @param manufacturer = Product object's manufacturer received from GUI
   */
  public Product(int id, String name, ItemType type, String manufacturer) {  // Product Constructor
    this.id = id;
    this.name = name;
    this.type = type;
    this.manufacturer = manufacturer;
  }

  /**
   * Method to return a Product object's type
   */
  public ItemType getType() {
    return type;
  }

  /**
   * Method to set a Product object's type
   */
  public void setType(ItemType type) {
    this.type = type;
  }

  /**
   * Method to set a Product object's id, set by database
   */
  public void setId(int id) { this.id = id; }

  /**
   * Method to get a Product object's id, set by database
   */
  public int getId() {
    return id;
  }

  /**
   * Method to set a Product object's name
   *
   * @param name = Name from the GUI to be set as Product object's name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Method to return a Product object's type
   */
  public String getName() {
    return name;
  }

  /**
   * Method to set a Product object's manufacturer
   *
   * @param manufacturer = Name of Product object's manufacturer
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * Method to return a Product object's type
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * Method to provide information about the Product when called.
   */
  public String toString() {
    return "Name: " + name + "\n"
        + "Manufacturer: " + manufacturer + "\n"
        + "Type: " + type.getItemCode();
  }
}


