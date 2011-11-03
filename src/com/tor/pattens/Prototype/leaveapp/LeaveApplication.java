package com.tor.pattens.Prototype.leaveapp;

import org.apache.log4j.Logger;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 09.04.2010
 * Time: 16:04:39
 * To change this template use File | Settings | File Templates.
 */
public class LeaveApplication implements Cloneable{
    private static final Logger log = Logger.getLogger(LeaveApplication.class);
    	private String reason;
	private Date startDate;
	private Date endDate;
	private Approver approver;

	public LeaveApplication(
			String reason, Date startDate, Date endDate, Approver approver){

		this.reason = reason;
		this.startDate = startDate;
		this.endDate = endDate;
		this.approver = approver;
	}

	public Object clone(){

		Approver copyApprover = new Approver(
			approver.getName(), approver.getDesignation());
		LeaveApplication copyApplication = new LeaveApplication(
			reason, ((Date)startDate.clone()), ((Date)endDate.clone()), copyApprover);
		return copyApplication;
	}

	public String toString(){
		return "[Leave Application:" + reason + "," + toString(startDate)
			+ "," + toString(endDate) + approver + "]";
	}

	private String toString(Date date){
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
		return format.format(date);
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Approver getApprover() {
		return approver;
	}

	public void setApprover(Approver approver) {
		this.approver = approver;
	}
}
