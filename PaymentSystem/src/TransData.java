/**
 * @(#)TransData.java
 *
 *
 * @Ujjwal Nair 
 * @version 1.00 2012/11/7
 */

	public class TransData
		{
			public int empno;
			public byte halfdays;
			public byte sickleave;
			public byte casualleave;
			public double monthsalary;	
	
	
			public TransData()
				{
					empno = 0;
					halfdays =0;
					sickleave=0;
					casualleave=0;
					monthsalary = 0;
				}
	
			public TransData(int no, byte hd, byte sl, byte cl, double ms)
   				{
				    empno=no;
				    halfdays=hd;
				    sickleave = sl;
				    casualleave = cl;
				    monthsalary = ms;
				}
    
			public void acceptdata()
			    {
			    	empno= ibid.inputInt("\nEnter Employee number : ");
					halfdays = ibid.inputByte("\nEnter number of half days : ");
					sickleave=ibid.inputByte("\nEnter number of sickleaves : ");
					casualleave = ibid.inputByte("\n Enter number of casual leaves:" );
			    }
		}   

