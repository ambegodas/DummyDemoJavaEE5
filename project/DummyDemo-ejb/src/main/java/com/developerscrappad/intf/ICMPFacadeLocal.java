package com.developerscrappad.intf;

import com.developerscrappad.entity.AppTable1Model;
import com.developerscrappad.entity.AppTable2Model;
import com.developerscrappad.entity.AppTable3Model;
import java.io.Serializable;
import javax.ejb.Local;

@Local
public interface ICMPFacadeLocal extends Serializable {

    public String printHelloMessage( String msg );

    public int performAddition( int value1, int value2 );

    public void executeCMPProcess( AppTable1Model model1, AppTable2Model model2, AppTable3Model model3 ) throws Exception;

    public AppTable1Model findAppTable1ByContents( String contents );

    public AppTable2Model findAppTable2ByContents( String contents );

    public AppTable3Model findAppTable3ByContents( String contents );

    public void cleanupTables() throws Exception;
}
