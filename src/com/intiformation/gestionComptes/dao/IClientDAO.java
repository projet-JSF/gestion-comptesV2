package com.intiformation.gestionComptes.dao;

import java.sql.Connection;
import java.util.List;

import com.intiformation.gestionComptes.model.Client;
import com.intiformation.gestionComptes.tool.DbConnection;


/**
 * Interface de la DAO du client
 * @author Laure
 *
 */
public interface IClientDAO {
	//Récupération de la connexion
	public Connection connection=DbConnection.getInstance();
	
	// Méthodes abstraites de la DAO
	
	public List<Client> getAllClients();

	public boolean ajouterClient(Client client);
	
	public boolean modifierClient(Client client);
	
	public boolean supprimerClientByID(int pIDClient);
	
	public Client getClientByID(int pIDClient);
	
	public List<Integer> getAllIDClients();
	
	
}//end class
