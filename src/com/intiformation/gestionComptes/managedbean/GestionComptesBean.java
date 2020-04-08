package com.intiformation.gestionComptes.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
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
	private Compte compte;
	
	//autres props utils aux differentes fonctions
	private int idCompteSelectionner;
	
	private List<Integer> listeIDCompte;
	private List<Integer> listeIDClient;
	
	private List<Compte> listeComptesDuConseillerLogged;

	//Props pour le virement
	private int idCompteReceveur;
	private int idCompteDonneur;
	private double montantVirement;
	
	//Props debits credit
	private double montantDebit;
	private double montantCredit;
	
	//dao des comptes
	ICompteDAO compteDAO;
	
	IClientDAO clientDAO;
	
	//dao du conseiller
	IConseillerDAO conseillerDAO;
	
	/*=========================CONSTRUCTEUR=========================*/
	/**
	 * Constructeur vide pour l'instanciation du managedbean <br/>
	 * Instancie la dao du compte
	 */
	
	public GestionComptesBean() {
		compteDAO=new CompteDAOImpl(); 
		conseillerDAO=new ConseillerDaoImpl();
		clientDAO=new ClientDaoImpl(); 
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


	public int getIdCompteReceveur() {
		return idCompteReceveur;
	}


	public void setIdCompteReceveur(int idCompteReceveur) {
		this.idCompteReceveur = idCompteReceveur;
	}


	public int getIdCompteDonneur() {
		return idCompteDonneur;
	}


	public void setIdCompteDonneur(int idCompteDonneur) {
		this.idCompteDonneur = idCompteDonneur;
	}


	public double getMontantVirement() {
		return montantVirement;
	}


	public void setMontantVirement(double montantVirement) {
		this.montantVirement = montantVirement;
	}


	public IClientDAO getClientDAO() {
		return clientDAO;
	}


	public void setClientDAO(IClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}


	public List<Integer> getListeIDClient() {
		return listeIDClient;
	}


	public void setListeIDClient(List<Integer> listeIDClient) {
		this.listeIDClient = listeIDClient;
	}


	public void setCompte(Compte compte) {
		this.compte = compte;
	}


	public ICompteDAO getCompteDAO() {
		return compteDAO;
	}


	public List<Integer> getListeIDCompte() {
		return listeIDCompte;
	}


	public void setListeIDCompte(List<Integer> listeIDCompte) {
		this.listeIDCompte = listeIDCompte;
	}


	public void setCompteDAO(ICompteDAO compteDAO) {
		this.compteDAO = compteDAO;
	}
	
	public int getIdCompteSelectionner() {
		return idCompteSelectionner;
	}


	public void setIdCompteSelectionner(int idCompteSelectionner) {
		this.idCompteSelectionner = idCompteSelectionner;
	}


	public double getMontantDebit() {
		return montantDebit;
	}


	public void setMontantDebit(double montantDebit) {
		this.montantDebit = montantDebit;
	}


	public double getMontantCredit() {
		return montantCredit;
	}


	public void setMontantCredit(double montantCredit) {
		this.montantCredit = montantCredit;
	}


	public List<Compte> getListeComptesDuConseillerLogged() {
		return listeComptesDuConseillerLogged;
	}


	public void setListeComptesDuConseillerLogged(List<Compte> listeComptesDuConseillerLogged) {
		this.listeComptesDuConseillerLogged = listeComptesDuConseillerLogged;
	}


	public IConseillerDAO getConseillerDAO() {
		return conseillerDAO;
	}


	public void setConseillerDAO(IConseillerDAO conseillerDAO) {
		this.conseillerDAO = conseillerDAO;
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
		List<Client> listeClientsduConseiller=conseillerDAO.getClientsduConseiller(idConseiller);
	
		List<Compte> listeCompteDuClient = new ArrayList<>();
		listeComptesDuConseillerLogged= new ArrayList<>();
		
		
		for(Client client : listeClientsduConseiller) {
			
			listeCompteDuClient=compteDAO.getCompteByIDClient(client.getIdClient());
			listeComptesDuConseillerLogged.addAll(listeCompteDuClient);
		}

		
		return listeComptesDuConseillerLogged;
	}//end findComptesDuConseiller

/*====================================================================================================*/	
/*==supprimerCompte=================================================================================*/
/*====================================================================================================*/	
	/**
	 * M�thode invoqu�e au click sur le lien 'supprimer' de accueil.xhtml 
	 * Permet de supprimer un client dans la bdd via la dao
	 */
	
	public void supprimerCompte(ActionEvent event) {
		System.out.println("Je suis dans supprimerCompte du MB de Compte");
		
		System.out.println(getCompte().toString());
		
		//2. recup de l'id du client � supprimer
		int compteID = compte.getIdCompte();
		System.out.println("Id du compte � supprimer :"+ compteID);
		
		//3. Recup du context
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//4. On test la suppression
		
		if (compteDAO.supprimerCompteByID(compteID)) {
			
			// ========== Suppression OK ========
			/* Envoi d'un message vers la vue via le context */
			System.out.println("Suppression OK");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,"R�ussite" ,"Le compte a �t� supprim� avec succ�s"));
			
		}else {
			
			// ========== Suppression NOT OK ========
			System.out.println("Suppression NOT OK");
			/* Envoi d'un lessage vers la vue via le context */
			
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Echec" , "La suppression du compte a �chou�e"));
			
			
		}//end else
	}//end supprimerClientsBdd

