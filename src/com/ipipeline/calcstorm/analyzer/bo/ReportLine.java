package com.ipipeline.calcstorm.analyzer.bo;

import java.util.ArrayList;

public class ReportLine {
	
	private String num;
	private ArrayList<String> apatheticData = new ArrayList<String>();
	private String premium;
	private String actualResult;
	private String status;
	
	public ReportLine() {}
	
	public ReportLine(String num, ArrayList<String> apatheticData, String premium, String actualResult,
			String status) {
		this.num = num;
		this.apatheticData = apatheticData;
		this.premium = premium;
		this.actualResult = actualResult;
		this.status = status;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public ArrayList<String> getApatheticData() {
		return apatheticData;
	}

	public void setApatheticData(ArrayList<String> apatheticData) {
		this.apatheticData = apatheticData;
	}

	public String getPremium() {
		return premium;
	}

	public void setPremium(String premium) {
		this.premium = premium;
	}

	public String getActualResult() {
		return actualResult;
	}

	public void setActualResult(String actualResult) {
		this.actualResult = actualResult;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ReportLine [num=" + num + ", apatheticData=" + apatheticData + ", premium=" + premium
				+ ", actualResult=" + actualResult + ", status=" + status + "]";
	}

}