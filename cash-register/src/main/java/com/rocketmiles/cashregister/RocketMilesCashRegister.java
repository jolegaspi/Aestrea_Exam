package com.rocketmiles.cashregister;

import java.util.List;
import java.util.Map;

import com.rocketmiles.cashregister.constants.Constants;
import com.rocketmiles.cashregister.exceptions.CashRegisterException;
import com.rocketmiles.cashregister.interfaces.CashRegister;

public class RocketMilesCashRegister implements CashRegister {

	//Initialize Cash Register.
	private long twenty = 0;
	private long ten = 0;
	private long five = 0;
	private long two = 0;
	private long value = 0;
	private long one = 0;
	private long total = 0;
	
	/**
	 * @param List<String>command - List of commands split by space delimiter.
	 * @param Map<String, Long> cashMap - The map that contains the denominations
	 * @return cashMap - The current status of the cashMap.
	 */
	@Override
	public  Map<String, Long> process(List<String> command, Map<String, Long> cashMap) throws CashRegisterException {
		if(command.get(0).equalsIgnoreCase(Constants.SHOW)) {
			show(cashMap);
		} else if(command.get(0).equalsIgnoreCase(Constants.CHANGE)) {
			cashMap = change(command, cashMap);
		} else if(command.get(0).equalsIgnoreCase(Constants.TAKE) && command.size() == 6) {
			cashMap = take(command, cashMap);
		} else if(command.get(0).equalsIgnoreCase(Constants.PUT) && command.size() == 6) {
			cashMap = put(command, cashMap);
		}  else {
			throw new CashRegisterException("Invalid Command");
		}
		
		return cashMap;
	}

	@Override
	public void show(Map<String, Long> cashMap) {
		System.out.println(status(cashMap));
	}

	@Override
	public  Map<String, Long> put(List<String> command, Map<String, Long> cashMap) {
		int i = 1;
		for(String denomination : cashMap.keySet()) {
			cashMap.put(denomination, cashMap.get(denomination) + Long.parseLong(command.get(i)));
			i++;
		}
		System.out.println(status(cashMap));
		return cashMap;
	}

	@Override
	public Map<String, Long> change(List<String> command, Map<String, Long> cashMap) throws CashRegisterException {
		value = Long.parseLong(command.get(1));
		if(total >= value) {
			for(String denomination : cashMap.keySet()) {
				Long iteration = processChange(cashMap.get(denomination), 0, Long.parseLong(denomination), value);
				System.out.print(iteration);
				System.out.print(" ");
				cashMap.put(denomination, cashMap.get(denomination) - iteration);
			}
			System.out.println("");
			if(value != 0) {
				throw new CashRegisterException("Sorry no change can be made");
			}
		} else {
			throw new CashRegisterException("Insufficient Funds");
		}
		System.out.println(status(cashMap));
		return cashMap;
	}

	@Override
	public Map<String, Long> take(List<String> command, Map<String, Long> cashMap) throws CashRegisterException {
		int i = 1;
		for(String denomination : cashMap.keySet()) {
			long currentAmount = cashMap.get(denomination) - Long.parseLong(command.get(i));
			if(currentAmount >= 0)
				cashMap.put(denomination, currentAmount);
			else {
				throw new CashRegisterException("Insufficient funds");
			}
			i++;
		}
		System.out.println(status(cashMap));
		return cashMap;
	}
	
	@Override
	public long processChange(long current, long iteration, long denomination, long value) {
		
		if(current == 0 || value < denomination) {
			return iteration;
		}

		else  {
			this.value = value - denomination;
			iteration++;
			return processChange(current-1, iteration, denomination, this.value);
		}	
	}
	
	public String status(Map<String, Long> cashMap) {
		twenty = cashMap.get("20");
		ten = cashMap.get("10");
		five = cashMap.get("5");
		two = cashMap.get("2");
		one = cashMap.get("1");
		total = twenty * 20 + ten * 10 + five * 5 + two * 2 + one;
		return String.format("$%d %d %d %d %d %d" , total, twenty, ten, five, two, one);
	}
}
