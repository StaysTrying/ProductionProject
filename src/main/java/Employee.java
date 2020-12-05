/**
 * @author Tyler Krawec
 * <p> Employee class for taking in data from my GUI and constructing Employee
 * objects with a gnerated username and email
 */
public class Employee {

  String name;
  String username;
  String password;
  String email;

  /**
   * Default constructor for an Employee
   */
  public Employee() {
    this.name = null;
    this.username = null;
    this.password = null;
    this.email = null;
  }

  /**
   * Constructor for an Employee
   *
   * @param name     = full name provided by GUI
   * @param password = valid password provided by GUI
   */
  public Employee(String name, String password) {
    this.name = name;
    this.username = setUsername(name);
    this.password = password;
    this.email = setEmail(name);
  }

  /**
   * Sets a custom username for an Employee given their name
   *
   * @param name = Employees name from GUI
   */
  public String setUsername(String name) {
    String[] arrUser = name.split(" ");
    username = (arrUser[0].charAt(0) + arrUser[1]).toLowerCase();
    return username;
  }

  /**
   * Method to return the username of an Employee
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets a custom email
   *
   * @param name = Employees name from GUI
   */
  public String setEmail(String name) {
    email = name.replace(' ', '.').toLowerCase() + "@oracleacademy.Test";
    return email;
  }

  /**
   * Method for returning an Employees email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Method to print the details of an Employee
   */
  public String toString() {
    return "Welcome Aboard! " + name + "\n"
        + "Your Employee Username and Email are..." + "\n"
        + "Username: " + username + "\n"
        + "Email: " + email + "\n" + "\n"
        + "Make a Note of Your Credentials as" + "\n"
        + "You Will Need Them To Log In!";
  }

}
