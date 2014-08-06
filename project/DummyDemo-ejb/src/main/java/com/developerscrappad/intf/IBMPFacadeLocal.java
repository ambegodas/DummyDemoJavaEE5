package com.developerscrappad.intf;

import com.developerscrappad.entity.AppTable1Model;
import com.developerscrappad.entity.AppTable2Model;
import com.developerscrappad.entity.AppTable3Model;
import java.io.Serializable;
import javax.ejb.Local;

@Local
public interface IBMPFacadeLocal extends Serializable {

    public void executeBMPProcess( AppTable1Model model1, AppTable2Model model2, AppTable3Model model3 ) throws Exception;

    public void cleanupTables() throws Exception;
}
