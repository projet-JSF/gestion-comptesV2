package com.intiformation.gestionComptes.model;

import java.io.Serializable;

public class CompteEpargne extends Compte implements Serializable{
	/*____________ Propriétés _____________*/
	
	private double taux=3;

	/*__________ Constructeurs ____________*/

	public CompteEpargne(int idCompte, double solde,  int clientID, double taux) {
		super(idCompte, solde, "epargne", clientID);
		this.taux = taux;
	}

	public CompteEpargne(int idCompte, double solde,  double taux) {
		super(idCompte, solde, "epargne");
	}
	/*________ Getters et Setters _________*/

	public double getTaux() {
		return taux;
	}

	public void setTaux(double taux) {
		this.taux = taux;
	}
	
	
	/*_____________ Méthodes ______________*/

	@Override
	public String toString() {
		return " \n_____________________________\n Identifiant du Compte : " + getIdCompte() + "\n Solde : " + getSolde() + "€ \n "
				+ "type du compte : " + getTypeCompte() + "\n Identifiant client : " + getClientID() 
				+ "\n Taux : " + taux +"% \n_____________________________";
	}
}//end class
