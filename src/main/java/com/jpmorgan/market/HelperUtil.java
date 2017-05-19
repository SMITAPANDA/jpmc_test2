package com.jpmorgan.market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class HelperUtil 
{
	final static Logger LOGGER = Logger.getLogger(HelperUtil.class);
	
	public enum SaleType 
	{
		/* Buy */
		B,
		/* Sell */
		S
	}
	
	/**
	 * 
	 * @param items
	 */
	public static void printEntityList(ArrayList<Item> items)
	{
		LOGGER.info("==================================================Entity List ================================================================");
		LOGGER.info("Entity, Buy/Sell, AgreedFx, Currency, Instruction Date , Settlement Date, Actual Settlement Date, Units, Price per Unit, Rank");
		LOGGER.info("==============================================================================================================================");
		for (int i =0; i <items.size(); i ++)
		{
			Item itm = items.get(i);
			LOGGER.info(itm.toString());
		}
	}
	
	/**
	 * Print Daily Report for incoming and outgoing transactions
	 * @param items
	 */
	public static void printDailySummary(ArrayList<Item> items) 
	{
		HashMap<Date,DailyReport> reportSummary = new HashMap<Date,DailyReport>();
		for (int i =0; items!=null && i <items.size(); i ++)
		{
			Item itm = items.get(i);
			DailyReport report =reportSummary.get(itm.getActualSettlementDate());
			if (report==null)
			{
				report = new DailyReport(0.0f, 0.0f);
			}
			report.incrementSales(itm);
			reportSummary.put(itm.getActualSettlementDate(), report);
		}
		
		LOGGER.info("=============Daily Report ========================");
		Iterator<Entry<Date, DailyReport>> itr = reportSummary.entrySet().iterator();
		while(itr.hasNext())
		{
			Entry<Date, DailyReport> value =itr.next();
			LOGGER.info("Date:"+value.getKey()+"  Incoming transaction:"+ value.getValue().getIncomingTransaction()+" USD"
			+" Outgoing transaction:"+ value.getValue().getOutgoingtTransaction()+" USD");
		}
		LOGGER.info("===========End of Report=======================");
	}
	
	/**
	 * Sort by total price and then assign Rank
	 * @param items
	 */
	public static void sortAndRankCommodity(ArrayList<Item> items)
	{
		Collections.sort(items, new Comparator<Item>() 
		{
			@Override
		    public int compare(Item itm1, Item itm2) 
		    {				
				if (itm1.getAgreedFx()*itm1.getUnitCount()*itm1.getUnitPrice() 
						== itm2.getAgreedFx()*itm2.getUnitCount()*itm2.getUnitPrice())
				{
					return 0;
				}
				else if (itm1.getAgreedFx()*itm1.getUnitCount()*itm1.getUnitPrice() 
						> itm2.getAgreedFx()*itm2.getUnitCount()*itm2.getUnitPrice())
				{
					return -1;
				}
				else
				{
					return 1;
				}
				
			}
		});
		
		int currentOutgoingRank = 1;
		int currentIncomingRank = 1;
		for(int i =0; i <items.size();i++)
		{
			if (items.get(i).getSaleType() == SaleType.B)
			{
				items.get(i).setRank(currentOutgoingRank);
				currentOutgoingRank++;
			}
			else
			{
				items.get(i).setRank(currentIncomingRank);
				currentIncomingRank++;
			}
		}
		printEntityList(items);
	}

	/**
	 * Reset Rank for all items
	 * @param items
	 */
	public static void resetCommodityRanking(ArrayList<Item> items) 
	{
		for(int i =0; i <items.size();i++)
		{
			items.get(i).setRank(-1);
		}
	}
		
	/**
	 * This method Ranks all the Commodity without Sorting
	 * @param items
	 */
	public static void rankCommodity(ArrayList<Item> items) 
	{
		int maxOutgoingRank =0;
		int maxIncomingRank =0;
		for(int i =0; i <items.size();i++)
		{
			if (items.get(i).getSaleType() == SaleType.B)
			{
				maxOutgoingRank++;
			}
			else
			{
				maxIncomingRank++;
			}
			
		}
		
		for (int i =0; i <items.size();i++)
		{
			int count=0;
			for (int j=0; j<items.size();j++)
			{
				if (items.get(i).getSaleType() == items.get(j).getSaleType() && i!=j)
				{
					float outerItemTotal = items.get(i).getAgreedFx() * items.get(i).getUnitCount()* items.get(i).getUnitPrice();
					float innerItemTotal = items.get(j).getAgreedFx() * items.get(j).getUnitCount()* items.get(j).getUnitPrice();
					if (innerItemTotal < outerItemTotal 
							|| (innerItemTotal == outerItemTotal && items.get(j).getRank() !=-1))
					{
						count++;
					}			
				}
			}
			if (items.get(i).getSaleType() == SaleType.B)
			{
				items.get(i).setRank(maxOutgoingRank-count);
			}
			else
			{
				items.get(i).setRank(maxIncomingRank-count);
			}
		}
		printEntityList(items);
	}
}
