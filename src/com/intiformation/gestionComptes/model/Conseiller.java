package com.intiformation.gestionComptes.model;

import java.io.Serializable;
/**
 * modele de données pour le conseiller 
 * modele sous forme de javaBean
 * @author IN-MP-018
 *
 */
public class Conseiller implements Serializable{
/*============= Props ====================*/
	
	private int idConseiller;
	private String nom;
	private String mail;
	private String motDePasse;
	
	/*============= Constructeurs ====================*/
	public Conseiller() {
		super();
	}
	public Conseiller(int idConseiller, String nom, String mail, String motDePasse) {
		super();
		this.idConseiller = idConseiller;
		this.nom = nom;
		this.mail = mail;
		this.motDePasse = motDePasse;
	}
	public Conseiller(String nom, String mail, String motDePasse) {
		super();
		this.nom = nom;
		this.mail = mail;
		this.motDePasse = motDePasse;
	}
	
	/*============= Getter Setter ====================*/
	
	
	public int getIdConseiller() {
		return idConseiller;
	}
	public void setIdConseiller(int idConseiller) {
		this.idConseiller = idConseiller;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	/*============= Méthode ====================*/
	
	@Override
	public String toString() {
		return "Conseiller [idConseiller=" + idConseiller + ", nom=" + nom + ", mail=" + mail + ", motDePasse="
				+ motDePasse + "]";
	}
	
	
	
}
