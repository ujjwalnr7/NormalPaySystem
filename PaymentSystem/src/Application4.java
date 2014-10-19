/**
 * @(#)Application4.java
 *
 * Lenovo U400
 * @Ujjwal Nair 
 * @version 9.50 2012/12/5
 * IDE: JCreator v4.50
 */

import java.io.IOException;
import java.io.RandomAccessFile;


public class Application4 {
	static final String FILENAME = "c:\\Users\\Ujjwal\\Desktop\\employee.dat";
    private static  int numEmployee = 0;
    private static RandomAccessFile theFile;
   

	public static void main(String args[]) 
	{ 
    int mainchoice =0;
    byte reportchoice =0;
    
    Transaction t1 = new Transaction();
	// if an uncoded choice is input then the choice is not accepted and the menu refreshes
	while (mainchoice !=4) 
	{
		// This is the main menu that is first output when the program is executed
     	ibid.output("**********SARAYU STEEL WORKS***************");
     	ibid.output("			MAIN MENU			");
 		ibid.output("1. EMPLOYEE RECORDS ");
 		ibid.output("2. SALARY CALCULATION ");
 		ibid.output("3. REPORT GENERATION ");
 		ibid.output("4. QUIT "  );	
		mainchoice = ibid.inputInt("Enter choice : ");
		// this choice is input as an Integer Value called 'main choice' 
		// the different methods of each choice
		if (mainchoice ==1)
		{
			// if the first option is chosen the checkforfile method is called 
			
			
		// The purpose of this method is to check for an existing employee master file 
			LinkList list  = new LinkList();// creates the linklist for all program operations	
	 		int choice;	
	 	     if (checkForFile())
	  			{
	    			ibid.output("Data file located and loaded with " + numEmployee + " existing records\n");
	  			}
	  		else
	  			{
	  				ibid.output("No data file found, creating new file \n");
	  			}
       		if (numEmployee > 0)   
     		// if the number of employee found is greater than zero they are loaded onto the linked list
     	 		loadfromfile(list); 
     	     
	    	do { // this do loop covers all the functions the employee menu functions 
	    		//with the ending condition being if the quit option is chosen
	    	
     			choice = menuChoice();
     			if(choice == 1) // the first option is the add option
     	      		{
     		  			EmpData emp = new EmpData(); //an object of empdata class is created
     		   			emp.acceptdata();// data is accepted
     		    		list.add(emp); // object is added to the list as a node
     		  		}
     		
     			else if(choice == 2) // this option chooses the find method
     		 		{
     		   			int finditem =list.find(ibid.inputInt("Enter employee number of the employee to be found: ")); 
     		   			// the employee number of the employee to be found is input	
     		   				if(finditem == 0)
     		   					// a flag called finditem is placed if it is zero it means the item was not found
     			  				ibid.output("Not found"); 
     						else
     							// if the record is obtained the record is output onto the screen
     			  				ibid.output(finditem); 
     	   	 		}
 
				else if(choice == 3) // this method displays all the records
					{
						list.traverse(); // this call the traverse method which displays all the records in the linklist
					}
			
				else if(choice == 4)// this method calls the delete method 
					{
						// since the employee number is the key field input this to identify the record
						int delitem = ibid.inputInt("Enter the employee number of the employee to be deleted: "); 
						// this statement passes the item to be deleted to the list method deletefirst which deletes the node
						list.deleteFirst(delitem); 
					}
			
				else if(choice == 5)// this method calls the quit method 
					{   
						addtofile(list);// this method adds the linkedlist to the .dat file which stores all the records
						ibid.output("Exiting"); 
					}
			
				else // this statement is called if the an illegal choice is entered and the user is made to reinput the choice
						ibid.output("Illegal choice");
			
			}while(choice != 5);
		}
	 	
	 	else if (mainchoice==2)	
	 	{
	 		t1.processpayroll();
	 	}
	 
	 	else if (mainchoice==3) //this is the report menu which offers all the options 
	 	{
	 		ibid.output("*********REPORTS MENU**********");
	 		// a monthly report generates all financial data of all employees for a given month
	 		ibid.output("1. Generate Monthly Report");
	 		// the cumulative financial report shows all the financial data of all the employees: total salary etc. 
	 		ibid.output("2. Generate Cummulative Finances Report"); 
	 		// this causes the menu to return to the main menu
	 		ibid.output("3. Exit to the Previous Menu " ); 
	 		// the reportchoice variable is a byte so as to maximize space efficiency	
	 		reportchoice = ibid.inputByte("Please Enter Your Choice: ");
	 		if (reportchoice==1)
	 		{	 			
	 			t1.monthlyreport();// the montly report method is called
	 		}
	 		else if (reportchoice==2)
	 		{
	 			t1.financereport(); // the finance report method is called 
	 		}	
	 		else if (reportchoice ==3)
	 		{
	 			return;
	 		}	
		}
	}
}
	
	
	// This is the menu choice method
 	// Purpose: prints the employee menu and get the user's choice
 	  public static int menuChoice() 
 	 	{
	 	    ibid.output("**********EMPLOYEE RECORDS - MENU***************");
	 		ibid.output("Enter 1 to add an Employee");
	 		ibid.output("Enter 2 to find an Employee");
	 		ibid.output("Enter 3 to list all the Employees");
	 		ibid.output("Enter 4 to delete an Employee");
	 		ibid.output("Enter 5 to Return to the main menu");
	 		return ibid.inputInt("Enter your Choice: "); // the choice is input as an Integer Value called 'Choice'
		}
	 
	
	/**
	 * Method to check for existing file
	 * 
	 * Return boolean true if there is an existing file
	 */
		
