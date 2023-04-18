package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.ConexaoMySQL;
import dao.ContaAdminDao;
import dto.ContaAdminDto;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;

public class Login extends JFrame {
	
	private ContaAdminDto contaAdminDto;
	private ContaAdminDao contaAdminDao;
	private ResultSet rs;
	private ConexaoMySQL conexaoMetodo;
	private Connection conexao;

	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField passwordField;
	private JLabel lblBiometria;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 396, 418);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(193, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("BEM-VINDO");
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(128, 74, 130, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblUsuario = new JLabel("Usuário");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setFont(new Font("Arial Black", Font.PLAIN, 13));
		lblUsuario.setBounds(32, 185, 64, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setForeground(new Color(255, 255, 255));
		lblSenha.setFont(new Font("Arial Black", Font.PLAIN, 13));
		lblSenha.setBounds(32, 248, 52, 14);
		contentPane.add(lblSenha);
		
		textUsuario = new JTextField();
		textUsuario.setForeground(new Color(0, 0, 0));
		textUsuario.setBackground(new Color(255, 255, 255));
		textUsuario.setBounds(112, 179, 224, 29);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(new Color(0, 0, 0));
		passwordField.setBackground(new Color(255, 255, 255));
		passwordField.setBounds(112, 243, 224, 29);
		contentPane.add(passwordField);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setBackground(new Color(255, 255, 255));
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Estabelece a conexão com o banco de dados
				conexaoMetodo = new ConexaoMySQL();
				conexao = conexaoMetodo.conectarBD();
				
				if (conexao != null) {
					// Armazena os dados na DTO
					contaAdminDto = new ContaAdminDto();
					contaAdminDto.setUsuario(textUsuario.getText());
					contaAdminDto.setSenha(passwordField.getText());
					
					contaAdminDao = new ContaAdminDao();
					
					// chama o método da classe ContaAdminDao para obter os dados de login do banco
					rs = contaAdminDao.obterRegistroLogin(contaAdminDto);
					
					// Faz a autenticação do login
					if (autenticarLogin(rs)) {
						dispose();
						TelaPrincipal telaPrincipal = new TelaPrincipal();
						telaPrincipal.setVisible(true);
						telaPrincipal.setLocationRelativeTo(null);
					} else {
						JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		
		btnEntrar.setFont(new Font("Arial Black", Font.PLAIN, 12));
		btnEntrar.setBounds(247, 312, 89, 23);
		contentPane.add(btnEntrar);
		
		lblBiometria = new JLabel("");
		lblBiometria.setIcon(new ImageIcon(Login.class.getResource("/imagens/biometria.png")));
		lblBiometria.setBounds(140, 32, 100, 100);
		contentPane.add(lblBiometria);
		
	}
	
	private boolean autenticarLogin(ResultSet rs) {
		boolean sucesso = false;
		try {
			if (rs.next()) {
				sucesso = true;
			}
		} catch (SQLException eSQL) {
			JOptionPane.showMessageDialog(null, "Login " + eSQL.getMessage());
		}
		return sucesso;
	}
	
}
