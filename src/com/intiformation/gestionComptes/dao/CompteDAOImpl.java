 package com.intiformation.gestionComptes.dao;

import java.util.List;

import com.intiformation.gestionComptes.model.Compte;
import com.intiformation.gestionComptes.model.CompteCourant;
import com.intiformation.gestionComptes.model.CompteEpargne;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompteDAOImpl implements ICompteDAO{

	
/*====================================================================================================*/	
/*==getAllComptes=====================================================================================*/
/*====================================================================================================*/
	
	/**
	 * Récupération de la liste des comptes dans la bdd
	 * @return liste des clients au format List de Comptes
	 */
	
	@Override
	public List<Compte> getAllComptes() {
		PreparedStatement ps = null; 
		ResultSet resultatRequete =null;
		
		try {
			String requeteSqlGetAll="SELECT * FROM comptes";
			ps = this.connection.prepareStatement(requeteSqlGetAll);
			
			resultatRequete = ps.executeQuery();

			Compte compte = null;
			
			List<Compte> listeCompte = new ArrayList <>();

			while (resultatRequete.next()) {
				int idCompte = resultatRequete.getInt(1);
				int clientID = resultatRequete.getInt(2);
				double solde = resultatRequete.getDouble(3);
				String typeCompte = resultatRequete.getString(4);
				double taux = resultatRequete.getInt(5);
				double decouvert  = resultatRequete.getInt(6);

				compte= new Compte(idCompte, solde, typeCompte, clientID, decouvert, taux);

				listeCompte.add(compte);
				
			}//end while
			return listeCompte;
		
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
	}// end getAllComptes


	
/*====================================================================================================*/	
/*==getCompteByID=====================================================================================*/
/*====================================================================================================*/
	
	/**
	 * Récuperation d'un compte de la bdd par son identifiant
	 * @param pIDClient : identifiant du compte à récuperer
	 * @return client : objet compte contenant toutes les informations du client ayant l'id= pIDCompte
	 * 
	 */	
	
	@Override
	public Compte getCompteByID(int pIDCompte) {
		PreparedStatement ps = null;
		ResultSet resultatRequete =null;
		
		try {
			String requeteSqlGetById="SELECT * FROM comptes WHERE id_compte = ?";
			ps = this.connection.prepareStatement(requeteSqlGetById);
			ps.setInt(1, pIDCompte);
			
			resultatRequete = ps.executeQuery();
			Compte compte = null;
			
			resultatRequete.next();
			
			int idCompte = resultatRequete.getInt(1);
			int clientID = resultatRequete.getInt(2);
			double solde = resultatRequete.getDouble(3);
			String typeCompte = resultatRequete.getString(4);
			double taux = resultatRequete.getInt(5);
			double decouvert  = resultatRequete.getInt(6);

			compte= new Compte(idCompte, solde, typeCompte, clientID, decouvert, taux);
			
			return compte;
		
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
	}// end getCompteByID

	
/*==========================================================================================================*/	
/*==supprimerCompteByID=====================================================================================*/
/*==========================================================================================================*/
		
	/**
	* Suppression d'un compte de la base de donnée 
	* @param pIDCompte : identifiant du compte à supprimer
	* @return boolean : true=l'operation s'est bien passé / false= il y a eu une erreur et l'operation n'a pas pu être effectuée
	*/	
	
	@Override
	public boolean supprimerCompteByID(int pIDCompte) {
		PreparedStatement ps = null;
	
		try {
			
			String requeteSqlDelete= "DELETE FROM comptes WHERE id_compte=?";
			
			ps = this.connection.prepareStatement(requeteSqlDelete);
			
			ps.setInt(1, pIDCompte);

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
	}// end supprimerCompteByID
	
	
/*======================================================================================================*/
/*==getAllIDComptes=====================================================================================*/
/*====================================================================================================*/
		
	/**
	* Récupération de la liste des identifiants des comptes
	* @return liste des identifiants sous forme d'une liste de int
	*/	
	
	@Override
	public List<Integer> getAllIDComptes() {
		PreparedStatement ps = null;
		ResultSet resultatRequete =null;
		
		try {
			String requeteSqlGetAll="SELECT id_compte FROM comptes";
			ps = this.connection.prepareStatement(requeteSqlGetAll);
			
			resultatRequete = ps.executeQuery();
			
			List<Integer> listeIDCompte = new ArrayList <>();

			while (resultatRequete.next()) {
				int idCompte = resultatRequete.getInt(1);
				
				listeIDCompte.add(idCompte);
				
			}//end while
			return listeIDCompte;
		
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
	}//end getAllIDComptes
	
	
	
/*====================================================================================================*/	
/*==ajouterCompte=====================================================================================*/
/*====================================================================================================*/
		
	/**
	* Ajout d'un compte à la base de données
	* 
	* @param compte = un objet compte contenant toutes les infos du nouveau compte
	* @return boolean : true=l'operation s'est bien passé / false= il y a eu une erreur et l'operation n'a pas pu être effectuée
	*/	
	
	@Override
	public boolean ajouterCompte(Compte compte) {
		PreparedStatement ps = null;
		
		try {
			String requeteSqlAdd= "INSERT INTO  comptes ( client_id , solde, type_compte, taux, decouvert) VALUES (?, ?, ?, ?, ?)";
			
			ps = this.connection.prepareStatement(requeteSqlAdd);
			
			if(compte.getClientID()==0) {
				ps.setString(1, null);
			}else {
				ps.setInt(1, compte.getClientID());
			}
			
			ps.setDouble(2, compte.getSolde());
			ps.setString(3, compte.getTypeCompte());
			ps.setDouble(4, compte.getTaux());
			ps.setDouble(5, compte.getDecouvert());

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
	}// end ajouterCompte

	
/*=====================================================================================================*/	
/*==modifierCompte=====================================================================================*/
/*=====================================================================================================*/
		
	/**
	* Modification d'un compte dans la bdd
	* 
	* @param compte = un objet compte ayant le même id que le compte à modifier et contenant toutes les infos nouvelles du nouveau compte
	* @return boolean : true=l'operation s'est bien passé / false= il y a eu une erreur et l'operation n'a pas pu être effectuée
	*/	
	
	@Override
	public boolean modifierCompte(Compte compte) {
		PreparedStatement ps = null;
		
		try {
			String requeteSqlAdd= "UPDATE comptes SET client_id=? , solde=?, type_compte=? , decouvert=?, taux=? WHERE id_compte= ? ";
			
			ps = this.connection.prepareStatement(requeteSqlAdd);
			
			ps.setInt(1, compte.getClientID());
			ps.setDouble(2, compte.getSolde());
			ps.setString(3, compte.getTypeCompte());
			ps.setDouble(4, compte.getDecouvert());
			ps.setDouble(5, compte.getTaux());
			ps.setInt(6, compte.getIdCompte());

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
	}// end modifierCompte


	
/*=====================================================================================================*/	
/*==getCompteByIDClient=====================================================================================*/
/*=====================================================================================================*/
			
	/**
	* Récuperation de la liste des comptes appartenant à un client 
	* 
	* @param compte = un objet compte ayant le même id que le compte à modifier et contenant toutes les infos nouvelles du nouveau compte
	* @return boolean : true=l'operation s'est bien passé / false= il y a eu une erreur et l'operation n'a pas pu être effectuée
	*/	
	@Override
	public List<Compte> getCompteByIDClient(int idClient) {
		PreparedStatement ps = null;
		ResultSet resultatRequete =null;
		
		try {
			String requeteSqlGetById="SELECT * FROM comptes WHERE client_id = ?";
			ps = this.connection.prepareStatement(requeteSqlGetById);
			ps.setInt(1, idClient);
			
			resultatRequete = ps.executeQuery();
			Compte compte = null;
			List<Compte> listeCompte = new ArrayList <>();

			while (resultatRequete.next()) {
				int idCompte = resultatRequete.getInt(1);
				int clientID = resultatRequete.getInt(2);
				double solde = resultatRequete.getDouble(3);
				String typeCompte = resultatRequete.getString(4);
				double taux = resultatRequete.getInt(5);
				double decouvert  = resultatRequete.getInt(6);


				if (typeCompte.equals("epargne")) {
					compte = new CompteEpargne(idCompte, solde, clientID, taux);
				}else {
					compte = new CompteCourant(idCompte, solde, clientID, decouvert);
				}
				
				listeCompte.add(compte);
				
			}//end while
			return listeCompte;
			
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
	}//getCompteByIDClient


/*=====================================================================================================*/	
/*==setClient=====================================================================================*/
/*=====================================================================================================*/
				
	/**
	* Attribution d'un client à un compte
	* 
	* @param id_compte = identifiant du compte pour lequel on veut modifier le client
	* @param idClient = identifiant du client auquel on veut attribuer le compte
	* @return boolean : true=l'operation s'est bien passé / false= il y a eu une erreur et l'operation n'a pas pu être effectuée
	*/	
	
	@Override
	public boolean setClient(int id_compte, int idClient) {
		PreparedStatement ps = null;
		
		try {
			String requeteSqlAdd= "UPDATE comptes SET client_id=? WHERE id_compte= ? ";
			
			ps = this.connection.prepareStatement(requeteSqlAdd);
			

			ps.setDouble(1, idClient);
			ps.setInt(2, id_compte);
			
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
	}//end setClient


/*=====================================================================================================*/	
/*==deposit=====================================================================================*/
/*=====================================================================================================*/
					
	/**
	* Credit d'un certain montant sur un compte <br/> 
	* 
	* @param compte = objet compte contenant les infos (incluant l'id) du compte à modifier 
	* @param montant = montant à crediter
	* @return boolean : true=l'operation s'est bien passé / false= il y a eu une erreur et l'operation n'a pas pu être effectuée
	*/	
	
	@Override
	public boolean deposit(Compte compte, double montant) {
		PreparedStatement ps = null;
		double solde = compte.getSolde();
		int id_compte = compte.getIdCompte();
		
		try {
			String requeteSqlAdd= "UPDATE comptes SET solde=? WHERE id_compte= ? ";
			
			ps = this.connection.prepareStatement(requeteSqlAdd);
			
			ps.setDouble(1, solde+montant);
			ps.setInt(2, id_compte);

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
	}//end deposit

/*=====================================================================================================*/	
/*==withdraw=====================================================================================*/
/*=====================================================================================================*/
						
	/**
	* Debit d'un certain montant sur un compte <br/> 
	* Attention : ne contient aucune vérification du solde du compte.
	* 
	* @param compte = objet compte contenant les infos (incluant l'id) du compte à modifier 
	* @param montant = montant à debiter
	* @return boolean : true=l'operation s'est bien passé / false= il y a eu une erreur et l'operation n'a pas pu être effectuée
	*/	
	
	@Override
	public boolean withdraw(Compte compte, double montant) {
		
		PreparedStatement ps = null;
		double solde = compte.getSolde();
		int id_compte = compte.getIdCompte();
		
		try {
			String requeteSqlAdd= "UPDATE comptes SET solde=? WHERE id_compte= ? ";
				
			ps = this.connection.prepareStatement(requeteSqlAdd);
	
				
			ps.setDouble(1, solde-montant);
			ps.setInt(2, id_compte);
	
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
		}//end try catch

	return false;
	}//end withdraw


/*=====================================================================================================*/	
/*==transfert=====================================================================================*/
/*=====================================================================================================*/
							
	/**
	* Transfert d'un certain montant d'un compte donneur à un compte receveur <br/>
	* = débit du compte donneur et credit du compte receveur <br/> 
	* Attention : ne contient aucune vérification du solde du compte donneur.
	* 
	* @param compteDonneur =  compte contenant les infos (incluant l'id) du compte à débiter
	* @param compteReceveur =  compte contenant les infos (incluant l'id) du compte à crediter
	* @param montant = montant à transferer
	* @return boolean : true=l'operation s'est bien passé / false= il y a eu une erreur et l'operation n'a pas pu être effectuée
	*/	
	
	@Override
	public boolean transfert(Compte compteDonneur,  Compte compteReceveur, double montant) {
		PreparedStatement ps = null;
		
		double soldeReceveur = compteReceveur.getSolde();
		int id_compteReceveur = compteReceveur.getIdCompte();
		
		
		double soldeDonneur = compteDonneur.getSolde();
		int id_compteDonneur = compteDonneur.getIdCompte();
		
		try {
			String requeteSqlRemove= "UPDATE comptes SET solde=? WHERE id_compte= ? ";
			
			ps = this.connection.prepareStatement(requeteSqlRemove);

			ps.setDouble(1, soldeDonneur-montant);
			ps.setInt(2, id_compteDonneur);

			int verifTransfertDonneur = ps.executeUpdate();
			
			String requeteSqlAdd= "UPDATE comptes SET solde=? WHERE id_compte= ? ";
			
			ps = this.connection.prepareStatement(requeteSqlAdd);

			ps.setDouble(1, soldeReceveur+montant);
			ps.setInt(2, id_compteReceveur);

			int verifTransfertReceveur = ps.executeUpdate();

			return (verifTransfertDonneur+verifTransfertReceveur==2);
			
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			try {
				if(ps!= null) ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}//end try catch

		return false;

	}//end transfert 


	
	
	

}// end class
