package com.developerscrappad.ejb;

import com.developerscrappad.entity.AppTable1Model;
import com.developerscrappad.entity.AppTable2Model;
import com.developerscrappad.entity.AppTable3Model;
import com.developerscrappad.foundation.intf.INames;
import com.developerscrappad.intf.IBMPFacadeLocal;
import com.developerscrappad.intf.IBMPFacadeRemote;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

@Stateless( name = "BMPFacade", mappedName = "ejb/BMPFacade" )
@TransactionManagement( TransactionManagementType.BEAN )
public class BMPFacade implements IBMPFacadeLocal, IBMPFacadeRemote {

    private static final long serialVersionUID = -6907549768950041273L;
    @Resource
    private EJBContext ctx;
    @PersistenceContext( unitName = INames.PU_NAME )
    private EntityManager em;

    public void executeBMPProcess( AppTable1Model model1, AppTable2Model model2, AppTable3Model model3 ) throws Exception {

       UserTransaction utx = ctx.getUserTransaction();

        try {
            utx.begin();

            em.persist( model1 );
            em.persist( model2 );
            em.persist( model3 );
            em.flush();
            utx.commit();
        } catch ( Exception ex ) {
            utx.rollback();
            throw ex;
        }
    }

    public void cleanupTables() throws Exception {

        UserTransaction utx = ctx.getUserTransaction();

        try {
            utx.begin();

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

            utx.commit();
        } catch ( Exception ex ) {
            utx.rollback();
            throw ex;
        }
    }
}
