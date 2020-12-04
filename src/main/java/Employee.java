public class Employee {
    String name;
    String username;
    String password;
    String email;

    public Employee(){ }

    public Employee(String name, String password){
        this.name = name;
        this.username = setUsername(name);
        this.password = password;
        this.email = setEmail(name);
    }

    public String setUsername(String name){
        String[] arrUser = name.split(" ");
        username = (arrUser[0].charAt(0) + arrUser[1]).toLowerCase();
        return username;
    }

    public String getUsername(){
        return username;
    }

    public String setEmail(String name){
        email = name.replace(' ', '.').toLowerCase() + "@oracleacademy.Test";
        return email;
    }

    public String getEmail(){
        return email;
    }

    public String toString(){
        return "Welcome Aboard! " + name + "\n"
                + "Your Employee Username and Email are..." + "\n"
                + "Username: " + username + "\n"
                + "Email: " + email + "\n" +"\n"
                + "Make a Note of Your Credentials as" + "\n"
                + "You Will Need Them To Log In!";
    }

}
