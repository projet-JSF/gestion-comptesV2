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
	
	// Propriété compte pour l'ajout et pour l'édition
	private Compte compte;
	
	//identifiant du compte selectionné pour la fonction selectionnerCompteSansParam
	private int idCompteSelectionner;
	
	//Client propriétaire duu compte selectionné
	private Client clientProprietaireCompteSelec;
	
	//Liste des id des comptes pour la page chercher_compte.xhtml
	private List<Integer> listeIDCompte;
	
	//Liste des id des clients pour les select mennu des pages ajouter_compte.xhtml et modifier_compte.xhtml
	private List<Integer> listeIDClient;
	
	//Liste des comptes appartenant aux clients du conseiller connecté pour l'affichage sur la page accueil.xhtml
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
	 * Recupere la liste des comptes appartenant au conseiller enregistré via la dao. <br/>
	 * Cette methode permet d'alimenter la table dans accueil.xhtml pour affichage
	 * @return Liste des comptes du conseiller connecté
	 */
	public List<Compte> findComptesDuConseiller(int idConseiller){
		System.out.println("Je suis dans findComptesDuConseiller du MB de Compte");
		
		//1. On recupere la liste des clients appartenant au conseiller par l'id du conseiller (parametre de la méthode)
		List<Client> listeClientsduConseiller=conseillerDAO.getClientsduConseiller(idConseiller);
	
		//2. Intitalisation de la liste des comptes du conseiller
		listeComptesDuConseillerLogged= new ArrayList<>();
		
		//3. Initialisation d'une liste de comptes appartenant à 1 client
		List<Compte> listeCompteDuClient = new ArrayList<>();
		
		//4. On parcourt la liste des clients du conseiller
		
		for(Client client : listeClientsduConseiller) {
			
			//Pour chaque client du conseiller, on recupere son identifiant
			int idClient =client.getIdClient();
			
			//Puis la liste de ses comptes (par son identifiant)
			listeCompteDuClient=compteDAO.getCompteByIDClient(idClient);
			
			//On fusionne la liste des comptes du client à la liste des comptes du conseiller
			listeComptesDuConseillerLogged.addAll(listeCompteDuClient);
			
		}//end for

		
		return listeComptesDuConseillerLogged;
	}//end findComptesDuConseiller

/*====================================================================================================*/	
/*==supprimerCompte=================================================================================*/
/*====================================================================================================*/	
	/**
	 * Méthode invoquée au click sur le lien 'supprimer' de afficher_compte.xhtml 
	 * Permet de supprimer un client dans la bdd via la dao
	 */
	
	public void supprimerCompte(ActionEvent event) {
		System.out.println("Je suis dans supprimerCompte du MB de Compte");
		
		//1. recup de l'id du client à supprimer
		int compteID = compte.getIdCompte();
		System.out.println("Id du compte à supprimer :"+ compteID);
		
		//2. Recup du context
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//3. On test la suppression
		
		if (compteDAO.supprimerCompteByID(compteID)) {
			
			// ========== Suppression OK ========
			/* Envoi d'un message vers la vue via le context */
			System.out.println("Suppression OK");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,"Réussite" ,"Le compte a été supprimé avec succès"));
			
		}else {
			
			// ========== Suppression NOT OK ========
			System.out.println("Suppression NOT OK");
			/* Envoi d'un lessage vers la vue via le context */
			
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Echec" , "La suppression du compte a échouée"));
			
			
		}//end else
	}//end supprimerClientsBdd

/*====================================================================================================*/	
/*==selectionnerCompteSansParam================================================================================*/
/*====================================================================================================*/	

	/**
	 * Invoquée au click du bouton Modifier de la page afficher_compte.xhtml <br/>
	 * Permet de stocker les info du compte affiché dans la propriétée 'compte' du managed bean.
	 * @param event
	 */
		
	public void selectionnerCompteSansParam(ActionEvent event) {
		System.out.println("Je suis dans selectionnerClientSansParam du MB de Client");

		//1. Recup de l'id du compte
		int compteID = getIdCompteSelectionner();
			
		//2. recup du compte dans la bdd par l'id
		Compte compteEdit = compteDAO.getCompteByID(compteID);
			
		//3. affectation du compte à sélectionné à la prop compte du managedbean
		setCompte(compteEdit);
		
		//4. Recup de la liste des id des clients pour alimenter le select menu de la page modifier_compte.xhtml
		setListeIDClient(clientDAO.getAllIDClients());
	
		
		}//end selectionnerClient
		
