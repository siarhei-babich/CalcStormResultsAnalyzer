package com.ipepeline.analyzer.bo;

public class TestStatus {
    
    private String label;
    private String status;
    
    public TestStatus() {
	
    }

    public TestStatus(String label, String status) {
	super();
	this.label = label;
	this.status = status;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
	return "TestStatus [label=" + label + ", status=" + status + ", getLabel()=" + getLabel() + ", getStatus()="
		+ getStatus() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
		+ super.toString() + "]";
    }

}