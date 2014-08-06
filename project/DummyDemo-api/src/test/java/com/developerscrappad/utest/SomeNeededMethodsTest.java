package com.developerscrappad.utest;

import com.developerscrappad.SomeNeededMethods;
import org.junit.Test;
import static org.junit.Assert.*;

public class SomeNeededMethodsTest {
	
	public void testSomeMethod() {
        SomeNeededMethods m = new SomeNeededMethods();
        String result = m.someMethod( "Maven" );

        assertEquals( "Hello Maven", result );
    }

}
