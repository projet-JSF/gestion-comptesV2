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

/**
 * Managedbean pour la gestion des comptes.
 * @author Marie
 *
 */

@ManagedBean(name="gestionCompte")
@SessionScoped
public class GestionComptesBean implements Serializable{

/*=========================PROPRIETES=========================*/
	
	// Liste des comptes pour alimenter la page accueil.xhtml
	List<Compte> listeComptesBDD;
	
	// Propri�t� compte pour l'ajout et pour l'�dition
	private Compte comptet;
	
	
	//dao des comptes
	ICompteDAO compteDAO;
	
	//dao du conseiller
	IConseillerDAO conseillerDAO;
	
	/*=========================CONSTRUCTEUR=========================*/
	/**
	 * Constructeur vide pour l'instanciation du managedbean <br/>
	 * Instancie la dao du compte
	 */
	
	public GestionComptesBean() {
		compteDAO=new CompteDaoImpl(); 
		conseillerDAO=new ConseillerDaoImpl();
	}//end constructeur
	
	
	/*=========================GETTER/SETTER=========================*/
	
	public List<Compte> getListeComptesBDD() {
		return listeComptesBDD;
	}


	public void setListeComptesBDD(List<Compte> listeComptesBDD) {
		this.listeComptesBDD = listeComptesBDD;
	}


	public Compte getCompte() {
		return compte;
	}


	public void setCompte(Compte compte) {
		this.compte = compte;
	}


	public ICompteDAO getCompteDAO() {
		return compteDAO;
	}


	public void setCompteDAO(ICompteDAO compteDAO) {
		this.compteDAO = compteDAO;
	}
	
	
	
	/*=========================METHODES=========================*/
	
/*====================================================================================================*/	
/*==findAllComptesBdd=================================================================================*/
/*====================================================================================================*/	
	
	/**
	 * Recupere la liste des comptes dans la bdd via la dao. <br/>
	 * Cette methode permet d'alimenter la table dans accueil.xhtml pour affichage
	 * @return
	 */
	public List<Compte> findAllComptesBdd(){
		System.out.println("Je suis dans findAllComptesBdd du MB de Compte");
		
		listeComptesBDD=compteDAO.getAllComptes();

		return listeComptesBDD;
	}//end findAllComptesBdd
	
/*====================================================================================================*/	
/*==findComptesDuConseiller=================================================================================*/
/*====================================================================================================*/	
		
	/**
	 * Recupere la liste des comptes appartenant au conseiller enregistr� via la dao. <br/>
	 * Cette methode permet d'alimenter la table dans accueil.xhtml pour affichage
	 * @return
	 */
	public List<Compte> findComptesDuConseiller(int idConseiller){
		System.out.println("Je suis dans findComptesDuConseiller du MB de Compte");
		
		listeComptesBDD=conseillerDAO.getComptesduConseiller(idConseiller);

		return listeComptesBDD;
	}//end findComptesDuConseiller

/*====================================================================================================*/	
/*==supprimerCompte=================================================================================*/
/*====================================================================================================*/	


	/**
	 * M�thode invoqu�e au click sur le lien 'supprimer' de accueil.xhtml 
	 * Permet de supprimer un compte dans la bdd via la dao
	 */
	
	public void supprimerCompte(ActionEvent event) {
		System.out.println("Je suis dans supprimerCompte du MB de Compte");
		
		//1. r�cup du param pass� dans le composant au click du lien supprimer
		UIParameter cp = (UIParameter) event.getComponent().findComponent("deleteID");
		
		//2. recup de la valeur du param => l'id du compte � supprimer
		int compteID = (int) cp.getValue();
		
		//3. Recup du context
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//4. On test la suppression
		
		if (compteDAO.supprimerCompteByID(compteID)) {
			
			// ========== Suppression OK ========
			/* Envoi d'un message vers la vue via le context */
			
			contextJSF.addMessage(null, new FacesMessage( "Le compte a �t� supprimer avec succ�s"));
			
		}else {
			
			// ========== Suppression NOT OK ========
			/* Envoi d'un lessage vers la vue via le context */
			
			contextJSF.addMessage(null, new FacesMessage("La suppression du compte a �chou�e"));
			
			
		}//end else
	}//end supprimerComptesBdd
	
	
/*====================================================================================================*/	
/*==selectionnerCompte================================================================================*/
/*====================================================================================================*/	

