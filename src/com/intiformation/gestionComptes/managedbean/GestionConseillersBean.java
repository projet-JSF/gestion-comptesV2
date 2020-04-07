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
 * managed bean pour la gestion des conseillers
 * @author IN-DF-019
 *
 */
@ManagedBean(name="GestionConseiller")
@SessionScoped
public class GestionConseillersBean implements Serializable{
	
	/*=========================PROPRIETES=========================*/

	
	//liste des conseillers au sein de la banque
	List<Conseiller> listeConseillersBDD;
	
	//propriétés conseillers
	private Conseiller conseiller;
	
	//autres propriétés utiles aux méthodes
	private int idConseillerSelectionner;
	private List<Client> listeClientsduConseiller;
	
	//dao du conseiller
	IConseillerDAO conseillerDAO;

	/*=========================CONSTRUCTEUR=========================*/

	
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
	 * récupération de la liste des conseillers en activité
	 */
	public List<Conseiller> findAllConseillers(){
		System.out.println("Trouver la liste de tous les conseillers actuellement présents dans la BDD");
		listeConseillersBDD=conseillerDAO.getAllConseillers();
		return listeConseillersBDD;
	}
	
	
/*====================================================================================================*/	
/*==supprimerConseiller=================================================================================*/
/*====================================================================================================*/	

	/**
	 * Supprimer un conseiller dans la BDD via la DAO, au clic sur le lien supprimer
	 */
	public void supprimerConseiller(ActionEvent event) {
		System.out.println("Je suis dans supprimerClient du MB de Client");
		
		//1. recup de l'id du conseiller à supprimer
		int conseillerID = conseiller.getIdConseiller();
		System.out.println("Id du conseiller à supprimer :"+ conseillerID);
		
		//3. Recup du context
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//Suppression des comptes associés à ce client
		
		List<Client> listeCLientsduConseiller = conseillerDAO.getClientsduConseiller(conseillerID);
		
		if(listeCLientsduConseiller.isEmpty()) { //on test si le conseiller a des client. 
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
		
		
		}else { //si le conseiller est affilié à des clients, on ne fait pas la suppression
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Echec" ,"La suppression du conseiller a échouée : le conseiller est affilié à des clients."));

		}//end else

		
	}//end supprimerConseiller
	
/*====================================================================================================*/	
/*==selectionnerClientSansParam================================================================================*/
/*====================================================================================================*/	

	/**
	 * Invoquée au click du bouton Afficher de la page liste_conseillers.xhtml <br/>
	 * Permet de stocker les info du conseiller selectionner dans la propriétée 'conseiller' du managed bean.
	 * @param event
	 */
		
	public void selectionnerClientSansParam(ActionEvent event) {
		System.out.println("Je suis dans selectionnerClientSansParam du MB de Client");

		//1. recup de l'id du conseiller à modifier
		int conseillerID = conseiller.getIdConseiller();
			
		//2. recup du conseiller dans la bdd par l'id
		Conseiller conseillerEdit = conseillerDAO.getConseillerByID(conseillerID);
			
		//3. affectation du conseiller à modifier à la prop conseiller du managedbean
		setConseiller(conseillerEdit);


	}//end selectionnerClient
	
/*====================================================================================================*/	
/*==selectionnerConseiller=================================================================================*/
/*====================================================================================================*/	

	/**
	 * permet d'obtenir des informations sur un conseiller
	 */
	public void  selectionnerConseiller(ActionEvent event) {
		System.out.println("Pour sélectionner un conseiller dans selectionnerConseiller du MB de Client");
		
		//1. récup du param passé dans le composant au click du lien modifier
		UIParameter cp = (UIParameter) event.getComponent().findComponent("updateID");
		
		//2. recup de la valeur du param => l'id du conseiller à modifier
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
	 * permet de modifier les informations concernant un conseiller
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
	 * pour Initialiser un conseiller vide en révision de la creation d'un nouveau client
	 */
	public void initialiserConseiller(ActionEvent event){
		System.out.println("Je suis dans initialiserConseiller du MB de Conseiller");
		
		//Instanciation d'un nouvel objet conseiller vide dans lequel on va stocker les infos du nouveau client via le formulaire

		Conseiller addConseiller = new Conseiller();
		
		//Affectation de l'objet à la prop Conseiller du MB
		setConseiller(addConseiller);
	}//end initialiserConseiller

	
	
/*====================================================================================================*/	
/*==ajouterConseiller=================================================================================*/
/*====================================================================================================*/	

	/**
	 * Ajoute un conseiller à la bdd
	 */
	public void ajouterConseiller(ActionEvent event) {
		System.out.println("Je suis dans ajouterConseiller du MB de Client");
		
		// Récuperation du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//On test si l'ajout a reussit
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
	 * Invoquée au click du bouton Voir les comptes de la page afficher-client.xhtml <br/>
	 * Permet d'afficher les comptes du client
	 * @param event
	 */
	public List<Client> findlisteClientsDuConseiller() {
		System.out.println("Je suis dans findlisteComptesDuClient du MB de Client");
		System.out.println(getIdConseillerSelectionner());
		
		listeClientsduConseiller = conseillerDAO.getClientsduConseiller(getIdConseillerSelectionner());
		System.out.println(listeClientsduConseiller);
			
		return listeClientsduConseiller;
			
	}//end findlisteClientsDuConseiller

}//end class	

