/**
 * @(#)transdatafile.java
 *
 *
 * @Ujjwal Nair
 * @version 1.00 2012/11/8
 */

import java.io.*;
import java.lang.Math;
//Class to perform operations on transaction file and perform the final payroll processing.

public class Transaction 
{
   private static  int numTrans = 0;
   private static RandomAccessFile theTransFile;
   private static RandomAccessFile theEmpFile;
   private static  int numEmployees = 0;
   public void processpayroll()
   	{
    //Payroll Processing is done on a monthly basis where the month is input by the user       
       String mname = acceptmonth();
       
       if (checkForFile(mname,"rw"))
	  	{
	    	ibid.output("Transaction file being processed for the month of: " + mname);
	  	}
	    	
      addTransDetails(mname);
    }
    
	public static void addTransDetails(String mname)
	{
		try // again try and catch methods have been implemented to catch any I/O Error
	 		{
	 			byte hd, sl, cl;	
	 			int posToRead, posToAddtrans=0;	   
	 			TransDataFile theTrans = new TransDataFile();
	 			theEmpFile = new RandomAccessFile("c:\\Users\\Ujwal\\Desktop\\employee.dat", "rw"); 
	 			numEmployees = (int)theEmpFile.length()/EmpDataFile.RECORD_BYTES;
	 			ibid.output("Number of employees are " + numEmployees);
	 			for(int x = 0; x < numEmployees; x = x + 1)
	 				{
					    posToRead = x * EmpDataFile.length();
						posToAddtrans= x * TransDataFile.length();
						EmpDataFile empfile =  EmpDataFile.readRecord(theEmpFile, posToRead);
						ibid.output("Enter info for Employee number : "+ empfile.getempno() + "  Employee name : "  +empfile.getempname().trim() );
					    hd = ibid.inputByte("Number of Half days : ");
					    sl = ibid.inputByte("Number of Sick leave : ");
					    cl = ibid.inputByte("Number of Casual leave : ");
					    theTrans.empno = empfile.getempno();
					    theTrans.casualleave = cl;
					    theTrans.sickleave = sl;
					    theTrans.halfdays = hd;
					    
					    //Calculate the tax, pf and the deductions to be made for the leaves.
					    double tax  = taxcalc(empfile.getbasicsal());   
					    double pf = pfcalc(empfile.getbasicsal());  
					    double leaves = leavecalc(empfile.getbasicsal(),sl,cl,hd ); 
					    theTrans.monthsalary = empfile.getbasicsal()+ pf-tax-leaves;	
					     
					    //Update the employee file for the cummulative fields. 
					    empfile.cumPF = empfile.cumPF + pf;
					    empfile.cumTax = empfile.cumTax + tax;
					    empfile.cumSal = empfile.cumSal + theTrans.monthsalary;
					    
					    //Write the transaction record to the transaction file.
					    TransDataFile.writeRecord(theTransFile,theTrans, posToAddtrans);
					    
					     EmpDataFile theEmp = new EmpDataFile(empfile.getempno(),empfile.getempname(),empfile.getbasicsal(), empfile.DOB, empfile.ContactNo, empfile.cumSal,empfile.cumPF, empfile.cumTax );
					  
					    // Write the updated employee record to the employee file.
					    EmpDataFile.writeRecord(theEmpFile,theEmp,posToRead);
	    			}
	  			generateslips(mname);  
      		}
      
	  catch(IOException io)
	  {
	     ibid.output("Error during read - " + io.getMessage());
	  }
	}
	 
	 // This method generates the first of the 2 possible reports
	 // In the monthly report the salary paid to every employee for a given month is displayed
	 // If that months finances have not yet been generated an error message is output
	 // again try and catch methods have been used to catch I/O errors   
	 public static void monthlyreport()
	 	{
	 		String mname = acceptmonth();
	 		if (checkForFile(mname,"r"))
	 			{
	 				ibid.output("Transaction file being processed for the month of : " + mname);
	    			try
	    				{
	    					int posToRead, posToReadtrans=0;
	    					theEmpFile = new RandomAccessFile("c:\\Users\\Ujwal\\Desktop\\employee.dat", "r"); 
	 						numEmployees = (int)theEmpFile.length()/EmpDataFile.RECORD_BYTES;
	 						double sum =0;
	 						for(int x = 0; x < numEmployees; x = x + 1)
	 							{
	 								posToRead = x * EmpDataFile.length();
									posToReadtrans= x * TransDataFile.length();
									EmpDataFile empfile =  EmpDataFile.readRecord(theEmpFile, posToRead);
									TransDataFile transfile = TransDataFile.readRecord(theTransFile, posToReadtrans);
									double tax  = taxcalc(empfile.getbasicsal());   
	    							double pf = pfcalc(empfile.getbasicsal());
	    							double leaves = leavecalc(empfile.getbasicsal(),transfile.sickleave,transfile.casualleave,transfile.halfdays );
	    							ibid.output("\nNet Total Salary paid for " + empfile.getempname().trim()  +  " is " +Math.round(( empfile.getbasicsal() + pf - tax-leaves)));
	    							sum = sum +  empfile.getbasicsal() + pf - tax-leaves;
	    						}
							ibid.output ("Total Salary paid for this month of " + mname + " is " + Math.round(sum));  
						}
	
	 				catch(IOException io)
	 					{
	 						ibid.output("Error during read - " + io.getMessage());
	 					}
	 			}		
	  
	  		else
	  			{
	  				ibid.output("Sorry , no report can be generated for this month");
	 			}
	 
	 
	
    	}    


