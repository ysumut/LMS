package entity;

public class Admin extends User {

    public Admin() {
        
    }

    public Admin(int userId, String full_name, String email) {
        super(userId, full_name, email, "admin");
    }
}
