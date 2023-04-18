package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConexaoMySQL {
	
	// Estabelece a conexão com o banco de dados
	public static Connection conectarBD() {
		Connection con;
		final String DRIVER_BD, LOCAL_BD, NOME_BD, USUARIO_BD, SENHA_BD;
		con = null;
		DRIVER_BD = "com.mysql.jdbc.Driver";
		NOME_BD = "alternativa";
		LOCAL_BD = "jdbc:mysql://localhost:3306/" + NOME_BD;
		USUARIO_BD = "root";
		SENHA_BD = "";
		try {
			Class.forName(DRIVER_BD);
			con = DriverManager.getConnection(LOCAL_BD, USUARIO_BD, SENHA_BD);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "ConexaoMySQL: " + e.getMessage());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ConexaoMySQL: " + e.getMessage());
		}
		return con;
	}
	
}
