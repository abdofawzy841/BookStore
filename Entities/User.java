package Entities;

public class User {
    private String user_name;
    private String password;
    private String last_name;
    private String first_name;
    private String email_address;
    private String phone;
    private String shipping_address;
    private boolean is_manager; // 1 for man >> 0 for normal user

    public User(String user_name, String password, String last_name,
                String first_name, String email_address, String phone,
                String shipping_address, boolean is_man) {
        this.user_name = user_name;
        this.password = password;
        this.last_name = last_name;
        this.first_name = first_name;
        this.email_address = email_address;
        this.phone = phone;
        this.shipping_address = shipping_address;
        this.is_manager = is_man;
    }
    
    public User () {}


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public boolean isManager() {
        return is_manager;
    }

    public void setManager(boolean is_man) {
        this.is_manager = is_man;
    }
}
