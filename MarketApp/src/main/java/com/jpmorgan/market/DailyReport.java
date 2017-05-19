package com.jpmorgan.market;

import com.jpmorgan.market.HelperUtil.SaleType;

public class DailyReport
{
    /**
     * SumTotal of all Outgoing transaction in USD
     */
	protected float outgoingtTransaction;

	/**
	 * SumTotal of all Incoming Transaction in USD
	 */
	protected float incomingTransaction;

	/**
	 * @param outgoingtTransaction
	 * @param incomingTransaction
	 */
	public DailyReport(float outgoingtTransaction, float incomingTransaction) 
	{
		this.outgoingtTransaction = outgoingtTransaction;
		this.incomingTransaction = incomingTransaction;
	}
	
	/**
	 * Getter for Outgoing Transactions
	 * @return
	 */
	public float getOutgoingtTransaction() 
	{
		return outgoingtTransaction;
	}

	/**
	 * Setter for Outgoing Transaction
	 * @param outgoingtTransaction
	 */
	public void setOutgoingtTransaction(float outgoingtTransaction) 
	{
		this.outgoingtTransaction = outgoingtTransaction;
	}

	/**
	 * Getter for allIncomingTransaction
	 * @return
	 */
	public float getIncomingTransaction() 
	{
		return incomingTransaction;
	}

	/**
	 * Setter for all Incoming Transaction
	 * @param incomingTransaction
	 */
	public void setIncomingTransaction(float incomingTransaction) 
	{
		this.incomingTransaction = incomingTransaction;
	}
	
	/**
	 * Increament the Sales Report
	 * @param itm
	 */
	public void incrementSales(Item itm)
	{
		if (itm.getSaleType()==SaleType.B)
		{
			outgoingtTransaction = outgoingtTransaction+ itm.getAgreedFx()*itm.getUnitCount()*itm.getUnitPrice();
		}
		else
		{
			incomingTransaction = incomingTransaction + itm.getAgreedFx()*itm.getUnitCount()*itm.getUnitPrice();
		}
	}
}