//	/**
//	 * M�thode invoqu�e au click sur le lien 'supprimer' de accueil.xhtml 
//	 * Permet de supprimer un compte dans la bdd via la dao
//	 */
//	
//	public void supprimerCompte(ActionEvent event) {
//		System.out.println("Je suis dans supprimerCompte du MB de Compte");
//		
//		//1. r�cup du param pass� dans le composant au click du lien supprimer
//		UIParameter cp = (UIParameter) event.getComponent().findComponent("deleteID");
//		
//		//2. recup de la valeur du param => l'id du compte � supprimer
//		int compteID = (int) cp.getValue();
//		
//		//3. Recup du context
//		FacesContext contextJSF = FacesContext.getCurrentInstance();
//		
//		//4. On test la suppression
//		
//		if (compteDAO.supprimerCompteByID(compteID)) {
//			
//			// ========== Suppression OK ========
//			/* Envoi d'un message vers la vue via le context */
//			
//			contextJSF.addMessage(null, new FacesMessage( "Le compte a �t� supprimer avec succ�s"));
//			
//		}else {
//			
//			// ========== Suppression NOT OK ========
//			/* Envoi d'un lessage vers la vue via le context */
//			
//			contextJSF.addMessage(null, new FacesMessage("La suppression du compte a �chou�e"));
//			
//			
//		}//end else
//	}//end supprimerComptesBdd
	
