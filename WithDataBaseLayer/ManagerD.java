package DataBase;
import Entities.*;

import java.util.ArrayList;

public class ManagerD {

    public boolean isManager(User u) {
        String q = "call isManager(\"" + u.getUser_name() + "\")";
        return DataBase.executeBooleanQueries(q);
    }

    public void addNewBook(int ISBN, String title) {
        String q = "call addNewBook(\"" + ISBN + "\",\"" + title + "\")";
        DataBase.executeQuery(q);
    }

    public boolean modifyBook(int ISBN, Book newBook){
        String q = "call modifyBook(\"" + ISBN + "\",\"" + newBook.getTitle() + "\","
                + newBook.getPublisherId() + "\"," + newBook.getPublication_year()
                + "\"," + newBook.getPrice() + "\"," + newBook.getCategory_id() + "\","
                + newBook.getMin_quantity() + "\"," + newBook.getCur_quantity() + "\")";
        return DataBase.executeBooleanQueries(q);
    }

    public void orderBook(int ISBN, int quantity){
        String q = "call orderBooks(\"" + ISBN + "\",\"" + quantity + "\")";
        DataBase.executeQuery(q);
    }

    public void confirmOrder(int orderId) {
        String q = "call orderBooks(\"" + orderId + "\")";
        DataBase.executeQuery(q);
    }

    public void setManager(String userName){
        String q = "call orderBooks(\"" + userName + "\")";
        DataBase.executeQuery(q);
    }

    public ArrayList<Pair<String,Integer>> totalBooksSales (){
        String q = "call totalBooksSales()";
        return DataBase.executeQueriesForEnt(q);
    }

    public ArrayList<Pair<String,Integer>> topCustomers (){
        String q = "call topCustomers()";
        return DataBase.executeQueriesForEnt(q);
    }

    public ArrayList<Pair<String,Integer>> topSellingBooks(){
        String q = "call topSellingBooks()";
        return DataBase.executeQueriesForEnt(q);
    }



}
