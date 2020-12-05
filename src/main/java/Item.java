/**
 * @author Tyler Krawec
 * <p>
 *   Interface for ensuring all Items have an Id, Name, and Manufacturer
 */
public interface Item {

  int getId();

  void setName(String name);

  String getName();

  void setManufacturer(String manufacturer);

  String getManufacturer();
}
