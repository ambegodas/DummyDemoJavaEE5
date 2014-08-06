package com.developerscrappad.intf;

import javax.ejb.Remote;

@Remote
public interface MyEJBRemote {

	public String testRemoteInterface();
}
