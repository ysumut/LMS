package entity;

public class User {

    private String full_name, email, type;
    private int userId;
    private boolean status = false;
    private String old_pass, new_pass, new_pass_repeat;
    
    public User() {

    }
    
    public User(int userId, String full_name, String email, String type) {
        this.full_name = full_name;
        this.userId = userId;
        this.email = email;
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getOld_pass() {
        return old_pass;
    }

    public void setOld_pass(String old_pass) {
        this.old_pass = old_pass;
    }

    public String getNew_pass() {
        return new_pass;
    }

    public void setNew_pass(String new_pass) {
        this.new_pass = new_pass;
    }

    public String getNew_pass_repeat() {
        return new_pass_repeat;
    }

    public void setNew_pass_repeat(String new_pass_repeat) {
        this.new_pass_repeat = new_pass_repeat;
    }
}
