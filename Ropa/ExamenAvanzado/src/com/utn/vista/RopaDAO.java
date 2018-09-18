package com.utn.vista;


import java.util.Date;


public interface RopaDAO {

	public Ropa modeloBuscar(Ropa ropa);
	
	public int modeloGetStock(Ropa ropa);
	
	public float modeloGetPrecio(Ropa ropa);
	
	public String modeloGetFabrica(Ropa ropa);
	
	public Date modeloGetFechaInicioProd(Ropa ropa);
	
	public int modeloRealizarVenta(Ropa ropa);
	
	public int modeloModificar(Ropa ropa, Ropa nuevoRopa);

	public String dbGetCredentials();
	
	public void dbSetCredentials();
}
