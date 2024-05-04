package Knihy;

import Knihy.Book;

public class Ucebnica extends Book
{
private String rocnik;
    
    public Ucebnica(String title, String author, int year, boolean available, String grade) 
    {
        super(title, author, year, available);
        this.rocnik = grade;
    }
    
    public String getGrade() 
    {
        return rocnik;
    }
    
    public void setGrade(String grade) 
    {
        this.rocnik = grade;
    }
    
    @Override
    public String toString() 
    {
        return super.toString() + ", Žáner: " + rocnik;
    }
}
