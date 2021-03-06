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
import com.intiformation.gestionComptes.dao.CompteDAOImpl;
import com.intiformation.gestionComptes.dao.ConseillerDaoImpl;
import com.intiformation.gestionComptes.dao.IClientDAO;
import com.intiformation.gestionComptes.dao.ICompteDAO;
import com.intiformation.gestionComptes.dao.IConseillerDAO;
import com.intiformation.gestionComptes.model.Client;
import com.intiformation.gestionComptes.model.Compte;
import com.intiformation.gestionComptes.model.Conseiller;

/**
 * Managedbean pour la gestion des clients
 * @author Laure
 *
 */

@ManagedBean(name="gestionClient")
@SessionScoped
public class GestionClientsBean implements Serializable{
	
	/*=========================PROPRIETES=========================*/
	
	// Liste des clients pour alimenter la page liste_all_client.xhtml
	List<Client> listeClientsBDD;
	
	// Propri�t� client pour l'ajout et pour l'�dition
	private Client client;
	
	//id du client selectionn�, renseign� lorsque l'on clique sur Modifier de la page afficher_client.xhtml
	private int idClientSelectionner;
	
	// Liste des identifiants des conseillers pour les menus de selection des pages modifier_client.xhtml et ajout_client.xhtml 
	private List<Integer> listeIDConseillers;
	
	//Liste des identifiants des clients
	private List<Integer> listeIDClient;
	
	//Liste des comptes du client pour la page afficher_client.xhtml
	private List<Compte> listeComptesDuClient;
	
	
	//dao du client
	IClientDAO clientDAO;
	
	//dao du conseiller
	IConseillerDAO conseillerDAO;
	
	//dao du compte
	ICompteDAO compteDAO;
	
	/*=========================CONSTRUCTEUR=========================*/
	/**
	 * Constructeur vide pour l'instanciation du managedbean <br/>
	 * Instancie la dao du client, du compte et du conseiller
	 */
	
	public GestionClientsBean() {
		clientDAO=new ClientDaoImpl(); 
		conseillerDAO=new ConseillerDaoImpl();
		compteDAO = new CompteDAOImpl();
	}//end constructeur
	
	
	/*=========================GETTER/SETTER=========================*/
	
	public List<Client> getListeClientsBDD() {
		return listeClientsBDD;
	}


	public void setListeClientsBDD(List<Client> listeClientsBDD) {
		this.listeClientsBDD = listeClientsBDD;
	}


	public List<Compte> getListeComptesDuClient() {
		return listeComptesDuClient;
	}


	public void setListeComptesDuClient(List<Compte> listeComptesDuClient) {
		this.listeComptesDuClient = listeComptesDuClient;
	}


	public ICompteDAO getCompteDAO() {
		return compteDAO;
	}


	public void setCompteDAO(ICompteDAO compteDAO) {
		this.compteDAO = compteDAO;
	}


	public Client getClient() {
		return client;
	}


	public void setClient(Client client) {
		this.client = client;
	}


	public IClientDAO getClientDAO() {
		return clientDAO;
	}


	public List<Integer> getListeIDClient() {
		return listeIDClient;
	}


	public int getIdClientSelectionner() {
		return idClientSelectionner;
	}


	public void setIdClientSelectionner(int idClientSelectionner) {
		this.idClientSelectionner = idClientSelectionner;
	}


	public void setListeIDClient(List<Integer> listeIDClient) {
		this.listeIDClient = listeIDClient;
	}


	public IConseillerDAO getConseillerDAO() {
		return conseillerDAO;
	}


	public void setConseillerDAO(IConseillerDAO conseillerDAO) {
		this.conseillerDAO = conseillerDAO;
	}


	public void setClientDAO(IClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}
	
	public List<Integer> getListeIDConseillers() {
		return listeIDConseillers;
	}


	public void setListeIDConseillers(List<Integer> listeIDConseillers) {
		this.listeIDConseillers = listeIDConseillers;
	}
	
	/*=========================METHODES=========================*/
	
/*====================================================================================================*/	
/*==findAllClientsBdd=================================================================================*/
/*====================================================================================================*/	
	
	/**
	 * Recupere la liste des clients dans la bdd via la dao. <br/>
	 * Cette methode permet d'alimenter la table dans liste_all_clients.xhtml pour affichage
	 * @return Liste des clients dans la bdd
	 */
	public List<Client> findAllClientsBdd(){
		System.out.println("Je suis dans findAllClientsBdd du MB de Client");
		
		listeClientsBDD=clientDAO.getAllClients();

		return listeClientsBDD;
	}//end findAllClientsBdd
	
/*====================================================================================================*/	
/*==findClientsDuConseiller=================================================================================*/
/*====================================================================================================*/	
		
