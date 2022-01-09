package WithDataBaseLayer;
import Entities.*;
import javafx.util.Pair;

import java.util.ArrayList;

public class ManagerDB {
	private DataBase DBM = new DataBase();

    public boolean isManager(User u) {
        String q = "call isManager(\"" + u.getUser_name() + "\")";
        System.out.println(q);
        return DBM.executeBooleanQueries(q);
    }

    public void addNewBook(int ISBN, String title) {
        String q = "call addNewBook(\"" + ISBN + "\",\"" + title + "\")";
        System.out.println(q);
        DBM.executeQuery(q);
    }

    public boolean modifyBook(int ISBN, Book newBook){
        String q = "call modifyBook(\"" + ISBN + "\",\"" + newBook.getTitle() + "\","
                + newBook.getPublisherId() + "\"," + newBook.getPublication_year()
                + "\"," + newBook.getPrice() + "\"," + newBook.getCategory_id() + "\","
                + newBook.getMin_quantity() + "\"," + newBook.getCur_quantity() + "\")";
        System.out.println(q);
        return DBM.executeBooleanQueries(q);
    }

    public void orderBook(int ISBN, int quantity){
        String q = "call orderBooks(\"" + ISBN + "\",\"" + quantity + "\")";
        System.out.println(q);
        DBM.executeQuery(q);
    }

    public void confirmOrder(int orderId) {
        String q = "call orderBooks(\"" + orderId + "\")";
        DBM.executeQuery(q);
    }

    public void setManager(String userName){
        String q = "call orderBooks(\"" + userName + "\")";
        DBM.executeQuery(q);
    }

    public ArrayList<Pair<String,Integer>> totalBooksSales (){
        String q = "call totalBooksSales()";
        return DBM.executeQueriesForEnt(q);
    }

    public ArrayList<Pair<String,Integer>> topCustomers (){
        String q = "call topCustomers()";
        return DBM.executeQueriesForEnt(q);
    }

    public ArrayList<Pair<String,Integer>> topSellingBooks(){
        String q = "call topSellingBooks()";
        return DBM.executeQueriesForEnt(q);
    }



}
