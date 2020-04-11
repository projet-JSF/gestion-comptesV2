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
	
	// Liste de tous les comptes pour affichage sur la page liste_all_comptes.xhtml
	List<Compte> listeComptesBDD;
	
	// Propri�t� compte pour l'ajout et pour l'�dition
	private Compte compte;
	
	//identifiant du compte selectionn� pour la fonction selectionnerCompteSansParam
	private int idCompteSelectionner;
	
	//Client propri�taire duu compte selectionn�
	private Client clientProprietaireCompteSelec;
	
	//Liste des id des comptes pour la page chercher_compte.xhtml
	private List<Integer> listeIDCompte;
	
	//Liste des id des clients pour les select mennu des pages ajouter_compte.xhtml et modifier_compte.xhtml
	private List<Integer> listeIDClient;
	
	//Liste des comptes appartenant aux clients du conseiller connect� pour l'affichage sur la page accueil.xhtml
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
	
	//dao des clients
	IClientDAO clientDAO;
	
	//dao du conseiller
	IConseillerDAO conseillerDAO;
	
	/*=========================CONSTRUCTEUR=========================*/
	/**
	 * Constructeur vide pour l'instanciation du managedbean <br/>
	 * Instancie la dao du compte, du client et du conseiller
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


	public Client getClientProprietaireCompteSelec() {
		return clientProprietaireCompteSelec;
	}


	public void setClientProprietaireCompteSelec(Client clientProprietaireCompteSelec) {
		this.clientProprietaireCompteSelec = clientProprietaireCompteSelec;
	}


	public IConseillerDAO getConseillerDAO() {
		return conseillerDAO;
	}


	public void setConseillerDAO(IConseillerDAO conseillerDAO) {
		this.conseillerDAO = conseillerDAO;
	}
	
	/*=========================METHODES=========================*/
	
/*====================================================================================================*/	
/*==findCompteByID=================================================================================*/
/*====================================================================================================*/	
		
	/**
	 * Recupere un compte par son id via la dao. <br/>
	 * @return compte
	 */
	public Compte findCompteByID(int idCompte){
		System.out.println("Je suis dans findCompteByID du MB de Compte");
		
		Compte compte=compteDAO.getCompteByID(idCompte);

		return compte;
	}//end findCompteByID	
	
/*====================================================================================================*/	
/*==findAllComptesBdd=================================================================================*/
/*====================================================================================================*/	
	
	/**
	 * Recupere la liste des comptes dans la bdd via la dao. <br/>
	 * Cette methode permet d'alimenter la table dans liste_all_comptes.xhtml pour affichage
	 * @return Liste des comptes de la bdd
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
	 * @return Liste des comptes du conseiller connect�
	 */
	public List<Compte> findComptesDuConseiller(int idConseiller){
		System.out.println("Je suis dans findComptesDuConseiller du MB de Compte");
		
		//1. On recupere la liste des clients appartenant au conseiller par l'id du conseiller (parametre de la m�thode)
		List<Client> listeClientsduConseiller=conseillerDAO.getClientsduConseiller(idConseiller);
	
		//2. Intitalisation de la liste des comptes du conseiller
		listeComptesDuConseillerLogged= new ArrayList<>();
		
		//3. Initialisation d'une liste de comptes appartenant � 1 client
		List<Compte> listeCompteDuClient = new ArrayList<>();
		
		//4. On parcourt la liste des clients du conseiller
		
		for(Client client : listeClientsduConseiller) {
			
			//Pour chaque client du conseiller, on recupere son identifiant
			int idClient =client.getIdClient();
			
			//Puis la liste de ses comptes (par son identifiant)
			listeCompteDuClient=compteDAO.getCompteByIDClient(idClient);
			
			//On fusionne la liste des comptes du client � la liste des comptes du conseiller
			listeComptesDuConseillerLogged.addAll(listeCompteDuClient);
			
		}//end for

		
		return listeComptesDuConseillerLogged;
	}//end findComptesDuConseiller

