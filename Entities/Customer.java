package Entities;

public class Customer extends User{
    public Customer(String user_name, String password,
                    String last_name, String first_name,
                    String email_address, String phone,
                    String shipping_address, boolean is_man) {
        super(user_name, password, last_name, first_name, email_address, phone, shipping_address, is_man);
    }
}
