package Knihy;

import Knihy.Book;

public class Roman extends Book
{
private String zaner;
    
    public Roman(String title, String author, int year, boolean available, String genre) 
    {
        super(title, author, year, available);
        this.zaner = genre;
    }
    
    public String getGenre() 
    {
        return zaner;
    }
    
    public void setGenre(String genre) 
    {
        this.zaner = genre;
    }
    
    @Override
    public String toString() 
    {
        return super.toString() + ", Žáner: " + zaner;
    }
}
