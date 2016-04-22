package com.ipepeline.analyzer.bo;

public class TestResult {
    
    private String label;
    private String responseMessage;
    private String status;
    
    public TestResult() {
    }

    public TestResult(String label, String responseMessage, String status) {
	super();
	this.label = label;
	this.responseMessage = responseMessage;
	this.status = status;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
	return "TestStatus [label=" + label + ", status=" + status + ", responseMessage=" + responseMessage + "]";
    }

}