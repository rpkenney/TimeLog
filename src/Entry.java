public class Entry{

    public static final int NUM_CATEGORIES = 8;

    public static final String[] CATEGORIES = {"E-Blasts", "News Posts", "Press Releases", "Social Media Posts", "Testimonials", "Virtual Tours", "Website", "Other"};

    private Date date;

    private Category[] categories;

    private double timeSpent;

    public Entry(Date date, Category[] categories, double timeSpent){
        this.date = date;
        this.timeSpent = timeSpent;
        this.categories = categories;
    }

    public Date getDate(){
        return date;
    }

    public Category getCategory(int category){
        return categories[category];
    }

    public Category[] getCategories(){
        return categories;
    }

    public double getTimeSpent(){
        return timeSpent;
    }

    public boolean isDateWithin(int month, int year){
        return date.isWithin(month, year);
    }

    public boolean isDateWithin(int year){
        return date.isWithin(year);
    }
}