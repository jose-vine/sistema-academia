package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import dao.AlunoDao;
import dao.ConexaoMySQL;
import dao.TurmaDao;
import dto.AlunoDto;
import dto.TurmaDto;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaTurma extends JFrame {

	private JPanel contentPane;
	private JTextField textNome;
	private JTextField textValor;
	private JTable tableTurmas;
	private DefaultTableModel modeloTabela;
	private JTextField textId;
	private JTextField textPesquisaTurma;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaTurma frame = new TelaTurma();
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
	public TelaTurma() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 690, 613);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(193, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTurma = new JLabel("");
		lblTurma.setIcon(new ImageIcon(TelaTurma.class.getResource("/imagens/adicionar-turma.png")));
		lblTurma.setBounds(483, 68, 140, 140);
		contentPane.add(lblTurma);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setForeground(new Color(255, 255, 255));
		lblNome.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNome.setBounds(35, 173, 46, 14);
		contentPane.add(lblNome);
		
		textNome = new JTextField();
		textNome.setForeground(new Color(0, 0, 0));
		textNome.setFont(new Font("Arial", Font.PLAIN, 13));
		textNome.setBackground(new Color(255, 255, 255));
		textNome.setEditable(false);
		textNome.setBounds(35, 193, 310, 20);
		contentPane.add(textNome);
		textNome.setColumns(10);
		
		JLabel lblValor = new JLabel("Valor");
		lblValor.setForeground(Color.WHITE);
		lblValor.setFont(new Font("Arial", Font.PLAIN, 12));
		lblValor.setBounds(35, 224, 46, 14);
		contentPane.add(lblValor);
		
		textValor = new JTextField();
		textValor.setFont(new Font("Arial", Font.PLAIN, 13));
		textValor.setForeground(new Color(0, 0, 0));
		textValor.setBackground(new Color(255, 255, 255));
		textValor.setEditable(false);
		textValor.setBounds(35, 244, 152, 20);
		contentPane.add(textValor);
		textValor.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 313, 607, 216);
		contentPane.add(scrollPane);
		
		tableTurmas = new JTable();
		tableTurmas.setFont(new Font("Arial", Font.PLAIN, 13));
		tableTurmas.getTableHeader().setReorderingAllowed(false);
		tableTurmas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				preencherCamposDaLinhaSelecionada();
			}
		});
		scrollPane.setViewportView(tableTurmas);
		
		textId = new JTextField();
		textId.setForeground(new Color(0, 0, 0));
		textId.setFont(new Font("Arial", Font.PLAIN, 13));
		textId.setBackground(new Color(255, 255, 255));
		textId.setEditable(false);
		textId.setColumns(10);
		textId.setBounds(35, 145, 152, 20);
		contentPane.add(textId);
		
		JLabel lblId = new JLabel("ID");
		lblId.setForeground(Color.WHITE);
		lblId.setFont(new Font("Arial", Font.PLAIN, 12));
		lblId.setBounds(35, 125, 46, 14);
		contentPane.add(lblId);
		
		// Editar turma
		JLabel lblEditar = new JLabel("");
		lblEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(verificarLinhaSelecionada()) {
					permitirEdicaoDosCampos();
				} else {
					JOptionPane.showMessageDialog(null, "Nenhuma turma foi selecionada!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		lblEditar.setIcon(new ImageIcon(TelaTurma.class.getResource("/imagens/procurar.png")));
		lblEditar.setBounds(115, 15, 50, 50);
		lblEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblEditar);
		
		// Atualizar a tabela das turmas
		JLabel lblAtualizar = new JLabel("");
		lblAtualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<TurmaDto> turmas = TurmaDao.retornarTurmasBD();
				gerarTabelaTurmas(gerarLinhasTabelaTurmas(turmas), gerarColunasTabelaTurmas());
				bloquearEdicaoDosCampos();
			}
		});
		lblAtualizar.setIcon(new ImageIcon(TelaTurma.class.getResource("/imagens/atualizar.png")));
		lblAtualizar.setBounds(195, 15, 50, 50);
		lblAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblAtualizar);
		
		// Excluir a turma
		JLabel lblExcluir = new JLabel("");
		lblExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(verificarLinhaSelecionada()) {
					try {
						int confirmUsuario = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir o aluno?");
						if (confirmUsuario == 0) {
							int valorId = Integer.parseInt(textId.getText());
							if(TurmaDao.excluirTurmaBD(valorId)) {
								JOptionPane.showMessageDialog(null, "Turma excluída com sucesso!");
							} else {
								JOptionPane.showMessageDialog(null, "Erro ao excluir a turma", "Erro", JOptionPane.ERROR_MESSAGE);
							}
						}
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "TelaTurma: " + e1.getMessage());
					} catch (HeadlessException e1) {
						JOptionPane.showMessageDialog(null, "TelaTurma: " + e1.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Nenhuma turma foi selecionada!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		lblExcluir.setIcon(new ImageIcon(TelaTurma.class.getResource("/imagens/excluir.png")));
		lblExcluir.setBounds(275, 15, 50, 50);
		lblExcluir.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblExcluir);
		
		// Salvar dados da turma
		JLabel lblSalvar = new JLabel("");
		lblSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (verificarLinhaSelecionada()) {
					// Armazenando dados nas variáveis locais
					String nome = textNome.getText();
					int id = Integer.parseInt(textId.getText());
					double valor = Double.parseDouble(textValor.getText());
					
					// Instanciando um objeto do tipo TurmaDto
					TurmaDto turmaDto = new TurmaDto();
					turmaDto.setIdTurma(id);
					turmaDto.setNome(nome);
					turmaDto.setValor(valor);
					
					// Altera os dados da turma no banco de dados
					if (TurmaDao.alterarDadosTurmaBD(turmaDto)) {
						JOptionPane.showMessageDialog(null, "Alterações realizadas com sucesso!");
					} else {
						JOptionPane.showMessageDialog(null, "Não foi possível alterar os dados da turma!", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Nenhuma turma foi selecionada!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
				
				bloquearEdicaoDosCampos();
			}
		});
		lblSalvar.setIcon(new ImageIcon(TelaTurma.class.getResource("/imagens/salvar.png")));
		lblSalvar.setBounds(355, 15, 50, 50);
		lblSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblSalvar);
		
		// Cadastrar nova turma
		JLabel lblAdicionarTurma = new JLabel("");
		lblAdicionarTurma.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CadastroTurma cadastroTurma = new CadastroTurma();
				cadastroTurma.setVisible(true);
				cadastroTurma.setLocationRelativeTo(null);
			}
		});
		lblAdicionarTurma.setIcon(new ImageIcon(TelaTurma.class.getResource("/imagens/adicionar-turma-botao.png")));
		lblAdicionarTurma.setBounds(35, 15, 50, 50);
		lblAdicionarTurma.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblAdicionarTurma);
		
		JLabel lblPesquisaTurma = new JLabel("Pesquisa");
		lblPesquisaTurma.setForeground(new Color(255, 255, 255));
		lblPesquisaTurma.setFont(new Font("Arial", Font.PLAIN, 12));
		lblPesquisaTurma.setBounds(464, 224, 59, 14);
		contentPane.add(lblPesquisaTurma);
		
		textPesquisaTurma = new JTextField();
		textPesquisaTurma.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == e.VK_ENTER) {
					String pesquisaTurma = textPesquisaTurma.getText();
					ArrayList<TurmaDto> turmas = TurmaDao.procurarTurmasBD(pesquisaTurma);
					gerarTabelaTurmas(gerarLinhasTabelaTurmas(turmas), gerarColunasTabelaTurmas());
				}
			}
		});
		textPesquisaTurma.setForeground(new Color(0, 0, 0));
		textPesquisaTurma.setFont(new Font("Arial", Font.PLAIN, 13));
		textPesquisaTurma.setBounds(464, 244, 178, 20);
		contentPane.add(textPesquisaTurma);
		textPesquisaTurma.setColumns(10);
		
		ArrayList<TurmaDto> turmas= TurmaDao.retornarTurmasBD();
		gerarTabelaTurmas(gerarLinhasTabelaTurmas(turmas), gerarColunasTabelaTurmas());
		
	}
	
	// Cria tabela com os dados dos alunos
	private void gerarTabelaTurmas(Object[][] linhas, String[] colunas) {
		try {
			modeloTabela = new DefaultTableModel(linhas, colunas) {
				@Override
				public boolean isCellEditable(int linha, int coluna) {
					return false;
				}
			};
			tableTurmas.setModel(modeloTabela);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "CadastroTurma: " + e.getMessage());
		}
	}
		
	// Cria as colunas da tabela
	private String[] gerarColunasTabelaTurmas() {
		String[] colunas = null;
		try {
			String colunaUm, colunaDois, colunaTres;
			colunaUm = "ID";
			colunaDois = "Nome";
			colunaTres = "Valor";
			colunas = new String[] {colunaUm, colunaDois, colunaTres};
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "CadastroTurma: " + e.getMessage());
		}
		return colunas;
	}
		
	// Cria as linhas da tabela
	private Object[][] gerarLinhasTabelaTurmas(ArrayList<TurmaDto> listaTurmas) {
		Object[][] dadosTurmas = null;
		try {
			int linhas = listaTurmas.size();
			int colunas = 3;
			dadosTurmas = new Object[linhas][colunas];
			for (int i = 0; i < dadosTurmas.length; i++) {
				for (int i2 = 0; i2 < dadosTurmas[0].length; i2++) {
					if (i2 == 0) {
						dadosTurmas[i][i2] = listaTurmas.get(i).getIdTurma();
					} else if (i2 == 1) {
						dadosTurmas[i][i2] = listaTurmas.get(i).getNome();
					} else if (i2 == 2) {
						dadosTurmas[i][i2] = listaTurmas.get(i).getValor();
					}
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "TelaPrincipal: " + e.getMessage());
		}
		return dadosTurmas;
	}
	
	// Preenche os campos com os dados da linha selecionada
	private void preencherCamposDaLinhaSelecionada() {
		TurmaDto turmaDto = null;
		try {
			int linhaSelecionada = tableTurmas.getSelectedRow();
			int valorId = Integer.parseInt(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
			turmaDto = TurmaDao.buscarUmaTurmaPorId(valorId);
			textId.setText(String.valueOf(turmaDto.getIdTurma()));
			textNome.setText(turmaDto.getNome());
			textValor.setText(String.valueOf(turmaDto.getValor()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "TelaPrincipal: " + e.getMessage());
		}
	}
	
	// Permite a edição dos campos de texto
	private void permitirEdicaoDosCampos() {
		textNome.setEditable(true);
		textValor.setEditable(true);
	}
	
	// Bloquear a edição dos campos de texto
	private void bloquearEdicaoDosCampos() {
		textNome.setEditable(false);
		textValor.setEditable(false);
	}
	
	// Verifica se a linha da tabela foi selecionada
	private boolean verificarLinhaSelecionada() {
		boolean sucesso = false;
		if(tableTurmas.getSelectedRow() != -1) {
			sucesso = true;
		}
		return sucesso;
	}
}
