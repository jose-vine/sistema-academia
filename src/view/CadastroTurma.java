package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.ConexaoMySQL;
import dao.TurmaDao;
import dto.TurmaDto;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class CadastroTurma extends JFrame {

	private JPanel contentPane;
	private JTextField textNome;
	private JTextField textValor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroTurma frame = new CadastroTurma();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CadastroTurma() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 557, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(193, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Cria as Jlabels
		JLabel lblNome = new JLabel("Nome");
		lblNome.setForeground(new Color(255, 255, 255));
		lblNome.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNome.setBounds(257, 229, 48, 14);
		contentPane.add(lblNome);
		
		JLabel lblValor = new JLabel("Valor");
		lblValor.setForeground(new Color(255, 255, 255));
		lblValor.setFont(new Font("Arial", Font.PLAIN, 15));
		lblValor.setBounds(261, 300, 40, 14);
		contentPane.add(lblValor);
		
		JLabel lblTurma = new JLabel("");
		lblTurma.setIcon(new ImageIcon(CadastroTurma.class.getResource("/imagens/adicionar-turma.png")));
		lblTurma.setBounds(209, 40, 140, 140);
		contentPane.add(lblTurma);
		
		// Cadastrar turma
		JLabel lblBotaoConfirmar = new JLabel("");
		lblBotaoConfirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Connection conexao = ConexaoMySQL.conectarBD();
				if (conexao != null) {
					try {
						// Armazenando os dados nas variáveis locais
						String nome = textNome.getText();
						double valor = Double.parseDouble(textValor.getText());
						
						// Instanciando um objeto do tipo TurmaDto
						TurmaDto turmaDto = new TurmaDto();
						turmaDto.setNome(nome);
						turmaDto.setValor(valor);
						
						// Cadastrando a turma no banco de dados
						if(TurmaDao.cadastrarTurmaBD(turmaDto)) {
							JOptionPane.showMessageDialog(null, "Turma cadastrada com sucesso!");
							textNome.requestFocus();
						} else {
							JOptionPane.showMessageDialog(null, "Erro ao cadastrar a turma!", "Erro", JOptionPane.ERROR_MESSAGE);
						}
						
						limparCampos();
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "CadastroTurma: " + e1.getMessage());
					} catch (HeadlessException e1) {
						JOptionPane.showMessageDialog(null, "CadastroTurma: " + e1.getMessage());
					}
				}
			}
		});
		lblBotaoConfirmar.setIcon(new ImageIcon(CadastroTurma.class.getResource("/imagens/botao_confirmar.png")));
		lblBotaoConfirmar.setBounds(481, 400, 50, 50);
		lblBotaoConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblBotaoConfirmar);
		
		// Cria os campos de texto
		textNome = new JTextField();
		textNome.setBounds(196, 249, 170, 20);
		contentPane.add(textNome);
		textNome.setColumns(10);
		
		textValor = new JTextField();
		textValor.setBounds(196, 320, 170, 20);
		contentPane.add(textValor);
		textValor.setColumns(10);
		
	}
	
	// Limpa os campos preenchidos
	private void limparCampos() {
		textNome.setText("");
		textValor.setText("");
	}
	
}
