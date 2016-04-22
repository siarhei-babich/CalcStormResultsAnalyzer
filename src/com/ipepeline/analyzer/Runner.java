package com.ipepeline.analyzer;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.ipepeline.analyzer.bo.Test;
import com.ipepeline.analyzer.bo.TestResult;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class Runner {
    
    private ArrayList<Test> tests;
    private ArrayList<TestResult> testResults;
    
    private Properties properties;
    
    private Configuration configuration;

    public static void main(String[] args) {
	
	Runner runner = new Runner();
	runner.getProperties();
	runner.getTests();
	runner.getTestStatuses();
	runner.setTestResults();
	runner.createPieChart();
	runner.createHTML();
    }
    
    public void getProperties() {
	properties = new Properties();

	InputStream input = null;

	try {
	    input = new FileInputStream("config.properties");

	    // load a properties file
	    properties.load(input);
	} catch (IOException ex) {
	    ex.printStackTrace();
	} finally {
	    if (input != null) {
		try {
		    input.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
    }

    public void getTests() {
	String csvFile = properties.getProperty("input.test_data_cvs_path");
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	tests = new ArrayList<Test>();
	try {
	    br = new BufferedReader(new FileReader(csvFile));
	    while ((line = br.readLine()) != null) {
		String[] testRow = line.split(cvsSplitBy);
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
    }
    
    public void getTestStatuses() {
	String csvFile = properties.getProperty("input.test_results_cvs_path");
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	testResults = new ArrayList<TestResult>();
	try {
	    br = new BufferedReader(new FileReader(csvFile));
	    while ((line = br.readLine()) != null) {
		String[] testStatusRow = line.split(cvsSplitBy);
		if (StringUtils.isNumeric(testStatusRow[11]) && testStatusRow[2].contains("[TEST]")) {
		    String success = testStatusRow[7];
		    if(testStatusRow[4].equals("no_rates")) {
			success = "n/a";
		    }
		    testResults.add(new TestResult(testStatusRow[2], testStatusRow[4], success));
		}
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
    }
    
    public void setTestResults() {
	if(tests.size() == testResults.size()) {
	    for (int i = 0; i < tests.size(); i++) {
		tests.get(i).setStatus(testResults.get(i).getStatus());
		tests.get(i).setActualResult(testResults.get(i).getResponseMessage());
	    }
	} else {
	    System.out.println("Unexpected number of test statuses!");
	}
    }
    
    public void createPieChart() {
	long passed = 0;
	long failed = 0;
	long na = 0;
	long zero = 0;
	for(Test test : tests) {
	    if(test.getStatus() == null) {
		zero += 1;
	    } else if(test.getStatus().equalsIgnoreCase("true")) {
		passed += 1;
	    } else if(test.getStatus().equalsIgnoreCase("false")) {
		failed += 1;
	    } else if(test.getStatus().equals("n/a")) {
		na += 1;
	    }
	}
	
	if(zero > 0) {
	    throw new RuntimeException("Indefinite test status!");
	}
	
	System.out.println("Results:");
	System.out.println("Total " + tests.size() + " (" + Math.round(tests.size() * 100 / tests.size()) + "%)");
	System.out.println("Passed " + passed + " (" + Math.round((double) passed * 100 / tests.size()) + "%)");
	System.out.println("Failed: " + failed + " (" + Math.round((double) failed * 100 / tests.size()) + "%)");
	System.out.println("N/A: " + na + " (" + Math.round((double) na * 100 / tests.size()) + "%)");
	
	DefaultPieDataset dataset = new DefaultPieDataset();
	dataset.setValue("Passed", new Double(passed));
	dataset.setValue("Failed", new Double(failed));
	dataset.setValue("N/A", new Double(na));
	
	JFreeChart chart = ChartFactory.createPieChart(
		"Test Results", // chart title
		dataset, // data
		true, // include legend
		true,
		false);
	PiePlot plot = (PiePlot) chart.getPlot();
	plot.setBackgroundPaint(Color.white);
	plot.setSectionPaint("Passed", new Color(211, 255, 206));
	plot.setSectionPaint("Failed", new Color(255, 192, 203));
	plot.setSectionPaint("N/A", new Color(176, 224, 230));
	plot.setSimpleLabels(true);
	
	PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
	plot.setLabelGenerator(gen);

	int width = 640; /* Width of the image */
	int height = 480; /* Height of the image */ 
	File pieChart = new File(properties.getProperty("output.directory") + "/" + "PieChart.jpeg");
	
	try {
	    ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    public void createHTML() {
	configuration = new Configuration();
	
	try {
	    configuration.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir")));
	} catch (IOException e1) {
	    e1.printStackTrace();
	}
	
	configuration.setDefaultEncoding("UTF-8");
	configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	
	Template template = null;
	
	try {
	    template = configuration.getTemplate("template.ftl");
	} catch (IOException e) {
	    e.printStackTrace();
	}

	Map<String, ArrayList<Test>> input = new HashMap<String, ArrayList<Test>>();
	input.put("tests", tests);
	

	Writer fileWriter = null;
	try {
	    fileWriter = new FileWriter(new File(properties.getProperty("output.directory") + "/" + "Report.html"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	try {
	    template.process(input, fileWriter);
	} catch (TemplateException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	try {
	    fileWriter.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}