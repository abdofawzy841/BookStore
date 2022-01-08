package DataBase;

import java.sql.*;
import Entities.*;
import java.util.*;
import javafx.util.*;

public class DataBase {
    private static Connection con;
    private static final String dpUrl = "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql6464024"; // to be modified
    private static final String user = "sql6464024";
    private static final String pass = "rquwbq7YLg";

    private DataBase() {
        connect();
    }

    private static void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dpUrl, user, pass);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void executeQuery(String q) { // for normal Queries
        try {
            connect();
            con.createStatement().executeQuery(q);
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Book> executeQueriesForBooks(String q) {
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

    public static boolean executeBooleanQueries(String q){
        boolean ans = false;

        try {
            connect();
            ResultSet r = con.createStatement().executeQuery(q);
            r.next();
            ans = r.getBoolean(1);
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return ans;
    }

    public static double executeDoubleQueries(String q){
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


    public static ArrayList<Double> executeQueriesForDoubles(String q) {
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

    public static ArrayList<Pair<String, Integer>> executeQueriesForEnt(String q){
        ArrayList<Pair<String, Integer>> list = new ArrayList<>();

        try {
            connect();
            ResultSet r = con.createStatement().executeQuery(q);
            while (r.next()) {
                list.add(new Pair(r.getString(1), r.getInt(2)));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }
}