/*====================================================================================================*/	
/*==selectionnerClientSansParam================================================================================*/
/*====================================================================================================*/	

	/**
	 * Invoqu�e au click du bouton Afficher de la page accueil.xhtml <br/>
	 * Permet de stocker les info du client selectionner dans la propri�t�e 'client' du managed bean.
	 * @param event
	 */
		
	public void selectionnerCompteSansParam(ActionEvent event) {
		System.out.println("Je suis dans selectionnerClientSansParam du MB de Client");

		int compteID = getIdCompteSelectionner();
			
		//3. recup du client dans la bdd par l'id
		Compte compteEdit = compteDAO.getCompteByID(compteID);
			
		//4. affectation du client � modifier � la prop client du managedbean
		setCompte(compteEdit);
		
		//Recup de la liste des id des clients
		setListeIDClient(clientDAO.getAllIDClients());
		System.out.println("Liste id client = "+ clientDAO.getAllIDClients());
		
		
		}//end selectionnerClient
		
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
		UIParameter cp = (UIParameter) event.getComponent().findComponent("afficherID");
		
		//2. recup de la valeur du param => l'id du client � modifier
		int compteID = (int) cp.getValue();
		
		//3. recup du compte dans la bdd par l'id
		Compte compteEdit = compteDAO.getCompteByID(compteID);
		
		//4. affectation du compte � modifier � la prop compte du managedbean
		setCompte(compteEdit);
		
		//Recup de la liste des id des clients
		setListeIDClient(clientDAO.getAllIDClients());
		System.out.println("Liste id client = "+ clientDAO.getAllIDClients());
		
		
		
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
		
		//On remet � 0 le taux/decouvert en fonction du type de compte
		if(compte.getTypeCompte().equals("courant")) {
			compte.setTaux(0);
		}else {
			compte.setDecouvert(0);
		}
		
		//La propri�t� compte du managed bean encapsule les infos du compte � modifier dans la dbb (r�cup�r�es du formulaire)
		
		//On test si la modification s'est bien pass�
		
		if(compteDAO.modifierCompte(compte)) {
			//------------- MODIF OK--------------------
			//Envoie d'un message de r�ussite
			System.out.println("La modif est un succ�s");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
															"Modification r�ussie",
															"Le compte a �t� modifi� avec succ�s"));
			
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
		
		//Recup de la liste des id des clients
		setListeIDClient(clientDAO.getAllIDClients());
		System.out.println("Liste id client = "+ clientDAO.getAllIDClients());
		
		
		
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
		
		//On remet � 0 le taux/decouvert en fonction du type de compte
		if(compte.getTypeCompte().equals("courant")) {
			compte.setTaux(0);
		}else {
			compte.setDecouvert(0);
		}
		
		
		//Ajout du compte dans la bdd
		//-> les infos du nouveau compte ont �t� stock� dans l'objet compte du MB au moment d'envoyer le formulaire
		
		
		//On test si l'ajout � reussit
		
		if(compteDAO.ajouterCompte(compte)) {
			//-----------------AJOUT OK-------------------
			System.out.println("L'ajout est un succ�s");
			//Envoie d'un message de r�ussite
			
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
					"Ajout r�ussi",
					"Le compte a �t� ajout� avec succ�s"));
		}else {
			//-----------------AJOUT NOT OK----------------
			System.out.println("L'ajout est un echec");
			//Envoie d'un message d'erreur

			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
					"Echec de l'ajout", 
					"L'ajout n'a pas �t� effectu�"));
		}//end else
	}//end ajouterCompte

	
/*====================================================================================================*/	
/*==findlisteIDComptes====================================================================================*/
/*====================================================================================================*/
				
	/**
	 * Invoqu�e au click du bouton Ajouter de la page ajouter-client.xhtml <br/>
	 * Permet d'ajouter un nouveau client � la bdd
	 * @param event
	 */
	public void findlisteIDComptes() {
		System.out.println("Je suis dans findlisteIDClients du MB de Client");
		System.out.println("Liste clients :" + compteDAO.getAllIDComptes());

			
		setListeIDCompte(compteDAO.getAllIDComptes());

	}//end findlisteIDComptes	
	
/*====================================================================================================*/	
/*==creditCompte====================================================================================*/
/*====================================================================================================*/
		
	/**
	 * Permet d'effectuer un depot sur un compte, au click du bouton 'Cr�diter' de la page 'afficher_compte.xhtml'
	 * @param event
	 */
	public void creditCompte (ActionEvent event) {
		System.out.println("Je suis dans creditCompte du MB de Compte");
			
		// R�cuperation du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
			
		//La propri�t� compte du managed bean encapsule les infos du compte � modifier dans la dbb (r�cup�r�es du formulaire) = modification solde
					//+ ajout du montant
		//La propri�t� montantCredit renseigne le montant � crediter. Il est renseign� par le champ du formulaire de la page afficher_compte.xhtml
		
		
		//On test si le credit s'est bien pass�
					
		if(compteDAO.deposit(compte, montantCredit)) {
			//------------- DEPOT OK--------------------
			//Envoie d'un message de r�ussite
			System.out.println("Le d�p�t est un succ�s");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
														"D�p�t effectu�",
														"Le compte a �t� modifier avec succ�s"));
			

			setCompte(compteDAO.getCompteByID(compte.getIdCompte())); //On met � jour l'objet compte 
			setMontantCredit(0); //on remet le montant de credit � 0
						
		}else {
			//-------------DEPOT NOT OK--------------------------
			//Envoie d'un message d'echec
			System.out.println("Le d�p�t est un echec");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
															"Echec du d�p�t", 
															"La modification n'a pas �t� effectu�"));
		}//end else
					
	}//end creditCompte

	

