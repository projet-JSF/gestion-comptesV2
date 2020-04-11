package com.intiformation.gestionComptes.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.intiformation.gestionComptes.dao.ClientDaoImpl;
import com.intiformation.gestionComptes.dao.ConseillerDaoImpl;
import com.intiformation.gestionComptes.dao.IClientDAO;
import com.intiformation.gestionComptes.dao.IConseillerDAO;
import com.intiformation.gestionComptes.model.Client;
import com.intiformation.gestionComptes.model.Compte;
import com.intiformation.gestionComptes.model.Conseiller;

/**
 * Managed bean pour la gestion des conseillers
 * @author Valentin
 *
 */
@ManagedBean(name="gestionConseiller")
@SessionScoped
public class GestionConseillersBean implements Serializable{
	
	/*=========================PROPRIETES=========================*/

	
	//liste des conseillers au sein de la banque
	List<Conseiller> listeConseillersBDD;
	
	//propriétés conseiller
	private Conseiller conseiller;
	
	//Identifient du conseiller selectionné
	private int idConseillerSelectionner;
	
	//Liste des clients du conseiller
	private List<Client> listeClientsduConseiller;
	
	//Liste des identifiants du conseiller pour le select menu de la page chercher_conseiller.xhtml
	private List<Integer> listeIDConseillers;
	
	//dao du conseiller
	IConseillerDAO conseillerDAO;

	/*=========================CONSTRUCTEUR=========================*/

	/**
	 * Constructeur vide qui instencie la dao du conseiller 
	 */
	public GestionConseillersBean() {
		conseillerDAO=new ConseillerDaoImpl();
	}
	
	
	/*=========================GETTER/SETTER=========================*/

	public List<Conseiller> getListeConseillersBDD() {
		return listeConseillersBDD;
	}


	public void setListeConseillersBDD(List<Conseiller> listeConseillersBDD) {
		this.listeConseillersBDD = listeConseillersBDD;
	}


	public int getIdConseillerSelectionner() {
		return idConseillerSelectionner;
	}


	public void setIdConseillerSelectionner(int idConseillerSelectionner) {
		this.idConseillerSelectionner = idConseillerSelectionner;
	}


	public IConseillerDAO getConseillerDAO() {
		return conseillerDAO;
	}


	public void setConseillerDAO(IConseillerDAO conseillerDAO) {
		this.conseillerDAO = conseillerDAO;
	}


	public List<Client> getListeClientsduConseiller() {
		return listeClientsduConseiller;
	}


	public void setListeClientsduConseiller(List<Client> listeClientsduConseiller) {
		this.listeClientsduConseiller = listeClientsduConseiller;
	}


	public List<Integer> getListeIDConseillers() {
		return listeIDConseillers;
	}


	public void setListeIDConseillers(List<Integer> listeIDConseillers) {
		this.listeIDConseillers = listeIDConseillers;
	}


	public Conseiller getConseiller() {
		return conseiller;
	}


	public void setConseiller(Conseiller conseiller) {
		this.conseiller = conseiller;
	}
	
	
	
	/*=========================METHODES=========================*/
	
	
/*====================================================================================================*/	
/*==findAllConseillers=================================================================================*/
/*====================================================================================================*/	

	/**
	 * Recupere la liste des conseillers de la base de données <br/>
	 * Pour l'affichage de la table de la page liste_all_conseiller.xhtml
	 * @return Liste des conseillers
	 */
	public List<Conseiller> findAllConseillers(){
		System.out.println("Trouver la liste de tous les conseillers actuellement présents dans la BDD");
		
		listeConseillersBDD=conseillerDAO.getAllConseillers();
		
		return listeConseillersBDD;
	}//end findAllConseillers
	
/*====================================================================================================*/	
/*==findlisteIDConseillers====================================================================================*/
/*====================================================================================================*/
			
	/**
	 * Recupere la liste des identifiants des conseillers <br/>
	 * Permet de remplir le select menu de la page chercher_conseiller.xhtml
	 * @return Liste des identifiants des conseillers (int)
	 */
	public List<Integer> findlisteIDConseillers() {
		System.out.println("Je suis dans findlisteIDConseillers du MB de Client");
			
		listeIDConseillers=conseillerDAO.getAllIDConseillers();

		return listeIDConseillers;
	}//end findlisteIDConseillers
		
/*====================================================================================================*/	
/*==supprimerConseiller=================================================================================*/
/*====================================================================================================*/	

	/**
	 * Supprimer un conseiller dans la BDD via la DAO, au clic du bouton supprimer de la page afficher_conseiller.xhtml
	 */
	public void supprimerConseiller(ActionEvent event) {
		System.out.println("Je suis dans supprimerClient du MB de Client");
		
		//1. recup de l'id du conseiller à supprimer
		int conseillerID = conseiller.getIdConseiller();
		
		//2. Recup du context
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//3. On cherche la liste des clients du conseiller
		
		List<Client> listeCLientsduConseiller = conseillerDAO.getClientsduConseiller(conseillerID);
		
		//4. On test si la liste des clients est vide : oui => on fait la suppression / non => On annule la suppression
		
		if(listeCLientsduConseiller.isEmpty()) { 
			//=> Le conseiller n'est affilié à aucun client
			
			//On test la suppression
			if (conseillerDAO.supprimerConseillerByID(conseillerID)) {
				
				// ========== Suppression OK ========
				/* Envoi d'un message vers la vue via le context */
				System.out.println("Suppression OK");
				contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,"Réussite" ,"Le conseiller a été supprimé avec succès"));
				
			}else {
				
				// ========== Suppression NOT OK ========
				System.out.println("Suppression NOT OK");
				/* Envoi d'un lessage vers la vue via le context */
				
				contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Echec" ,"La suppression du conseiller a échouée"));
				
				
			}//end else
		
		
		}else { 
			//si le conseiller est affilié à des clients, on ne fait pas la suppression
			
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Echec" ,"La suppression du conseiller a échouée : le conseiller est affilié à des clients."));

		}//end else

		
	}//end supprimerConseiller
	
