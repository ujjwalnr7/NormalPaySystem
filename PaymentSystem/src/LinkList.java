
 /* @(#)linklist.java
 *
 *
 * @Ujjwal Nair 
 * @version 1.00 2012/8/4
 */


class LinkList 
{ // the Head node is the first node in the linklist and is used in all subsequent methods
   private Node head; 
   public LinkList() 
   	{ 
   		setHead(null); 	
   	} // constructor 
   
   public void setHead(Node head) 
   	{
   		this.head = head;
   	} // setHead  
   
   public Node getHead() 
   	{
   		return head;
   	} // getHead 
 

// Purpose : add to linked list from the file


public void add(EmpDataFile empfile)
{
	EmpData emp= new EmpData(empfile.getempno(), empfile.getempname(), empfile.getbasicsal(), empfile.DOB, empfile.ContactNo, empfile.cumSal,empfile.cumPF, empfile.cumTax);
	Node newnode = new Node(emp);
    Node cur = null; // current node is represented by 'cur'
		if(getHead() == null)
	    	setHead(newnode);
	 	else
			{
				cur = getHead();
				//	ibid.output("head is : " + head);
				while (cur.getNext() !=null) 
					{cur = cur.getNext();}
				cur.setNext(newnode);
				newnode.setNext(null);
				//	ibid.output("Adding to end of list : " + emp.getempname());		
			}    
	
}
// Purpose: This method adds the nodes to the LinkedList in the order of their key fields: empno (employee number)

public void add(EmpData emp) 
{
	Node newnode = new Node(emp);
    
    if (getHead() == null)
   	    setHead(newnode);
 	
 	else if (emp.getempno() < getHead().getData().getempno())
      {
      	newnode.setNext(head); 
      		setHead(newnode);
      }
    
    else 
      {
      	Node tempo = head;
      	Node prev =tempo;
      	while(tempo.getData().getempno() < emp.getempno())
      		{    		
      			prev = tempo;
    			tempo = tempo.getNext();
    			if (tempo==null)
    				break;
    		}	
    			
    	if (tempo == null)   			
    		{
    			prev.setNext(newnode);
    			newnode.setNext(null);
    		}
    	
    	else
    		{
    		  prev.setNext(newnode);
    		  newnode.setNext(tempo); 
    		}
    	
     
      }	
         
}  
 


// Purpose: This method iteratively traverses the Linked List 

public void traverse() 
{
	 Node cur =getHead();
	 while(cur != null) 
	 	{
	 		ibid.output(cur.getData().toString());
	 		cur = cur.getNext();
	 	}
} 


// Purpose: Recursive Method for finding items in the Linked List
public int find(int target) 
{
 	  return find(getHead(), target);
}   
 
 
private int find(Node cur, int target)
{
	if(cur == null) 
		return 0;
	else if(cur.getData().getempno() == target) 
		{ 
			ibid.output(cur.getData().toString()); 
			return 1;
		} 
 	else {return find(cur.getNext(), target);}
} 
 		 

// Purpose: Delete the First Record with a matching Employee Number (Empno) 
// Post-condition: If a matching employee number is found that record is deleted shortening the Linked List
public void deleteFirst(int target)
{
	boolean found = false;
 	Node prev = head;
 	Node current = head.getNext();
 	if(head.getData().getempno() == target)
 	 	{
 	 	    found = true;
 	 		head = head.getNext();
 	 	}	
 	 else
 	 	{
 	 		while ( current !=null)
 	 			{
 	 				if (current.getData().getempno() == target)
 	 					{
          	   				found = true;
          	   				break;
          	   			} 
     	     		prev=current;	    
     	     		current = current.getNext();
     	     	}
 	        
 	        if (found == true)
 	        {
 	        	prev.setNext(current.getNext()) ;
				ibid.output("Item has been deleted"); 
 	        }
 	        else 
 	 	       ibid.output("Sorry no such item");
 	     } // deleteFirst
}

}