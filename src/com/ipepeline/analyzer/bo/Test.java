package com.ipepeline.analyzer.bo;

public class Test {
    
    private String num;
    private String supplierId;
    private String gender;
    private String healthClass;
    private String term;
    private String faceAmount;
    private String waiver;
    private String waiverRating;
    private String cr;
    private String cru;
    private String termAccel;
    private String lifeElements;
    private String commonTable;
    private String flatExtra;
    private String flatYears;
    private String actualAge;
    private String paymentOption;
    private String mode;
    private String premium;
    private String actualResult;
    private String state;
    private String status;
    
    public Test() {
	super();
    }

    public Test(String num, String supplierId, String gender, String healthClass, String term, String faceAmount,
	    String waiver, String waiverRating, String cr, String cru, String termAccel, String lifeElements,
	    String commonTable, String flatExtra, String flatYears, String actualAge, String paymentOption, String mode,
	    String premium, String actualResult, String state, String status) {
	super();
	this.num = num;
	this.supplierId = supplierId;
	this.gender = gender;
	this.healthClass = healthClass;
	this.term = term;
	this.faceAmount = faceAmount;
	this.waiver = waiver;
	this.waiverRating = waiverRating;
	this.cr = cr;
	this.cru = cru;
	this.termAccel = termAccel;
	this.lifeElements = lifeElements;
	this.commonTable = commonTable;
	this.flatExtra = flatExtra;
	this.flatYears = flatYears;
	this.actualAge = actualAge;
	this.paymentOption = paymentOption;
	this.mode = mode;
	this.premium = premium;
	this.actualResult = actualResult;
	this.state = state;
	this.status = status;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHealthClass() {
        return healthClass;
    }

    public void setHealthClass(String healthClass) {
        this.healthClass = healthClass;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getFaceAmount() {
        return faceAmount;
    }

    public void setFaceAmount(String faceAmount) {
        this.faceAmount = faceAmount;
    }

    public String getWaiver() {
        return waiver;
    }

    public void setWaiver(String waiver) {
        this.waiver = waiver;
    }

    public String getWaiverRating() {
        return waiverRating;
    }

    public void setWaiverRating(String waiverRating) {
        this.waiverRating = waiverRating;
    }

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }

    public String getCru() {
        return cru;
    }

    public void setCru(String cru) {
        this.cru = cru;
    }

    public String getTermAccel() {
        return termAccel;
    }

    public void setTermAccel(String termAccel) {
        this.termAccel = termAccel;
    }

    public String getLifeElements() {
        return lifeElements;
    }

    public void setLifeElements(String lifeElements) {
        this.lifeElements = lifeElements;
    }

    public String getCommonTable() {
        return commonTable;
    }

    public void setCommonTable(String commonTable) {
        this.commonTable = commonTable;
    }

    public String getFlatExtra() {
        return flatExtra;
    }

    public void setFlatExtra(String flatExtra) {
        this.flatExtra = flatExtra;
    }

    public String getFlatYears() {
        return flatYears;
    }

    public void setFlatYears(String flatYears) {
        this.flatYears = flatYears;
    }

    public String getActualAge() {
        return actualAge;
    }

    public void setActualAge(String actualAge) {
        this.actualAge = actualAge;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
	return "Test [num=" + num + ", supplierId=" + supplierId + ", gender=" + gender + ", healthClass=" + healthClass
		+ ", term=" + term + ", faceAmount=" + faceAmount + ", waiver=" + waiver + ", waiverRating="
		+ waiverRating + ", cr=" + cr + ", cru=" + cru + ", termAccel=" + termAccel + ", lifeElements="
		+ lifeElements + ", commonTable=" + commonTable + ", flatExtra=" + flatExtra + ", flatYears="
		+ flatYears + ", actualAge=" + actualAge + ", paymentOption=" + paymentOption + ", mode=" + mode
		+ ", premium=" + premium + ", actualResult=" + actualResult + ", state=" + state + ", status=" + status
		+ "]";
    }
    
}