/*====================================================================================================*/	
/*==selectionnerConseillerSansParam================================================================================*/
/*====================================================================================================*/	

	/**
	 * Invoquée au click du bouton Modifier de la page afficher_conseiller.xhtml <br/>
	 * Permet de stocker les info du conseiller affiché dans la propriétée 'conseiller' du managed bean.
	 * @param event
	 */
		
	public void selectionnerConseillerSansParam(ActionEvent event) {
		System.out.println("Je suis dans selectionnerClientSansParam du MB de Client");

		//1. recup de l'id du conseiller à modifier
		int conseillerID = getIdConseillerSelectionner();
		
		//2. recup du conseiller dans la bdd par l'id
		Conseiller conseillerEdit = conseillerDAO.getConseillerByID(conseillerID);
			
		//3. affectation du conseiller à modifier à la prop conseiller du managedbean
		setConseiller(conseillerEdit);


	}//end selectionnerClient
	
/*====================================================================================================*/	
/*==selectionnerConseiller=================================================================================*/
/*====================================================================================================*/	

	/**
	 * Invoquée au click du lien Afficher de la page liste_all_conseiller.xhtml <br/>
	 * Permet de stocker les info du conseiller selectionné dans la propriétée 'conseiller' du managed bean.
	 */
	public void  selectionnerConseiller(ActionEvent event) {
		System.out.println("Pour sélectionner un conseiller dans selectionnerConseiller du MB de Client");
		
		//1. récup du param passé dans le composant au click du lien modifier
		UIParameter cp = (UIParameter) event.getComponent().findComponent("afficherID");
		
		//2. recup de la valeur du param => l'id du conseiller à afficher
		int conseillerID = (int) cp.getValue();
		
		//3. recup du conseiller dans la bdd par l'id
		Conseiller conseillerEdit = conseillerDAO.getConseillerByID(conseillerID);
		
		//4. affectation du conseiller à modifier à la prop conseillerEdit du managedbean
		setConseiller(conseillerEdit);
	}
	

/*====================================================================================================*/	
/*==modifierConseiller=================================================================================*/
/*====================================================================================================*/	

	/**
	 *  Invoquée au click du bouton Modifier de la page modifier_conseiller.xhtml <br/>
	 *  Permet de modifier le conseiller dans la bdd
	 */
	public void modifierConseiller(ActionEvent event) {
		System.out.println("Pour modifier les infos d'un conseiller avec modifierClient du MB de Client");
		
		//1. Récupération du context JSP
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//2. On test si la modification s'est bien passée
		if(conseillerDAO.modifierConseiller(conseiller)) {
			System.out.println("La modification des informations du conseiller est un succès");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
															"Modification réussie",
															"Les informations du conseiller ont été modifiées avec succès"));
		}else {
			System.out.println("La modification est un echec");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
															"Echec de la modification", 
															"La modification n'a pas pu être effectuée"));
		}//end else
	}//end modifierConseiller
	
/*====================================================================================================*/	
/*==initialiserConseiller=================================================================================*/
/*====================================================================================================*/	

	/**
	 * Invoqué au clic de Ajouter un conseiller dans la bar de menu
	 * pour Initialiser un conseiller vide en prévision de la creation d'un nouveau conseiller
	 */
	public void initialiserConseiller(ActionEvent event){
		System.out.println("Je suis dans initialiserConseiller du MB de Conseiller");
		
		//1. Instanciation d'un nouvel objet conseiller vide dans lequel on va stocker les infos du nouveau client via le formulaire

		Conseiller addConseiller = new Conseiller();
		
		//2. Affectation de l'objet à la prop Conseiller du MB
		setConseiller(addConseiller);
	}//end initialiserConseiller

	
	
/*====================================================================================================*/	
/*==ajouterConseiller=================================================================================*/
/*====================================================================================================*/	

	/**
	 * Invoqué au clic de Ajouter dans la page ajouter_conseiller.xhtml
	 * Ajoute un conseiller à la bdd
	 */
	public void ajouterConseiller(ActionEvent event) {
		System.out.println("Je suis dans ajouterConseiller du MB de Client");
		
		//1. Récuperation du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//2. On test si l'ajout a reussit
		if(conseillerDAO.ajouterConseiller(conseiller)) {
			System.out.println("L'ajout est un succès");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
					"Ajout réussi",
					"Le conseiller a été ajouté avec succès"));
		}else {
			System.out.println("L'ajout est un echec");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
					"Echec de l'ajout", 
					"L'ajout n'a pas été effectué"));
		}//end else
	}//end ajouterConseiller
	
	
/*====================================================================================================*/	
/*==findlisteClientsDuConseiller====================================================================================*/
/*====================================================================================================*/
				
	/**
	 * 
	 * Permet d'afficher les clients du conseiller
	 * @param event
	 */
	public List<Client> findlisteClientsDuConseiller() {
		System.out.println("Je suis dans findlisteComptesDuClient du MB de Client");
		
		listeClientsduConseiller = conseillerDAO.getClientsduConseiller(getIdConseillerSelectionner());
			
		return listeClientsduConseiller;
			
	}//end findlisteClientsDuConseiller

}//end class	

