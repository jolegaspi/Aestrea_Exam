package com.rocketmiles.cashregister.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import com.rocketmiles.cashregister.RocketMilesCashRegister;
import com.rocketmiles.cashregister.exceptions.CashRegisterException;

public class CashRegisterTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	RocketMilesCashRegister cashRegister;
	Map<String, Long> cashMap;

	@BeforeEach
	void initialize() {
		cashRegister = new RocketMilesCashRegister();
		cashMap = new LinkedHashMap<String, Long>();
		cashMap.put("20", (long) 0);
		cashMap.put("10", (long) 0);
		cashMap.put("5", (long) 0);
		cashMap.put("2", (long) 0);
		cashMap.put("1", (long) 0);
	}

	@Test
	void assertPut() throws CashRegisterException {
		List<String> commandList = Arrays.asList("put 1 1 1 1 1".split(" "));
		cashRegister.process(commandList, cashMap);
		assertTrue(cashRegister.status(cashMap).equals("$38 1 1 1 1 1"));
	}

	@Test
	void assertChange() throws CashRegisterException {
		List<String> commandList = Arrays.asList("put 1 1 1 1 1".split(" "));
		cashRegister.process(commandList, cashMap);
		commandList = Arrays.asList("change 12".split(" "));
		cashRegister.process(commandList, cashMap);
		assertTrue(cashRegister.status(cashMap).equals("$26 1 0 1 0 1"));
	}

	@Test
	void assertTake() throws CashRegisterException {
		List<String> commandList = Arrays.asList("put 1 1 1 1 1".split(" "));
		cashRegister.process(commandList, cashMap);
		commandList = Arrays.asList("take 1 1 1 1 1".split(" "));
		cashRegister.process(commandList, cashMap);
		assertTrue(cashRegister.status(cashMap).equals("$0 0 0 0 0 0"));
	}

	@Test
	void assertShow() throws CashRegisterException {
		List<String> commandList = Arrays.asList("put 1 1 1 1 1".split(" "));
		cashRegister.process(commandList, cashMap);
		commandList = Arrays.asList("show".split(" "));
		cashRegister.process(commandList, cashMap);
		assertTrue(cashRegister.status(cashMap).equals("$38 1 1 1 1 1"));
	}
	
	@Test
	void assertChangeInsufficientFunds() throws CashRegisterException {
	    Throwable exception = assertThrows(
	    		CashRegisterException.class, () -> {
	    			List<String> commandList = Arrays.asList("put 1 1 1 1 1".split(" "));
	        		cashRegister.process(commandList, cashMap);
	        		commandList = Arrays.asList("change 39".split(" "));
	        		cashRegister.process(commandList, cashMap);
	            }
	    );
	    assertEquals("Insufficient Funds", exception.getMessage());
	}
	
	@Test
	void assertNoChange() throws CashRegisterException {
	    Throwable exception = assertThrows(
	    		CashRegisterException.class, () -> {
	    			List<String> commandList = Arrays.asList("put 1 1 1 1 1".split(" "));
	        		cashRegister.process(commandList, cashMap);
	        		commandList = Arrays.asList("change 36".split(" "));
	        		cashRegister.process(commandList, cashMap);
	        		commandList = Arrays.asList("change 1".split(" "));
	        		cashRegister.process(commandList, cashMap);
	            }
	    );
	    assertEquals("Sorry no change can be made", exception.getMessage());
	}
	@Test
	void assertTakeInsufficientFunds() throws CashRegisterException {
	    Throwable exception = assertThrows(
	    		CashRegisterException.class, () -> {
	        		List<String> commandList = Arrays.asList("take 1 1 1 1 1".split(" "));
	        		cashRegister.process(commandList, cashMap);
	            }
	    );
	    assertEquals("Insufficient funds", exception.getMessage());
	}
	@Test
	void assertInvalidCommand() throws CashRegisterException {
	    Throwable exception = assertThrows(
	    		CashRegisterException.class, () -> {
	        		List<String> commandList = Arrays.asList("blaaa 123213".split(" "));
	        		cashRegister.process(commandList, cashMap);
	            }
	    );
	    assertEquals("Invalid Command", exception.getMessage());
	}
}
