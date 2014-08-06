package com.developerscrappad.ejb;

import com.developerscrappad.SomeNeededMethods;
import com.developerscrappad.entity.AppTable1Model;
import com.developerscrappad.entity.AppTable2Model;
import com.developerscrappad.entity.AppTable3Model;
import com.developerscrappad.foundation.intf.INames;
import com.developerscrappad.helper.SomeHelper;
import com.developerscrappad.intf.ICMPFacadeLocal;
import com.developerscrappad.intf.ICMPFacadeRemote;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(name = "CMPFacade", mappedName = "ejb/CMPFacade" )
@Remote(ICMPFacadeRemote.class)
@Local(ICMPFacadeLocal.class)
public class CMPFacade implements ICMPFacadeLocal, ICMPFacadeRemote {

    private static final long serialVersionUID = -6300266925518305055L;

    @Resource
    private EJBContext ctx;

    @PersistenceContext( unitName = INames.PU_NAME )
    private EntityManager em;

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public int performAddition( int value1, int value2 ) {
        SomeHelper h = new SomeHelper();

        return h.addition( value2, value2 );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public String printHelloMessage( String msg ) {
        SomeNeededMethods smn = new SomeNeededMethods();

        return smn.someMethod( msg );
    }

    public void executeCMPProcess( AppTable1Model model1, AppTable2Model model2, AppTable3Model model3 ) throws Exception {
        try {
            em.persist( model1 );
            em.persist( model2 );
            em.persist( model3 );
            em.flush();
        } catch ( Exception ex ) {
            ctx.setRollbackOnly();
            throw ex;
        }
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public AppTable1Model findAppTable1ByContents( String contents ) {
        Query q = em.createNamedQuery( "AppTable1Model.findByContents" );
        q.setParameter( "contents", contents );

        return ( AppTable1Model ) q.getSingleResult();
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public AppTable2Model findAppTable2ByContents( String contents ) {
        Query q = em.createNamedQuery( "AppTable2Model.findByContents" );
        q.setParameter( "contents", contents );

        return ( AppTable2Model ) q.getSingleResult();
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public AppTable3Model findAppTable3ByContents( String contents ) {
        Query q = em.createNamedQuery( "AppTable3Model.findByContents" );
        q.setParameter( "contents", contents );

        return ( AppTable3Model ) q.getSingleResult();
    }

    public void cleanupTables() throws Exception {
        String sql1 = "TRUNCATE APP_TABLE1";
        String sql2 = "TRUNCATE APP_TABLE2";
        String sql3 = "TRUNCATE APP_TABLE3";

        Query q = em.createNativeQuery( sql1 );
        q.executeUpdate();
        em.flush();

        q = em.createNativeQuery( sql2 );
        q.executeUpdate();
        em.flush();

        q = em.createNativeQuery( sql3 );
        q.executeUpdate();
        em.flush();
    }
}
