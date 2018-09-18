package com.utn.vista;

public class RopaDAOFactory {
	
	public RopaDAOJDBCImpl crearRopaDAOJDBCImpl () {
		return new RopaDAOJDBCImpl();
	}
	

}
