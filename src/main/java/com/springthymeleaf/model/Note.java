package com.springthymeleaf.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Om Prajapati
 *
 */

@Entity
@Table(name = "NOTE")
public class Note {

	@Id
	@GenericGenerator(strategy = "native", name = "noteGen")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "noteGen")
	@Column(name = "NOTE_ID")
	private int noteId;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATE_DATE")
	private Date createdDate;

	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name = "PIN")
	private String pin;

	@Column(name = "ARCHIVE_STATUS")
	private String archiveStatus;

	@Column(name = "REMINDER_STATUS")
	private String reminderStatus;

	@Column(name = "NOTE_COLOR")
	private String noteColor;

	@Lob
	@Column(name = "IMAGE", columnDefinition = "LONGBLOB")
	private String image;

	@ManyToOne()
	@JsonIgnore
	@JoinColumn(name = "USER_ID")
	private User user;

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getArchiveStatus() {
		return archiveStatus;
	}

	public void setArchiveStatus(String archiveStatus) {
		this.archiveStatus = archiveStatus;
	}

	public String getReminderStatus() {
		return reminderStatus;
	}

	public void setReminderStatus(String reminderStatus) {
		this.reminderStatus = reminderStatus;
	}

	public String getNoteColor() {
		return noteColor;
	}

	public void setNoteColor(String noteColor) {
		this.noteColor = noteColor;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
