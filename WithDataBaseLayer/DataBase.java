package WithDataBaseLayer;

import java.sql.*;
import Entities.*;
import javafx.util.Pair;

import java.util.*;

public class DataBase {
    private Connection con;
    private final String dpUrl = "jdbc:mysql://localhost:3306/bookstore"; // to be modified
    private final String user = "root";
    private final String pass = "1200";

    public DataBase() {
        connect();
    }

    private  void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dpUrl, user, pass);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public  void executeQuery(String q) { // for normal Queries
        try {
            connect();
            con.createStatement().executeQuery(q);
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public  ArrayList<Book> executeQueriesForBooks(String q) {
        ArrayList<Book> list = new ArrayList<>();

        try {
            connect();
            ResultSet r = con.createStatement().executeQuery(q);
            while (r.next()) {
                list.add(new Book(r.getInt(1), r.getString(2),
                        r.getInt(3), r.getString(4), r.getDouble(5),
                        r.getInt(6), r.getInt(7), r.getInt(8)));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }
    
    public  User executeUserQuery(String q) {
    	User user = new User();
        try {
            connect();
            ResultSet r = con.createStatement().executeQuery(q);
            while (r.next()) {
                user = new User(r.getString(1),r.getString(2),r.getString(3),r.getString(4),
                		r.getString(5),r.getString(6), r.getString(7),r.getBoolean(8));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public boolean executeBooleanQueries(String q){
        boolean ans = false;

        try {
            connect();
            ResultSet r = con.createStatement().executeQuery(q);
            r.next();
            ans = r.getBoolean(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return ans;
    }

    public  double executeDoubleQueries(String q){
        double ans = 0.0;

        try {
            connect();
            ResultSet r = con.createStatement().executeQuery(q);
            r.next();
            ans = r.getDouble(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return ans;
    }


    public  ArrayList<Double> executeQueriesForDoubles(String q) {
        ArrayList<Double> list = new ArrayList<>();

        try {
            connect();
            ResultSet r = con.createStatement().executeQuery(q);
            while (r.next()) {
                list.add(r.getDouble(1));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }
    
    public  ArrayList<Tuple> executeQueriesForTuple(String q) {
        ArrayList<Tuple> list = new ArrayList<>();
        try {
            connect();
            ResultSet r = con.createStatement().executeQuery(q);
            while (r.next()) {
                list.add(new Tuple(r.getInt(1), r.getString(2)));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }
    
    public  Book executeBookQuery(String q) {
    	Book b = null;
    	try {
            connect();
            ResultSet r = con.createStatement().executeQuery(q);
            while (r.next()) {
            	b = new Book(r.getInt(1), r.getString(2),
                        r.getInt(3), r.getString(4), r.getDouble(5),
                        r.getInt(6), r.getInt(7), r.getInt(8));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return b;
    }
    
    public ArrayList<Order> executeOrdersQuerey(String q){
    	ArrayList<Order> list = new ArrayList<Order>();
    	try {
            connect();
            ResultSet r = con.createStatement().executeQuery(q);
            while (r.next()) {
                list.add(new Order(r.getInt(1), r.getInt(2), r.getInt(3)));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    	
    }
    
    public  ArrayList<Pair<Book,Integer>> executeCartQuery(String q) {
    	ArrayList<Pair<Book,Integer>> list = new ArrayList<>();
    	try {
            connect();
            ResultSet r = con.createStatement().executeQuery(q);
            while (r.next()) {
            	Book b = new Book(r.getInt(1), r.getString(2),
                        r.getInt(3), r.getString(4), r.getDouble(5),
                        r.getInt(6), r.getInt(7), r.getInt(8));
            	int count = r.getInt(9);
            	Pair<Book,Integer> pair = new Pair<Book, Integer>(b, count);
            	list.add(pair);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return list;
    }
    
    
}

