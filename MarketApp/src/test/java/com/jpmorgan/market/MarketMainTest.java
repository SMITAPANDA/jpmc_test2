package com.jpmorgan.market;

import org.junit.Assert;
import org.junit.Test;

public class MarketMainTest 
{
	
	public void testLengthOfTheUniqueKey() 
	{

		MarketMain testObj = new MarketMain();
		Assert.assertEquals(36, testObj.toString());

	}

}
