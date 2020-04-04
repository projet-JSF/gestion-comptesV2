package com.intiformation.gestionComptes.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utilitaire pour recuperer une connexion vers la base de données
 * impllémentation du design pattern du GOF singleton
 * @author IN-MP-018
 *
 */
public class DbConnection {
	
	/*============= Props ====================*/
	
	// infos pour la connexion
	private static final String DB_URL = "jdbc:mysql://localhost:3306/bdd_gestion_comptes_V2";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";
	private static final String JDBC_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	
	// Déclaration de la seule instance de la connexion à retourner
	
	private static Connection connection;
	
	/*============= Constructeurs ====================*/
	
	private DbConnection() {}
	
	
	/*============= Méthode ====================*/
	/**
	 * Méthode qui renvoie la seule instance de Connection
	 * @return
	 */
	public static Connection getInstance() {
		if(connection==null) {
			
			try {
				//chargement du pilote jdbc de mySQL
				Class.forName(JDBC_DRIVER_CLASS);
				
				//recup de la connexion du DriveManager 
				connection=DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println("... Erreur lors de la creation de la connexion à la bdd");
				e.printStackTrace();
			}
		}//end if
		return connection;
	}//end getInstance


}//end class
