package com.ipepeline.analyzer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

public class Runner {

    public static void main(String[] args) {
	System.out.println("Hello, World");

	Runner runner = new Runner();
	runner.run();
    }

    public void run() {

	String csvFile = "./Lincoln- test cases.csv";
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	ArrayList<Test> tests = new ArrayList<Test>();
	try {
	    br = new BufferedReader(new FileReader(csvFile));
	    while ((line = br.readLine()) != null) {
		// use comma as separator
		String[] testRow = line.split(cvsSplitBy);
		// System.out.println("Test [Num= " + test[0] + " , supplierId=
		// " + test[1] + " , state=" + test[19] + "]");
		if (StringUtils.isNumeric(testRow[0])) {
		    Test test = new Test();
		    test.setNum(testRow[0]);
		    test.setSupplierId(testRow[1]);
		    test.setGender(testRow[2]);
		    test.setHealthClass(testRow[3]);
		    test.setTerm(testRow[4]);
		    test.setFaceAmount(testRow[5]);
		    test.setWaiver(testRow[6]);
		    test.setWaiverRating(testRow[7]);
		    test.setCr(testRow[8]);
		    test.setCru(testRow[9]);
		    test.setTermAccel(testRow[10]);
		    test.setLifeElements(testRow[11]);
		    test.setCommonTable(testRow[12]);
		    test.setFlatExtra(testRow[13]);
		    test.setFlatYears(testRow[14]);
		    test.setActualAge(testRow[15]);
		    test.setPaymentOption(testRow[16]);
		    test.setMode(testRow[17]);
		    test.setPremium(testRow[18]);
		    test.setState(testRow[19]);
		    tests.add(test);
		}
	    }
	    
	    System.out.println(String.format("Number of tests: %d", tests.size()));
	    System.out.println("All tests:");
	    for(Test t : tests) {
		System.out.println(t.toString());
	    }
	    
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (br != null) {
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	System.out.println("Done");
    }

}