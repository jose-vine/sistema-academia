package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

import javax.swing.JOptionPane;

import dto.AlunoDto;
import dto.Endereco;
import dto.TurmaDto;

public class AlunoDao {
	
	private static Connection conexao;
	private static String comandoSql;
	private static PreparedStatement pstm;
	private static ResultSet rs;
	private static String tabelaBD = "alunos";
	
	
	// Cadastra um aluno no banco de dados
	public static boolean cadastrarAlunoBD(AlunoDto alunoDto) {
		boolean sucesso = false;
		conexao = ConexaoMySQL.conectarBD();
		if (conexao != null) {
			try {
				comandoSql = "insert into " + tabelaBD + " (id_aluno, id_turma, nome_aluno, telefone, cpf, cep, logradouro, bairro, cidade, sexo, numero_logradouro, vencimento) values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstm = conexao.prepareStatement(comandoSql);
				pstm.setInt(1, alunoDto.getTurma().getIdTurma());
				pstm.setString(2, alunoDto.getNome());
				pstm.setString(3, alunoDto.getTelefone());
				pstm.setString(4, alunoDto.getCpf());
				pstm.setString(5, alunoDto.getEndereco().getCep());
				pstm.setString(6, alunoDto.getEndereco().getLogradouro());
				pstm.setString(7, alunoDto.getEndereco().getBairro());
				pstm.setString(8, alunoDto.getEndereco().getCidade());
				pstm.setString(9, alunoDto.getSexo());
				pstm.setInt(10, alunoDto.getEndereco().getNumeroLogradouro());
				pstm.setDate(11, new Date(alunoDto.getVencimento().getTime()));
				pstm.execute();
				pstm.close();
				sucesso = true;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "AlunoDao: " + e.getMessage());
			}
		}
		return sucesso;
	}
	
	// Retorna os registros dos alunos do banco de dados
	public static ArrayList<AlunoDto> retornarAlunosBD() {
		ArrayList<AlunoDto> alunos = new ArrayList<>();
		conexao = ConexaoMySQL.conectarBD();
		if (conexao != null ) {
			try {
				comandoSql = "select * from " + tabelaBD + " join turmas on alunos.id_turma = turmas.id_turma";
				pstm = conexao.prepareStatement(comandoSql);
				rs = pstm.executeQuery();
				while (rs.next()) {
					AlunoDto alunoDto = new AlunoDto();
					alunoDto.setNome(rs.getString("nome_aluno"));
					alunoDto.setTelefone(rs.getString("telefone"));
					alunoDto.setCpf(rs.getString("cpf"));
					alunoDto.setSexo(rs.getString("sexo"));
					alunoDto.setIdAluno(rs.getInt("id_aluno"));
					alunoDto.setVencimento(rs.getDate("vencimento"));
					
					TurmaDto turmaDto = new TurmaDto();
					turmaDto.setNome(rs.getString("nome_turma"));
					turmaDto.setIdTurma(rs.getInt("id_turma"));
					
					alunoDto.setTurma(turmaDto);
					
					Endereco endAluno = new Endereco();
					endAluno.setLogradouro(rs.getString("logradouro"));
					endAluno.setCidade(rs.getString("cidade"));
					endAluno.setCep(rs.getString("cep"));
					endAluno.setBairro(rs.getString("bairro"));
					endAluno.setNumeroLogradouro(rs.getInt("numero_logradouro"));
					
					alunoDto.setEndereco(endAluno);
					alunos.add(alunoDto);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "AlunoDao retornaLista: " + e.getMessage());
			}
		}
		return alunos;
	}
	
	// Alterar dados de um aluno no banco de dados
	public static boolean alterarAlunoBD(AlunoDto alunoDto) {
		boolean sucesso = false;
		conexao = ConexaoMySQL.conectarBD();
		if (conexao != null) {
			comandoSql = "update " + tabelaBD + " set nome_aluno = ?, telefone = ?, cpf = ?, cep = ?, logradouro = ?, bairro = ?, cidade = ?, sexo = ?, numero_logradouro = ?, vencimento = ?, id_turma = ? where id_aluno = ?";
			try {
				pstm = conexao.prepareStatement(comandoSql);
				pstm.setString(1, alunoDto.getNome());
				pstm.setString(2, alunoDto.getTelefone());
				pstm.setString(3, alunoDto.getCpf());
				pstm.setString(4, alunoDto.getEndereco().getCep());
				pstm.setString(5, alunoDto.getEndereco().getLogradouro());
				pstm.setString(6, alunoDto.getEndereco().getBairro());
				pstm.setString(7, alunoDto.getEndereco().getCidade());
				pstm.setString(8, alunoDto.getSexo());
				pstm.setInt(9, alunoDto.getEndereco().getNumeroLogradouro());
				pstm.setDate(10, new Date(alunoDto.getVencimento().getTime()));
				pstm.setInt(11, alunoDto.getTurma().getIdTurma());
				pstm.setInt(12, alunoDto.getIdAluno());
				pstm.execute();
				pstm.close();
				sucesso = true;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "AlunoDao: " + e.getMessage());
			}
		}
		return sucesso;
	}
	
	// Busca os dados do aluno no banco por meio do id
	public static AlunoDto buscarUmAlunoPorId(int valorId) {
		AlunoDto alunoDto = null;
		conexao = ConexaoMySQL.conectarBD();
		if (conexao != null) {
			try {
				comandoSql = "select * from " + tabelaBD + " join turmas on alunos.id_turma = turmas.id_turma where id_aluno = ?";
				pstm = conexao.prepareStatement(comandoSql);
				pstm.setInt(1, valorId);
				rs = pstm.executeQuery();
				if (rs.next()) {
					alunoDto = new AlunoDto();
					alunoDto.setCpf(rs.getString("cpf"));
					alunoDto.setIdAluno(rs.getInt("id_aluno"));
					alunoDto.setNome(rs.getString("nome_aluno"));
					alunoDto.setSexo(rs.getString("sexo"));
					alunoDto.setTelefone(rs.getString("telefone"));
					alunoDto.setVencimento(rs.getDate("vencimento"));
					
					TurmaDto turmaDto = new TurmaDto();
					turmaDto.setIdTurma(rs.getInt("id_turma"));
					turmaDto.setNome(rs.getString("nome_turma"));
					alunoDto.setTurma(turmaDto);
					
					Endereco enderecoAluno = new Endereco();
					enderecoAluno.setBairro(rs.getString("bairro"));
					enderecoAluno.setCep(rs.getString("cep"));
					enderecoAluno.setCidade(rs.getString("cidade"));
					enderecoAluno.setLogradouro(rs.getString("logradouro"));
					enderecoAluno.setNumeroLogradouro(rs.getInt("numero_logradouro"));
					alunoDto.setEndereco(enderecoAluno);				
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "AlunoDao: " + e.getMessage());
			}
		}
		return alunoDto;
	}
	
	// Exclui o aluno do banco de dados por meio do id
	public static boolean excluirUmAlunoPorId(int valorId) {
		boolean sucesso = false;
		conexao = ConexaoMySQL.conectarBD();
		if (conexao != null) {
			try {
				comandoSql = "delete from " + tabelaBD + " where id_aluno = ?";
				pstm = conexao.prepareStatement(comandoSql);
				pstm.setInt(1, valorId);
				pstm.execute();
				pstm.close();
				sucesso = true;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "AlunoDao: " + e.getMessage());
			}
		}
		return sucesso;
	}
	
	// Procurar aluno no banco de dados
	public static ArrayList<AlunoDto> procurarAlunoBD(String nome) {
		Statement stm = null;
		ArrayList<AlunoDto> alunos = new ArrayList<>();
		conexao = ConexaoMySQL.conectarBD();
		if (conexao != null) {
			try {
				comandoSql = "select * from " + tabelaBD + " join turmas on alunos.id_turma = turmas.id_turma where alunos.nome_aluno like '" + nome + "%'";
				stm = conexao.createStatement();
				rs = stm.executeQuery(comandoSql);
				while (rs.next()) {
					AlunoDto alunoDto = new AlunoDto();
					alunoDto.setCpf(rs.getString("cpf"));
					alunoDto.setIdAluno(rs.getInt("id_aluno"));
					alunoDto.setNome(rs.getString("nome_aluno"));
					alunoDto.setSexo(rs.getString("sexo"));
					alunoDto.setTelefone(rs.getString("telefone"));
					alunoDto.setVencimento(rs.getDate("vencimento"));
					
					Endereco endAluno = new Endereco();
					endAluno.setBairro(rs.getString("bairro"));
					endAluno.setCep(rs.getString("cep"));
					endAluno.setCidade(rs.getString("cidade"));
					endAluno.setLogradouro(rs.getString("logradouro"));
					endAluno.setNumeroLogradouro(rs.getInt("numero_logradouro"));
					
					TurmaDto turmaDto = new TurmaDto();
					turmaDto.setNome(rs.getString("nome_turma"));
					turmaDto.setIdTurma(rs.getInt("id_turma"));
					
					alunoDto.setEndereco(endAluno);
					alunoDto.setTurma(turmaDto);
					alunos.add(alunoDto);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "AlunoDao: " + e.getMessage());
			}
		}
		return alunos;
	}
	
}
