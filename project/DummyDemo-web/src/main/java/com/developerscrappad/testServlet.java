package com.developerscrappad;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



import com.developerscrappad.intf.ICMPFacadeLocal;
import com.developerscrappad.intf.ICMPFacadeRemote;
import com.developerscrappad.intf.MyEJBLocal;


public class testServlet extends HttpServlet {
	

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("user").equals("ejb")){
			PrintWriter pwPrintWriter = response.getWriter();	
			 MyEJBLocal bean = null;
		     bean = ClientUtility.doLookup();
		     pwPrintWriter.write(bean.testLocalInterface());
		     
		} else if (request.getParameter("user").equals("mdb")){
			
			QueueConnection queueConnection=null;
			QueueConnectionFactory queueFactory =null;
			QueueSession queueSession =null;
			Queue queue =null;
			QueueSender queueSender =null;
			TextMessage message =null;
		   
			try {
		        
	            InitialContext context = new InitialContext();
	            queueFactory = (QueueConnectionFactory)context.lookup("/ConnectionFactory");
	            queueConnection = queueFactory.createQueueConnection();
	            queueSession = queueConnection.createQueueSession
	                                         (false,javax.jms.Session.AUTO_ACKNOWLEDGE);
	            queue = (Queue)context.lookup("/queue/MyQueue");
	            queueSender = queueSession.createSender(queue);
	            message = queueSession.createTextMessage();
	            
	            System.out.println("Configuration For Bean Done");
	            
	            String resultMessage = "Test Message";
	            message.setText(resultMessage);
	            queueSender.send(message);

	        } catch (Exception ex) {
	            System.out.println("Error:"+ex.getMessage());
	        }
	    }
			
		}
		 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


}
