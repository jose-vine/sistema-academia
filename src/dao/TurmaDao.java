package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import dto.TurmaDto;

public class TurmaDao {
	
	private static PreparedStatement pstm;
	private static Connection conexao;
	private static ResultSet rs;
	private static String comandoSql;
	private static String tabelaBD = "turmas";
	
	public TurmaDao() {
		tabelaBD = "turmas";
		comandoSql = "";
	}
	
	// Cadastrar uma turma no banco de dados
	public static boolean cadastrarTurmaBD(TurmaDto turmaDto) {
		boolean sucesso = false;
		conexao = ConexaoMySQL.conectarBD();
		if (conexao != null) {
			try {
				comandoSql = "insert into " + tabelaBD + " (id_turma, nome_turma, valor) values (default, ?, ?)";
				pstm = conexao.prepareStatement(comandoSql);
				pstm.setString(1, turmaDto.getNome());
				pstm.setDouble(2, turmaDto.getValor());
				pstm.execute();
				pstm.close();
				sucesso = true;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "TurmaDao: " + e.getMessage());
			}
		}
		return sucesso;
	}
	
	// Retorna um array com as turmas que estão no banco de dados
	public static ArrayList<TurmaDto> retornarTurmasBD() {
		ArrayList<TurmaDto> turmas = new ArrayList<>();
		conexao = ConexaoMySQL.conectarBD();
		if (conexao != null) {
			try {
				comandoSql = "select * from " + tabelaBD;
				pstm = conexao.prepareStatement(comandoSql);
				rs = pstm.executeQuery();
				while (rs.next()) {
					TurmaDto turmaDto = new TurmaDto();
					turmaDto.setIdTurma(rs.getInt("id_turma"));
					turmaDto.setNome(rs.getString("nome_turma"));
					turmaDto.setValor(rs.getDouble("valor"));
					turmas.add(turmaDto);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "TurmaDao: " + e.getMessage());
			}
		}
		return turmas;
	}
	
	// Buscar uma turma pelo id no banco de dados
	public static TurmaDto buscarUmaTurmaPorId(int valorId) {
		TurmaDto turmaDto = new TurmaDto();
		conexao = ConexaoMySQL.conectarBD();
		if (conexao != null) {
			try {
				comandoSql = "select * from " + tabelaBD + " where id_turma = ?";
				pstm = conexao.prepareStatement(comandoSql);
				pstm.setInt(1, valorId);
				rs = pstm.executeQuery();
				if (rs.next()) {
					turmaDto.setIdTurma(rs.getInt("id_turma"));
					turmaDto.setNome(rs.getString("nome_turma"));
					turmaDto.setValor(rs.getDouble("valor"));
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "TurmaDao: " + e.getMessage());
			}
		}
		return turmaDto;
	}
	
	// Exclui uma turma do banco de dados
	public static boolean excluirTurmaBD(int valorId) {
		boolean sucesso = false;
		conexao = ConexaoMySQL.conectarBD();
		if(conexao != null) {
			try {
				comandoSql = "delete from " + tabelaBD + " where id_turma = ?";
				pstm = conexao.prepareStatement(comandoSql);
				pstm.setInt(1, valorId);
				pstm.execute();
				pstm.close();
				sucesso = true;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "TurmaDao: " + e.getMessage());
			}
		}
		return sucesso;
	}
	
	// Altera os dados da turma no banco de dados
	public static boolean alterarDadosTurmaBD(TurmaDto turmaDto) {
		boolean sucesso = false;
		conexao = ConexaoMySQL.conectarBD();
		if(conexao != null) {
			try {
				comandoSql = "update " + tabelaBD + " set nome_turma = ?, valor = ? where id_turma = ?";
				pstm = conexao.prepareStatement(comandoSql);
				pstm.setString(1, turmaDto.getNome());
				pstm.setDouble(2, turmaDto.getValor());
				pstm.setInt(3, turmaDto.getIdTurma());
				pstm.execute();
				pstm.close();
				sucesso = true;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "TurmaDao: " + e.getMessage());
			}
		}
		return sucesso;
	}
	
	// Retorna um array com as turmas pesquisadas
	public static ArrayList<TurmaDto> procurarTurmasBD(String pesquisa) {
		Statement st;
		ArrayList<TurmaDto> turmas = new ArrayList<>();
		conexao = ConexaoMySQL.conectarBD();
		if (conexao != null) {
			try {
				comandoSql = "select * from " + tabelaBD + " where nome_turma like '" + pesquisa + "%'";
				st = conexao.createStatement();
				rs = st.executeQuery(comandoSql);
				while(rs.next()) {
					TurmaDto turmaDto = new TurmaDto();
					turmaDto.setIdTurma(rs.getInt("id_turma"));;
					turmaDto.setNome(rs.getString("nome_turma"));
					turmaDto.setValor(rs.getDouble("valor"));
					turmas.add(turmaDto);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "TurmaDao: " + e.getMessage());
			}
			
		}
		return turmas;
	}
	
	// Identificar se existe uma turma cadastrada no banco de dados;
	public static boolean verificarExistenciaDeTurmaBD() {
		boolean sucesso = false;
		conexao = ConexaoMySQL.conectarBD();
		if (conexao != null) {
			try {
				comandoSql = "select * from " + tabelaBD;
				pstm = conexao.prepareStatement(comandoSql);
				rs = pstm.executeQuery();
				if (rs.next()) {
					sucesso = true;
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "TurmaDao: " + e.getMessage());
			}
		}
		return sucesso;
	}
}
