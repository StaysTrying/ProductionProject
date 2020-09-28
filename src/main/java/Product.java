public abstract class Product implements Item{

    int id;
    String Name;
    ItemType Type;
    String Manufacturer;

    public Product(String name, ItemType type, String manufacturer){
        this.Name = name;
        this.Type = type;
        this.Manufacturer = manufacturer;
    }

    public int getId(){
        return id;
    }

    public void setName(String name){
        Name = name;
    }

    public String getName(){
        return Name;
    }

    public void setManufacturer(String manufacturer){
        Manufacturer = manufacturer;
    }

    public String getManufacturer(){
        return Manufacturer;
    }

    public String toString() {
        return "Name: " + Name + "\n" + "Manufacturer: " + Manufacturer + "\n" + "Type: "
                + Type;
    }

}
