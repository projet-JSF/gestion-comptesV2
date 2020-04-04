package com.intiformation.gestionComptes.model;

import java.io.Serializable;

/**
 * Classe abstraite mere de CompteEpargne et CompteCourant
 * @author IN-MP-018
 *
 */
public class Compte implements Serializable{
	/*____________ Propriétés _____________*/
	
	private int idCompte;
	private double solde;
	private String typeCompte;
	private int clientID;
	private double decouvert;
	private double taux;

	
	/*__________ Constructeurs ____________*/
	public Compte(int idCompte, double solde, String typeCompte, int clientID) {
		super();
		this.idCompte = idCompte;
		this.solde = solde;
		this.typeCompte = typeCompte;
		this.clientID = clientID;
	}
	public Compte() {
		super();
	}
	public Compte(int idCompte, double solde, String typeCompte) {
		super();
		this.idCompte = idCompte;
		this.solde = solde;
		this.typeCompte = typeCompte;
	}

	public Compte(int idCompte, double solde, String typeCompte, int clientID, double decouvert, double taux) {
		super();
		this.idCompte = idCompte;
		this.solde = solde;
		this.typeCompte = typeCompte;
		this.clientID = clientID;
		this.decouvert = decouvert;
		this.taux = taux;
	}
	
	public Compte(double solde, String typeCompte, int clientID, double decouvert, double taux) {
		super();
		this.solde = solde;
		this.typeCompte = typeCompte;
		this.clientID = clientID;
		this.decouvert = decouvert;
		this.taux = taux;
	}
	
	/*________ Getters et Setters _________*/
	

	public int getIdCompte() {
		return idCompte;
	}
	public void setIdCompte(int idCompte) {
		this.idCompte = idCompte;
	}
	public double getSolde() {
		return solde;
	}
	public void setSolde(double solde) {
		this.solde = solde;
	}
	public String getTypeCompte() {
		return typeCompte;
	}
	public void setTypeCompte(String typeCompte) {
		this.typeCompte = typeCompte;
	}
	public int getClientID() {
		return clientID;
	}
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	
	public double getDecouvert() {
		return decouvert;
	}
	public void setDecouvert(double decouvert) {
		this.decouvert = decouvert;
	}
	public double getTaux() {
		return taux;
	}
	public void setTaux(double taux) {
		this.taux = taux;
	}

	
	/*_____________ Méthodes ______________*/
	@Override
	public String toString() {
		return "Compte [idCompte=" + idCompte + ", solde=" + solde + ", typeCompte=" + typeCompte + ", clientID="
				+ clientID + ", decouvert=" + decouvert + ", taux=" + taux + "]";
	}

	
}//end class


