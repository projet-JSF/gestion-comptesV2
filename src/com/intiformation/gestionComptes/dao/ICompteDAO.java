package com.intiformation.gestionComptes.dao;

import java.sql.Connection;
import java.util.List;

import com.intiformation.gestionComptes.model.Compte;
import com.intiformation.gestionComptes.tool.DbConnection;


/**
 * Interface de la DAO du compte
 * @author Laure
 *
 */

public interface ICompteDAO {
	
	//Récupération de la connexion
	public Connection connection=DbConnection.getInstance();
	
	// Méthodes abstraites de la DAO
	
	public List<Compte> getAllComptes();

	public boolean ajouterCompte(Compte compte);
	
	public boolean modifierCompte(Compte compte);
	
	public boolean supprimerCompteByID(int pIDCompte);
	
	public Compte getCompteByID(int pIDCompte);
	
	public List<Integer> getAllIDComptes();
	
	public List<Compte> getCompteByIDClient(int idClient);
	
	public boolean setClient(int id_compte, int idClient);
	
	public boolean deposit(Compte compte, double montant);
	
	public boolean withdraw(Compte compte, double montant);
	
	public boolean transfert(Compte compteDonneur, Compte compteReceveur, double montant);
	
}//end class
