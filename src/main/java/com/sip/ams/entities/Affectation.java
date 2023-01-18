package com.sip.ams.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Affectation {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "idClient")
	private int idClient;
	
	@Column(name = "idAgent")
	private int idAgent;
	
	@Column(name = "dateAffectation")
	private Date dateAffectation;
	
	@Column(name = "idAdmin")
	private int idAdmin;
	
	@Column(name = "status")
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Affectation() {
	
	}

	

	@Override
	public String toString() {
		return "Affectation [id=" + id + ", idClient=" + idClient + ", idAgent=" + idAgent + ", dateAffectation="
				+ dateAffectation + ", idAdmin=" + idAdmin + ", status=" + status + "]";
	}

	public Affectation(int idClient, int idAgent, Date dateAffectation, int idAdmin, String status) {
		super();
		this.idClient = idClient;
		this.idAgent = idAgent;
		this.dateAffectation = dateAffectation;
		this.idAdmin = idAdmin;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public int getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(int idAgent) {
		this.idAgent = idAgent;
	}

	public Date getDateAffectation() {
		return dateAffectation;
	}

	public void setDateAffectation(Date dateAffectation) {
		this.dateAffectation = dateAffectation;
	}

	public int getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}

}
