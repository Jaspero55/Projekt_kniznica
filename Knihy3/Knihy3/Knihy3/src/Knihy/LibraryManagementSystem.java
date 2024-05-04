package Knihy;

import Knihy.DBConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



public class LibraryManagementSystem 
{
	// ArrayList pre uchovávanie kníhx
    private ArrayList<Book> books;
    
    public LibraryManagementSystem() 
    {
        this.books = new ArrayList<>();
    }
    
    // Pridanie novej knihy
    public void addBook(Book book) 
    {
        books.add(book);
    }
    
    // Úprava knihy
    public void editBook(String title, String author, int year, boolean available) 
    {
        for (Book book : books) 
        {
            if (book.getTitle().equals(title)) 
            {
                book.setAuthor(author);
                book.setYear(year);
                book.setAvailable(available);
                return;
            }
        }
        System.out.println("Kniha s názvom " + title + " nebola nájdená.");
    }
    
    // Odstránenie knihy
    public void removeBook(String title) 
    {
        books.removeIf(book -> book.getTitle().equals(title));
    }
    
    // Označenie knihy ako vypůjčené/vrácené
    public void markBook(String title, boolean available) 
    {
        for (Book book : books) 
        {
            if (book.getTitle().equals(title)) 
            {
                book.setAvailable(available);
                return;
            }
        }
        System.out.println("Kniha s názvom " + title + " nebola nájdená.");
    }
    
    // Výpis kníh v abecednom poradí
    public void listBooks() 
    {
        Collections.sort(books, Comparator.comparing(Book::getTitle));
        for (Book book : books) {
            System.out.println(book);
        }
    }
    
    
    // Vyhľadanie knihy
    public void findBook(String title) 
    {
        for (Book book : books) 
        {
            if (book.getTitle().equals(title)) 
            {
                System.out.println(book);
                return;
            }
        }
        System.out.println("Kniha s názvom " + title + " nebola nájdená.");
    }
    
    // Výpis všetkých kníh daného autora v chronologickom poradí
    public void listBooksByAuthor(String author) 
    {
        ArrayList<Book> authorBooks = new ArrayList<>();
        for (Book book : books) 
        {
            if (book.getAuthor().equals(author)) 
            {
                authorBooks.add(book);
            }
        }
        Collections.sort(authorBooks, Comparator.comparing(Book::getYear));
        for (Book book : authorBooks) 
        {
            System.out.println(book);
        }
    }
    
    // Výpis všetkých kníh daného žánru
    public void listBooksByGenre(String zaner) 
    {
        for (Book book : books) 
        {
            if (book instanceof Roman && ((Roman) book).getGenre().equals(zaner)) 
            {
                System.out.println(book);
            } 
            else if (book instanceof Ucebnica && ((Ucebnica) book).getGrade().equals(zaner)) 
            {
                System.out.println(book);
            }
        }
    }
    
    // Výpis vypůjčených kníh s informáciou o type
    public void listBorrowedBooks() 
    {
        for (Book book : books) 
        {
            if (!book.isAvailable())
            {
                if (book instanceof Roman)
                {
                    System.out.println(book + " - Román");
                } 
                else if (book instanceof Ucebnica) 
                {
                    System.out.println(book + " - Učebnica");
                }
            }
        }
    }
    
