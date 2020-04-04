package com.intiformation.gestionComptes.dao;
/**
 * Interface de la DAO pour le conseiller 
 * @author IN-MP-018
 *
 */

import java.sql.Connection;
import java.util.List;

import com.intiformation.gestionComptes.model.Client;
import com.intiformation.gestionComptes.model.Conseiller;
import com.intiformation.gestionComptes.tool.DbConnection;



/**
 * Interface de la DAO du conseiller
 * @author Laure
 *
 */

public interface IConseillerDAO {
	
	//Récupération de la connexion
	public Connection connection=DbConnection.getInstance();
	
	// Méthodes abstraites de la DAO
	

	public boolean isConseillerExists(String pMail, String pMdp);
	
	public List<Conseiller> getAllConseillers();

	public boolean ajouterConseiller(Conseiller conseiller);
	
	public boolean modifierConseiller(Conseiller conseiller);
	
	public boolean supprimerConseillerByID(int pIDConseiller);
	
	public Conseiller getConseillerByID(int pIDConseiller);
	
	public List<Integer> getAllIDConseillers();
	
	public List<Client> getClientsduConseiller(int pIDConseiller);
	
	public Conseiller getConseillerByMailMDP(String pMail, String pMdp);
	
	public boolean isEmailAlreadyTaken(String pMail);
	

}
