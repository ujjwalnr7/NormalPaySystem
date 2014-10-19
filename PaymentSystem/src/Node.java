/**
 * @(#)Node.java
 *
 *
 * @Ujjwal Nair 
 * @version 1.00 2012/8/4
 */
// This class structures the nodes that hold the employee data

class Node 
{
    EmpData empdets; 
    Node next; 
    	
    // this is the constructor
    public Node(EmpData employee) 
    	{
        	setData(employee);
    		setNext(null);
    	} 
    
    public void setData(EmpData employee)
    	{      
  		  empdets = employee;
  		}	
    
    public void setNext(Node next) 
    	{ 
    		this.next = next; 
    	} 
    
    public EmpData getData() 
    	{ 
    		return empdets;
   	 	}
        
   	public Node getNext() 
   		{ 
   			return next; 
   		} 
}   	