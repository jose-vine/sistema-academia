package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dto.ContaAdminDto;

public class ContaAdminDao {
	
	String comandoSql, tabelaBD;
	PreparedStatement pstm;
	ConexaoMySQL conexaoMetodo;
	Connection conexao;
	ResultSet rs;
	
	public ResultSet obterRegistroLogin(ContaAdminDto contaAdminDto) {
		// Estabelece a conexão com o banco de dados
		conexaoMetodo = new ConexaoMySQL();
		conexao = conexaoMetodo.conectarBD();
		rs = null;
		// Retorna os dados de login registrados no banco
		if (conexao != null) {
			tabelaBD = "conta_admin";
			comandoSql = "select * from " + tabelaBD + " where usuario = ? and senha = ?";
			try {
				pstm = conexao.prepareStatement(comandoSql);
				pstm.setString(1, contaAdminDto.getUsuario());
				pstm.setString(2, contaAdminDto.getSenha());
				rs = pstm.executeQuery();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "ContaAdminDao: " + e.getMessage());
			}
		}
		return rs;
	}
	
}