/*====================================================================================================*/	
/*==selectionnerCompte================================================================================*/
/*====================================================================================================*/	

	/**
	 * Invoquée au click du bouton Afficher de la page accueil.xhtml et liste_all_compte.xhtml <br/>
	 * Permet de stocker les info du compte selectionné dans la propriétée 'compte' du managed bean.
	 * @param event
	 */
	
	public void selectionnerCompte(ActionEvent event) {
		System.out.println("Je suis dans selectionnerCompte du MB de Compte");

		//1. récup du param passé dans le composant afficherID au click du lien afficher
		UIParameter cp = (UIParameter) event.getComponent().findComponent("afficherID");
		
		//2. Recup de la valeur du param => l'id du compte à afficher
		int compteID = (int) cp.getValue();
		
		//3. Recup du compte dans la bdd par l'id
		Compte compteEdit = compteDAO.getCompteByID(compteID);
		
		//4. Affectation du compte à afficher à la prop compte du managedbean
		setCompte(compteEdit);
		
		//5. Recup du client propriétaire
		setClientProprietaireCompteSelec(clientDAO.getClientByID(compte.getClientID()));
		
		
	}//end selectionnerCompte
	
/*====================================================================================================*/	
/*==getProprietaire===================================================================================*/
/*====================================================================================================*/
	/**
	 * Permet de recuperer le client propriétaire d'un compte dans une liste (pour l'affichage sous forme de table)
	 * @return une liste contenant un seul client (proprio du compte selectionner)
	 */
	public List<Client> getProprietaire(){
		System.out.println("Je suis dans getProprietaire du managed bean du compte");
		
		// 1. Initialisation d'une liste vide pour stocker le client propriétaire
		List<Client> proprietaireFormatListe= new ArrayList<>();
		
		//2. Ajout du client à la liste
		proprietaireFormatListe.add(clientProprietaireCompteSelec);
		
		return proprietaireFormatListe;
	}//end getProprietaire
	
	
/*====================================================================================================*/	
/*==modifierCompte====================================================================================*/
/*====================================================================================================*/

	/**
	 * Invoquée au click du bouton Modifier de la page modifier-compte.xhtml <br/>
	 * Permet de modifier un compte de la bdd
	 * @param event
	 */
	
	public void modifierCompte(ActionEvent event) {
		System.out.println("Je suis dans modifierCompte du MB de Compte");
		
		//1. Récupération du context JSP	
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//2. On remet à 0 le taux/decouvert en fonction du type de compte 
		if(compte.getTypeCompte().equals("courant")) {
			//Si le compte est courant, on met le taux à 0
			compte.setTaux(0);
		}else {
			//Si le compte est epargne, on met le decouvert à 0
			compte.setDecouvert(0);
		}
		
		//3. La propriété compte du managed bean encapsule les infos du compte à modifier dans la dbb (récupérées du formulaire)
		
		//On test si la modification s'est bien passé
		
		if(compteDAO.modifierCompte(compte)) {
			//------------- MODIF OK--------------------
			//Envoie d'un message de réussite
			System.out.println("La modif est un succès");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
															"Modification réussie",
															"Le compte a été modifié avec succès"));
			
		}else {
			//-------------MODIF NOT OK--------------------------
			//Envoie d'un message d'echec
			System.out.println("La modif est un echec");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
															"Echec de la modification", 
															"La modification n'a ps été effectué"));
		}//end else
		
	}//end modifierCompte
	

/*====================================================================================================*/	
/*==initialiserCompte====================================================================================*/
/*====================================================================================================*/
	
	/**
	 * Invoqué au click du bouton 'ajouter un compte' de la bar de menu
	 * Permet d'initialiser un objet de type Compte à vide pour recuperer les valeurs saisies dans le formulaire de la page ajouter_compte.xhtml
	 */
	public void initialiserCompte(ActionEvent event){
		
		System.out.println("Je suis dans initialiserCompte du MB de Compte");
		
		//1. Instanciation d'un nouvel objet client vide dans lequel on va stocker les infos du nouveau compte via le formulaire
		Compte addCompte = new Compte();
		
		//2. Affectation de l'objet à la prop compte du MB
		setCompte(addCompte);
		
		//Recup de la liste des id des clients pour le select menu de la page ajouter_compte.xhtml
		setListeIDClient(clientDAO.getAllIDClients());
		
	}//end initialiserCompte

	
