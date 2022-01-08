package DataBase;
import Entities.*;

import java.sql.*;
import java.util.*;

public class UserDB {
    public void signUp(User u) {
        try {
            String q = "call signUp(\"" + u.getUser_name() + "\",\"" + u.getPassword() + "\","
                    + u.getLast_name() + ",\"" + u.getFirst_name() + "\"," + u.getEmail_address()
                    + ",\"" + u.getPhone() + "\",\"" + u.getShipping_address() + "\")";

            System.out.println(q);
            DataBase.executeQuery(q);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void editUserData(User oldU, User newU) { // here you send the old user data and the new user data
        try {
            String q = "call editUserInfo(\"" + oldU.getUser_name() + "\",\""
                    + newU.getUser_name() + "\",\"" + newU.getPassword() + "\","
                    + newU.getLast_name() + ",\"" + newU.getFirst_name() + "\"," + newU.getEmail_address()
                    + ",\"" + newU.getPhone() + "\",\"" + newU.getShipping_address() + "\")";

            System.out.println(q);
            DataBase.executeQuery(q);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Book> getBooksByCategory(String category) throws SQLException{
        String q = "call getBooksByCategory(\"" + category + "\")";
        return DataBase.executeQueriesForBooks(q);
    }

    public ArrayList<Book> getBooksByTitle(String title) throws SQLException{
        String q = "call searchForBookByTitle(\"" + title + "\")";
        return DataBase.executeQueriesForBooks(q);
    }

    public ArrayList<Book> getBooksByAuthor(String author) throws SQLException{
        String q = "call searchForBookByAuthor (\"" + author + "\")";
        return DataBase.executeQueriesForBooks(q);
    }

    public ArrayList<Book> getBooksByPublisher(int publisher) throws SQLException{
        String q = "call searchForBookByPublisher (\"" + publisher + "\")";
        return DataBase.executeQueriesForBooks(q);
    }



    public ArrayList<Book> searchForBookByISBN(int ISBN) throws SQLException {
        String q = "call searchForBookByPublisher (\"" + ISBN + "\")";
        return DataBase.executeQueriesForBooks(q);
    }


    public boolean addBookToCart(User u, int ISBN, int numOfcopies) {
        String q = "call addBookToShoppingCart (\"" + u.getUser_name() + "\",\"" + ISBN + "\","
                                                + numOfcopies + "\")";
        return DataBase.executeBooleanQueries(q);
    }

    public ArrayList<Book> viewCartItems(User u){
        String q = "call searchForBookByPublisher (\"" + u.getUser_name() + "\")";
        return DataBase.executeQueriesForBooks(q);
    }

    public Double viewBookCartPrice (User u, int ISBN) {
        String q = "call viewBookCartPrice (\"" + u.getUser_name() + "\",\"" + ISBN + "\")";
        return DataBase.executeDoubleQueries(q);
    }

    public double viewCartPrice(User u) {
        String q = "call viewBookCartPrice (\"" + u.getUser_name() + "\")";
        return DataBase.executeDoubleQueries(q);
    }

    public boolean removeItemFromCart(User u, int ISBN){
        String q = "call removeItemFromCart  (\"" + u.getUser_name() + "\",\"" + ISBN + "\")";
        return DataBase.executeBooleanQueries(q);
    }

    public void buyBook(User u, int ISBN, double bookPrice,
                        String cardNumber, String expireDate){
        try {
            String q = "call buyBook(\"" + u.getUser_name() + "\",\"" + ISBN + "\","
                    + bookPrice + ",\"" + cardNumber + "\"," + expireDate + "\")";

            System.out.println(q);
            DataBase.executeQuery(q);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
