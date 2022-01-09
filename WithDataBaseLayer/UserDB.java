package WithDataBaseLayer;
import Entities.*;
import javafx.util.Pair;

import java.time.*;
import java.util.*;

public class UserDB {
	
	private DataBase DBM = new DataBase();
    public boolean signUp(User u) {
        String q = "select signUp(\"" + u.getUser_name() + "\",\"" + u.getPassword() + "\",\""
                 + u.getLast_name() + "\",\"" + u.getFirst_name() + "\",\"" + u.getEmail_address()
                + "\",\"" + u.getPhone() + "\",\"" + u.getShipping_address() + "\")";
        return DBM.executeBooleanQueries(q);
    }

    public boolean login(User u) {
        String q = "select login(\"" + u.getUser_name() + "\",\"" + u.getPassword() + "\")";
        return DBM.executeBooleanQueries(q);
    }
    
    //get a user from DB
    public User getUser(String userName) {
        String q = "call getUser(\"" + userName + "\")";
    	return DBM.executeUserQuery(q);
    }

    public boolean editUserData(String oldUserName, User newU) { // here you send the old user data and the new user data
        String q = "select editUserInfo(\"" + oldUserName + "\",\""
                + newU.getUser_name() + "\",\"" + newU.getPassword() + "\",\""
                + newU.getLast_name() + "\",\"" + newU.getFirst_name() + "\",\"" + newU.getEmail_address()
                + "\",\"" + newU.getPhone() + "\",\"" + newU.getShipping_address() + "\")";
        return DBM.executeBooleanQueries(q);
    }

    public ArrayList<Book> getBooksByCategory(String category) {
        String q = "call searchForBookByCategory(\"%" + category + "%\")";
        return DBM.executeQueriesForBooks(q);
    }

    public ArrayList<Book> getBooksByTitle(String title){
        String q = "call searchForBookByTitle(\"%" + title + "%\")";
        return DBM.executeQueriesForBooks(q);
    }

    public ArrayList<Book> getBooksByAuthor(String author) {
        String q = "call searchForBookByAuthor(\"%" + author + "%\")";
        return DBM.executeQueriesForBooks(q);
    }

    public ArrayList<Book> getBooksByPublisher(String publisher){
        String q = "call searchForBookByPublisher(\"%" + publisher + "%\")";
        return DBM.executeQueriesForBooks(q);
    }

    public ArrayList<Book> getBookByISBN(int ISBN){
        String q = "call searchForBookByISBN(\"" + ISBN + "\")";
        return DBM.executeQueriesForBooks(q);
    }

    public boolean addBookToCart(User u, int ISBN, int numOfCopies) {
        String q = "select addBookToShoppingCart(\"" + u.getUser_name() + "\"," + ISBN + ","
                                                + numOfCopies + ")";
        return DBM.executeBooleanQueries(q);
    }

    public ArrayList<Pair<Book,Integer>> viewCartItems(User u){
        String q = "call viewCartItems(\"" + u.getUser_name() + "\")";
        return DBM.executeCartQuery(q);
    }

    public Double viewBookCartPrice (User u, int ISBN) {
        String q = "call viewBookCartPrice(\"" + u.getUser_name() + "\"," + ISBN + ")";
        return DBM.executeDoubleQueries(q);
    }

    public double viewCartPrice(User u) {
        String q = "call viewCartPrice(\"" + u.getUser_name() + "\")";
		return DBM.executeDoubleQueries(q);
    }

    public boolean removeItemFromCart(User u, int ISBN){
        String q = "select removeItemFromCart(\"" + u.getUser_name() + "\"," + ISBN + ")";
        return DBM.executeBooleanQueries(q);
    }

    public boolean buyBook(User u, int ISBN, double bookPrice,
                        String cardNumber, LocalDate expireDate) {
        String q = "select buyBook(\"" + u.getUser_name() + "\"," + ISBN + ","
                + bookPrice + ",\"" + cardNumber + "\",\"" + expireDate + "\")";
        return DBM.executeBooleanQueries(q);
    }
    
    public void logOut(User u) {
        String q = "call logout(\"" + u.getUser_name() + "\")";
        DBM.executeQuery(q);
        return;
    }

}
