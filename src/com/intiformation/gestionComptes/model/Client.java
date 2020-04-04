package com.intiformation.gestionComptes.model;

import java.io.Serializable;

/**
 * modele de données pour le client 
 * modele sous forme de javaBean
 * @author IN-MP-018
 *
 */
public class Client implements Serializable{
	
	/*============= Props ====================*/

	private int idClient;
	private String nom;
	private String prenom;
	private String adresse;
	private String codePostal;
	private String ville;
	private String telephone;
	private int conseillerId;

	
	/*============= Constructeurs ====================*/
	
	public Client() {
		super();
	}


	public Client(int idClient, String nom, String prenom, String adresse, String codePostal, String ville,
			String telephone, int conseillerId) {
		super();
		this.idClient = idClient;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.ville = ville;
		this.telephone = telephone;
		this.conseillerId = conseillerId;
	}

	public Client( String nom, String prenom, String adresse, String codePostal, String ville,
			String telephone, int conseillerId) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.ville = ville;
		this.telephone = telephone;
		this.conseillerId = conseillerId;
	}


	/*============= Getter Setter ====================*/
	
	
	public int getConseillerId() {
		return conseillerId;
	}


	public void setConseillerId(int conseillerId) {
		this.conseillerId = conseillerId;
	}


	public int getIdClient() {
		return idClient;
	}


	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public String getCodePostal() {
		return codePostal;
	}


	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}


	public String getVille() {
		return ville;
	}


	public void setVille(String ville) {
		this.ville = ville;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	

	@Override
	public String toString() {
		return "Client [idClient=" + idClient + ", nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse
				+ ", codePostal=" + codePostal + ", ville=" + ville + ", telephone=" + telephone + ", conseiller="+ conseillerId+"]";
	}
	
	
	
}
