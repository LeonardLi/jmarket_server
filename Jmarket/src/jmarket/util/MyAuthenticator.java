package jmarket.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * ����������֤��
 * @author Jenson
 *
 */
public class MyAuthenticator extends Authenticator{
		private String userName=null;   
	    private String password=null;   
	        
	    public MyAuthenticator(){   
	    } 
	    
	    public MyAuthenticator(String username, String password) {    
	        this.userName = username;    
	        this.password = password;    
	    }    
	    
	    protected PasswordAuthentication getPasswordAuthentication(){   
	        return new PasswordAuthentication(userName, password);   
	    }   
}