/*====================================================================================================*/	
/*==ajouterCompte====================================================================================*/
/*====================================================================================================*/
	
	/**
	 * Invoquée au click du bouton Ajouter de la page ajouter-compte.xhtml <br/>
	 * Permet d'ajouter un nouveau compte à la bdd
	 * @param event
	 */
	public void ajouterCompte(ActionEvent event) {
		System.out.println("Je suis dans ajouterCompte du MB de Compte");
		
		// 1. Récuperation du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//2. On remet à 0 le taux/decouvert en fonction du type de compte
		if(compte.getTypeCompte().equals("courant")) {
			// Si le compte est courant, on met le taux à 0
			compte.setTaux(0);
		}else {
			// Si le compte est epargne on met le decouvert à 0
			compte.setDecouvert(0);
		}//end else
		
		
		//3. Ajout du compte dans la bdd
		//-> les infos du nouveau compte ont été stocké dans l'objet compte du MB au moment d'envoyer le formulaire
		
		
		//On test si l'ajout à reussit
		
		if(compteDAO.ajouterCompte(compte)) {
			//-----------------AJOUT OK-------------------
			System.out.println("L'ajout du compte est un succès");
			//Envoie d'un message de réussite
			
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
					"Ajout réussi",
					"Le compte a été ajouté avec succès"));
		}else {
			//-----------------AJOUT NOT OK----------------
			System.out.println("L'ajout du compte est un echec");
			//Envoie d'un message d'erreur

			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
					"Echec de l'ajout", 
					"L'ajout du compte n'a pas été effectué"));
		}//end else
	}//end ajouterCompte

	
/*====================================================================================================*/	
/*==findlisteIDComptes====================================================================================*/
/*====================================================================================================*/
				
	/**
	 * Invoquée au click du bouton chercher un compte de la bar de menu <br/>
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
	 * Permet d'effectuer un depot sur un compte, au click du bouton 'Créditer' de la page 'afficher_compte.xhtml'
	 * @param event
	 */
	public void creditCompte (ActionEvent event) {
		System.out.println("Je suis dans creditCompte du MB de Compte");
			
		// 1. Récuperation du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
			
		//La propriété compte du managed bean encapsule les infos du compte à crediter 
		//La propriété montantCredit renseigne le montant à crediter. Il est renseigné par le champ du formulaire de la page afficher_compte.xhtml
		
		
		//2. On test si le credit s'est bien passé
					
		if(compteDAO.deposit(compte, montantCredit)) {
			//------------- DEPOT OK--------------------
			//Envoie d'un message de réussite
			System.out.println("Le dépôt est un succès");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
														"Dépôt effectué",
														"Le compte a été modifier avec succès"));
			

			setCompte(compteDAO.getCompteByID(compte.getIdCompte())); //On met à jour l'objet compte 
			setMontantCredit(0); //on remet le montant de credit à 0
						
		}else {
			//-------------DEPOT NOT OK--------------------------
			//Envoie d'un message d'echec
			System.out.println("Le dépôt est un echec");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
															"Echec du dépôt", 
															"La modification n'a pas été effectué"));
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
		
		//1. On recupere l'id du client propriétaire du compte
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
	* Permet d'effectuer un retrait sur un compte, au click du bouton 'Débiter' de la page 'afficher_compte.xhtml'
	* @param event
	*/
	public void debitCompte (ActionEvent event) {
		System.out.println("Je suis dans debitCompte du MB de Compte");
				
		// 1. Récuperation du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
				
		//La propriété compte du managed bean encapsule les infos du compte à debiter dans la dbb 
		//La propriété montantDebit renseigne le montant à débiter. Il est renseigné par le champ du formulaire de la page afficher_compte.xhtml
			
		
		//2. On calcule le montant maximum que l'on peut retirer du compte
		double montantmaximum;
		
		if(compte.getTypeCompte().equals("courant")) {
			//Si le compte est courant, le montant maximum équivaut au solde + decouvert
			montantmaximum=compte.getSolde()+compte.getDecouvert();
			
		}else {
			//Si le compte est épargne, le montant maximum est simplement le solde
			montantmaximum=compte.getSolde();
		}//end else
		
		
		//3. On vérifie si la somme à débitée est disponible sur le compte : montantDebit < montantmaximum = autorisation
		
		if (montantDebit < montantmaximum) {
			//Si condition le retrait est autorisé :
			
			System.out.println("Le retrait est autorisé.");
			
			//4. On test si le debit s'est bien passé
			if(compteDAO.withdraw(compte, montantDebit)) {
				//------------- DEPOT OK--------------------
				//Envoie d'un message de réussite
				System.out.println("Le retrait a été effectué avec succès");
				contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
															"Retrait effectué",
															"Le compte a été modifier avec succès"));
					
	
				setCompte(compteDAO.getCompteByID(compte.getIdCompte())); //On met à jour l'objet compte 
				setMontantDebit(0); //on remet le montant de credit à 0
								
			}else {
				//-------------DEPOT NOT OK--------------------------
				//Envoie d'un message d'echec
				System.out.println("Le retrait n'a pas été effectué.");
				contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
																	"Echec du retrait", 
																	"La modification n'a pas été effectué"));
			}//end else
			
		}else {
			//Si la somme demandée est trop élevée :
			System.out.println("Le retrait n'a pas été autorisé.");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
																"Retrait refusé", 
																"Solde insuffisant"));
			
		}//end else
		

			
	}//end debitCompte
		
		
