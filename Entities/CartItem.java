package Entities;

public class CartItem {
	private int ISBN;
    private String title;
    private double price;
    private int num_copies;
    public CartItem() {}
    
    public CartItem(int ISBN, String title, double price, int num_copies) {
    	this.ISBN = ISBN;
    	this.title = title;
    	this.price = price;
    	this.num_copies = num_copies;
    }
    
    
	public int getISBN() {
		return ISBN;
	}
	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getNum_copies() {
		return num_copies;
	}
	public void setNum_copies(int num_copies) {
		this.num_copies = num_copies;
	}
    
    

}
