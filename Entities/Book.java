package Entities;

public class Book {
    private int ISBN;
    private String title;
    private int publisherId;
    private String publication_year;
    private double price;
    private int category_id;
    private int min_quantity;
    private int cur_quantity;

    public Book(int ISBN, String title, int publisherId,
                String publication_year, double price,
                int category_id, int min_quantity, int cur_quantity) {
        this.ISBN = ISBN;
        this.title = title;
        this.publisherId = publisherId;
        this.publication_year = publication_year;
        this.price = price;
        this.category_id = category_id;
        this.min_quantity = min_quantity;
        this.cur_quantity = cur_quantity;
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(String publication_year) {
        this.publication_year = publication_year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getMin_quantity() {
        return min_quantity;
    }

    public void setMin_quantity(int min_quantity) {
        this.min_quantity = min_quantity;
    }

    public int getCur_quantity() {
        return cur_quantity;
    }

    public void setCur_quantity(int cur_quantity) {
        this.cur_quantity = cur_quantity;
    }
}
