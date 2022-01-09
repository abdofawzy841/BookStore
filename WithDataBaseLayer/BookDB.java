package WithDataBaseLayer;
import Entities.*;

public class BookDB {
	DataBase DBM = new DataBase();
    // here you create a book object and send it to this fun
    public void insertBook(Book b, String userName, String password) {
        String q = "call addNewBook(\"" + userName + "\",\"" + password + "\","
                + b.getISBN() + ",\"" + b.getTitle() + "\"," + b.getPublisherId()
                + ",\"" + b.getPublication_year() + "\",\"" + b.getPrice() +
                "\"," + b.getCategory_id() + "," + b.getMin_quantity() + ","
                + b.getCur_quantity() + ")";

        DBM.executeQuery(q);
    }
    // here you create a new book object and send it to this fun
    public void updateBookDate(String userName, String password, Book newBook){
       String q = "call modifyBook(\"" + userName + "\",\"" + password + "\","
                + newBook.getISBN() + ",\"" + newBook.getTitle() + "\"," + newBook.getPublisherId()
                + ",\"" + newBook.getPublication_year() + "\",\"" + newBook.getPrice() +
                "\"," + newBook.getCategory_id() + "," + newBook.getMin_quantity() + ","
                + newBook.getCur_quantity() + ")";

       DBM.executeQuery(q);
    }
}
