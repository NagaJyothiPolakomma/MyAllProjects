package com.vyshu;

import org.apache.synapse.MessageContext; 
import org.apache.synapse.mediators.AbstractMediator;



public class Singleton_class extends AbstractMediator { 

	private static  Singleton_class Sng;
		 private  Singleton_class()
		{
			
		}
	    public static  Singleton_class instatnce()
	    {
	    	if(Sng==null)
	    	{
	    	   synchronized (Singleton_class.class) {
	    		   if(Sng==null)                             //if two if conditions are there it is called double checking locking in java 
	    		   {
	    	       Sng= new  Singleton_class();            //synchronized means if two threads enter second thread will wait until the first thread execution will complete after that the second thread will enter.
	    		   }
	    	   }
	    	}
			return Sng;
	    }
	@Override
	public boolean mediate(MessageContext arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	}
		

	