/*====================================================================================================*/	
/*==supprimerCompte=================================================================================*/
/*====================================================================================================*/	
	/**
	 * M�thode invoqu�e au click sur le lien 'supprimer' de afficher_compte.xhtml 
	 * Permet de supprimer un client dans la bdd via la dao
	 */
	
	public void supprimerCompte(ActionEvent event) {
		System.out.println("Je suis dans supprimerCompte du MB de Compte");
		
		//1. recup de l'id du client � supprimer
		int compteID = compte.getIdCompte();
		System.out.println("Id du compte � supprimer :"+ compteID);
		
		//2. Recup du context
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//3. On test la suppression
		
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

/*====================================================================================================*/	
/*==selectionnerCompteSansParam================================================================================*/
/*====================================================================================================*/	

	/**
	 * Invoqu�e au click du bouton Modifier de la page afficher_compte.xhtml <br/>
	 * Permet de stocker les info du compte affich� dans la propri�t�e 'compte' du managed bean.
	 * @param event
	 */
		
	public void selectionnerCompteSansParam(ActionEvent event) {
		System.out.println("Je suis dans selectionnerClientSansParam du MB de Client");

		//1. Recup de l'id du compte
		int compteID = getIdCompteSelectionner();
			
		//2. recup du compte dans la bdd par l'id
		Compte compteEdit = compteDAO.getCompteByID(compteID);
			
		//3. affectation du compte � s�lectionn� � la prop compte du managedbean
		setCompte(compteEdit);
		
		//4. Recup de la liste des id des clients pour alimenter le select menu de la page modifier_compte.xhtml
		setListeIDClient(clientDAO.getAllIDClients());
	
		
		}//end selectionnerClient
		
/*====================================================================================================*/	
/*==selectionnerCompte================================================================================*/
/*====================================================================================================*/	

	/**
	 * Invoqu�e au click du bouton Afficher de la page accueil.xhtml et liste_all_compte.xhtml <br/>
	 * Permet de stocker les info du compte selectionn� dans la propri�t�e 'compte' du managed bean.
	 * @param event
	 */
	
	public void selectionnerCompte(ActionEvent event) {
		System.out.println("Je suis dans selectionnerCompte du MB de Compte");

		//1. r�cup du param pass� dans le composant afficherID au click du lien afficher
		UIParameter cp = (UIParameter) event.getComponent().findComponent("afficherID");
		
		//2. Recup de la valeur du param => l'id du compte � afficher
		int compteID = (int) cp.getValue();
		
		//3. Recup du compte dans la bdd par l'id
		Compte compteEdit = compteDAO.getCompteByID(compteID);
		
		//4. Affectation du compte � afficher � la prop compte du managedbean
		setCompte(compteEdit);
		
		//5. Recup du client propri�taire
		setClientProprietaireCompteSelec(clientDAO.getClientByID(compte.getClientID()));
		
		
	}//end selectionnerCompte
	
/*====================================================================================================*/	
/*==getProprietaire===================================================================================*/
/*====================================================================================================*/
	/**
	 * Permet de recuperer le client propri�taire d'un compte dans une liste (pour l'affichage sous forme de table)
	 * @return une liste contenant un seul client (proprio du compte selectionner)
	 */
	public List<Client> getProprietaire(){
		System.out.println("Je suis dans getProprietaire du managed bean du compte");
		
		// 1. Initialisation d'une liste vide pour stocker le client propri�taire
		List<Client> proprietaireFormatListe= new ArrayList<>();
		
		//2. Ajout du client � la liste
		proprietaireFormatListe.add(clientProprietaireCompteSelec);
		
		return proprietaireFormatListe;
	}//end getProprietaire
	
	
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
		
		//2. On remet � 0 le taux/decouvert en fonction du type de compte 
		if(compte.getTypeCompte().equals("courant")) {
			//Si le compte est courant, on met le taux � 0
			compte.setTaux(0);
		}else {
			//Si le compte est epargne, on met le decouvert � 0
			compte.setDecouvert(0);
		}
		
		//3. La propri�t� compte du managed bean encapsule les infos du compte � modifier dans la dbb (r�cup�r�es du formulaire)
		
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
	 * Invoqu� au click du bouton 'ajouter un compte' de la bar de menu
	 * Permet d'initialiser un objet de type Compte � vide pour recuperer les valeurs saisies dans le formulaire de la page ajouter_compte.xhtml
	 */
	public void initialiserCompte(ActionEvent event){
		
		System.out.println("Je suis dans initialiserCompte du MB de Compte");
		
		//1. Instanciation d'un nouvel objet client vide dans lequel on va stocker les infos du nouveau compte via le formulaire
		Compte addCompte = new Compte();
		
		//2. Affectation de l'objet � la prop compte du MB
		setCompte(addCompte);
		
		//Recup de la liste des id des clients pour le select menu de la page ajouter_compte.xhtml
		setListeIDClient(clientDAO.getAllIDClients());
		
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
		
		// 1. R�cuperation du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//2. On remet � 0 le taux/decouvert en fonction du type de compte
		if(compte.getTypeCompte().equals("courant")) {
			// Si le compte est courant, on met le taux � 0
			compte.setTaux(0);
		}else {
			// Si le compte est epargne on met le decouvert � 0
			compte.setDecouvert(0);
		}//end else
		
		
		//3. Ajout du compte dans la bdd
		//-> les infos du nouveau compte ont �t� stock� dans l'objet compte du MB au moment d'envoyer le formulaire
		
		
		//On test si l'ajout � reussit
		
		if(compteDAO.ajouterCompte(compte)) {
			//-----------------AJOUT OK-------------------
			System.out.println("L'ajout du compte est un succ�s");
			//Envoie d'un message de r�ussite
			
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
					"Ajout r�ussi",
					"Le compte a �t� ajout� avec succ�s"));
		}else {
			//-----------------AJOUT NOT OK----------------
			System.out.println("L'ajout du compte est un echec");
			//Envoie d'un message d'erreur

			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
					"Echec de l'ajout", 
					"L'ajout du compte n'a pas �t� effectu�"));
		}//end else
	}//end ajouterCompte

	
