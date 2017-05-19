package com.jpmorgan.market;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.market.HelperUtil.SaleType;

public class HelperUtilTest 
{
	final static Logger LOGGER = Logger.getLogger(HelperUtilTest.class);
	
	@Before
	public void setUp()
	{
		LOGGER.info("JUnit Test Cases for MarketApp Starts...");
	}
	
	@After
	public void completed()
	{
		LOGGER.info("JUnit Test Cases for MarketApp Completed...");
	}
	
	@Test
	public void printDailySummaryCheck()
	{
		ArrayList<Item> items = new ArrayList<>();
		Random rand = new Random();
		
		items.add(new Item("foo", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "01/01/2016", "01/01/2016", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("bar", SaleType.S, rand.nextFloat()*(5 - 0), "AED", "05/01/2016", "07/01/2016", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test1", SaleType.B, rand.nextFloat()*(5 - 0), "SAR", "03/01/2016", "07/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test2", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "03/01/2016", "07/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test3", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "02/01/2016", "07/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test4", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "04/01/2016", "04/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test4", SaleType.S, rand.nextFloat()*(5 - 0), "USD", "04/01/2016", "04/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		
		HelperUtil.printDailySummary(items);
	}
	
	@Test
	public void printDailySummaryNullCheck()
	{		
		HelperUtil.printDailySummary(null);
	}

	@Test
	public void rankCommoditySanityCheck()
	{	
		ArrayList<Item> items = new ArrayList<>();
		Random rand = new Random();
		
		items.add(new Item("foo", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "01/01/2016", "01/01/2016", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("bar", SaleType.S, rand.nextFloat()*(5 - 0), "AED", "05/01/2016", "07/01/2016", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test1", SaleType.B, rand.nextFloat()*(5 - 0), "SAR", "03/01/2016", "07/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test2", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "03/01/2016", "07/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test3", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "02/01/2016", "07/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test4", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "04/01/2016", "04/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test4", SaleType.S, rand.nextFloat()*(5 - 0), "USD", "04/01/2016", "04/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		
		HelperUtil.rankCommodity(items);
		float[] totalBuyPrice = new float[items.size()] ;
		float[] totalSellPrice = new float[items.size()] ;
		Arrays.fill(totalBuyPrice, -1.0f);
		Arrays.fill(totalSellPrice, -1.0f);
		for (int i =0; i <items.size(); i ++)
		{
			Item itm =items.get(i);
			int currentRank =itm.getRank();
			float total = itm.getAgreedFx() * itm.getUnitCount()* itm.getUnitPrice();
			if (itm.getSaleType() == SaleType.S)
			{
				totalSellPrice[currentRank -1] = total;
			}
			else
			{
				totalBuyPrice[currentRank - 1] = total;
			}
		}
		for (int j =0;j < items.size() -1; j ++)
		{
			if ((totalBuyPrice[j] < totalBuyPrice[j+1])
					|| (totalSellPrice[j] < totalSellPrice[j+1]))
			{
				Assert.fail("Ranks are not correct");
			}
		}

	}
	
	@Test
	public void rankCommodityDuplicateCheck()
	{	
		ArrayList<Item> items = new ArrayList<>();
				
		Random rand = new Random();
		items.add(new Item("foo", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "01/01/2016", "01/01/2016", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("bar", SaleType.S, rand.nextFloat()*(5 - 0), "AED", "05/01/2016", "07/01/2016", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test1", SaleType.B, rand.nextFloat()*(5 - 0), "SAR", "03/01/2016", "07/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test2", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "03/01/2016", "07/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test3", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "02/01/2016", "07/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test4", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "04/01/2016", "04/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test4", SaleType.S, rand.nextFloat()*(5 - 0), "USD", "04/01/2016", "04/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		
		HelperUtil.rankCommodity(items);
		Map<Integer, Item> buyItems = new HashMap<Integer, Item>();
		Map<Integer, Item> sellItems = new HashMap<Integer, Item>();
		for (int i =0; i <items.size(); i ++)
		{
			Item itm =items.get(i);
			if ((itm.getSaleType() == SaleType.S && sellItems.get(itm.getRank())!=null)
					||(itm.getSaleType() == SaleType.B && buyItems.get(itm.getRank())!=null))
			{
				Assert.fail("Duplicate Record found for Same Rank");
			}
			else
			{
				if (itm.getSaleType() == SaleType.S)
				{
					sellItems.put(itm.getRank(), itm);
				}
				else
				{
					buyItems.put(itm.getRank(), itm);
				}
			}
		}
	}
	
	@Test
	public void sortAndRankCommoditySanityCheck()
	{
		ArrayList<Item> items = new ArrayList<>();
		Random rand = new Random();
		
		items.add(new Item("foo", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "01/01/2016", "01/01/2016", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("bar", SaleType.S, rand.nextFloat()*(5 - 0), "AED", "05/01/2016", "07/01/2016", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test1", SaleType.B, rand.nextFloat()*(5 - 0), "SAR", "03/01/2016", "07/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test2", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "03/01/2016", "07/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test3", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "02/01/2016", "07/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test4", SaleType.B, rand.nextFloat()*(5 - 0), "USD", "04/01/2016", "04/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));
		items.add(new Item("test4", SaleType.S, rand.nextFloat()*(5 - 0), "USD", "04/01/2016", "04/01/2017", rand.nextInt(500) + 1, 100.0f + rand.nextFloat()*(500 - 100)));

		
		HelperUtil.sortAndRankCommodity(items);
		
		int currentOutgoingRank = 1;
		int currentIncomingRank = 1;
		for(int i =0; i <items.size();i++)
		{
			if (items.get(i).getSaleType() == SaleType.B)
			{
				if (items.get(i).getRank()!= currentOutgoingRank)
				{
					Assert.fail("Sort And Rank failed...");
				}
				else
				{
					currentOutgoingRank++;
				}
			}
			else
			{
				if (items.get(i).getRank()!= currentIncomingRank)
				{
					Assert.fail("Sort And Rank failed...");
				}
				else
				{
					currentIncomingRank++;
				}
			}
		}
	}
	
	
}
