package com.utn.vista;

import javax.swing.JOptionPane;

import java.awt.HeadlessException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

public class Test {

	public static void main(String[] args) {
		
		 
		
		boolean logInState = false;
		
		LogIn login = new LogIn();
		
		login.logInPrompt();
		
		logInState = login.logInCheck();
		
		if(!logInState) 
		
		{
			
			JOptionPane.showMessageDialog(null, "Usuario y contraseña equivocados.");
			
		}
		
		else 
		
		{
			Ropa ropa = new Ropa();
			
			JOptionPane.showMessageDialog(null, "Bienvenido");
			
			RopaDAOJDBCImpl ropaDAO = new RopaDAOFactory().crearRopaDAOJDBCImpl(); 
		
			boolean seguir = true;
			
			
	                Object[] options = {
	                		"Agregar un modelo",
	                        "Ver stock de un modelo",
	                		"Ver precio de un modelo",
	                		"Ver fabrica de origen de un modelo",
	                		"Ver fecha de inicio de produccion de un modelo",
	                		"Realizar un venta",
	                		"Modificar un modelo",
	                		"Cerrar aplicacion"
	                		};
	    			
	                JComboBox optionList = new JComboBox(options);
	                optionList.setSelectedIndex(7);
	                
	                while(seguir) {
	                
	                JOptionPane.showMessageDialog(null, optionList, "Elija una opcion", JOptionPane.QUESTION_MESSAGE);
	            int choice = optionList.getSelectedIndex();
			
	           
	                  
	                switch(choice) 
			
			{
			
			case 0:
				
				ropa.setNombreModelo(JOptionPane.showInputDialog("Por favor ingrese el nombre del nuevo modelo."));
				
				String[] optionsTemp = { "chon-wan", "JFK", "umbakte" };
				
				ropa.setNombreModelo(JOptionPane.showInputDialog("Ingrese un nombre."));
				ropa.setPrecio(Float.parseFloat(JOptionPane.showInputDialog("Ingrese un precio.")));
				ropa.setFabrica((JOptionPane.showOptionDialog(null, "Por favor elija una fabrica", null, 0, 0, null,
						optionsTemp, 0)) + 1);

				Date date;
				try {
					date = new SimpleDateFormat("dd/MM/yyyy").parse(JOptionPane
							.showInputDialog("Por favor ingrese la fecha de inicio de produccion en dd/mm/yyyy"));

					

					ropa.setFechaInicioProd(date);
				} catch (HeadlessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ropa.setStock(Integer.parseInt(JOptionPane.showInputDialog("Por favor ingrese el stock")));
				
				
				
				
				
				if(ropaDAO.agregarModelo(ropa)==0) {
					JOptionPane.showMessageDialog(null, "Modelo ya existente.");
				}else {
					JOptionPane.showMessageDialog(null, "Nuevo modelo ingresado con exito");

				}
				
				break;
			case 1:
				
				ropa.setNombreModelo(JOptionPane.showInputDialog("Por favor ingrese el nombre del modelo a revisar stock"));
				if(ropaDAO.modeloGetStock(ropa)== -1) {
					JOptionPane.showMessageDialog(null, "modelo no encontrado");
				}else {
				JOptionPane.showMessageDialog(null,
						"El stock del modelo " + ropa.getNombreModelo() + " es " + ropaDAO.modeloGetStock(ropa));
				}
				break;
			case 2:
				
				ropa.setNombreModelo(JOptionPane.showInputDialog("Por favor ingrese el nombre del modelo del que desea obtener el precio"));
				
				if(ropaDAO.modeloGetPrecio(ropa)==-1) {
					JOptionPane.showMessageDialog(null, "Precio no encontrado");

				}else {
				JOptionPane.showMessageDialog(null,
						"El precio del modelo " + ropa.getNombreModelo() + " es " + ropaDAO.modeloGetPrecio(ropa));
				}
				
				break;
			case 3:
				
				ropa.setNombreModelo(JOptionPane.showInputDialog("Por favor ingrese el nombre del modelo del que desea saber la fabrica de origen"));
				
				if(ropaDAO.modeloGetFabrica(ropa).equals("fabrica no encontrada")) {
					JOptionPane.showMessageDialog(null, ropaDAO.modeloGetFabrica(ropa));
				}else {
				JOptionPane.showMessageDialog(null,
						"La fabrica del modelo " + ropa.getNombreModelo() + " es " + ropaDAO.modeloGetFabrica(ropa));
				}
				
				break;
			case 4:
				
				ropa.setNombreModelo(JOptionPane.showInputDialog("Por favor ingrese el nombre del modelo del que desea obtener la fecha de inicio de produccion"));
				
				
				if(ropaDAO.modeloGetFechaInicioProd(ropa).equals(null)) {
					JOptionPane.showMessageDialog(null, "Fecha no encontrada");

				}else {
				JOptionPane.showMessageDialog(null, "LA fecha de inicio de produccion del modelo "
						+ ropa.getNombreModelo() + " es " + ropaDAO.modeloGetFechaInicioProd(ropa));
				}
				
				break;
			case 5:
				
				ropa.setNombreModelo(JOptionPane.showInputDialog("Por favor ingrese el nombre del modelo a vender"));
				
				if(ropaDAO.modeloRealizarVenta(ropa)==0) {

					JOptionPane.showMessageDialog(null, "Error en el proseamiento de la venta.\nModelo no encontrado.");

				}else if (ropaDAO.modeloRealizarVenta(ropa)==1){
					JOptionPane.showMessageDialog(null,
							"Error en el proseamiento de la venta.\nRevise que haya stock del modelo.");

				}else {

					JOptionPane.showMessageDialog(null, "Venta realizada con exito");
				}
								
				break;
			case 6:
				
				ropa.setNombreModelo(JOptionPane.showInputDialog("Por favor ingrese el nombre del modelo a modificar"));
				Ropa nuevoRopa = new Ropa();
				String[] optionsTemp = { "chon-wan", "JFK", "umbakte" };
				
				nuevoRopa.setNombreModelo(JOptionPane.showInputDialog("Ingrese un nuevo nombre."));
				nuevoRopa.setPrecio(Float.parseFloat(JOptionPane.showInputDialog("Ingrese un nuevo precio.")));
				nuevoRopa.setFabrica((JOptionPane.showOptionDialog(null, "Por favor elija una fabrica", null, 0, 0, null,
						optionsTemp, 0)) + 1);

				Date date;
				try {
					date = new SimpleDateFormat("dd/MM/yyyy").parse(JOptionPane
							.showInputDialog("Por favor ingrese la fecha de inicio de produccion en dd/mm/yyyy"));

					

					nuevoRopa.setFechaInicioProd(date);
				} catch (HeadlessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				nuevoRopa.setStock(Integer.parseInt(JOptionPane.showInputDialog("Por favor ingrese el stock")));
				
				
				
				
				
				if(ropaDAO.modeloModificar(ropa, nuevoRopa)==0) {
					JOptionPane.showMessageDialog(null, "Error en el proseamiento de la modificacion.");

				}else {
					JOptionPane.showMessageDialog(null, "Modificacion realizada con exito");
				}
				
				
				
				
				break;
			default:
			
				System.exit(0);
			
			}
	                }
	           	 }
		
			}
		
	
		}			


