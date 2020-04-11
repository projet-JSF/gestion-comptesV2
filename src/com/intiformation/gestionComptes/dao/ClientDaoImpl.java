package com.intiformation.gestionComptes.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.intiformation.gestionComptes.model.Client;



/**
 * Implementation concrete de la dao du client <br/>
 * Implemente l'interface IClientDAO
 * 
 * @author Laure
 *
 */
public class ClientDaoImpl implements IClientDAO{

/*====================================================================================================*/	
/*==getAllClients=====================================================================================*/
/*====================================================================================================*/

	/**
	 * Récupération de la liste des clients dans la bdd
	 * @return liste des clients au format List de Client
	 */
	@Override
	public List<Client> getAllClients() {
		PreparedStatement ps = null;
		ResultSet resultatRequete =null;
		
		try {
			String requeteSqlGetAll="SELECT * FROM clients";
			ps = this.connection.prepareStatement(requeteSqlGetAll);
			
			resultatRequete = ps.executeQuery();

			Client client = null;
			
			List<Client> listeClient = new ArrayList <>();

			while (resultatRequete.next()) {
				int idClient = resultatRequete.getInt(1);
				String nom = resultatRequete.getString(2);
				String prenom = resultatRequete.getString(3);
				String adresse = resultatRequete.getString(4);
				String codePostal = resultatRequete.getString(5);
				String ville = resultatRequete.getString(6);
				String telephone = resultatRequete.getString(7);
				int conseillerId = resultatRequete.getInt(8);

				client = new Client(idClient, nom, prenom, adresse, codePostal, ville, telephone, conseillerId);
				
				listeClient.add(client);
				
			}//end while
			return listeClient;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultatRequete!= null) resultatRequete.close();
				if(ps!= null) ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}// end getAllClients()


/*====================================================================================================*/	
/*==ajouterClient=====================================================================================*/
/*====================================================================================================*/
	
	/**
	 * Ajout d'un client à la base de données
	 * 
	 * @param client = un objet client contenant toutes les informations du nouveau client
	 * @return boolean : true=l'operation s'est bien passée / false= il y a eu une erreur et l'operation n'a pas pu être effectuée
	 */
	
	@Override
	public boolean ajouterClient(Client client) {
	PreparedStatement ps = null;
		
		try {
			String requeteSqlAdd= "INSERT INTO  clients ( prenom , nom, adresse, codePostal,ville, telephone, conseiller_id) VALUES (?, ?, ?, ?, ?, ?, ? )";
			
			ps = this.connection.prepareStatement(requeteSqlAdd);
			
			ps.setString(1, client.getPrenom());
			ps.setString(2, client.getNom());
			ps.setString(3, client.getAdresse());
			ps.setString (4, client.getCodePostal());
			ps.setString(5, client.getVille());
			ps.setString(6, client.getTelephone());
			ps.setInt(7, client.getConseillerId());

			int verifAjout = ps.executeUpdate();

			return (verifAjout==1);
			
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			try {
				if(ps!= null) ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
		
	}

/*=====================================================================================================*/	
/*==modifierClient=====================================================================================*/
/*=====================================================================================================*/
	
	/**
	 * Modification d'un client dans la bdd
	 * 
	 * @param client = un objet client ayant le même id que le client à modifier et contenant toutes les infos nouvelles du nouveau client
	 * @return boolean : true=l'operation s'est bien passée / false= il y a eu une erreur et l'operation n'a pas pu être effectuée
	 */
	
	@Override
	public boolean modifierClient(Client client) {
		PreparedStatement ps = null;
		try {
	
			String requeteSqlAdd= "UPDATE clients SET prenom = ?, nom =?, adresse=?, codePostal=?,ville=?, telephone=?, conseiller_id=? WHERE id_client= ? ";
			
			ps = this.connection.prepareStatement(requeteSqlAdd);
		
			ps.setString(1, client.getPrenom());
			ps.setString(2, client.getNom());
			ps.setString(3, client.getAdresse());
			ps.setString (4, client.getCodePostal());
			ps.setString(5, client.getVille());
			ps.setString(6, client.getTelephone());
			ps.setInt(7, client.getConseillerId());
			
			ps.setInt(8, client.getIdClient());
			

			int verifAjout = ps.executeUpdate();
			
			return (verifAjout==1);
			
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			try {
				if(ps!= null) ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
		
	}// end modifierClient

	
/*==========================================================================================================*/	
/*==supprimerClientByID=====================================================================================*/
/*==========================================================================================================*/
	
	/**
	 * Suppression d'un client de la base de donnée 
	 * @param pIDClient : identifiant du client à supprimer
	 * @return boolean : true=l'operation s'est bien passée / false= il y a eu une erreur et l'operation n'a pas pu être effectuée
	 */
	
	@Override
	public boolean supprimerClientByID(int pIDClient) {
		PreparedStatement ps = null;
		
		try {
			
			String requeteSqlDelete= "DELETE FROM clients WHERE id_client=?";
			
			ps = this.connection.prepareStatement(requeteSqlDelete);
			
			ps.setInt(1, pIDClient);

			int verifdelete = ps.executeUpdate();

			return (verifdelete==1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps!= null) ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
		
	}// end supprimerClient

/*====================================================================================================*/
/*==getClientByID=====================================================================================*/
/*====================================================================================================*/
	
	/**
	 * Récuperation d'un client de la bdd par son identifiant
	 * @param pIDClient : identifiant du client à récuperer
	 * @return client : objet client contenant toutes les informations du client ayant l'id= pIDClient
	 * 
	 */
	
	
	@Override
	public Client getClientByID(int pIDClient) {
		PreparedStatement ps = null;
		ResultSet resultatRequete =null;
		
		try {
			String requeteSqlGetById="SELECT * FROM clients WHERE id_client = ?";
			ps = this.connection.prepareStatement(requeteSqlGetById);
			ps.setInt(1, pIDClient);
			
			resultatRequete = ps.executeQuery();
			Client client = null;
			
			resultatRequete.next();
			int idClient = resultatRequete.getInt(1);
			String nom = resultatRequete.getString(2);
			String prenom = resultatRequete.getString(3);
			String adresse = resultatRequete.getString(4);
			String codePostal = resultatRequete.getString(5);
			String ville = resultatRequete.getString(6);
			String telephone = resultatRequete.getString(7);
			int conseillerID = resultatRequete.getInt(8);

			client = new Client(idClient, nom, prenom, adresse, codePostal, ville, telephone,conseillerID);
			
			return client;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		
			try {
				if(resultatRequete!= null) resultatRequete.close();
				if(ps!= null) ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;		
	}// end getClientByID


/*======================================================================================================*/
/*==getAllIDClients=====================================================================================*/
/*====================================================================================================*/
	
	/**
	 * Récupération de la liste des identifiants des clients
	 * @return liste des identifiants sous forme d'une liste de int
	 */
	
	@Override
	public List<Integer> getAllIDClients() {
		PreparedStatement ps = null;
		ResultSet resultatRequete =null;
		
		try {
			String requeteSqlGetAll="SELECT id_client FROM clients";
			ps = this.connection.prepareStatement(requeteSqlGetAll);
			
			resultatRequete = ps.executeQuery();

			Client client = null;
			
			List<Integer> listeIDClient = new ArrayList <>();

			while (resultatRequete.next()) {
				int idClient = resultatRequete.getInt(1);
				
				listeIDClient.add(idClient);
	 			
			}//end while
			return listeIDClient;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultatRequete!= null) resultatRequete.close();
				if(ps!= null) ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}//end getAllIDClients

	
	
	
}//end class 
