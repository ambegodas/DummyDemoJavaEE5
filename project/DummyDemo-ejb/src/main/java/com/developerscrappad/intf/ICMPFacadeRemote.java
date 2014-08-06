package com.developerscrappad.intf;

import javax.ejb.Remote;

@Remote
public interface ICMPFacadeRemote extends ICMPFacadeLocal {

    public static final String JNDI_NAME = "ejb/CMPFacade";
}