/*====================================================================================================*/	
/*==findlisteIDComptes====================================================================================*/
/*====================================================================================================*/
				
	/**
	 * Invoqu�e au click du bouton chercher un compte de la bar de menu <br/>
	 * Permet de recuperer la liste des identifiants des comptes
	 * @param event
	 */
	public void findlisteIDComptes() {
		System.out.println("Je suis dans findlisteIDComptes du MB de Compte");
			
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
			
		// 1. R�cuperation du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
			
		//La propri�t� compte du managed bean encapsule les infos du compte � crediter 
		//La propri�t� montantCredit renseigne le montant � crediter. Il est renseign� par le champ du formulaire de la page afficher_compte.xhtml
		
		
		//2. On test si le credit s'est bien pass�
					
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
	 * Recupere l'id du conseiller du compte par l'id du compte <br/>
	 * @param idCompte : identifiant du compte pour lequel on chercher l'id du conseiller
	 * @return idConseiller
	 */
	public int findConseillerduCompte(int idCompte){
		System.out.println("Je suis dans findConseillerduCompte du MB de Compte");
		
		//1. On recupere l'id du client propri�taire du compte
		int IDclient = compteDAO.getCompteByID(idCompte).getClientID();
		
		//2. On recupere le client par son ID
		Client client = clientDAO.getClientByID(IDclient);
		
		//3. On recupere l'id du conseiller par l'objet client
		int idConseiller = client.getConseillerId();
				
		return idConseiller;
	}//end findComptesDuConseiller

	
	
/*====================================================================================================*/	
/*==debitCompte====================================================================================*/
/*====================================================================================================*/
			
	/**
	* Permet d'effectuer un retrait sur un compte, au click du bouton 'D�biter' de la page 'afficher_compte.xhtml'
	* @param event
	*/
	public void debitCompte (ActionEvent event) {
		System.out.println("Je suis dans debitCompte du MB de Compte");
				
		// 1. R�cuperation du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
				
		//La propri�t� compte du managed bean encapsule les infos du compte � debiter dans la dbb 
		//La propri�t� montantDebit renseigne le montant � d�biter. Il est renseign� par le champ du formulaire de la page afficher_compte.xhtml
			
		
		//2. On calcule le montant maximum que l'on peut retirer du compte
		double montantmaximum;
		
		if(compte.getTypeCompte().equals("courant")) {
			//Si le compte est courant, le montant maximum �quivaut au solde + decouvert
			montantmaximum=compte.getSolde()+compte.getDecouvert();
			
		}else {
			//Si le compte est �pargne, le montant maximum est simplement le solde
			montantmaximum=compte.getSolde();
		}//end else
		
		
		//3. On v�rifie si la somme � d�bit�e est disponible sur le compte : montantDebit < montantmaximum = autorisation
		
		if (montantDebit < montantmaximum) {
			//Si condition le retrait est autoris� :
			
			System.out.println("Le retrait est autoris�.");
			
			//4. On test si le debit s'est bien pass�
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
			
		}else {
			//Si la somme demand�e est trop �lev�e :
			System.out.println("Le retrait n'a pas �t� autoris�.");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
																"Retrait refus�", 
																"Solde insuffisant"));
			
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
					
		// 1. R�cuperation du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
					
		//La propri�t� montantVirement renseigne le montant � transf�rer. Il est renseign� par le champ du formulaire de la page virement_compte.xhtml
		// les propri�t�s idCompteDonneur et idCompteReceveur renseigne respectivement les id des comptes � d�biter et � cr�diter. Ils sont renseign�s par les select menu de la page virement_compte.xhtml
		
		//2. On recupere les comptes donneur et receveur par leurs ID
		Compte compteDonneur = compteDAO.getCompteByID(idCompteDonneur);  
		Compte compteReceveur = compteDAO.getCompteByID(idCompteReceveur);
		
		//3. On calcule le montant maximum que l'on peut d�biter du compte donneur
		double montantmaximum;
		if(compteDonneur.getTypeCompte().equals("courant")) {
			// Pour un compte courant : montant maximum = solde + decouvert
			montantmaximum=compteDonneur.getSolde()+compteDonneur.getDecouvert();
			
		}else {
			// Pour un compte epargne : montant maximum = solde
			montantmaximum=compteDonneur.getSolde();
		}//end else
		
		//4. On test si le montant � virer est inf�rieur au montant maximum
		
		if (montantVirement < montantmaximum) {
			// Virement autoris�
			System.out.println("Le virement a �t� autoris�.");
			
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
			
			
		}else {
			//Virement non autoris�
			
			System.out.println("Le virement n'a pas �t� autoris�.");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
																"Virement refus�", 
																"Solde insuffisant"));
			
		}//end else					
							
	}//end virementCompte
	
	
	
}//end class
