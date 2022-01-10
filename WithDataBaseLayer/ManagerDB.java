package WithDataBaseLayer;

import Entities.*;

import java.util.ArrayList;

public class ManagerDB {
	private DataBase DBM = new DataBase();

	public boolean isManager(User u) {
		String q = "call isManager(\"" + u.getUser_name() + "\")";
		return DBM.executeBooleanQueries(q);
	}

	public boolean addNewBook(Book newBook, int authorId) {
		String q = "select addNewBook(" + newBook.getISBN() + ",\"" + newBook.getTitle() + "\","
				+ newBook.getPublisherId() + ",\"" + newBook.getPublication_year() + "\"," + newBook.getPrice() + ","
				+ newBook.getCategory_id() + "," + newBook.getMin_quantity() + "," + newBook.getCur_quantity() + ","
				+ authorId + ")";
		return DBM.executeBooleanQueries(q);
	}

	public boolean modifyBook(Book newBook) {
		String q = "select modifyBook(" + newBook.getISBN() + ",\"" + newBook.getTitle() + "\","
				+ newBook.getPublisherId() + ",\"" + newBook.getPublication_year() + "\"," + newBook.getPrice() + ","
				+ newBook.getCategory_id() + "," + newBook.getMin_quantity() + "," + newBook.getCur_quantity() + ")";
		return DBM.executeBooleanQueries(q);
	}

	public boolean orderBook(int ISBN, int quantity) {
		String q = "select orderBooks(\"" + ISBN + "\",\"" + quantity + "\")";
		return DBM.executeBooleanQueries(q);
	}

	public boolean confirmOrder(int orderId) {
		String q = "select confirmOrder("+ orderId + ")";
		return DBM.executeBooleanQueries(q);
	}

	public boolean setManager(String userName) {
		String q = "select promote(\"" + userName + "\")";
		return DBM.executeBooleanQueries(q);
	}
	
	public boolean setCustomer(String userName) {
		String q = "select demote(\"" + userName + "\")";
		return DBM.executeBooleanQueries(q);
	}

	public ArrayList<Tuple> totalBooksSales() {
		String q = "call totalBooksSales()";
		return DBM.executeQueriesForTuple(q);
	}

	public ArrayList<Tuple> topCustomers() {
		String q = "call topCustomers()";
		return DBM.executeQueriesForTuple(q);
	}

	public ArrayList<Tuple> topSellingBooks() {
		String q = "call topSellingBooks()";
		return DBM.executeQueriesForTuple(q);
	}

	public ArrayList<Tuple> getCategories() {
		String q = "call getCategories()";
		return DBM.executeQueriesForTuple(q);
	}

	public ArrayList<Tuple> getPublishers() {
		String q = "call getPublishers()";
		return DBM.executeQueriesForTuple(q);
	}

	public ArrayList<Tuple> getAuthors() {
		String q = "call getAuthors()";
		return DBM.executeQueriesForTuple(q);
	}

	public Book getBook(int ISBN) {
		String q = "call getBook (" + ISBN + ")";
		return DBM.executeBookQuery(q);
	}

	public ArrayList<Order> getOrders() {
		String q = "call getOrders()";
		return DBM.executeOrdersQuerey(q);
	}

}
