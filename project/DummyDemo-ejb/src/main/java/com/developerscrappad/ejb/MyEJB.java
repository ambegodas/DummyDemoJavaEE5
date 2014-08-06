package com.developerscrappad.ejb;

import javax.ejb.Stateless;

import com.developerscrappad.intf.MyEJBLocal;
import com.developerscrappad.intf.MyEJBRemote;

@Stateless
public class MyEJB implements MyEJBLocal,MyEJBRemote{

	@Override
	public String testRemoteInterface() {
		// TODO Auto-generated method stub
		return "Remote Interface Accessed";
	}

	@Override
	public String testLocalInterface() {
		// TODO Auto-generated method stub
		return "Local Interface Accessed";
	}


}
