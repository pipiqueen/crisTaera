package com.utn.vista;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;

import javax.swing.JOptionPane;

public class RopaDAOJDBCImpl implements RopaDAO {

	public String url = "jdbc:mysql://localhost:3306/ropa?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	public int modeloGetStock(Ropa ropa) {

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try

		{

			con = DriverManager.getConnection(url, "root", dbGetCredentials());

			stmt = con.prepareStatement("SELECT stock FROM modelos WHERE nombre_modelo = ?");

			stmt.setString(1, ropa.getNombreModelo());

			rs = stmt.executeQuery();

			if (rs.next())

			{

				ropa.setStock(Integer.parseInt(rs.getString("stock")));

				return ropa.getStock();

			}

			else

			{

				return -1;

			}

		}

		catch (SQLException e)

		{

			throw new RuntimeException(e);

		}

		finally

		{

			try

			{
				rs.close();
				stmt.close();
				con.close();

			}

			catch (SQLException e)

			{

				e.printStackTrace();

			}

		}

	}

	public float modeloGetPrecio(Ropa ropa)

	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try

		{

			con = DriverManager.getConnection(url, "root", dbGetCredentials());

			stmt = con.prepareStatement("SELECT precio FROM modelos WHERE nombre_modelo = ?");

			stmt.setString(1, ropa.getNombreModelo());

			rs = stmt.executeQuery();

			if (rs.next())

			{

				ropa.setPrecio(Float.parseFloat(rs.getString("precio")));

				return ropa.getPrecio();
			}

			else

			{
				return -1;
				
			}

		}

		catch (SQLException e)

		{

			throw new RuntimeException(e);

		}

		finally

		{

			try

			{
				rs.close();
				stmt.close();
				con.close();

			}

			catch (SQLException e)

			{

				e.printStackTrace();

			}

		}

	}

	public String modeloGetFabrica(Ropa ropa)

	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try

		{

			con = DriverManager.getConnection(url, "root", dbGetCredentials());

			stmt = con.prepareStatement("SELECT * FROM modelos INNER JOIN fabricas WHERE nombre_modelo = ?");

			stmt.setString(1, ropa.getNombreModelo());

			rs = stmt.executeQuery();

			if (rs.next())

			{

				

				return rs.getString("f_nombre");
			}

			else

			{

				return "Fabrica no encontrada";

			}

		}

		catch (SQLException e)

		{

			throw new RuntimeException(e);

		}

		finally

		{

			try

			{

				rs.close();
				stmt.close();
				con.close();

			}

			catch (SQLException e)

			{

				e.printStackTrace();

			}

		}
	
	}

	public Date modeloGetFechaInicioProd(Ropa ropa)

	{

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try

		{

			con = DriverManager.getConnection(url, "root", dbGetCredentials());

			stmt = con.prepareStatement("SELECT fecha_inicio_prod FROM modelos WHERE nombre_modelo = ?");

			stmt.setString(1, ropa.getNombreModelo());

			rs = stmt.executeQuery();

			if (rs.next())

			{

				try {
					ropa.setFechaInicioProd(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("fecha_inicio_prod")));
				} catch (ParseException e) {
					return null;
					
				}

				return ropa.getFechaInicioProd();
			}

			else

			{

				return null;
			}

		}

		catch (SQLException e)

		{

			throw new RuntimeException(e);

		}

		finally

		{

			try

			{

				rs.close();
				stmt.close();
				con.close();

			}

			catch (SQLException e)

			{

				e.printStackTrace();

			}

		}

	}

	public int modeloRealizarVenta(Ropa ropa)

	{
		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		ResultSet rs = null;

		try

		{

			con = DriverManager.getConnection(url, "root", dbGetCredentials());

			stmt = con.prepareStatement("SELECT stock FROM modelos WHERE nombre_modelo = ?");

			stmt.setString(1, ropa.getNombreModelo());

			rs = stmt.executeQuery();

			if (rs.next())

			{

				ropa.setStock(rs.getInt("stock"));

				if (ropa.getStock() >= 0) {

					stmt1 = con.prepareStatement("UPDATE modelos SET stock = ? WHERE nombre_modelo = ?");

					stmt1.setInt(1, (ropa.getStock() - 1));

					stmt1.setString(2, ropa.getNombreModelo());

					

					return stmt1.executeUpdate();
				} else {

					return 1;
				}
			}

			else

			{
				return 0;
			}

		}

		catch (SQLException e)

		{

			throw new RuntimeException(e);

		}

		finally

		{

			try

			{
				rs.close();
				stmt.close();
				con.close();

			}

			catch (SQLException e)

			{

				e.printStackTrace();

			}

		}
	}

	public int modeloModificar(Ropa ropa, Ropa nuevoRopa)

	{
		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		ResultSet rs = null;

		try

		{

			con = DriverManager.getConnection(url, "root", dbGetCredentials());

			stmt = con.prepareStatement("SELECT * FROM modelos WHERE nombre_modelo = ?");

			stmt.setString(1, ropa.getNombreModelo());

			rs = stmt.executeQuery();

			if (rs.next())

			{

				stmt1 = con.prepareStatement(
						"UPDATE modelos SET nombre_modelo = ?, precio = ?, fabrica = ?, fecha_inicio_prod = ?, stock = ? WHERE nombre_modelo = ?");

				stmt1.setString(1, nuevoRopa.getNombreModelo());
				stmt1.setFloat(2, nuevoRopa.getPrecio());
				stmt1.setInt(3, (nuevoRopa.getFabrica() + 1));

				java.sql.Date dateSql = new java.sql.Date(nuevoRopa.getFechaInicioProd().getTime());

				stmt1.setDate(4, dateSql);

				stmt1.setInt(5, nuevoRopa.getStock());
				stmt1.setString(6, ropa.getNombreModelo());

				return stmt1.executeUpdate();
			}

			else

			{
				return 0;

			}

		}

		catch (SQLException e)

		{

			throw new RuntimeException(e);

		}

		finally

		{

			try

			{
				rs.close();
				stmt1.close();
				stmt.close();
				con.close();

			}

			catch (SQLException e)

			{

				e.printStackTrace();

			}

		}
	

	}

	public int agregarModelo(Ropa ropa) {
		{
			Connection con = null;
			PreparedStatement stmt = null;
			PreparedStatement stmt1 = null;
			ResultSet rs = null;

			try

			{
				String password = dbGetCredentials();
				System.out.println(password);
				con = DriverManager.getConnection(url, "root", password);

				stmt = con.prepareStatement("SELECT * FROM modelos WHERE nombre_modelo = ?");

				stmt.setString(1, ropa.getNombreModelo());

				rs = stmt.executeQuery();

				if (!rs.next())

				{

					stmt1 = con.prepareStatement(
							"INSERT INTO modelos (nombre_modelo, precio, fabrica, fecha_inicio_prod, stock) VALUES (?,?,?,?,?)");
					stmt1.setString(1, ropa.getNombreModelo());
					stmt1.setFloat(2, ropa.getPrecio());
					stmt1.setInt(3, ropa.getFabrica() + 1);

					
						java.sql.Date dateSql = new java.sql.Date(ropa.getFechaInicioProd().getTime());

						stmt1.setDate(4, dateSql);
				

					stmt1.setInt(5, ropa.getStock());

									return stmt1.executeUpdate();
				}

				else

				{

					return 0;

				}

			}

			catch (SQLException e)

			{

				throw new RuntimeException(e);

			}

			finally

			{

			}
			

		}

	}

	public String dbGetCredentials()

	{

		Path file = Paths.get("C:\\Users\\Java\\Desktop\\crisTaera-master\\Ropa\\Tarea Avanzado\\dbCredentials.txt");

		ObjectInputStream in;
		try

		{

			in = new ObjectInputStream(Files.newInputStream(file));

			StringBuilder cred = (StringBuilder) in.readObject();
			StringBuilder tmp = new StringBuilder();
			final int OFFSET = 4;

			for (int i = 0; i < cred.length(); i++)

			{

				tmp.append((char) (cred.charAt(i) - OFFSET));

			}

			String reversed = new StringBuffer(tmp.toString()).reverse().toString();
			return new String(Base64.getDecoder().decode(reversed));

		}

		catch (IOException | ClassNotFoundException e)

		{

			e.printStackTrace();

		}

		return null;

	}

	// Esta comentado porque si bien estaria bueno poder cambiar la pass desde el
	// programa
	// esto implicaria hacer el acceso al archivo config de sql y no al archivo del
	// cual levanta
	// el pass encriptado

	@Override
	public void dbSetCredentials()

	{

		/*
		 * Path file = Paths.get("C:\\Users\\Laboratorio\\Desktop\\dbCredentials.txt");
		 * 
		 * ObjectOutputStream out; try {
		 * 
		 * out = new ObjectOutputStream(Files.newOutputStream(file));
		 * 
		 * String b64encoded = Base64.getEncoder().encodeToString("asd123".getBytes());
		 * 
		 * String reverse = new StringBuffer(b64encoded).reverse().toString();
		 * 
		 * StringBuilder tmp = new StringBuilder(); final int OFFSET = 4; for (int i =
		 * 0; i < reverse.length(); i++) { tmp.append((char) (reverse.charAt(i) +
		 * OFFSET)); }
		 * 
		 * out.writeObject(tmp);
		 * 
		 * } catch (IOException e) {
		 * 
		 * e.printStackTrace();
		 * 
		 * }
		 */

	}

	@Override
	public Ropa modeloBuscar(Ropa ropa) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try

		{

			con = DriverManager.getConnection(url, "root", dbGetCredentials());

			stmt = con.prepareStatement("SELECT * FROM modelos INNER JOIN fabricas WHERE nombre_modelo = ?");

			stmt.setString(1, ropa.getNombreModelo());

			rs = stmt.executeQuery();

			if (rs.next())

			{
				try {
					ropa.setFechaInicioProd(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("fecha_inicio_prod")));
				} catch (ParseException e) {
					return null;
					
				}
				ropa.setStock(Integer.parseInt(rs.getString("stock")));
				ropa.setPrecio(Float.parseFloat(rs.getString("precio")));
				ropa.setFabricaString(rs.getString("f_nombre"));
				
				return ropa;

			}

			else

			{

				return -1;

			}

		}

		catch (SQLException e)

		{

			throw new RuntimeException(e);

		}

		finally

		{

			try

			{
				rs.close();
				stmt.close();
				con.close();

			}

			catch (SQLException e)

			{

				e.printStackTrace();

			}

		}

		
		return null;
	}



	
}
