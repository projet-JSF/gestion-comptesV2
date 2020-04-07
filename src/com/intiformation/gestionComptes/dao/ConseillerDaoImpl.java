package com.intiformation.gestionComptes.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.intiformation.gestionComptes.model.Client;
import com.intiformation.gestionComptes.model.Conseiller;



/**
 * Implementation concrete de la couche dao du conseiller 
 * @author IN-MP-018
 *
 */
public class ConseillerDaoImpl implements IConseillerDAO{
	
/*=====================================================================================================*/		
/*==isConseillerExists=================================================================================*/
/*=====================================================================================================*/	
	/**
	 * Verifie si le conseiller existe par rapport à son mail et mot de passe <br/>
	 * Méthode utilisée pour l'identification 
	 * @param pMail = mail du conseiller
	 * @param pMdp = mot de passe du conseiller
	 * @return boolean : true=le conseiller existe / false=le mdp et/ou mail est incorrect
	 */
	
	@Override
	public boolean isConseillerExists(String pMail, String pMdp) {
		PreparedStatement ps = null;
		ResultSet resultatRequete=null;
		
		try {
			
		//1. requete SQL
			String isExistsReq="SELECT COUNT(id_conseiller) FROM conseillers WHERE mail=? AND mot_de_passe=?";
		
		//2. requete préparéee
			ps=this.connection.prepareStatement(isExistsReq);
			
		//2.1 passage de params à la requete préparée
			ps.setString(1,  pMail);
			ps.setString(2,  pMdp);
		
		// 3. execution + recup du resultat 
			resultatRequete=ps.executeQuery();
		
		//4. lecture du resultat 
			resultatRequete.next();
			int verifIfExists=resultatRequete.getInt(1);
			
		//5. renvoi du resultat
			return (verifIfExists==1)?true:false;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultatRequete!= null) resultatRequete.close();
				if(ps!= null) ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}//end finally		
		
		
		return false;
	}// end  isConseillerExists



	
