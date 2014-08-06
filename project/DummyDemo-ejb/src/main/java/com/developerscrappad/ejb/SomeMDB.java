package com.developerscrappad.ejb;

import com.developerscrappad.SomeNeededMethods;
import com.developerscrappad.entity.AppTable1Model;
import com.developerscrappad.foundation.intf.INames;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@MessageDriven( name = "MyTestQueue", activationConfig = {
	@ActivationConfigProperty( propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
	@ActivationConfigProperty( propertyName = "destination", propertyValue = "queue/MyQueue" ),	
    @ActivationConfigProperty( propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge" )
    
} )
public class SomeMDB implements MessageListener {

    @PersistenceContext( unitName = INames.PU_NAME )
    private EntityManager em;

    @Override
    public void onMessage( Message message ) {
        TextMessage txtMsg = ( TextMessage ) message;

        try {
            if ( txtMsg.getText() == null ) {
                throw new EJBException( "Message is null..." );
            } else {
                SomeNeededMethods snm = new SomeNeededMethods();

                AppTable1Model model = new AppTable1Model();
                model.setContents( snm.someMethod( txtMsg.getText() ) );
                em.persist( model );
                System.out.print("MDB executed");
            }
        } catch ( JMSException ex ) {
            throw new EJBException( "Something wrong with message..." );
        }
    }
}