	// This method generates the second of the 2 possible reports
	// The finances report shows the cumulative salaries and deductions made for any employee the user chooses
	public static void financereport()
		{
			try
				{
					int posToRead;
					int eno;
					boolean found = false;
					theEmpFile = new RandomAccessFile("c:\\Users\\Ujwal\\Desktop\\employee.dat", "r");
					numEmployees = (int)theEmpFile.length()/EmpDataFile.RECORD_BYTES;
					eno=ibid.inputInt("Enter the employee number to generate report for : ");
					for(int x = 0; x < numEmployees; x = x + 1)
						{
							posToRead = x * EmpDataFile.length();
							EmpDataFile empfile =  EmpDataFile.readRecord(theEmpFile, posToRead);
							if (empfile.getempno() == eno)
								{
									ibid.output("\nEmployee Name: "+empfile.getempname().trim()+"                           "+"Employee Number: "+empfile.getempno());
   									ibid.output("Cummulative Salary  : " + Math.round(empfile.cumSal));
   									ibid.output("Cummulative Tax : " + Math.round(empfile.cumTax));
   									ibid.output("Cummulative PF : " + Math.round(empfile.cumPF));
   									found =true;
   									break;
   								}
  						}
  					if (found == false)
  						{
  							ibid.output("no such employee exists");
  						}
  				} 
  			catch(IOException io)
  				{
  					ibid.output("Error during read - " + io.getMessage());
  				}
		}

	// This method generates the payment slips for each and every employee
	public static void generateslips(String mname)
		{
			try
				{
					int posToRead, posToReadtrans=0;	   
	 				TransDataFile theTrans = new TransDataFile();
	 				theEmpFile = new RandomAccessFile("c:\\Users\\Ujwal\\Desktop\\employee.dat", "rw"); 
	 				numEmployees = (int)theEmpFile.length()/EmpDataFile.RECORD_BYTES;
	 				ibid.output("Creating salary slips for " + numEmployees + "  Employees " );	
					for(int x = 0; x < numEmployees; x = x + 1)
						{
							posToRead = x * EmpDataFile.length();
							posToReadtrans= x * TransDataFile.length();
							EmpDataFile empfile =  EmpDataFile.readRecord(theEmpFile, posToRead);
							TransDataFile transfile = TransDataFile.readRecord(theTransFile, posToReadtrans);
							double tax  = taxcalc(empfile.getbasicsal());   
	    					double pf = pfcalc(empfile.getbasicsal()); 
	    					ibid.output("pf is : " + pf);	 
	    	
						    double leaves = leavecalc(empfile.getbasicsal(),transfile.sickleave,transfile.casualleave,transfile.halfdays ); 
						    // the following code is for the display of the payslip and its format
						    ibid.output("***************"+mname+"*****************");
							ibid.output("\nEmployee Name: "+empfile.getempname().trim()+"                           "+"Employee Number: "+empfile.getempno());
							ibid.output("\nAdditions"+"                                   							" + "Deductions");
							ibid.output("\nBasic Salary "+empfile.getbasicsal()+"            											" +"Tax "+Math.round(tax));
							ibid.output("\nProvident Fund "+ pf +"               										"+"Casual Leave "+transfile.casualleave);
							ibid.output("\n                                     									"+"Sick Leaves "+transfile.sickleave);
							ibid.output("\n                                     									"+"Half Days   "+transfile.halfdays);
							ibid.output("\n                             											"+"Total Deductions "+ Math.round((tax+ leaves)));
							ibid.output("\nNet Total Salary "+ Math.round((empfile.getbasicsal() + pf - tax-leaves)));
	    				}
				}
	 		catch(IOException io)
	 			{
	 				ibid.output("Error during read - " + io.getMessage());
	 			}
	 	
		}


// Tax and Provident Fund Deductions are hardcoded and the following code shows the graduations
// Tax calculation
	public static double taxcalc(double basic)
		{
			if (basic  <=5000)
				return 0;
			else if( basic <= 19000)
				return (double)(5 * basic/100);
			else if (basic <= 25000)
				return (double)(10* basic/100);
			else
				return (double)(15* basic/100);
		}

// Provident Fund Calculations
	public static double pfcalc(double basic)
		{
			if (basic  <=5000)
				return (double)(4*basic/100);
			else if( basic <= 19000)
				return (double)( 10* basic/100);
			else if (basic <= 25000)
				return (double)(17* basic/100);
			else
				return (double)(20 * basic/100);
		}
// This code calculates the code for the salary deductions based on leaves taken (Sick Leave, Half Days etc.)
	public static double leavecalc(double basic, int sl, int cl, int hd)
		{
			double deduct=0;
			if ( (cl+ sl) <= 3)
				deduct = 0;
			else 
				deduct = basic/30 * (cl + sl) ;
			if (hd >2)
				deduct = deduct + (basic /60 * hd);
				return deduct;	 
		}

	private static boolean checkForFile(String mname, String mode)
    	{
      		// See if a file already exists
      		String m = "d:\\";
      		mname = m.concat(mname).concat(".dat");
	  		try
	  			{
	  				theTransFile = new RandomAccessFile(mname, mode);
	    			numTrans = (int) theTransFile.length() / TransDataFile.length();
	    			ibid.output("records : " + numTrans);
	    			return true;
	  			}
	  		catch( IOException io)
	  			{
	    			ibid.output("Error trying to open file " + io.getMessage());
	  			}
	  		return false;
    	}    
    

	public static String acceptmonth()
		{
    		// this is the array of available months for calculation, invalid months generate error messages and every month used decreases the number of available months
    		String[] mnames = {"January", "February", "March", "April", "May", "June", "July", "August", "SEptember", "October","November", "December"};
			int mno = ibid.inputInt("Enter month number to enter transaction data : " );
			return mnames[mno-1];
		}	
	
}   