	 private static boolean checkForFile()
    	{
	      // See if a file already exists
		  try
		  {
		    theFile = new RandomAccessFile(FILENAME, "rw");
		    ibid.output(theFile.length());
		    ibid.output(EmpDataFile.length());
		    
		    numEmployee = (int) theFile.length() / EmpDataFile.length();
		    ibid.output("records : " + numEmployee);
		    return true;
		  }
		  catch( IOException io)
		  {
		    ibid.output("Error trying to open file " + io.getMessage());
		  }
		  return false;
    	}
		
	/* this method is used to 
	 * save the linkedlist on to the data file 
	 * which will store all the 
	 * employee records
	 */	
	
	private static void addtofile(LinkList list)
		{
		  try
		  {
		   long posToAdd=0;
		   numEmployee = 0;
		   Node cur = list.getHead();
		   EmpDataFile theEmp = new EmpDataFile(cur.getData().getempno(),cur.getData().getempname(),cur.getData().getbasicsal(), 
		   							cur.getData().DOB, cur.getData().ContactNo,cur.getData().cumSal, cur.getData().cumPF, cur.getData().cumTax);
			   
		  
		   while(cur != null) 
		 	{
		 	
		 	 theEmp.writeRecord(theFile,theEmp, posToAdd);
		     numEmployee = numEmployee + 1;
		   	 cur = cur.getNext(); 
		   	 posToAdd= numEmployee * EmpDataFile.length();
		   	 
		   	 if( cur!=null)
		   	 { 
		   	 	theEmp = new EmpDataFile(cur.getData().getempno(),cur.getData().getempname(),cur.getData().getbasicsal(), 
		   	 	cur.getData().DOB, cur.getData().ContactNo,cur.getData().cumSal, cur.getData().cumPF, cur.getData().cumTax);
		     
		   	 }
		 	}
		   }
		   catch(IOException io)
	  		{
		  		ibid.output("Error adding record " + io.getMessage()); 
			}
	  
		}

	/* This method
	 * calls the method 
	 * which loads existing data records from
	 * the data file of they exist
	 */  
	
	public static void loadfromfile(LinkList list)
		{
			EmpData emp = new EmpData();
			Node head=null;
			try
			  {
			   for(int x = 0; x < numEmployee; x = x + 1)
			    {
			    int posToRead = x * EmpDataFile.length();
				EmpDataFile empfile =  EmpDataFile.readRecord(theFile, posToRead);
			    ibid.output("Loading record \n" + x);
			    ibid.output("" + x + ": " +empfile.toString());
			    list.add(empfile);
			    }
		      }
		  	catch(IOException io)
		  		{
		     		ibid.output("Error during read - " + io.getMessage());
		 		}
	
		}
} // class Application