	/**
	 * Recupere la liste des clients appartenant au conseiller enregistr� via la dao. <br/>
	 * Cette methode permet d'alimenter la table dans accueil.xhtml pour affichage
	 * @return Liste des clients appartenant au conseiller
	 */
	public List<Client> findClientsDuConseiller(int idConseiller){
		System.out.println("Je suis dans findClientsDuConseiller du MB de Client");
		
		listeClientsBDD=conseillerDAO.getClientsduConseiller(idConseiller);

		return listeClientsBDD;
	}//end findClientsDuConseiller

/*====================================================================================================*/	
/*==supprimerClient=================================================================================*/
/*====================================================================================================*/	
	
	/**
	 * M�thode invoqu�e au click sur le lien 'supprimer' de afficher_client.xhtml </br>
	 * Permet de supprimer un client dans la bdd via la dao
	 */
	
	public void supprimerClient(ActionEvent event) {
		System.out.println("Je suis dans supprimerClient du MB de Client");
		
		//1. recup de l'id du client � supprimer
		int clientID = client.getIdClient();
		System.out.println("Id du client � supprimer :"+ clientID);
		
		//2. Recup du context
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//3. Suppression des comptes associ�s � ce client
		
		List<Compte> listeComptesClientSelectionner = compteDAO.getCompteByIDClient(clientID);
		
		if(!(listeComptesClientSelectionner.isEmpty())) {
			for(Compte compte : listeComptesClientSelectionner) {
				if(compteDAO.supprimerCompteByID(compte.getIdCompte())) {
					contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"R�ussite" , "Les comptes du client ont �t� supprim�s avec succ�s"));
				}else {
					contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Echec" ,"La suppression des comptes du client a �chou�e"));
				}//end else
			}//end for
		}//end if
		
		
		//4. On test la suppression
		
		if (clientDAO.supprimerClientByID(clientID)) {
			
			// ========== Suppression OK ========
			/* Envoi d'un message vers la vue via le context */
			System.out.println("Suppression OK");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,"R�ussite" ,"Le client a �t� supprim� avec succ�s"));
			
		}else {
			
			// ========== Suppression NOT OK ========
			System.out.println("Suppression NOT OK");
			/* Envoi d'un lessage vers la vue via le context */
			
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Echec" ,"La suppression du client a �chou�e"));
			
		}//end else
	}//end supprimerClientsBdd
	
	
	
/*====================================================================================================*/	
/*==selectionnerClientSansParam================================================================================*/
/*====================================================================================================*/	

	/**
	 * Invoqu�e au click du bouton Modifier de la page afficher_client.xhtml <br/>
	 * Permet de stocker les info du client selectionn� dans la propri�t�e 'client' du managed bean.
	 * @param event
	 */
	
	public void selectionnerClientSansParam(ActionEvent event) {
		System.out.println("Je suis dans selectionnerClientSansParam du MB de Client");

		//1. Recup de l'id du client s�lectionn�
		int clientID = getIdClientSelectionner();
		
		//2. recup du client dans la bdd par l'id
		Client clientEdit = clientDAO.getClientByID(clientID);
		
		//3. affectation du client � modifier � la prop client du managedbean
		setClient(clientEdit);
		
		//4. Recup de la liste des conseillers pour le select menu de la page modifier_client.xhtml
		setListeIDConseillers(conseillerDAO.getAllIDConseillers());
		
	}//end selectionnerClient
	
/*====================================================================================================*/	
/*==selectionnerClient================================================================================*/
/*====================================================================================================*/	

	/**
	 * Invoqu�e au click du bouton Afficher de la page accueil.xhtml et liste_all_client.xhtml <br/>
	 * Permet de stocker les info du client selectionn� dans la propri�t�e 'client' du managed bean.
	 * @param event
	 */
		
	public void selectionnerClient(ActionEvent event) {
		System.out.println("Je suis dans selectionnerClient du MB de Client");
		
		//1. r�cup du param pass� dans le composant au click du lien modifier
		UIParameter cp = (UIParameter) event.getComponent().findComponent("afficherID");
			
		//2. recup de la valeur du param => l'id du client � modifier
		int clientID = (int) cp.getValue();
		
		setIdClientSelectionner(clientID);
		
		//3. recup du client dans la bdd par l'id
		Client clientEdit = clientDAO.getClientByID(clientID);
			
		//4. affectation du client � modifier � la prop clientEdit du managedbean
		setClient(clientEdit);
		
//		setListeIDConseillers(conseillerDAO.getAllIDConseillers());


		}//end selectionnerClient	
	