/*====================================================================================================*/	
/*==findComptesDuConseiller=================================================================================*/
/*====================================================================================================*/	

		
	/**
	 * Recupere la liste des comptes appartenant au conseiller enregistr� via la dao. <br/>
	 * Cette methode permet d'alimenter la table dans accueil.xhtml pour affichage
	 * @return
	 */
	public int findConseillerduCompte(int idCompte){
		System.out.println("Je suis dans findConseillerduCompte du MB de Compte");
		
		int IDclient = compteDAO.getCompteByID(idCompte).getClientID();
		
		Client client = clientDAO.getClientByID(IDclient);
		
		int idConseiller = client.getConseillerId();
				
		return idConseiller;
	}//end findComptesDuConseiller

	
	
/*====================================================================================================*/	
/*==debitCompte====================================================================================*/
/*====================================================================================================*/
			
	/**
	* Permet d'effectuer un retrait sur un compteDonneur et un ajout sur un compteReceveur, au click du bouton 'D�biter' de la page 'afficher_compte.xhtml'
	* @param event
	*/
	public void debitCompte (ActionEvent event) {
		System.out.println("Je suis dans debitCompte du MB de Compte");
				
		// R�cuperation du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
				
		//La propri�t� compte du managed bean encapsule les infos du compte � modifier dans la dbb (r�cup�r�es du formulaire) = modification solde 
						//+ retrait du montant 
		//La propri�t� montantDebit renseigne le montant � d�biter. Il est renseign� par le champ du formulaire de la page afficher_compte.xhtml
		// la propri�t� montantDebit renseigne respectivement le montant � d�biter du compte.
			
			
		//On test si le debit s'est bien pass�
			
		if(compteDAO.withdraw(compte, montantDebit)) {
			//------------- DEPOT OK--------------------
			//Envoie d'un message de r�ussite
			System.out.println("Le retrait a �t� effectu� avec succ�s");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
														"Retrait effectu�",
														"Le compte a �t� modifier avec succ�s"));
				

			setCompte(compteDAO.getCompteByID(compte.getIdCompte())); //On met � jour l'objet compte 
			setMontantDebit(0); //on remet le montant de credit � 0
							
		}else {
			//-------------DEPOT NOT OK--------------------------
			//Envoie d'un message d'echec
			System.out.println("Le retrait n'a pas �t� effectu�.");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
																"Echec du retrait", 
																"La modification n'a pas �t� effectu�"));
		}//end else
			
	}//end debitCompte
		
		
/*====================================================================================================*/	
/*==virementCompte====================================================================================*/
/*====================================================================================================*/
				
	/**
	* Permet d'effectuer un virement d'un compte donneur � un compte receveur, au click du bouton 'Virement' de la page 'virement_compte.xhtml'
	* @param event
	*/
	public void virementCompte (ActionEvent event) {
		System.out.println("Je suis dans virementCompte du MB de Compte");
					
		// R�cuperation du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
					
		//La propri�t� compte du managed bean encapsule les infos du compte � modifier dans la dbb (r�cup�r�es du formulaire) = modification solde des 2 comptes
						//+ retrait du montant 
		//La propri�t� montant renseigne le montant � transf�rer. Il est renseign� par le champ du formulaire de la page virement_compte.xhtml
		// les propri�t�s idCompteDonneur et idCompteReceveur renseigne respectivement les id des comptes � d�biter et � cr�diter.
				
		Compte compteDonneur = compteDAO.getCompteByID(idCompteDonneur);  //on recupere les comptes par leur ID
		Compte compteReceveur = compteDAO.getCompteByID(idCompteReceveur);
				
				
		//On test si le transf�re s'est bien pass�
		if(compteDAO.transfert(compteDonneur,compteReceveur, montantVirement)) {
					
			//------------- TRANSFERT OK--------------------
			//Envoie d'un message de r�ussite
			System.out.println("Le transf�re a �t� effectu� avec succ�s");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
														"D�bit effectu�",
														"Le compte d�biteur a �t� modifier avec succ�s"));
					
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
														"Cr�dit effectu�",
														"Le compte receveur a �t� modifier avec succ�s"));
					

			setMontantVirement(0);//on remet le montant � 0
								
		}else {
					
			//-------------TRANSFERT NOT OK--------------------------
			//Envoie d'un message d'echec
			System.out.println("Le transf�re n'a pas �t� effectu�.");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
																	"Echec du transf�re", 
																	"Les modifications n'ont pas �t� effectu�"));
		}//end else
							
							
	}//end virementCompte
}//end class
