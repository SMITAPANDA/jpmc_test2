package com.jpmorgan.market;

import java.util.ArrayList;

import com.jpmorgan.market.HelperUtil.SaleType;

public class MarketMain {

	public static void main(String[] args) 
	{
		ArrayList<Item> items = new ArrayList<>();
		items.add(new Item("foo", SaleType.B, 0.50f, "USD", "01/01/2016", "01/01/2016", 200, 100.25f));
		items.add(new Item("bar", SaleType.S, 0.22f, "AED", "05/01/2016", "07/01/2016", 450, 150.5f));
		items.add(new Item("test1", SaleType.B, 0.22f, "SAR", "03/01/2016", "07/01/2017", 200, 150.5f));
		items.add(new Item("test2", SaleType.B, 0.22f, "USD", "03/01/2016", "07/01/2017", 200, 150.5f));
		items.add(new Item("test3", SaleType.B, 0.22f, "USD", "02/01/2016", "07/01/2017", 200, 150.5f));
		items.add(new Item("test4", SaleType.B, 0.22f, "USD", "04/01/2016", "04/01/2017", 300, 150.5f));
		items.add(new Item("test4", SaleType.S, 0.22f, "USD", "04/01/2016", "04/01/2017", 33, 150.5f));
		
	
		HelperUtil.printDailySummary(items);
		HelperUtil.rankCommodity(items);
		
		HelperUtil.resetCommodityRanking(items);
		HelperUtil.sortAndRankCommodity(items);
		
	}

}