/*====================================================================================================*/	
/*==virementCompte====================================================================================*/
/*====================================================================================================*/
				
	/**
	* Permet d'effectuer un virement d'un compte donneur à un compte receveur, au click du bouton 'Virement' de la page 'virement_compte.xhtml'
	* @param event
	*/
	public void virementCompte (ActionEvent event) {
		System.out.println("Je suis dans virementCompte du MB de Compte");
					
		// 1. Récuperation du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
					
		//La propriété montantVirement renseigne le montant à transférer. Il est renseigné par le champ du formulaire de la page virement_compte.xhtml
		// les propriétés idCompteDonneur et idCompteReceveur renseigne respectivement les id des comptes à débiter et à créditer. Ils sont renseignés par les select menu de la page virement_compte.xhtml
		
		//2. On recupere les comptes donneur et receveur par leurs ID
		Compte compteDonneur = compteDAO.getCompteByID(idCompteDonneur);  
		Compte compteReceveur = compteDAO.getCompteByID(idCompteReceveur);
		
		//3. On calcule le montant maximum que l'on peut débiter du compte donneur
		double montantmaximum;
		if(compteDonneur.getTypeCompte().equals("courant")) {
			// Pour un compte courant : montant maximum = solde + decouvert
			montantmaximum=compteDonneur.getSolde()+compteDonneur.getDecouvert();
			
		}else {
			// Pour un compte epargne : montant maximum = solde
			montantmaximum=compteDonneur.getSolde();
		}//end else
		
		//4. On test si le montant à virer est inférieur au montant maximum
		
		if (montantVirement < montantmaximum) {
			// Virement autorisé
			System.out.println("Le virement a été autorisé.");
			
			//On test si le transfère s'est bien passé
			if(compteDAO.transfert(compteDonneur,compteReceveur, montantVirement)) {
						
				//------------- TRANSFERT OK--------------------
				//Envoie d'un message de réussite
				System.out.println("Le transfère a été effectué avec succès");
				contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
															"Débit effectué",
															"Le compte débiteur a été modifier avec succès"));
						
				contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
															"Crédit effectué",
															"Le compte receveur a été modifier avec succès"));
						
	
				setMontantVirement(0);//on remet le montant à 0
									
			}else {
						
				//-------------TRANSFERT NOT OK--------------------------
				//Envoie d'un message d'echec
				System.out.println("Le transfère n'a pas été effectué.");
				contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
																		"Echec du transfère", 
																		"Les modifications n'ont pas été effectué"));
			}//end else
			
			
		}else {
			//Virement non autorisé
			
			System.out.println("Le virement n'a pas été autorisé.");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
																"Virement refusé", 
																"Solde insuffisant"));
			
		}//end else					
							
	}//end virementCompte
	
	
	
}//end class
