/**
 * @author Tyler Krawec
 * <p>
 *   Widget class used to create Product objects via the Abstract class; Product
 */
public class Widget extends Product {

  Widget(String name, ItemType type, String manufacturer) {
    super(name, type, manufacturer);
  }

  Widget(int id, String name, ItemType type, String manufacturer) {
    super(id, name, type, manufacturer);
  }

}
