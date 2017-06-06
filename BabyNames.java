
/**
 * Write a description of class BabyNames here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;

public class BabyNames
{
    public void totalBirths() {
        
        FileResource fr = new FileResource();
        CSVParser data = fr.getCSVParser(false);
        int totalBoys = 0;
        int totalGirls = 0;
        int countBoysNames = 0;
        int countGirlsNames = 0;
        
        for (CSVRecord r : data) {
            if(r.get(1).equals("M")) {
                totalBoys += Integer.parseInt(r.get(2));
                countBoysNames ++;
            }
            else {
                totalGirls += Integer.parseInt(r.get(2));
                countGirlsNames++;
            }
        }
        
        System.out.println("Total of girls born: " + totalGirls +"\n" +
                           "Total of boys born: " + totalBoys + "\n" +
                           "Total name of girls: " + countGirlsNames +"\n" +
                           "Total name of boys: " + countBoysNames + "\n" +
                           "Total names: " + (totalGirls + totalBoys)); 
    
    }
    
    public int getRank(int year, String name, String gender){
        
        FileResource fr = new FileResource("./data/us_babynames_by_year/yob"+ year + ".csv");
        CSVParser data = fr.getCSVParser(false);
        int rank = 0;
        
        for (CSVRecord r : data) {
            if(r.get(1).equals(gender)) {
                rank ++;
                if (r.get(0).equals(name)) {
                    return rank;
                }
            }
        }
        return -1;
    }
    
    public String getName(int year, int rank, String gender){
        
        FileResource fr = new FileResource("./data/us_babynames_by_year/yob"+ year + ".csv");
        CSVParser data = fr.getCSVParser(false);
        
        for (CSVRecord r : data){
            String name = r.get(0);
            if(rank == getRank(year, name, gender)){
                return name;
            }
        }
        return "NO NAME";
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        int nameRank = getRank(year, name, gender);
        String newName = getName(newYear, nameRank, gender);
        if (gender.equals("M")) {
            System.out.println(name + " born in " + year + " would be " + newName + " if he was born in " + newYear + ".");
        } else {
            System.out.println(name + " born in " + year + " would be " + newName + " if she was born in " + newYear + ".");
        }
    }
    
    public int yearOfHighestRank(String name, String gender){
        
        DirectoryResource dr = new DirectoryResource();
        int yearRank = -1;
        int rank = 0;
        
        for (File file : dr.selectedFiles()){
            
            FileResource fr = new FileResource(file);
            CSVParser data = fr.getCSVParser(false);
            int year = extractYear(file);
            int currRank = getRank(year, name, gender);
            
            if(currRank != -1 && currRank != rank && (rank == 0 || rank > currRank)){
                rank = currRank;
                yearRank = year;
                
            }
        }
        return yearRank;
    }
    
    public double getAverageRank(String name, String gender){
        
        DirectoryResource dr = new DirectoryResource();
        double averageRank = -1.0;
        double numYears = 0;
        double sumRank = 0;
        
        for(File file : dr.selectedFiles()){
            
            FileResource fr = new FileResource(file);
            int year = extractYear(file);
            int currRank = getRank(year, name, gender);
            if (currRank != -1){
                sumRank += currRank; 
                numYears ++;
            }
        }
        return sumRank/numYears;
    }
    
    public int getTotalBirthsRankedHigher (int year, String name, String gender){
        
        int rank = getRank(year, name, gender) -1;
        int totalBirths = 0;
        System.out.println("Name: " + name + ". Rank: " + getRank(year, name, gender));
        
        while (rank > 0) {
            String currName = getName(year, rank, gender);
            totalBirths += getTotalBirths(year, currName, gender);
            System.out.println("Rank: " + rank + ". Name: " + currName);
            System.out.println("Number of " + currName + " born: " + getTotalBirths(year, currName, gender));
            System.out.println("Total births: " + totalBirths);
            rank --;
        }
 
        return totalBirths;
    }
    
    public int getTotalBirths(int year, String name, String gender){
        
        FileResource fr = new FileResource("./data/us_babynames_by_year/yob"+ year + ".csv");
        CSVParser data = fr.getCSVParser(false);
        
        int count = -1;
        
        for(CSVRecord r : data){
            if(r.get(0).equals(name) && r.get(1).equals(gender)){
                System.out.println("name: " + r.get(0) + " total: " + r.get(2));
                count = Integer.parseInt(r.get(2)); 
            }
        }
        return count;
    }
    
    public int extractYear(File file){
        String fileName = file.getName();
        int idx = fileName.indexOf("yob") +3;
        int year = Integer.parseInt(fileName.substring(idx, idx+4));
        return year;
    }
    
    
    
    /* Testing area */
    
    public void testGetTotalBirths(){
        System.out.println(getTotalBirths(1990, "Jessica", "F"));
    }
    
    public void testGetTotalBirthsRankedHigher() {
        //System.out.println(getTotalBirthsRankedHigher(2012,"Ethan", "M"));
        //System.out.println(getTotalBirthsRankedHigher(2012,"Picolino", "M"));
        //System.out.println(getTotalBirthsRankedHigher(1990,"Drew", "M"));
        System.out.println(getTotalBirthsRankedHigher(1990,"Emily", "F"));
    }
    public void testGetAverageRank(){
        System.out.println(getAverageRank("Mason", "M"));
        System.out.println(getAverageRank("Jacob", "M"));
    }
    public void testYearOfHighestRank(){
        System.out.println(yearOfHighestRank("Mason", "M"));
        System.out.println(yearOfHighestRank("Shoockoolittles", "M"));
    }
    public void testExctractYear(){
        //System.out.println(extractYear("../us_babynames/us_babynames_test/yob2014short.csv"));
        //System.out.println(extractYear("yob2014short.csv"));
    }
    public void testwhatisNameInYear(){
        whatIsNameInYear("Isabella", 2012, 2014, "F");
    }
    
    public void testGetName() {
        System.out.println("It should return Mason:");
        System.out.println(getName(2012, 2, "M"));
        System.out.println("It should return NO NAME");
        System.out.println(getName(2012, 6, "F"));
    }
    public void testOfGetRank() {
        System.out.println("It should return 2: \n");
        System.out.println(getRank(2012, "Mason", "M"));
        System.out.println("It should return -1: \n");
        System.out.println(getRank(2012, "Mason", "F"));
    }
    
    public void testTotalBirths() {
        totalBirths();
    }
}