	/**
	 * Invoqu�e au click du bouton Modifier de la page accueil.xhtml <br/>
	 * Permet de stocker les info du compte selectionner dans la propri�t�e 'compte' du managed bean.
	 * @param event
	 */
	
	public void selectionnerCompte(ActionEvent event) {
		System.out.println("Je suis dans selectionnerCompte du MB de Compte");

		//1. r�cup du param pass� dans le composant au click du lien modifier
		UIParameter cp = (UIParameter) event.getComponent().findComponent("updateID");
		
		//2. recup de la valeur du param => l'id du client � modifier
		int compteID = (int) cp.getValue();
		
		//3. recup du compte dans la bdd par l'id
		Compte compteEdit = compteDAO.getCompteByID(compteID);
		
		//4. affectation du compte � modifier � la prop compte du managedbean
		setCompte(compteEdit);
		
		/**
		 * Dans la page editer_compte.xhtml => on recupere le compte � editer via la prop compte du MB
		 */
		
	}//end selectionnerCompte
	
	
	
/*====================================================================================================*/	
/*==modifierCompte====================================================================================*/
/*====================================================================================================*/

	/**
	 * Invoqu�e au click du bouton Modifier de la page modifier-compte.xhtml <br/>
	 * Permet de modifier un compte de la bdd
	 * @param event
	 */
	
	public void modifierCompte(ActionEvent event) {
		System.out.println("Je suis dans modifierCompte du MB de Compte");
		
		//1. R�cup�ration du context JSP	
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//La propri�t� compte du managed bean encapsule les infos du compte � modifier dans la dbb (r�cup�r�es du formulaire)
		
		//On test si la modification s'est bien pass�
		
		if(compteDAO.modifierCompte(compte)) {
			//------------- MODIF OK--------------------
			//Envoie d'un message de r�ussite
			System.out.println("La modif est un succ�s");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
															"Modification r�ussie",
															"Le compte a �t� modifier avec succ�s"));
			
		}else {
			//-------------MODIF NOT OK--------------------------
			//Envoie d'un message d'echec
			System.out.println("La modif est un echec");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
															"Echec de la modification", 
															"La modification n'a ps �t� effectu�"));
		}//end else
		
	}//end modifierCompte
	

/*====================================================================================================*/	
/*==initialiserCompte====================================================================================*/
/*====================================================================================================*/
	
	/**
	 * Invoqu� au click du bouton 'ajouter un compte' de la page 'accueil.xhtml'
	 * Permet d'initialiser un objet de type Compte � vide pour recuperer les valeurs saisies dans le formulaire de la page ajouter_compte.xhtml
	 */
	public void initialiserCompte(ActionEvent event){
		
		System.out.println("Je suis dans initialiserCompte du MB de Compte");
		
		//Instanciation d'un nouvel objet client vide dans lequel on va stocker les infos du nouveau compte via le formulaire
		Compte addCompte = new Compte();
		
		//Affectation de l'objet � la prop compte du MB
		setCompte(addCompte);
		
	}//end initialiserCompte

	
/*====================================================================================================*/	
/*==ajouterCompte====================================================================================*/
/*====================================================================================================*/
	
	/**
	 * Invoqu�e au click du bouton Ajouter de la page ajouter-compte.xhtml <br/>
	 * Permet d'ajouter un nouveau compte � la bdd
	 * @param event
	 */
	public void ajouterCompte(ActionEvent event) {
		System.out.println("Je suis dans ajouterCompte du MB de Compte");
		
		// R�cuperation du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//Ajout du compte dans la bdd
		//-> les infos du nouveau compte ont �t� stock� dans l'objet compte du MB au moment d'envoyer le formulaire
		
		//On test si l'ajout � reussit
		
		if(compteDAO.ajouterCompte(compte)) {
			//-----------------AJOUT OK-------------------
			System.out.println("L'ajout est un succ�s");
			//Envoie d'un message de r�ussite
			
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
					"Ajout r�ussi",
					"Le compte a �t� ajouter avec succ�s"));
		}else {
			//-----------------AJOUT NOT OK----------------
			System.out.println("L'ajout est un echec");
			//Envoie d'un message d'erreur

			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
					"Echec de l'ajout", 
					"L'ajout n'a pas �t� effectu�"));
		}//end else
	}//end ajouterCompte
	
	
}//end classe
