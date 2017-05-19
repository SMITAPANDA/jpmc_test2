/**
 * 
 */
package com.jpmorgan.market;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jpmorgan.market.HelperUtil.SaleType;

/**
 * Commodity Item
 * @author Smita Panda
 */
public class Item 
{
	/**
	 *  Financial entity whose shares are to be bought or sold 
	 */
	private String entityName;

	/**
	 * SalesType of the Commodity  (i.e. B For Buy and S for Sell) 
	 */
	private SaleType saleType;
	
	/**
	 * Agreed Fedex
	 */
	private float agreedFx;
	
	/**
	* Currency Type
	*/
	private String currencyCode;
	
	/**
	 *  Date on which the instruction was sent to JP Morgan by various clients
	 */
	private Date  instructionDate;
	
	/**
	 *  The date on which the client wished for the instruction to be settled with respect to Instruction Date
	 */
	private Date  settlementDate;
	
	/**
	 * Actual Settlement Date as per Working Day for the week 
	 */
	private Date actualSettlementDate;
	
	/**
	 * Number of units
	 */
	private int unitCount;
	
	/**
	 * Per Unit Type
	 */
	private float unitPrice;
	
	/**
	 *  rank of this commodity
	 */
	private int rank = -1;
	

	/**
	 * Default constructor
	 * @param entityName
	 * @param saleType
	 * @param agreedFx
	 * @param currency
	 * @param instructionDate
	 * @param settlementDate
	 * @param unitCount
	 * @param unitPrice
	 */
	public Item(String entityName, SaleType saleType, float agreedFx, String currencyCode, String instructionDate,
			String settlementDate, int unitCount, float unitPrice) 
	{
		this.entityName = entityName;
		this.saleType = saleType;
		this.agreedFx = agreedFx;
		this.currencyCode = currencyCode;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		try
		{
			this.instructionDate = sdf.parse(instructionDate);
			this.settlementDate = sdf.parse(settlementDate);
		}
		catch(Exception err)
		{
			//Ignore parsing error
		}
		this.unitCount = unitCount;
		this.unitPrice = unitPrice;
		intSettlementDate();
	}
	
	/**
	 * Get Rank of this commodity
	 * @return
	 */
	public int getRank() 
	{
		return rank;
	}
	
	/**
	 * Set Rank of this commodity
	 * @return
	 */
	public void setRank(int rank) 
	{
		this.rank = rank;
	}

	/**
	 * Getter for EntityName
	 * @return
	 */
	public String getEntityName() 
	{
		return entityName;
	}

	/**
	 * SEtter for EntityName
	 * @param entityName
	 */
	public void setEntityName(String entityName) 
	{
		this.entityName = entityName;
	}

	/**
	 * Getter for SalesType
	 * @return
	 */
	public SaleType getSaleType() 
	{
		return saleType;
	}

	/**
	 * Setter for Sales Type
	 * @param saleType
	 */
	public void setSaleType(SaleType saleType) 
	{
		this.saleType = saleType;
	}

	/**
	 * Getter for Agreed Fedex rate exchange
	 * @return
	 */
	public float getAgreedFx() 
	{
		return agreedFx;
	}

	/**
	 * Setter for Agreed Fedex rate exchange
	 * @param agreedFx
	 */
	public void setAgreedFx(float agreedFx) 
	{
		this.agreedFx = agreedFx;
	}

	/**
	 * Getter for CurrencyCode
	 * @return
	 */
	public String getCurrencyCode() 
	{
		return currencyCode;
	}

	/**
	 * Setter for CurrencyCode
	 * @param currencyCode
	 */
	public void setCurrencyCode(String currencyCode) 
	{
		this.currencyCode = currencyCode;
	}

	/**
	 * Getter for Instruction Date
	 * @return
	 */
	public Date getInstructionDate() 
	{
		return instructionDate;
	}

	/**
	 * Setter for Instruction Date
	 * @param instructionDate
	 */
	public void setInstructionDate(Date instructionDate) 
	{
		this.instructionDate = instructionDate;
	}

	/**
	 * Getter for SettlementDate
	 * @return
	 */
	public Date getSettlementDate() 
	{
		return settlementDate;
	}

	/**
	 * Setter for Settlement Date
	 * @param settlementDate
	 */
	public void setSettlementDate(Date settlementDate) 
	{
		this.settlementDate = settlementDate;
	}

	/**
	 * Getter for Actual SettlementDate as per WeekDay
	 * @return
	 */
	public Date getActualSettlementDate() 
	{
		return actualSettlementDate;
	}

	/**
	 * Setter for Actual SettlementDate as per WeekDay
	 * @param actualSettlementDate
	 */
	public void setActualSettlementDate(Date actualSettlementDate) 
	{
		this.actualSettlementDate = actualSettlementDate;
	}

	/**
	 * Getter for number of Units
	 * @return
	 */
	public int getUnitCount() {
		return unitCount;
	}

	/**
	 * Setter for number of units
	 * @param unitCount
	 */
	public void setUnitCount(int unitCount) {
		this.unitCount = unitCount;
	}

	/**
	 * Getter for per unit price
	 * @return
	 */
	public float getUnitPrice() {
		return unitPrice;
	}

	/**
	 * Setter for Per unit price
	 * @param unitPrice
	 */
	public void setUnitPrice(float unitPrice) 
	{
		this.unitPrice = unitPrice;
	}
	
	/**
	 * Actual Settlement Date as per the working dates based on CurrencyCode
	 */
	private void intSettlementDate()
	{
		if (settlementDate!=null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(settlementDate);
			int dayOftheWeek = cal.get(Calendar.DAY_OF_WEEK);
			switch (dayOftheWeek)
			{
				case Calendar.SUNDAY:
					if (! "AED".equalsIgnoreCase(currencyCode) && !"SAR".equalsIgnoreCase(currencyCode))
					{
						cal.add(Calendar.DATE, 1);
					}
					actualSettlementDate = cal.getTime();
					break;
				case Calendar.FRIDAY:
					if ("AED".equalsIgnoreCase(currencyCode) || "SAR".equalsIgnoreCase(currencyCode))
					{
						cal.add(Calendar.DATE, 2);
					}
					actualSettlementDate = cal.getTime();
					break;
				case Calendar.SATURDAY:
					if ("AED".equalsIgnoreCase(currencyCode) || "SAR".equalsIgnoreCase(currencyCode))
					{
						cal.add(Calendar.DATE, 1);
					}
					else
					{
						cal.add(Calendar.DATE, 2);
					}
					actualSettlementDate = cal.getTime();
					break;
				default:
					actualSettlementDate = settlementDate;
			}			
		}
	}

	@Override
	public String toString() 
	{
		return "Item ["+entityName + ", " + saleType + ", " + agreedFx
				+ ", " + currencyCode + ", " + instructionDate + ", "
				+ settlementDate + ", " + actualSettlementDate + ", " + unitCount
				+ ", " + unitPrice + ", " + rank + "]";
	}
	
	
}