/*====================================================================================================*/	
/*==modifierClient====================================================================================*/
/*====================================================================================================*/

	/**
	 * Invoqu�e au click du bouton Modifier de la page modifier-client.xhtml <br/>
	 * Permet de modifier un client de la bdd
	 * @param event
	 */
	
	public void modifierClient(ActionEvent event) {
		System.out.println("Je suis dans modifierClient du MB de Client");
		
		//1. R�cup�ration du context JSP	
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//La propri�t� client du managed bean encapsule les infos du client � modifier dans la dbb (r�cup�r�es du formulaire)
		
		//On test si la modification s'est bien pass�
		
		if(clientDAO.modifierClient(client)) {
			//------------- MODIF OK--------------------
			//Envoie d'un message de r�ussite
			System.out.println("La modif est un succ�s");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
															"Modification r�ussie",
															"Le client a �t� modifi� avec succ�s"));
			
		}else {
			//-------------MODIF NOT OK--------------------------
			//Envoie d'un message d'echec
			System.out.println("La modif est un echec");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
															"Echec de la modification", 
															"La modification n'a ps �t� effectu�"));
		}//end else
		
	}//end modifierClient
	

/*====================================================================================================*/	
/*==initialiserClient====================================================================================*/
/*====================================================================================================*/
	
	/**
	 * Invoqu� au click du bouton 'ajouter un client' de la bar de menu
	 * Permet d'initialiser un objet de type Client � vide pour recuperer les valeurs saisies dans le formulaire de la page ajouter_client.xhtml
	 */
	public void initialiserClient(ActionEvent event){
		
		System.out.println("Je suis dans initialiserClient du MB de Client");
		
		//Instanciation d'un nouvel objet client vide dans lequel on va stocker les infos du nouveau client via le formulaire
		Client addClient = new Client();
		
		//Affectation de l'objet � la prop client du MB
		setClient(addClient);
		
		//Recup de la liste des conseillers pour le select menu de la page ajouter_client.xhtml
		setListeIDConseillers(conseillerDAO.getAllIDConseillers());
		
	}//end initialiserClient

	
/*====================================================================================================*/	
/*==ajouterClient====================================================================================*/
/*====================================================================================================*/
	
	/**
	 * Invoqu�e au click du bouton Ajouter de la page ajouter-client.xhtml <br/>
	 * Permet d'ajouter un nouveau client � la bdd
	 * @param event
	 */
	public void ajouterClient(ActionEvent event) {
		System.out.println("Je suis dans ajouterClient du MB de Client");
		
		// R�cuperation du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//Ajout du client dans la bdd
		//-> les infos du nouveau client ont �t� stock� dans l'objet client du managed bean au moment d'envoyer le formulaire
		
		//On test si l'ajout a reussit
		
		if(clientDAO.ajouterClient(client)) {
			//-----------------AJOUT OK-------------------
			System.out.println("L'ajout est un succ�s");
			//Envoie d'un message de r�ussite
			
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
					"Ajout r�ussi",
					"Le client a �t� ajout� avec succ�s"));
		}else {
			//-----------------AJOUT NOT OK----------------
			System.out.println("L'ajout est un echec");
			//Envoie d'un message d'erreur

			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
					"Echec de l'ajout", 
					"L'ajout n'a pas �t� effectu�"));
		}//end else
	}//end ajouterClient
	
	
	
	
	
/*====================================================================================================*/	
/*==findlisteIDClients====================================================================================*/
/*====================================================================================================*/
			
	/**
	 * Permet de recuperer la liste des clients pour le select menu de la page chercher_client.xhtml
	 * @param event
	 */
	public void findlisteIDClients() {
		System.out.println("Je suis dans findlisteIDClients du MB de Client");
		
		//Recup de la liste des id des clients
		setListeIDClient(clientDAO.getAllIDClients());

	}//end findlisteIDConseillers
	
/*====================================================================================================*/	
/*==findlisteIDConseillers====================================================================================*/
/*====================================================================================================*/
		
	/**
	 * Permet de recuperer la liste des identifiants des conseillers pour le select menu des pages ajouter_client.xhtml et modifier_client.xhtml
	 */
	
	public List<Integer> findlisteIDConseillers() {
		System.out.println("Je suis dans findlisteIDConseillers du MB de Client");
		
		listeIDConseillers=conseillerDAO.getAllIDConseillers();

		return listeIDConseillers;
	}//end findlisteIDConseillers

/*====================================================================================================*/	
/*==findlisteComptesDuClient==========================================================================*/
/*====================================================================================================*/
			
	/**
	 * Permet de recuperer les comptes du client pour la page afficher_client.xhtml
	 * @param event
	 */
	public List<Compte> findlisteComptesDuClient() {
		System.out.println("Je suis dans findlisteComptesDuClient du MB de Client");

		listeComptesDuClient = compteDAO.getCompteByIDClient(getIdClientSelectionner());

		return listeComptesDuClient;
		
	}//end findlisteComptesDuClient

	
}//end class