    // Uloženie informácií o knihe do súboru
    public void saveToFile(String title) 
    {
    	try (PrintWriter writer = new PrintWriter(new FileWriter(title + ".txt"))) 
    	{
            for (Book book : books) 
            {
                if (book.getTitle().equals(title)) 
                {
                	
                    writer.println(book.getTitle());
                    writer.println(book.getAuthor());
                    writer.println(book.getYear());
                    writer.println(book.isAvailable());
                    if (book instanceof Roman) 
                    {
                        writer.println("Žáner");
                        writer.println(((Roman) book).getGenre());
                    } 
                    
                    else if (book instanceof Ucebnica) 
                    {
                        writer.println("Žáner");
                        writer.println(((Ucebnica) book).getGrade());
                    }
                    writer.println();
                    return;
                }
            }
            System.out.println("Kniha s názvom " + title + " nebola nájdená.");
        } 
    	catch (IOException e) 
    	{
            System.out.println("Chyba pri zápise do súboru.");
        }
    }
    
    
    
    
    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename + ".txt"))) {
            String title = reader.readLine();
            String author = reader.readLine();
            int year = Integer.parseInt(reader.readLine());
            boolean available = Boolean.parseBoolean(reader.readLine());
            String info = reader.readLine();
            
            boolean isDifferent = true; // Predpokladáme, že kniha je iná, kým sa nepreukáže opak

            if (info != null && !info.isEmpty()) {
                String genreOrGrade = reader.readLine();
                for (Book book : books) {
                    if (book.getTitle().equals(title) && book.getAuthor().equals(author) && book.getYear() == year && book.isAvailable() == available) {
                        isDifferent = false; // Nájdená rovnaká kniha, nemusíme pridávať novú
                        break;
                    }
                }
                if (isDifferent) { // Ak je kniha iná, pridáme ju
                    if (info.equals("Žáner")) {
                        addBook(new Roman(title, author, year, available, genreOrGrade));
                        System.out.println("Nová kniha pridaná.");
                    } else if (info.equals("Žáner")) {
                        addBook(new Ucebnica(title, author, year, available, genreOrGrade));
                        System.out.println("Nová kniha pridaná.");
                    }
                } else {
                    System.out.println("Kniha už existuje v zozname.");
                }
            } else {
                // Kontrola existencie knihy podľa názvu
                for (Book book : books) {
                    if (book.getTitle().equals(title)) {
                        isDifferent = false; // Nájdená rovnaká kniha, nemusíme pridávať novú
                        break;
                    }
                }
                if (isDifferent) { // Ak je kniha iná, pridáme ju
                    addBook(new Book(title, author, year, available));
                    System.out.println("Nová kniha pridaná.");
                } else {
                    System.out.println("Kniha už existuje v zozname.");
                }
            }
        } catch (IOException e) {
            System.out.println("Chyba pri čítaní zo súboru.");
        }
    }
    
    
    
    

    
    
    
   /* public void loadFromDatabase() {
	    try {
	    	
	    	
	    	this.createTables();
	    	
	        // Vytvorenie spojenia s databázou
	        //Connection connection = DriverManager.getConnection("jdbc:sqlite:/C:\\Users\\lovis\\Documents\\Skola\\PC2T\\KuKodu\\Databaza\\SQLite\\sqlite-tools-win-x64-3450300\\myDB.db");
	    	DBConnection.getDBConnection();

	        // Vytvorenie príkazu SQL na získanie údajov z tabuľky Books
	        String query = "SELECT * FROM Books";

	        // Vytvorenie príkazu SQL a vykonanie dotazu
	        Statement statement = DBConnection.dbConnection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);

	        // Iterovanie cez výsledok a pridanie údajov do ArrayListu
	        while (resultSet.next()) {
	            String title = resultSet.getString("title");
	            String author = resultSet.getString("author");
	            int year = resultSet.getInt("year");
	            boolean available = resultSet.getBoolean("available");

	            // Získanie žánru a ročníka knihy pomocou JOIN
	            String genreQuery = "SELECT genre_name FROM Genres WHERE book_title = ?";
	            PreparedStatement genreStatement = DBConnection.dbConnection.prepareStatement(genreQuery);
	            genreStatement.setString(1, title);
	            ResultSet genreResult = genreStatement.executeQuery();
	            String genre = genreResult.getString("genre_name");

	            String gradeQuery = "SELECT grade_name FROM Grades WHERE book_title = ?";
	            PreparedStatement gradeStatement = DBConnection.dbConnection.prepareStatement(gradeQuery);
	            gradeStatement.setString(1, title);
	            ResultSet gradeResult = gradeStatement.executeQuery();
	            String grade = gradeResult.getString("grade_name");

	            // Vytvorenie objektu knihy na základe údajov z databázy a pridanie do ArrayListu
	            if (genre != null) {
	                books.add(new Roman(title, author, year, available, genre));
	            } else if (grade != null) {
	                books.add(new Ucebnica(title, author, year, available, grade));
	            }
	        }

	        // Zatvorenie spojenia s databázou
	        DBConnection.closeConnection();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Chyba pri načítaní údajov z databázy.");
	    }
	}*/
    
    
}

