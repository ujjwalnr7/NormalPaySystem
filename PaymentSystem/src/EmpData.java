
/**
 * @(#)empdata.java
 *
 * IDE used: JCreator v4.50
 * @ Ujjwal Nair 
 * @version 4.00 2012/8/4
 */


class EmpData //here the different objects of the class empdata are initialised
{
	protected int empno; // key field: employee number
	private String empname; //employee name
	private double basicsal; // basic salary
	public String DOB; // Date of Birth
	public String ContactNo;// Contact Number
	public double cumSal;// Cumulative Salary
	public double cumPF; // Cumulative Provident Fund
	public double cumTax; // Cumulative Tax
	
	
	public EmpData()
	{
		empno = 0;
		empname= null;
		basicsal = 0;
	}
	public EmpData(int no, String name, double sal, String dob, String Phone, double Csal, double CPF, double Ctax)
   	{ // above is the constructor of the class where all the parameters are passed
    	empno=no;
    	empname = name;
    	basicsal = sal;
    	DOB = dob;
    	ContactNo = Phone;
    	cumSal = Csal;
    	cumPF=CPF;
    	cumTax = Ctax;	
	}
    
    public void acceptdata()
    { // this method is called when the add employee option is chosen in the employee menu
    // the statements below are esentially input statements that obtain the user variables
    	empno= ibid.inputInt("\nEnter Employee number (only numeric characters): ");
     	empname = ibid.inputString("\nEnter Employee name: ");
		basicsal=ibid.inputDouble("\nEnter Basic Salary: ");
		DOB = ibid.inputString("\nEnter Date of Birth (DD/MM/YYYY): ");
		ContactNo=ibid.inputString("\nEnter employee contact number: ");
		cumSal=0;
		cumPF=0; // the last 3 fields are are not entered as they are cumulative fields that are hardcoded into the program 
		cumTax=0;
    }	
    
	/* all the following 
	 * methods are the set and get methods 
	 * for the private and protected variables
	 * used in the program: empno, basicsal and empname
	 */
	
	public int getempno()			
	{		
		return empno;
	}
	public String getempname()
	{
		return empname;
	}
	public  double getbasicsal()
	{
		return basicsal;
	}
	public void setempno(int eno)
	{
		empno = eno;

	}
	public void setempname(String ename)
	{
		empname = ename;

	}
	public void setbasicsal(double sal)
	{
		basicsal = sal;
	
	}
	
	/* this method converts all the data into strings to
	 * display all the data in a single line and so 
	 * trim the strings to the corrent set length
	 */
	
	public String toString()
	{
		return ( this.getempname().trim() + " - " 
		       + this.getempno() + " - "
		       + this.getbasicsal() + "-" + this.DOB.trim() + "-" + this.ContactNo.trim());
	}
}
