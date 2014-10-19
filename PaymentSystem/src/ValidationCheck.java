/**
 * @(#)ValidationCheck.java
 *
 *
 * @author Ujjwal Nair
 * @version 4.00 2012/8/21
 */


public class ValidationCheck 
{
		
		// this is a validation method for strings
		// it extracts the characters and then compares them against the correct ASCII values
		// this allows only numbers to be input into that particular string field
		public static boolean StringValidate (String Checker)
		{		
			char check;
			boolean valid=true;
			for (int i=0;i<Checker.length();i++)
			{
				check = Checker.charAt(i);
				if ((check>=65 && check<=90) || (check>=97 && check<=122) || (check==32))
				{
					valid=true;
				}
				else
				{
					valid = false;
					break;
				}
			}
			return valid;
		}
		
					
}