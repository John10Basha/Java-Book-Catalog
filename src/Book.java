import java.io.Serializable;

public class Book implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
    private String authors;
    private double price;
    private String isbn;
    private String genre;
    private int year;

    public Book(String title, String authors, double price, String isbn, String genre, int year) {
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.isbn = isbn;
        this.genre = genre;
        this.year = year;
    }

    // Getters and setters
    public String getTitle() {
    	return title; 
    }
    public void setTitle(String title) { 
    	this.title = title; 
    }
    public String getAuthors() {
    	return authors; 
    }
    public void setAuthors(String authors) { 
    	this.authors = authors;
    }
    public double getPrice() { 
    	return price; 
    }
    public void setPrice(double price) { 
    	this.price = price; 
    }
    public String getIsbn() { 
    	return isbn;
    }
    public void setIsbn(String isbn) { 
    	this.isbn = isbn; 
    }
    public String getGenre() { 
    	return genre;
    }
    public void setGenre(String genre) { 
    	this.genre = genre;
    }
    public int getYear() {
    	return year; 
    }
    public void setYear(int year) { 
    	this.year = year; 
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) 
        {
        	return false;
        }
        Book book = (Book) o;
        return (this.title.equals(book.title) && this.authors.equals(book.authors) && this.price == book.price && 
        		this.isbn.equals(book.isbn) && this.genre.equals(book.genre) && this.year == book.year);
    }

    @Override
    public String toString() {
        return title + ", " + authors + ", " + price + ", " + isbn + ", " + genre + ", " + year + ", ";
    }

}
