package com.developerscrappad.itest;
 
import com.developerscrappad.intf.IBMPFacadeRemote;
import com.developerscrappad.intf.ICMPFacadeRemote;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
 
public class MDBIntegrationTest {
 
    private Properties env;
    private Context ctx;
 
    public MDBIntegrationTest() {
		//This is specific to Glassfish, please change the environment variables for different app server accordingly.
        env = new Properties();
        env.setProperty( "java.naming.factory.initial", "com.sun.enterprise.naming.impl.SerialInitContextFactory" );
        env.setProperty( "java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl" );
        env.setProperty( "java.naming.factory.url.pkgs", "com.sun.enterprise.naming" );
        env.setProperty( "org.omg.CORBA.ORBInitialHost", "localhost" );
        env.setProperty( "org.omg.CORBA.ORBInitialPort", "3700" );
        env.setProperty( Context.PROVIDER_URL, "iiop://127.0.0.1:3700" );
    }
 
    @BeforeClass
    public static void setUpClass() {
    }
 
    @AfterClass
    public static void tearDownClass() {
    }
 
    @Before
    public void setUp() throws Exception {
        ctx = new InitialContext( env );
    }
 
    @After
    public void tearDown() throws Exception {
        IBMPFacadeRemote bmpFacade = ( IBMPFacadeRemote ) ctx.lookup( IBMPFacadeRemote.JNDI_NAME );
        bmpFacade.cleanupTables();
    }
 
    @Test
    public void testMDB() {
        try {
            sendJMSMessageToQueue( "mdbcontents" );
            Thread.sleep( 10000 );
 
            ICMPFacadeRemote cmpFacade = ( ICMPFacadeRemote ) ctx.lookup( ICMPFacadeRemote.JNDI_NAME );
 
            try {
                cmpFacade.findAppTable1ByContents( "Hello mdbcontents" );
            } catch ( Exception ex ) {
                fail();
            }
        } catch ( Exception ex ) {
            ex.printStackTrace();
            fail();
        }
    }
 
    private Message createJMSMessageForjmsQueue( Session session, String messageData ) throws JMSException {
        // TODO create and populate message to send
        TextMessage tm = session.createTextMessage();
        tm.setText( messageData );
 
        return tm;
    }
 
    private void sendJMSMessageToQueue( String messageData ) throws NamingException, JMSException {
        ConnectionFactory cf = ( ConnectionFactory ) ctx.lookup( "jms/TestQueueConnectionFactory" );
        Connection conn = null;
        Session s = null;
 
        try {
            conn = cf.createConnection();
            s = conn.createSession( false, s.AUTO_ACKNOWLEDGE );
            Destination destination = ( Destination ) ctx.lookup( "jms/TestQueue" );
            MessageProducer mp = s.createProducer( destination );
            mp.send( createJMSMessageForjmsQueue( s, messageData ) );
        } finally {
            if ( s != null ) {
                try {
                    s.close();
                } catch ( JMSException e ) {
                    Logger.getLogger( this.getClass().getName() ).log( Level.WARNING, "Cannot close session", e );
                }
            }
            if ( conn != null ) {
                conn.close();
            }
        }
    }
}
