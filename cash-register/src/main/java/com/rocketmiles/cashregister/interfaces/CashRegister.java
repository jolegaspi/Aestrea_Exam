package com.rocketmiles.cashregister.interfaces;

import java.util.List;
import java.util.Map;

import com.rocketmiles.cashregister.exceptions.CashRegisterException;

/* A cash register interface for all functions of a cash register */
public interface CashRegister {
	
	public Map<String, Long> process(List<String> command, Map<String, Long>cashMap) throws CashRegisterException;
	
	public void show(Map<String, Long>cashMap);
	
	public Map<String, Long> put(List<String> command, Map<String, Long>cashMap) throws CashRegisterException;
	
	public Map<String, Long> change(List<String> command, Map<String, Long>cashMap) throws CashRegisterException;
	
	public Map<String, Long> take(List<String> command, Map<String, Long>cashMap) throws CashRegisterException;
	
	public long processChange(long current, long iteration, long denomination, long value);
}
