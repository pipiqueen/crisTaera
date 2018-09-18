package com.utn.vista;

import static java.nio.file.StandardOpenOption.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LogIn {

	private String[] login = new String[2];
	private String usuarioSum = null;
	private Path path = Paths.get("C:\\Users\\Java\\Desktop\\crisTaera-master\\Ropa\\Tarea Avanzado\\users.txt");

	public void logInPrompt() {

		JFrame frame = new JFrame();

		JPanel panel = new JPanel(new BorderLayout(5, 5));

		JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
		label.add(new JLabel("Usuario", SwingConstants.RIGHT));
		label.add(new JLabel("Contraseña", SwingConstants.RIGHT));
		panel.add(label, BorderLayout.WEST);

		JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
		JTextField usuario = new JTextField();
		controls.add(usuario);
		JPasswordField contrasena = new JPasswordField();
		controls.add(contrasena);
		panel.add(controls, BorderLayout.CENTER);

		JOptionPane.showMessageDialog(frame, panel, "login", JOptionPane.QUESTION_MESSAGE);

		login[0] = usuario.getText();
		String contrasenaTemp = new String(contrasena.getPassword());
		login[1] = contrasenaTemp;
		usuarioSum = login[0] + login[1];

	}

	public boolean logInCheck() {

		if (login[0].equals("") || login[1].equals("")) {

			JOptionPane.showMessageDialog(null, "por favor ingrese datos validos");

			return false;

		} else {

			String usuarioCheck = get_SHA_512_SecurePassword(usuarioSum, "deberia guardar y buscar esto en la bd");

			ObjectInputStream in;

			try {

				in = new ObjectInputStream(Files.newInputStream(path));

				String encriptedUser = (String) in.readObject();

				if (usuarioCheck.equals(encriptedUser)) {
					return true;
				} else {
					return false;
				}

			} catch (IOException | ClassNotFoundException e) {

				e.printStackTrace();
			}

		}
		return false;

	}

	public String get_SHA_512_SecurePassword(String usuarioSum, String salt) {

		String contrasenaGenerada = null;

		try {

			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes(StandardCharsets.UTF_8));
			byte[] bytes = md.digest(usuarioSum.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));

			}

			contrasenaGenerada = sb.toString();

		}

		catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
			return null;
		}

		return contrasenaGenerada;

	}
}
