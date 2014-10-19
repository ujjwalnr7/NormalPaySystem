/**
 * @(#)EmpDataFile.java
 *
 *
 * @author 
 * @version 1.00 2012/8/4
 */


import java.io.*;
/**
 * A Class to keep details of Employees, can read and write records
 * from an open RandomAccessFile
 */
public class EmpDataFile extends EmpData
{
	// instance variables - or data members
	// These are determined by the structure of the EmpData Class
	
	static final int NO_BYTES = 2;  // empno is an int 2 bytes
	static final int NAME_BYTES = 25;  // allowed length of employee name
	static final int SAL_BYTES = 8;    // salary is double - 8 bytes
	static final int DOB_BYTES = 10; //Date of birth is fixed to 10 bytes
	static final int PHONE_BYTES = 10; //Phone number fixed to 10 bytes
	static final int CUMSAL_BYTES = 8; //Cumulative salary file fixed to 8 bytes
	static final int CUMPROV_BYTES = 8; // cumulative field fixed to 8 bytes
	static final int CUMTAX_BYTES = 8; //Cumulative double field fixed to 8 bytes
	
	// Calculate length of a record - used to seek to correct position
	// in the data file.
	static final int RECORD_BYTES = NO_BYTES 
	                              + NAME_BYTES 
	                              + SAL_BYTES+DOB_BYTES+PHONE_BYTES+CUMSAL_BYTES+CUMPROV_BYTES+CUMTAX_BYTES;
	
	/**
	 * No argument Constructor, calls EmpData constructor
	 * super = a call to the super class constructor
	 */
	public EmpDataFile()
	{	// example of inheritance where EmpDataFile inherits EmpData's parameters
	    super();
	    super.setempname( fixLength(getempname(), NAME_BYTES));
	    super.ContactNo = fixLength(super.ContactNo, PHONE_BYTES);
	    super.DOB = fixLength(super.DOB,DOB_BYTES);
	}
	/**
	 * Constructor for objects of class EmpData
	 */
	public EmpDataFile(int no, String name, double sal, String dob, String Phone, double Csal, double CPF, double Ctax)
	{	//parameters of the constructor
	  super(no , name, sal, dob, Phone, Csal, CPF, Ctax);
	  super.setempname( fixLength(name, NAME_BYTES));
	  super.ContactNo = fixLength(Phone, PHONE_BYTES);
	  super.DOB = fixLength(dob,DOB_BYTES);
	}
	/**
	 * Method to add item to data file
	 * 
	 * @parameter - the file to add to - must be open for writing
	 * @paramwter - the object to be written into the file
	 * @parameter - position to add the record in the file
	 * @throws IOException if unable to complete the write operation
	 */	
	
	public static void writeRecord( RandomAccessFile theFile, 
	                                EmpDataFile theEmp, 
	                                long posToAdd ) throws IOException // an IO Exception is thrown if either the read or write fails and cought
	{ // this method of IO Error capture is implemented throughtout the program
      theFile.seek(posToAdd);
     
      theFile.writeInt(theEmp.getempno());
      theFile.writeBytes(theEmp.getempname());
      theFile.writeDouble(theEmp.getbasicsal());
      theFile.writeBytes(theEmp.DOB);
      theFile.writeBytes(theEmp.ContactNo);
      theFile.writeDouble(theEmp.cumSal);
      theFile.writeDouble(theEmp.cumPF);
      theFile.writeDouble(theEmp.cumTax);
    }
	  
	/**
	 * Method to read item from data file
	 * 
	 * @parameter - the file to add to - must be open
	 * @parameter - long the position to start reading in the file
	 * @returns an EmpDataFile object (null if not read)
	 * @throws IOException if unable to complete the read operation
	 */	
	public static EmpDataFile readRecord( RandomAccessFile theFile,
	                                         long posToRead ) throws IOException
	{
	  theFile.seek(posToRead);
	  
      // Remaining fields can be read directly
      int no = theFile.readInt();
      
      // Read a String by filling a byte array then converting
	  byte[] theEmp = new byte[NAME_BYTES];
      theFile.readFully(theEmp);
      String name = new String(theEmp);
      
      double sal = theFile.readDouble();
      
      byte[] theEmpDOB = new byte[DOB_BYTES];
      theFile.readFully(theEmpDOB);
      String dob = new String(theEmpDOB);
      
      byte[] theEmpPhone = new byte[PHONE_BYTES];
      theFile.readFully(theEmpPhone);
      String phone = new String(theEmpPhone);
      
      
      double Csal = theFile.readDouble();
      double CPF = theFile.readDouble();
      double CTAX = theFile.readDouble();
     
      // construct and return a EmpDataFile object
      return new EmpDataFile(no, name, sal, dob,phone,Csal,CPF,CTAX);
      
    }/** 
	 * Method to fix length of title String
	 * 
	 * @parameter - title - the original String
	 * @parameter - size - the fixed length size in bytes
	 * @return a String of exactly size bytes
	 */
    public static String fixLength(String name, int size)
    {
      StringBuffer sb = new StringBuffer(name);
      sb.setLength(size);
      return sb.toString();
    }
	/**
	 * Method to return record length
	 * 
	 * @return length of a EmpDataFile object in bytes
	 */
    public static int length()
    {
      return RECORD_BYTES;
    }
    
}