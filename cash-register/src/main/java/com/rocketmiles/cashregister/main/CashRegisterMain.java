package com.rocketmiles.cashregister.main;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.rocketmiles.cashregister.RocketMilesCashRegister;
import com.rocketmiles.cashregister.constants.Constants;
import com.rocketmiles.cashregister.exceptions.CashRegisterException;

public class CashRegisterMain {
	
	public static void main(String[] args) {
		Map<String, Long> cashMap = new LinkedHashMap<String, Long>();
		  cashMap.put("20", (long) 0);
		  cashMap.put("10", (long) 0);
		  cashMap.put("5", (long) 0);
		  cashMap.put("2", (long) 0);
		  cashMap.put("1", (long) 0);
	      Scanner scanner = new Scanner (System.in);
	      System.out.println("Cash Register is ready");
	      System.out.print("Enter command: ");
	      RocketMilesCashRegister cashRegister = new RocketMilesCashRegister();
	      while(scanner.hasNext()) {
	    	  System.out.println("Enter command: ");
		      String input = scanner.nextLine();
		      if(input.equalsIgnoreCase(Constants.QUIT)) {
		    	  System.out.println("Quitting..");
		    	  scanner.close();
		          break;
		      }
			  List<String> commandList =  Arrays.asList(input.split(" "));
			  try {
				cashRegister.process(commandList, cashMap);
			} catch (CashRegisterException e) {
				System.out.println(e.getMessage());
			} 
	      }
	}

}
