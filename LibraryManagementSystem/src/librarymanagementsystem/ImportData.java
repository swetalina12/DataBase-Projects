

package librarymanagementsystem;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class ImportData {
    static int count=0;
   static Connection conn = null;
   
   
   public static void loadData()
   {
       try
       {
         System.out.println("connecting to Db.....");
         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
         Statement stmt = conn.createStatement();
         stmt.execute("CREATE DATABASE IF NOT EXISTS Library");
         stmt.execute("use Library ;");
         //create table BOOK
         stmt.execute("CREATE TABLE IF NOT EXISTS BOOK(" +
					"Book_id CHAR(10) NOT NULL, " +
					"Title VARCHAR(100) NOT NULL, " +
					"CONSTRAINT pk_BOOK PRIMARY KEY (Book_id) " +
					");");
         
         //create table BOOK_AUTHORS
         stmt.execute("CREATE TABLE IF NOT EXISTS BOOK_AUTHORS(" +
					"Book_id CHAR(10) NOT NULL, " +
					"Author_name VARCHAR(150) NOT NULL, " +
					"Type INT NOT NULL , " +
					"CONSTRAINT pk_BOOK_AUTHORS PRIMARY KEY (Book_id,Author_name) ," +
				"CONSTRAINT fk_BOOK_AUTHORS FOREIGN KEY (Book_id) REFERENCES BOOK(Book_id) " +
					");");
         
         //create table LIBRARY_BRANCH
         stmt.execute("CREATE TABLE IF NOT EXISTS LIBRARY_BRANCH(" +
					"Branch_id INT NOT NULL, " +
					"Branch_name VARCHAR(50) NOT NULL, " +
					"Address VARCHAR(50) NOT NULL , " +
					"CONSTRAINT pk_LIBRARY_BRANCH PRIMARY KEY (Branch_id)" +
						");");
         
        //create table BOOK_COPIES
        stmt.execute("CREATE TABLE IF NOT EXISTS BOOK_COPIES(" +
					"Book_id CHAR(10) NOT NULL, " +
					"Branch_id INT NOT NULL, " +
					"No_of_copies INT NOT NULL DEFAULT 0 , " +
					"CONSTRAINT pk_BOOK_COPIES PRIMARY KEY (Book_id,Branch_id) ," +
					"CONSTRAINT fk1_BOOK_COPIES FOREIGN KEY (Book_id) REFERENCES BOOK(Book_id)," +
					"CONSTRAINT fk2_BOOK_COPIES FOREIGN KEY (Branch_id) REFERENCES LIBRARY_BRANCH(Branch_id)" +
					");");
        //create table BORROWER
        stmt.execute("CREATE TABLE IF NOT EXISTS BORROWER(" +
					"Card_no INT NOT NULL, " +
					"Fname VARCHAR(25) NOT NULL, " +
					"Lname VARCHAR(25) NOT NULL , " +
					"Address VARCHAR(50) NOT NULL, " +
					"Phone CHAR(14) NOT NULL , " +
					"CONSTRAINT pk_BORROWER PRIMARY KEY (Card_no)" +
					");");
        
       //create table BOOK_LOANS
        stmt.execute("CREATE TABLE IF NOT EXISTS BOOK_LOANS(" +
					"Loan_id INT NOT NULL, " +
					"Book_id CHAR(10) NOT NULL, " +
					"Branch_id INT NOT NULL, " +
					"Card_no INT NOT NULL, " +
					"Date_out VARCHAR(25) NOT NULL, " +
					"Due_Date VARCHAR(25) NOT NULL , " +
					"Date_in VARCHAR(25) , " +
					"CONSTRAINT pk_BOOK_LOANS PRIMARY KEY (Loan_id)," +
					"CONSTRAINT fk1_BOOK_LOANS FOREIGN KEY (Book_id) REFERENCES BOOK(Book_id)," +
					"CONSTRAINT fk2_BOOK_LOANS FOREIGN KEY (Branch_id) REFERENCES LIBRARY_BRANCH(Branch_id)," +
					"CONSTRAINT fk3_BOOK_LOANS FOREIGN KEY (Card_no) REFERENCES BORROWER(Card_no)" +
					");");
        
           //create table FINES
          stmt.execute("CREATE TABLE IF NOT EXISTS FINES(" +
					"Loan_id INT NOT NULL, " +
                                        "Fine_amount FLOAT,"+
					"Paid BOOLEAN, " +
                 "CONSTRAINT fk1_FINES FOREIGN KEY (Loan_id) REFERENCES BOOK_LOANS(Loan_id)" +
						");");
          //Login Table
           stmt.execute("CREATE TABLE IF NOT EXISTS LOGIN(" +
					"Login_id INT NOT NULL, " +
                                        "PASSWORD CHAR(10) NOT NULL,"+
                 "CONSTRAINT pk_LOGIN PRIMARY KEY (Login_id)" +
						");");
      
     
        
        System.out.println("table creation successful");
        
        //*********LOADING TABLES***********:
        //BOOK:
        
         stmt.executeUpdate("INSERT INTO LOGIN VALUES('123','123');");
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM BOOK;");
        while (rs.next()){
            count++;
            if (count==0)
            System.out.println("BOOKS table is empty");
        }
            int skipLine=0;
            String csvFile ="books_authors.csv";
            System.out.println("reading books_authors");
            String line;
            String cvsSplitBy = "\t";
           
            try{
                
                FileReader inputFile = new FileReader(csvFile);     
                BufferedReader in = new BufferedReader(inputFile);
                
                       while ((line = in.readLine()) != null) {
                          
                           if(skipLine == 0) {
                            skipLine++;  
                             continue;
                            }
                          // String split
                           String splitAuthorRow[] = line.split(cvsSplitBy);
                           String book_id = splitAuthorRow[0];
                           //first value is book id
                           Book b= new Book();
                           b.book_ID = book_id;
                           
                           //second value is authors full name separated by spaces
                           String fullName = splitAuthorRow[1];
                           //third value is book title
                            String intermediatebookTitle = splitAuthorRow[2];
                            String bookTitle=intermediatebookTitle.replace("'", "''");
                            b.book_Title=bookTitle;
                             String nameOfAuthor[] = fullName.split(", ");
                            
                            System.out.println("INSERT INTO BOOK VALUES('"+ b.book_ID+"','"+b.book_Title+"');");  
			    stmt.executeUpdate("INSERT INTO BOOK VALUES('"+ b.book_ID+"','"+b.book_Title+"');");
                            AuthorName a = new AuthorName(fullName); 
                            System.out.println("INSERT INTO BOOK_AUTHORS VALUES('"+b.book_ID+"','"+fullName+"','"+a.type+"');");
                            stmt.executeUpdate("INSERT INTO BOOK_AUTHORS VALUES"
                                     + "('"+b.book_ID+"','"+fullName+"','"+a.type+"');");
                            
//                            for(int i=0;i<nameOfAuthor.length;i++)
//                            {
//                                 System.out.println("INSERT INTO BOOK_AUTHORS VALUES('"+b.book_ID+"','"+nameOfAuthor[i]+"','"+a.type+"');");
//                                stmt.executeUpdate("INSERT INTO BOOK_AUTHORS VALUES"
//                                        + "('"+b.book_ID+"','"+nameOfAuthor[i]+"','"+a.type+"');");
//                            }	           
            }
            } 
            catch(IOException e)
            {
                e.printStackTrace();
            }

        //LIBRARY BRANCH:
         count=0;
         ResultSet lbs = stmt.executeQuery("SELECT * FROM LIBRARY_BRANCH;");
         while (lbs.next()){
            count++;
            //System.out.println("value:"+count);
            if (count==0)
            System.out.println("LIBRARY_BRANCH table is empty");
        }
            skipLine=0;
            csvFile ="library_branch.csv";
            System.out.println("reading library_branch");
           // String line;
            //String cvsSplitBy = "\t";
           
            try{
                
                FileReader inputFile = new FileReader(csvFile);     
                BufferedReader in = new BufferedReader(inputFile);
                
                       while ((line = in.readLine()) != null) {
                          LibraryBranch lb=new LibraryBranch();
                           if(skipLine == 0) {
                            skipLine++;  
                             continue;
                            }
                          // String split
                           String splitAuthorRow[] = line.split(cvsSplitBy);
                           int branch_id =  Integer.parseInt(splitAuthorRow[0]);
                           //first value is branch_id
                          lb.branch_id = branch_id;
                           //second value is branch name
                           String branch_name = splitAuthorRow[1];
                           lb.branch_name=branch_name;
                           //third value is branch Address
                            String address = splitAuthorRow[2];
                           lb.address=address;
                            
                            System.out.println("INSERT INTO LIBRARY_BRANCH VALUES('"+ lb.branch_id+"','"+lb.branch_name+"''"+lb.address+"');");  
			    stmt.executeUpdate("INSERT INTO LIBRARY_BRANCH VALUES('"+ lb.branch_id+"','"+ lb.branch_name+"','"+lb.address+"');");
                       }
            }
       
            catch(IOException e)
            {
                e.printStackTrace();
            }
            
            //BOOK_COPIES
         count=0;
         ResultSet bc = stmt.executeQuery("SELECT * FROM BOOK_COPIES ;");
         while (bc.next()){
            count++;
            //System.out.println("value:"+count);
            if (count==0)
            System.out.println("BOOK_COPIES table is empty");
        }
            skipLine=0;
            csvFile ="book_copies.csv";
            System.out.println("reading book_copies");
           // String line;
            //String cvsSplitBy = "\t";
           
            try{
                
                FileReader inputFile = new FileReader(csvFile);     
                BufferedReader in = new BufferedReader(inputFile);
                
                       while ((line = in.readLine()) != null) {
                          LibraryBranch lb=new LibraryBranch();
                           if(skipLine == 0) {
                            skipLine++;  
                             continue;
                            }
                          // String split
                           String splitAuthorRow[] = line.split(cvsSplitBy);
                           //first value is book_id
                          String book_id =  splitAuthorRow[0];
                           //second value is branch id
                           int branch_id = Integer.parseInt(splitAuthorRow[1]);
                           //third value is branch Address
                          int no_of_copies=Integer.parseInt(splitAuthorRow[2]);
                            
                            System.out.println("INSERT INTO BOOK_COPIES VALUES('"+ book_id+"','"+branch_id+"','"+no_of_copies+"');");  
			    stmt.executeUpdate("INSERT INTO BOOK_COPIES VALUES('"+ book_id+"','"+ branch_id+"','"+no_of_copies+"');");
                       }
            }
       
            catch(IOException e)
            {
                e.printStackTrace();
            }
            
              //BORROWER: 
                   count=0;
         ResultSet br = stmt.executeQuery("SELECT * FROM BORROWER;");
         while (br.next()){
            count++;
            //System.out.println("value:"+count);
            if (count==0)
            System.out.println("BORROWER table is empty");
        }
            skipLine=0;
            csvFile ="borrowers.csv";
            System.out.println("reading book_copies");
           // String line;
            //String cvsSplitBy = "\t";
           
            try{
                
                
                FileReader inputFile = new FileReader(csvFile);     
                BufferedReader in = new BufferedReader(inputFile);
                
                       while ((line = in.readLine()) != null) {
                         // LibraryBranch lb=new LibraryBranch();
                           if(skipLine == 0) {
                            skipLine++;  
                             continue;
                            }
                          // String split
                           String splitAuthorRow[] = line.split(cvsSplitBy);
                           //first value is card_no
                          String card_no =  splitAuthorRow[0];
                          String fname = splitAuthorRow[1];
                          String lname = splitAuthorRow[2];
                          String shortAddress=splitAuthorRow[3];
                          String city=splitAuthorRow[4];
                          String state=splitAuthorRow[5];
                          String phoneNumber=splitAuthorRow[6];
                          String address=shortAddress+" "+city+" "+state;
                         
                            
                            System.out.println("INSERT INTO BORROWER VALUES('"+ card_no+"','"+fname+"','"+lname+"','"+address+"','"+phoneNumber+"');");  
			    stmt.executeUpdate("INSERT INTO BORROWER VALUES('"+ card_no+"','"+ fname+"','"+ lname+"','"+ address+"','"+phoneNumber+"');");
                       }     
                      
            }
       
            catch(IOException e)
            {
                e.printStackTrace();
            }
            
            
         //BOOK_LOANS: 
         count=0;
         ResultSet bl = stmt.executeQuery("SELECT * FROM BOOK_LOANS;");
         while (bl.next()){
            count++;
            //System.out.println("value:"+count);
            if (count==0)
            System.out.println("BOOK_LOANS table is empty");
        }
            skipLine=0;
            csvFile ="book_loans_data.csv";
            System.out.println("reading book_loans_data");
           
            try{
                FileReader inputFile = new FileReader(csvFile);     
                BufferedReader in = new BufferedReader(inputFile);
                
                       while ((line = in.readLine()) != null) {
                           if(skipLine == 0) {
                            skipLine++;  
                             continue;
                            }
                          // String split
                            String splitAuthorRow[] = line.split(cvsSplitBy);
                           //first value is card_no
                            int load_id =  Integer.parseInt(splitAuthorRow[0]);
                            String book_id = splitAuthorRow[1];
                            int branch_id = Integer.parseInt(splitAuthorRow[2]);
                            int card_no=Integer.parseInt(splitAuthorRow[3]);
                            String date_out=splitAuthorRow[4];
                            String due_date=splitAuthorRow[5];
                            String date_in=splitAuthorRow[6];
                         
                            
                         System.out.println("INSERT INTO BORROWER VALUES('"+ load_id+"','"+book_id+"','"+branch_id+"','"+card_no+"','"+date_out+"');");  
                          stmt.executeUpdate("INSERT INTO BOOK_LOANS VALUES('"+ load_id+"','"+ book_id+"','"+ branch_id+"','"+ card_no+"','"+date_out+"','"+due_date+"','"+date_in+"');");
                       }          
            }
       
            catch(IOException e)
            //catch(IOException|ParseException e)
            {
                e.printStackTrace();
            }

             conn.close();
             System.out.println("Success!!");
       }
       
       catch(SQLException ex){
           System.out.println("Error in connection: " + ex.getMessage());
       }
   }  
}
