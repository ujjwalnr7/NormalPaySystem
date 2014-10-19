import java.io.*;
public class TransDataFile extends TransData
{
	static final int RECORD_BYTES = 15;
	/**
	 * No argument Constructor, calls TransData constructor
	 * super = a call to the super class constructor
	 */
	public TransDataFile()
	{
		super();
	}
	
	/**
	 * Constructor for objects of class TransData
	 */
	
	public TransDataFile(int empno, byte halfdays , byte  sickleave, byte casualleave, double monthlysalary)// parameters for the constructor
	{
	  super(empno , halfdays, sickleave, casualleave, monthlysalary);
	}

	// Method to write items to the file and throw an error if there is an IO Exception
	public static void writeRecord( RandomAccessFile theFile, 
	                                TransDataFile theTrans, 
	                                long posToAdd ) throws IOException
	{
      theFile.seek(posToAdd); 
      theFile.writeInt(theTrans.empno);
      theFile.writeByte(theTrans.halfdays);
      theFile.writeByte(theTrans.sickleave);
      theFile.writeByte(theTrans.casualleave);
      theFile.writeDouble(theTrans.monthsalary);
    }
	  
	/**
	 * Method to read item from data file
	 * 
	 * @param the file to add to - must be open
	 * @param long the position to start reading in the file
	 * @return a TransDataFile object (null if not read)
	 * @throws IOException if unable to complete the read operation
	 */	
	public static TransDataFile readRecord( RandomAccessFile theFile,
	                                         long posToRead ) throws IOException
	{
	  theFile.seek(posToRead);
	  
      // Remaining fields can be read directly
      int no = theFile.readInt();
      byte hd = theFile.readByte();
      byte sl = theFile.readByte();
      byte cl = theFile.readByte();
      
      double ms = theFile.readDouble();
      // construct and return a TransDataFile object
      return new TransDataFile(no, hd, sl, cl,ms);
      
    }
    
    public static int length()
    {
      return RECORD_BYTES;
    }
    
}
