package com.intiformation.gestionComptes.managedbean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.intiformation.gestionComptes.dao.ConseillerDaoImpl;
import com.intiformation.gestionComptes.dao.IConseillerDAO;
import com.intiformation.gestionComptes.model.Conseiller;



/**
 * managedbean pourl'authentification de l'utilisateur <br/>
 * @author IN-MP-018
 *
 */

@ManagedBean(name="authenticationBean")
@SessionScoped 
public class AuthentificationBean implements Serializable{

	/*=========================PROPRIETES=========================*/
	
	private String login = "pierre@gmail.com";
	private String password = "0000";
	
	private Conseiller conseillerlogged; /// A mettre dans  gestionConseiller MB
	
	//dao
	
	private IConseillerDAO conseillerDAO;
	
	
	/*=========================CONSTRUCTEUR=========================*/
	
	public AuthentificationBean() {
		conseillerDAO=new ConseillerDaoImpl();

	}
	
	/*=========================GETTER/SETTER=========================*/
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Conseiller getConseillerlogged() {
		return conseillerlogged;
	}

	public void setConseillerlogged(Conseiller conseillerlogged) {
		this.conseillerlogged = conseillerlogged;
	}

	public IConseillerDAO getConseillerDAO() {
		return conseillerDAO;
	}

	public void setConseillerDAO(IConseillerDAO conseillerDAO) {
		this.conseillerDAO = conseillerDAO;
	}
	
	
	/*=========================METHODES=========================*/
	
	
/*====================================================================================================*/	
/*==connecterUtilisateur==============================================================================*/
/*====================================================================================================*/	
		

	/**
	 * Permet de connecter l'utilisateur et de lui creer une session
	 * @return
	 */
	
	public String connecterUtilisateur() {
		System.out.println("Je suis dans connecterUtilisateur du MB authentification ");
		
		//recup du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//1. vérif si l'utilisateur existe dans la bdd
		
		if (conseillerDAO.isConseillerExists(login, password)) {
			System.out.println("Le conseiller existe => connexion");
			
			/*---L'utilisateur existe ----*/
			
			//recup de la session utilisateur et Creation d'une session si elle n'existe pas 
			HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(true);
			
			//Sauvegarde du login dans la session
			session.setAttribute("user_login", login);
			
			//Recuperation du conseiller connecté et sauvegarde dans l'attribut du managed bean
			setConseillerlogged(conseillerDAO.getConseillerByMailMDP(login, password)); 
			
			return "accueil.xhtml";
			
		}else {
			/*---L'utilisateur n'existe pas ----*/
			System.out.println("Le conseiller n'existe pas  => erreur");
			
			//def du message vie FacesMessage
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec de connexion", "Identifiant ou mot de passe invalide");
			
			//envoi du message vers la vue via le context JSF
			
			/**
			 * addMessage(String, FacesMessage):
			 * String =  soit l'id du composant soit null pour l'ensemble de la page 
			 */
			contextJSF.addMessage(null,message);
			

			//redirection vers Authentification.xhtml
			return "authentification.xhtml";
		}
	
	}//end connecterUtilisateur
	
	
	
/*====================================================================================================*/	
/*==deconnecterUtilisateur============================================================================*/
/*====================================================================================================*/	
	
	
	/**
	 * Permet de deconnecter l'utilisateur
	 * @return
	 */
	public String deconnecterUtilisateur() {
		System.out.println("Je suis dans deconnecterUtilisateur du MB authentification ");
		//recup du context jsp
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//recup de la session utilisateur
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
		
		//Deconnexion = destruction de la session
		session.invalidate();
		
		//Initialisation d'un conseiller vide et sauvegarde dans l'attribut du managed bean
		 setConseillerlogged(new Conseiller());
		
		//redirection vers authentification.xhtml
		return "authentification.xhtml";
	}//end deconnecterUtilisateur
	
	
	
	
	
	

	
	
}//end class
