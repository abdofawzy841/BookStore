package Entities;

public class Order {
	int id;
	int ISBN;
	int num_copies;
	public Order(int id, int isbn, int num_copies) {
		this.id = id;
		this.ISBN = isbn;
		this.num_copies = num_copies;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getISBN() {
		return ISBN;
	}
	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}
	public int getNum_copies() {
		return num_copies;
	}
	public void setNum_copies(int num_copies) {
		this.num_copies = num_copies;
	}
	
}