/*====================================================================================================*/	
/*==getAllConseillers=====================================================================================*/
/*====================================================================================================*/

	/**
	* Récupération de la liste des conseillers dans la bdd
	* @return liste des conseillers au format List de Conseillers
	*/	
	
	@Override
	public List<Conseiller> getAllConseillers() {
		PreparedStatement ps = null;
		ResultSet resultatRequete =null;
		
		try {
			String requeteSqlGetAll="SELECT * FROM conseillers";
			ps = this.connection.prepareStatement(requeteSqlGetAll);
			
			resultatRequete = ps.executeQuery();

			Conseiller conseiller = null;
			
			List<Conseiller> listeConseiller = new ArrayList <>();

			while (resultatRequete.next()) {
				int idConseiller = resultatRequete.getInt(1);
				String nom = resultatRequete.getString(2);
				String mail = resultatRequete.getString(3);
				String motDePasse = resultatRequete.getString(4);

				conseiller = new Conseiller(idConseiller, nom, mail, motDePasse);
				
				listeConseiller.add(conseiller);
				
			}//end while
			return listeConseiller;
		
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
	}// end getAllConseillers

	

/*====================================================================================================*/	
/*==ajouterConseiller=====================================================================================*/
/*====================================================================================================*/
		
	/**
	* Ajout d'un conseiller à la base de données
	* 
	* @param conseiller = un objet conseiller contenant toutes les infos du nouveau conseiller
	* @return boolean : true=l'operation s'est bien passé / false= il y a eu une erreur et l'operation n'a pas pu être effectuée
	*/	
	
	
	@Override
	public boolean ajouterConseiller(Conseiller conseiller) {
		PreparedStatement ps = null;
		
		try {
			String requeteSqlAdd= "INSERT INTO  conseillers ( nom, mail, mot_de_passe) VALUES (?, ?, ? )";
			
			ps = this.connection.prepareStatement(requeteSqlAdd);
			
			ps.setString(1, conseiller.getNom());
			ps.setString(2, conseiller.getMail());
			ps.setString(3, conseiller.getMotDePasse());

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
	}// end ajouterConseiller


	
/*=====================================================================================================*/	
/*==modifierClient=====================================================================================*/
/*=====================================================================================================*/
		
	/**
	 * Modification d'un conseiller dans la bdd
	 * 
	 * @param conseiller = un objet conseiller ayant le même id que le conseiller à modifier et contenant toutes les infos nouvelles du nouveau conseiller
	 * @return boolean : true=l'operation s'est bien passé / false= il y a eu une erreur et l'operation n'a pas pu être effectuée
	 */
	
	@Override
	public boolean modifierConseiller(Conseiller conseiller) {
		PreparedStatement ps = null;
		try {
	
			String requeteSqlAdd= "UPDATE conseillers SET nom =?, mail=?, mot_de_passe=? WHERE id_conseiller= ? ";
			
			ps = this.connection.prepareStatement(requeteSqlAdd);
		
			ps.setString(1, conseiller.getNom());
			ps.setString(2, conseiller.getMail());
			ps.setString(3, conseiller.getMotDePasse());
			ps.setInt(4, conseiller.getIdConseiller());
			

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
	}//end modifierConseiller

	
	
/*==========================================================================================================*/	
/*==supprimerConseillerByID=====================================================================================*/
/*==========================================================================================================*/
		
	/**
	 * Suppression d'un conseiller de la base de donnée 
	 * @param pIDConseiller : identifiant du conseiller à supprimer
	 * @return boolean : true=l'operation s'est bien passé / false= il y a eu une erreur et l'operation n'a pas pu être effectuée
	 */	
	
	
	@Override
	public boolean supprimerConseillerByID(int pIDConseiller) {
		PreparedStatement ps = null;
		
		try {
			
			String requeteSqlDelete= "DELETE FROM conseillers WHERE id_conseiller=?";
			
			ps = this.connection.prepareStatement(requeteSqlDelete);
			
			ps.setInt(1, pIDConseiller);

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
	}//end supprimerConseillerByID

	
	
/*====================================================================================================*/
/*==getConseillerByID=====================================================================================*/
/*====================================================================================================*/
	
	/**
	 * Récuperation d'un conseiller de la bdd par son identifiant
	 * @param pIDConseiller : identifiant du conseiller à récuperer
	 * @return conseiller : objet conseiller contenant toutes les informations du conseiller ayant l'id= pIDConseiller
	 * 
	 */	
	
	
	@Override
	public Conseiller getConseillerByID(int pIDConseiller) {
		PreparedStatement ps = null;
		ResultSet resultatRequete =null;
		
		try {
			String requeteSqlGetById="SELECT * FROM conseillers WHERE id_conseiller = ?";
			ps = this.connection.prepareStatement(requeteSqlGetById);
			ps.setInt(1, pIDConseiller);
			
			resultatRequete = ps.executeQuery();
			Conseiller conseiller = null;
			
			resultatRequete.next();
			int idConseiller = resultatRequete.getInt(1);
			String nom = resultatRequete.getString(2);
			String mail = resultatRequete.getString(3);
			String motDePasse = resultatRequete.getString(4);


			conseiller = new Conseiller(idConseiller, nom, mail, motDePasse);
			
			return conseiller;
		
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
	}//end getConseillerByID

	
	
/*======================================================================================================*/
/*==getAllIDConseillers=====================================================================================*/
/*====================================================================================================*/
		
	/**
	* Récupération de la liste des identifiants des conseillers
	* @return liste des identifiants sous forme d'une liste de int
	*/
	
	
	@Override
	public List<Integer> getAllIDConseillers() {
		PreparedStatement ps = null;
		ResultSet resultatRequete =null;
		
		try {
			String requeteSqlGetAll="SELECT id_conseiller FROM conseillers";
			ps = this.connection.prepareStatement(requeteSqlGetAll);
			
			resultatRequete = ps.executeQuery();

			Conseiller conseiller = null;
			
			List<Integer> listeIDConseiller = new ArrayList <>();

			while (resultatRequete.next()) {
				int idConseiller = resultatRequete.getInt(1);
				
				listeIDConseiller.add(idConseiller);
				
			}//end while
			return listeIDConseiller;
		
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
	}//end getAllIDConseillers

	
	
/*======================================================================================================*/
/*==getClientsduConseiller=====================================================================================*/
/*====================================================================================================*/
			
	/**
	* Récupération de la liste des clients d'un conseillers
	* @param pIDConseiller : identifiant du conseiller dont on veut récuperer les clients
	* @return Liste des clients du conseiller
	*/
		

	@Override
	public List<Client> getClientsduConseiller(int pIDConseiller) {
		PreparedStatement ps = null;
		ResultSet resultatRequete =null;
		
		try {
			String requeteSqlGetAll="SELECT * FROM clients WHERE conseiller_id=?";
			ps = this.connection.prepareStatement(requeteSqlGetAll);
			
			ps.setInt(1, pIDConseiller);
			
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
	}//end getClientsduConseiller



	/*====================================================================================================*/
	/*==getConseillerByMailMDP=====================================================================================*/
	/*====================================================================================================*/
		
		/**
		 * Récuperation d'un conseiller de la bdd par son mail et mot de passe
		 * @param pMail : mail du conseiller à récuperer
		 * @param pMdp : mot de passe du conseiller à recuperer
		 * @return conseiller : objet conseiller contenant toutes les informations du conseiller
		 * 
		 */	
	@Override
	public Conseiller getConseillerByMailMDP(String pMail, String pMdp) {
		PreparedStatement ps = null;
		ResultSet resultatRequete =null;
		
		try {
			String requeteSqlGetById="SELECT * FROM conseillers WHERE mail=? AND mot_de_passe=?";
			ps = this.connection.prepareStatement(requeteSqlGetById);
			ps.setString(1,  pMail);
			ps.setString(2,  pMdp);
			
			resultatRequete = ps.executeQuery();
			Conseiller conseiller = null;
			
			resultatRequete.next();
			int idConseiller = resultatRequete.getInt(1);
			String nom = resultatRequete.getString(2);
			String mail = resultatRequete.getString(3);
			String motDePasse = resultatRequete.getString(4);


			conseiller = new Conseiller(idConseiller, nom, mail, motDePasse);
			
			return conseiller;
		
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
	}//end getConseillerByMailMDP


	/*=====================================================================================================*/		
	/*==isEmailAlreadyTaken=================================================================================*/
	/*=====================================================================================================*/	
		/**
		 * Verifie si le conseiller existe par rapport à son mail <br/>
		
		 * @param pMail = mail du conseiller
		
		 * @return boolean : true=le conseiller existe / false=le mdp et/ou mail est incorrect
		 */

		@Override
		public boolean isEmailAlreadyTaken(String pMail) {
			PreparedStatement ps = null;
			ResultSet resultatRequete=null;
			
			try {
				
			//1. requete SQL
				String isExistsReq="SELECT COUNT(id_conseiller) FROM conseillers WHERE mail=?";
			
			//2. requete préparéee
				ps=this.connection.prepareStatement(isExistsReq);
				
			//2.1 passage de params à la requete préparée
				ps.setString(1,  pMail);
			
			
			// 3. execution + recup du resultat 
				resultatRequete=ps.executeQuery();
			
			//4. lecture du resultat 
				resultatRequete.next();
				int verifIfExists=resultatRequete.getInt(1);
				
			//5. renvoi du resultat
				return (verifIfExists!=0)?true:false;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if(resultatRequete!= null) resultatRequete.close();
					if(ps!= null) ps.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}//end finally	
			return false;
		}
	
	

}//end class
