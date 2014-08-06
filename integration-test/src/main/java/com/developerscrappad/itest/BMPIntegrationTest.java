package com.developerscrappad.itest;
 
import com.developerscrappad.entity.AppTable1Model;
import com.developerscrappad.entity.AppTable2Model;
import com.developerscrappad.entity.AppTable3Model;
import com.developerscrappad.intf.IBMPFacadeRemote;
import com.developerscrappad.intf.ICMPFacadeRemote;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
 
public class BMPIntegrationTest {
 
    private Properties env;
    private Context ctx;
 
    public BMPIntegrationTest() {
		//This is specific to Glassfish, please change the environment variables for different app server accordingly.
        env = new Properties();
        env.setProperty( "java.naming.factory.initial", "com.sun.enterprise.naming.impl.SerialInitContextFactory" );
        env.setProperty( "java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl" );
        env.setProperty( "java.naming.factory.url.pkgs", "com.sun.enterprise.naming" );
        env.setProperty( "org.omg.CORBA.ORBInitialHost", "localhost" );
        env.setProperty( "org.omg.CORBA.ORBInitialPort", "3700" );
        env.setProperty( Context.PROVIDER_URL, "iiop://127.0.0.1:3700" );
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
    public void testBMPProcessCommit() throws Exception {
        AppTable1Model model1 = new AppTable1Model();
        model1.setContents( "contents1" );
 
        AppTable2Model model2 = new AppTable2Model();
        model2.setContents( "contents2" );
 
        AppTable3Model model3 = new AppTable3Model();
        model3.setContents( "contents3" );
 
        IBMPFacadeRemote bmpFacade = ( IBMPFacadeRemote ) ctx.lookup( IBMPFacadeRemote.JNDI_NAME );
        bmpFacade.executeBMPProcess( model1, model2, model3 );
 
        ICMPFacadeRemote cmpFacade = ( ICMPFacadeRemote ) ctx.lookup( ICMPFacadeRemote.JNDI_NAME );
        model1 = cmpFacade.findAppTable1ByContents( "contents1" );
        assertEquals( "contents1", model1.getContents() );
 
        model2 = cmpFacade.findAppTable2ByContents( "contents2" );
        assertEquals( "contents2", model2.getContents() );
 
        model3 = cmpFacade.findAppTable3ByContents( "contents3" );
        assertEquals( "contents3", model3.getContents() );
    }
 
    @Test
    public void testBMPProcessRollback() throws Exception {
        AppTable1Model model1 = new AppTable1Model();
        model1.setContents( "contents1" );
 
        AppTable2Model model2 = new AppTable2Model();
        model2.setContents( "contents2" );
 
        AppTable3Model model3 = new AppTable3Model();
        model3.setContents( "contents3" );
 
        IBMPFacadeRemote bmpFacade = ( IBMPFacadeRemote ) ctx.lookup( IBMPFacadeRemote.JNDI_NAME );
        bmpFacade.executeBMPProcess( model1, model2, model3 );
 
        model1 = new AppTable1Model();
        model1.setContents( "contents1.1" );
 
        model2 = new AppTable2Model();
        model2.setContents( "contents2.2" );
 
        model3 = new AppTable3Model();
        model3.setContents( "contents3" );
 
        try {
            bmpFacade.executeBMPProcess( model1, model2, model3 );
            fail();
        } catch ( Exception ex ) {
            //absorb
        }
 
        ICMPFacadeRemote cmpFacade = ( ICMPFacadeRemote ) ctx.lookup( ICMPFacadeRemote.JNDI_NAME );
 
        try {
            model1 = cmpFacade.findAppTable1ByContents( "contents1.1" );
            fail();
        } catch ( Exception ex ) {
            //absorb
        }
 
        model1 = cmpFacade.findAppTable1ByContents( "contents1" );
        assertEquals( "contents1", model1.getContents() );
 
        try {
            model2 = cmpFacade.findAppTable2ByContents( "contents2.2" );
            fail();
        } catch ( Exception ex ) {
            //absorb
        }
 
        model2 = cmpFacade.findAppTable2ByContents( "contents2" );
        assertEquals( "contents2", model2.getContents() );
 
        model3 = cmpFacade.findAppTable3ByContents( "contents3" );
        assertEquals( "contents3", model3.getContents() );
    }
}
