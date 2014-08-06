package com.developerscrappad.intf;

import javax.ejb.Remote;

@Remote
public interface IBMPFacadeRemote extends IBMPFacadeLocal {

    public static final String JNDI_NAME = "ejb/BMPFacade";
}
