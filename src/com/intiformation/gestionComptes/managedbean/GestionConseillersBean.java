package com.intiformation.gestionComptes.managedbean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * managed bean pour la gestion des conseillers
 * @author IN-DF-019
 *
 */
@ManagedBean(name="GestionConseiller")
@SessionScoped
public class GestionConseillerBean implements Serializable{
	
	/*propri�t�s*/
	
	//liste des conseillers au sein de la banque
	List<Conseiller> listeConseillersBDD;
	
	//propri�t�s conseillers
	private Conseiller conseiller;
	
	
	/*constructeurs*/
	
	public GestionConseillerBean() {
		
	}
	/*getters/setters*/


	public List<Conseiller> getListeConseillersBDD() {
		return listeConseillersBDD;
	}


	public void setListeConseillersBDD(List<Conseiller> listeConseillersBDD) {
		this.listeConseillersBDD = listeConseillersBDD;
	}


	public Conseiller getConseiller() {
		return conseiller;
	}


	public void setConseiller(Conseiller conseiller) {
		this.conseiller = conseiller;
	}
	
	
	
	/*m�thodes*/
	
	/*findAllConseillers*/
	/**
	 * r�cup�ration de la liste des conseillers en activit�
	 */
	public List<Conseiller> findAllConseillers(){
		System.out.println("Trouver la liste de tous les conseillers actuellement pr�sents dans la BDD");
		listeConseillersBDD=conseillerDAO.getAllConseillers();
		return listeConseillersBDD;
	}
	
	
	/*supprimerConseiller*/
	/**
	 * Supprimer un conseiller dans la BDD via la DAO, au clic sur le lien supprimer
	 */
	public void supprimerConseiller(ActionEvent event) {
		System.out.println("Pour supprimer un conseiller dans supprimerConseiller du MB de Conseiller");
		UIParameter cp = (UIParameter) event.getComponent().findComponent("deleteID");
		int conseillerID = (int) cp.getValue();
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		if (conseillerDAO.supprimerConseillerByID(conseillerID)) {
			contextJSF.addMessage(null, new FacesMessage( "Le conseiller a �t� retir� avec succ�s"));
		}else {
			contextJSF.addMessage(null, new FacesMessage("Le retrait du conseiller a �chou�"));
		}
	}
	
	
	/*selectionnerConseiller*/
	/**
	 * permet d'obtenir des informations sur un conseiller
	 */
	public void  selectionnerConseiller(ActionEvent event) {
		System.out.println("Pour s�lectionner un conseiller dans selectionnerConseiller du MB de Client");
		UIParameter cp = (UIParameter) event.getComponent().findComponent("updateID");
		int conseillerID = (int) cp.getValue();
		Conseiller conseillerEdit = conseillerDAO.getConseillerByID(conseillerID);
		setConseiller(conseillerEdit);
	}
	
	/*modifierConseiller*/
	/**
	 * permet de modifier les informations concernant un conseiller
	 */
	System.out.println("Pour modifier les infos d'un conseiller avec modifierClient du MB de Client");
	FacesContext contextJSF = FacesContext.getCurrentInstance();
	if(conseillerDAO.modifierConseiller(conseiller)) {
		System.out.println("La modification des informations du conseiller est un succ�s");
		contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
														"Modification r�ussie",
														"Les informations du conseiller ont �t� modifi�es avec succ�s"));
	}else {
		System.out.println("La modification est un echec");
		contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
														"Echec de la modification", 
														"La modification n'a pas pu �tre effectu�e"));
	}
	
	/*initialiser un conseiller*/
	/**
	 * pour 
	 */
	public void initialiserConseiller(ActionEvent event){
		System.out.println("Je suis dans initialiserConseiller du MB de Conseiller");
		Conseiller addConseiller = new Conseiller();
		setConseiller(addConseiller);
	}
	
	/*ajouter conseiller*/
	/**
	 * 
	 */
	public void ajouterConseiller(ActionEvent event) {
		System.out.println("Je suis dans ajouterConseiller du MB de Client");
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		if(conseillerDAO.ajouterConseiller(conseiller)) {
			System.out.println("L'ajout est un succ�s");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_INFO,
					"Ajout r�ussi",
					"Le conseiller a �t� ajouter avec succ�s"));
		}else {
			System.out.println("L'ajout est un echec");
			contextJSF.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
					"Echec de l'ajout", 
					"L'ajout n'a pas �t� effectu�"));
		}
	}
	
	
	

}