package com.springthymeleaf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PDF_DETAILS")
public class DocDetails {

	@Id
	@GenericGenerator(strategy = "native", name = "noteGen")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "noteGen")
	@Column(name = "DOC_ID")
	private int docId;

	@Column(name = "DOC_NAME")
	private String docName;

	@Column(name = "DOC_TYPE")
	private String docType;

	@Column(name = "DOC_AMOUNT")
	private double docAmount;

	@Column(name = "TAX")
	private double tax;

	@Column(name = "TAX_TYPE")
	private String taxType;

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public double getDocAmount() {
		return docAmount;
	}

	public void setDocAmount(double docAmount) {
		this.docAmount = docAmount;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	@Override
	public String toString() {
		return "DocDaitails [docId=" + docId + ", docName=" + docName + ", docType=" + docType + ", docAmount="
				+ docAmount + ", tax=" + tax + ", taxType=" + taxType + "]";
	}

}
