package com.ipipeline.calcstorm.analyzer;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.ipipeline.calcstorm.analyzer.bo.ReportLine;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class Runner {

	private ArrayList<ArrayList<String>> testDataTable = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> testResultsTable = new ArrayList<ArrayList<String>>();
	private ArrayList<ReportLine> reportLines = new ArrayList<ReportLine>();
	private ArrayList<ArrayList<String>> reportTable = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> reportHeaders = new  ArrayList<ArrayList<String>>();

	private Properties properties;

	private Configuration configuration;

	public static void main(String[] args) {

		Runner runner = new Runner();
		runner.getProperties();

		runner.setTestDataTable();
		runner.setTestResultsTable();
		runner.setReportLines();

		runner.setReportTable();
		runner.setReportHeaders();

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

	public void createPieChart() {
		long passed = 0;
		long failed = 0;
		long na = 0;
		long zero = 0;

		int numberOfTests = reportLines.size() - 1;

		for (int i = 1; i <= numberOfTests; i++) {
			if (reportLines.get(i).getStatus() == null) {
				zero += 1;
			} else if (reportLines.get(i).getStatus().equalsIgnoreCase("passed")) {
				passed += 1;
			} else if (reportLines.get(i).getStatus().equalsIgnoreCase("failed")) {
				failed += 1;
			} else if (reportLines.get(i).getStatus().equals("n/a")) {
				na += 1;
			}
		}

		if (zero > 0) {
			throw new RuntimeException("Indefinite test status!");
		}

		System.out.println("Results:");

		System.out.println("Total " + numberOfTests + " (" + Math.round(numberOfTests * 100 / numberOfTests) + "%)");
		System.out.println("Passed " + passed + " (" + Math.round((double) passed * 100 / numberOfTests) + "%)");
		System.out.println("Failed " + failed + " (" + Math.round((double) failed * 100 / numberOfTests) + "%)");
		System.out.println("N/A " + na + " (" + Math.round((double) na * 100 / numberOfTests) + "%)");

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Passed", new Double(passed));
		dataset.setValue("Failed", new Double(failed));
		dataset.setValue("N/A", new Double(na));

		JFreeChart chart = ChartFactory.createPieChart("Test Results", // chart
				// title
				dataset, // data
				true, // include legend
				true, false);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setSectionPaint("Passed", new Color(211, 255, 206));
		plot.setSectionPaint("Failed", new Color(255, 192, 203));
		plot.setSectionPaint("N/A", new Color(176, 224, 230));
		plot.setSimpleLabels(true);

		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: {1} ({2})", new DecimalFormat("0"),
				new DecimalFormat("0%"));
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

		Map<String, ArrayList<ArrayList<String>>> input = new HashMap<String, ArrayList<ArrayList<String>>>();
		input.put("headers", reportHeaders);
		input.put("rows", reportTable);

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

	private ArrayList<ArrayList<String>> getTable(String csvFile) {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();

		try {
			br = new BufferedReader(new FileReader(csvFile));

			while ((line = br.readLine()) != null) {
				ArrayList<String> row = new ArrayList<String>(Arrays.asList(line.split(cvsSplitBy, -1)));
				rows.add(row);
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
		return rows;
	}

	public void setTestDataTable() {
		testDataTable.addAll(getTable(properties.getProperty("input.test_data_cvs.path")));
	}

	public ArrayList<ArrayList<String>> getTestResultsTable() {
		return testResultsTable;
	}

	public void setTestResultsTable() {
		testResultsTable.addAll(getTable(properties.getProperty("input.test_results_cvs.path")));
	}

	public ArrayList<ReportLine> getReportLines() {
		return reportLines;
	}

	public void setReportLines() {
		int num = testDataTable.get(0).indexOf(properties.getProperty("input.test_data_cvs.column.num"));
		int premium = testDataTable.get(0).indexOf(properties.getProperty("input.test_data_cvs.column.premium"));

		for(int i = 0; i < testDataTable.size(); i++) {
			ReportLine reportLine = new ReportLine();
			ArrayList<String> apatheticData = new ArrayList<String>();
			for(int j = 0; j < testDataTable.get(i).size(); j++) {

				if(j == num) {
					reportLine.setNum(testDataTable.get(i).get(j));
				} else if(j == premium) {
					reportLine.setPremium(testDataTable.get(i).get(j));
				} else {
					apatheticData.add(testDataTable.get(i).get(j));
				}
			}
			reportLine.setApatheticData(apatheticData);
			reportLines.add(reportLine);
		}

		int labelColumnIndex = testResultsTable.get(0).indexOf("label");
		int actualResultColumnIndex = testResultsTable.get(0).indexOf("responseMessage");
		int successColumnIndex = testResultsTable.get(0).indexOf("success");

		int testNumber = 0;

		for(ArrayList<String> row : testResultsTable) {
			if(row.get(labelColumnIndex).contains("[TEST] Get Quote")) {
				String actualResultValue = row.get(actualResultColumnIndex);
				String successValue = row.get(successColumnIndex);
				reportLines.get(testNumber + 1).setActualResult(actualResultValue);
				reportLines.get(testNumber + 1).setSuccess(successValue);

				if(reportLines.get(testNumber + 1).getActualResult().equals("no_rates")) {
					reportLines.get(testNumber + 1).setStatus("n/a");
				} else if(reportLines.get(testNumber + 1).getActualResult().equals("OK")) {
					reportLines.get(testNumber + 1).setStatus("passed");
				} else if (reportLines.get(testNumber + 1).getSuccess().equalsIgnoreCase("false")) {
					reportLines.get(testNumber + 1).setStatus("failed");
				} else if (reportLines.get(testNumber + 1).getSuccess().equalsIgnoreCase("true")) {
					reportLines.get(testNumber + 1).setStatus("passed");
				}
				testNumber++;
			}
		}
	}

	public ArrayList<ArrayList<String>> getReportTable() {
		return reportTable;
	}

	public void setReportTable() {		
		for(int i = 1; i < reportLines.size(); i++) {
			ArrayList<String> row = new ArrayList<String>(); 
			row.add(reportLines.get(i).getNum());

			for (String string : reportLines.get(i).getApatheticData()) {
				row.add(string);
			}
			row.add(reportLines.get(i).getPremium());
			row.add(reportLines.get(i).getActualResult());
			row.add(reportLines.get(i).getStatus());
			reportTable.add(row);
		}
	}

	public void setReportHeaders() {
		ArrayList<String> headers = new ArrayList<String>();
		headers.add(properties.getProperty("input.test_data_cvs.column.num"));
		for(String string : reportLines.get(0).getApatheticData()) {
			headers.add(string);
		}
		headers.add(properties.getProperty("input.test_data_cvs.column.premium"));
		headers.add("actualResult");
		headers.add("status");
		reportHeaders.add(headers);
	}

}