package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import dao.AlunoDao;
import dao.TurmaDao;
import dto.AlunoDto;
import dto.Endereco;
import dto.TurmaDto;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Canvas;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Label;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Button;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTextField textNome;
	private JTextField textLogradouro;
	private JTextField textNumeroLog;
	private JTextField textBairro;
	private JTextField textCidade;
	private JFormattedTextField formattedTextCpf;
	private JFormattedTextField formattedTextTelefone;
	private JFormattedTextField formattedTextCep;
	private JFormattedTextField formattedTextVenc;
	private JLabel lblBotaoModificarLinha;
	private JLabel lblBotaoSalvar;
	private JLabel lblBotaoAtualizar;
	private JLabel lblId;
	private JTextField textId;
	private JTable tableAlunos;
	private DefaultTableModel modeloTabela;
	private JTextField textNomeCampoPesquisar;
	private ResultSet rs;
	private JTextField textTurma;
	private JTextField textSexo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
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
	public TelaPrincipal() {
		setResizable(false);
		setTitle("Academia Alternativa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(193, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Criam as JLabels
		JLabel lblNome = new JLabel("Nome");
		lblNome.setForeground(new Color(255, 255, 255));
		lblNome.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNome.setBounds(34, 154, 46, 14);
		contentPane.add(lblNome);
		
		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setForeground(new Color(255, 255, 255));
		lblTelefone.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTelefone.setBounds(34, 205, 53, 14);
		contentPane.add(lblTelefone);
		
		JLabel lblLogradouro = new JLabel("Logradouro");
		lblLogradouro.setForeground(new Color(255, 255, 255));
		lblLogradouro.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLogradouro.setBounds(182, 256, 72, 14);
		contentPane.add(lblLogradouro);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setForeground(new Color(255, 255, 255));
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCpf.setBounds(354, 205, 33, 14);
		contentPane.add(lblCpf);
		
		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setForeground(new Color(255, 255, 255));
		lblSexo.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSexo.setBounds(549, 154, 46, 14);
		contentPane.add(lblSexo);
		
		JLabel lblCep = new JLabel("CEP");
		lblCep.setForeground(new Color(255, 255, 255));
		lblCep.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCep.setBounds(34, 256, 33, 14);
		contentPane.add(lblCep);
		
		JLabel lblNumeroLog = new JLabel("Nº");
		lblNumeroLog.setForeground(new Color(255, 255, 255));
		lblNumeroLog.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNumeroLog.setBounds(549, 256, 23, 14);
		contentPane.add(lblNumeroLog);
		
		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setForeground(new Color(255, 255, 255));
		lblBairro.setFont(new Font("Arial", Font.PLAIN, 12));
		lblBairro.setBounds(34, 306, 46, 14);
		contentPane.add(lblBairro);
		
		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setForeground(new Color(255, 255, 255));
		lblCidade.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCidade.setBounds(354, 306, 46, 14);
		contentPane.add(lblCidade);
		
		JLabel lblVencimento = new JLabel("Vencimento");
		lblVencimento.setForeground(new Color(255, 255, 255));
		lblVencimento.setFont(new Font("Arial", Font.PLAIN, 12));
		lblVencimento.setBounds(464, 306, 78, 14);
		contentPane.add(lblVencimento);
		
		lblId = new JLabel("ID");
		lblId.setForeground(new Color(255, 255, 255));
		lblId.setFont(new Font("Arial", Font.PLAIN, 12));
		lblId.setBounds(34, 104, 46, 14);
		contentPane.add(lblId);
		
		// Botão modificar campos
		lblBotaoModificarLinha = new JLabel("");
		lblBotaoModificarLinha.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (verificarLinhaSelecionada()) {
					permitirEdicaoDosCampos();
				} else {
					JOptionPane.showMessageDialog(null, "Nenhum aluno foi selecionado!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		lblBotaoModificarLinha.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/procurar.png")));
		lblBotaoModificarLinha.setBounds(193, 31, 53, 50);
		lblBotaoModificarLinha.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblBotaoModificarLinha);
		
		// Botão salvar alterações
		lblBotaoSalvar = new JLabel("");
		lblBotaoSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tableAlunos.getSelectedRow() != -1) {
					alterarDadosAluno();
					bloquearEdicaoDosCampos();
				} else {
					JOptionPane.showMessageDialog(null, "Nenhum aluno foi selecionado!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		lblBotaoSalvar.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/salvar.png")));
		lblBotaoSalvar.setBounds(433, 31, 53, 50);
		lblBotaoSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblBotaoSalvar);
		
		// Botão atualizar tabela
		lblBotaoAtualizar = new JLabel("");
		lblBotaoAtualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<AlunoDto> alunos = AlunoDao.retornarAlunosBD();
				gerarTabela(gerarLinhasTabela(alunos), gerarColunasTabela());
				bloquearEdicaoDosCampos();
			}
		});
		lblBotaoAtualizar.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/atualizar.png")));
		lblBotaoAtualizar.setBounds(273, 31, 53, 50);
		lblBotaoAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblBotaoAtualizar);
		
		JLabel lblBotaoExcluir = new JLabel("");
		lblBotaoExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Excluir aluno;
				int confirmUsuario, linhaSelecionada, valorId;
				if (verificarLinhaSelecionada()) {
					confirmUsuario = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir o aluno?");
					if (confirmUsuario == 0) {
						linhaSelecionada = tableAlunos.getSelectedRow();
						valorId = Integer.parseInt(tableAlunos.getValueAt(linhaSelecionada, 0).toString());
						if (AlunoDao.excluirUmAlunoPorId(valorId)) {
							JOptionPane.showMessageDialog(null, "Aluno excluído com sucesso!");
						} else {
							JOptionPane.showMessageDialog(null, "Não foi possível excluir o aluno!", "Erro", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Nenhum aluno foi selecionado!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		lblBotaoExcluir.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/excluir.png")));
		lblBotaoExcluir.setBounds(353, 31, 53, 50);
		lblBotaoExcluir.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblBotaoExcluir);
		
		// Sequência de comandos que criam os campos com formatação simples
		
		textId = new JTextField();
		textId.setFont(new Font("Arial", Font.PLAIN, 13));
		textId.setBackground(new Color(255, 255, 255));
		textId.setEditable(false);
		textId.setBounds(34, 124, 86, 20);
		contentPane.add(textId);
		textId.setColumns(10);
		
		textNome = new JTextField();
		textNome.setBackground(new Color(255, 255, 255));
		textNome.setEditable(false);
		textNome.setFont(new Font("Arial", Font.PLAIN, 13));
		textNome.setBounds(34, 174, 497, 20);
		contentPane.add(textNome);
		textNome.setColumns(10);
		
		textLogradouro = new JTextField();
		textLogradouro.setBackground(new Color(255, 255, 255));
		textLogradouro.setEditable(false);
		textLogradouro.setFont(new Font("Arial", Font.PLAIN, 13));
		textLogradouro.setColumns(10);
		textLogradouro.setBounds(182, 275, 297, 20);
		contentPane.add(textLogradouro);
		
		textNumeroLog = new JTextField();
		textNumeroLog.setBackground(new Color(255, 255, 255));
		textNumeroLog.setEditable(false);
		textNumeroLog.setFont(new Font("Arial", Font.PLAIN, 13));
		textNumeroLog.setColumns(10);
		textNumeroLog.setBounds(549, 275, 53, 20);
		contentPane.add(textNumeroLog);
		
		textBairro = new JTextField();
		textBairro.setBackground(new Color(255, 255, 255));
		textBairro.setEditable(false);
		textBairro.setFont(new Font("Arial", Font.PLAIN, 13));
		textBairro.setColumns(10);
		textBairro.setBounds(34, 325, 297, 20);
		contentPane.add(textBairro);
		
		textCidade = new JTextField();
		textCidade.setBackground(new Color(255, 255, 255));
		textCidade.setEditable(false);
		textCidade.setFont(new Font("Arial", Font.PLAIN, 13));
		textCidade.setColumns(10);
		textCidade.setBounds(354, 325, 89, 20);
		contentPane.add(textCidade);
		
		// Sequência de comandos que criam os campos com formatação especial
		MaskFormatter mascaraCpf = null;
		MaskFormatter mascaraTelefone = null;
		MaskFormatter mascaraCep = null;
		MaskFormatter mascaraVenc = null;
		
		try {
			mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraTelefone = new MaskFormatter("(##)#####-####");
			mascaraCep = new MaskFormatter("#####-###");
			mascaraVenc = new MaskFormatter("##/##/####");
			
			mascaraCpf.setPlaceholderCharacter('_');
			mascaraTelefone.setPlaceholderCharacter('_');
			mascaraCep.setPlaceholderCharacter('_');
			mascaraVenc.setPlaceholderCharacter('_');
			
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "CadastroAluno: " + e.getMessage());
		}
		
		formattedTextCpf = new JFormattedTextField(mascaraCpf);
		formattedTextCpf.setFont(new Font("Arial", Font.PLAIN, 13));
		formattedTextCpf.setBackground(new Color(255, 255, 255));
		formattedTextCpf.setEditable(false);
		formattedTextCpf.setBounds(354, 225, 248, 20);
		contentPane.add(formattedTextCpf);
		
		formattedTextTelefone = new JFormattedTextField(mascaraTelefone);
		formattedTextTelefone.setFont(new Font("Arial", Font.PLAIN, 13));
		formattedTextTelefone.setBackground(new Color(255, 255, 255));
		formattedTextTelefone.setEditable(false);
		formattedTextTelefone.setBounds(34, 225, 297, 20);
		contentPane.add(formattedTextTelefone);
		
		formattedTextCep = new JFormattedTextField(mascaraCep);
		formattedTextCep.setFont(new Font("Arial", Font.PLAIN, 13));
		formattedTextCep.setBackground(new Color(255, 255, 255));
		formattedTextCep.setEditable(false);
		formattedTextCep.setBounds(34, 275, 89, 20);
		contentPane.add(formattedTextCep);
		
		formattedTextVenc = new JFormattedTextField(mascaraVenc);
		formattedTextVenc.setFont(new Font("Arial", Font.PLAIN, 13));
		formattedTextVenc.setEditable(false);
		formattedTextVenc.setBackground(new Color(255, 255, 255));
		formattedTextVenc.setBounds(464, 325, 138, 20);
		contentPane.add(formattedTextVenc);
		
		// Cria a barra de rolagem para a tabela
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 377, 969, 255);
		contentPane.add(scrollPane);
		
		// Cria a tabela
		tableAlunos = new JTable();
		tableAlunos.getTableHeader().setReorderingAllowed(false);
		tableAlunos.setFont(new Font("Arial", Font.PLAIN, 14));
		tableAlunos.setForeground(new Color(0, 0, 0));
		tableAlunos.setBackground(new Color(255, 255, 255));
		tableAlunos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				preencherCamposDaLinhaSelecionada();
			}
		});
		scrollPane.setViewportView(tableAlunos);
		
		// Cria o menu
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1034, 20);
		contentPane.add(menuBar);
		
		// Cria as opções de menu
		JMenu mnCadastro = new JMenu("Cadastro");
		menuBar.add(mnCadastro);
		
		JMenuItem mntmAluno = new JMenuItem("Aluno");
		mntmAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadastroAluno cadastroAluno = new CadastroAluno();
				cadastroAluno.setVisible(true);
				cadastroAluno.setLocationRelativeTo(null);
			}
		});
		mnCadastro.add(mntmAluno);
		
		JMenuItem mntmTurma = new JMenuItem("Turma");
		mntmTurma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaTurma cadastroTurma = new TelaTurma();
				cadastroTurma.setVisible(true);
				cadastroTurma.setLocationRelativeTo(null);
			}
		});
		mnCadastro.add(mntmTurma);
		
		textNomeCampoPesquisar = new JTextField();
		textNomeCampoPesquisar.setFont(new Font("Arial", Font.PLAIN, 13));
		textNomeCampoPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == e.VK_ENTER) {
					String pesquisa = textNomeCampoPesquisar.getText();
					ArrayList<AlunoDto> alunos = AlunoDao.procurarAlunoBD(pesquisa);
					gerarTabela(gerarLinhasTabela(alunos), gerarColunasTabela());
				}
			}
		});
		textNomeCampoPesquisar.setBounds(674, 275, 328, 20);
		contentPane.add(textNomeCampoPesquisar);
		textNomeCampoPesquisar.setColumns(10);
		
		JLabel lblTurma = new JLabel("Turma");
		lblTurma.setForeground(Color.WHITE);
		lblTurma.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTurma.setBounds(143, 103, 46, 14);
		contentPane.add(lblTurma);
		
		JLabel lblNomeCampoPesquisar = new JLabel("Pesquisa");
		lblNomeCampoPesquisar.setForeground(Color.WHITE);
		lblNomeCampoPesquisar.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNomeCampoPesquisar.setBounds(674, 255, 72, 14);
		contentPane.add(lblNomeCampoPesquisar);
		
		JLabel lblAdicionarAluno = new JLabel("");
		lblAdicionarAluno.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CadastroAluno cadastroAluno = new CadastroAluno();
				cadastroAluno.setVisible(true);
				cadastroAluno.setLocationRelativeTo(null);
			}
		});
		lblAdicionarAluno.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/adicionar-usuario-botao.png")));
		lblAdicionarAluno.setBounds(33, 31, 53, 50);
		lblAdicionarAluno.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblAdicionarAluno);
		
		JLabel lblAdicionarTurma = new JLabel("");
		lblAdicionarTurma.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaTurma cadastroTurma = new TelaTurma();
				cadastroTurma.setVisible(true);
				cadastroTurma.setLocationRelativeTo(null);
			}
		});
		lblAdicionarTurma.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/turma_tela_principal.png")));
		lblAdicionarTurma.setBounds(113, 31, 50, 50);
		lblAdicionarTurma.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblAdicionarTurma);
		
		JLabel lblAlunoTelaPrincipal = new JLabel("");
		lblAlunoTelaPrincipal.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/aluno_tela_principal.png")));
		lblAlunoTelaPrincipal.setBounds(770, 104, 120, 120);
		contentPane.add(lblAlunoTelaPrincipal);
		
		textTurma = new JTextField();
		textTurma.setBackground(new Color(255, 255, 255));
		textTurma.setEditable(false);
		textTurma.setBounds(143, 124, 138, 20);
		contentPane.add(textTurma);
		textTurma.setColumns(10);
		
		textSexo = new JTextField();
		textSexo.setBackground(new Color(255, 255, 255));
		textSexo.setEditable(false);
		textSexo.setBounds(549, 174, 53, 20);
		contentPane.add(textSexo);
		textSexo.setColumns(10);
		
		ArrayList<AlunoDto> alunos = AlunoDao.retornarAlunosBD();
		gerarTabela(gerarLinhasTabela(alunos), gerarColunasTabela());
		
	}
	
	// Verifica se uma linha da tabela foi selecionada
	private boolean verificarLinhaSelecionada() {
		boolean sucesso = false;
		try {
			if (tableAlunos.getSelectedRow() != -1) {
				sucesso = true;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "TelaPrincipal: " + e.getMessage());
		}
		return sucesso;
	}
	
	// Preenche os campos com os dados da linha selecionada
	private void preencherCamposDaLinhaSelecionada() {
		try {
			int linhaSelecionada = tableAlunos.getSelectedRow();
			int valorId = Integer.parseInt(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
			AlunoDto alunoDto = AlunoDao.buscarUmAlunoPorId(valorId);
			textNome.setText(alunoDto.getNome());
			textId.setText(String.valueOf(alunoDto.getIdAluno()));
			textLogradouro.setText(alunoDto.getEndereco().getLogradouro());
			textNumeroLog.setText(String.valueOf(alunoDto.getEndereco().getNumeroLogradouro()));
			textBairro.setText(alunoDto.getEndereco().getBairro());
			textCidade.setText(alunoDto.getEndereco().getCidade());
			formattedTextTelefone.setText(alunoDto.getTelefone());
			formattedTextCpf.setText(alunoDto.getCpf());
			formattedTextCep.setText(alunoDto.getEndereco().getCep());
			formattedTextVenc.setText(converterDateParaString(alunoDto.getVencimento()));
			textSexo.setText(alunoDto.getSexo());
			textTurma.setText(alunoDto.getTurma().getNome());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "TelaPrincipal: " + e.getMessage());
		}
	}
	
	// Cria tabela com os dados dos alunos
	private void gerarTabela(Object[][] linhas, String[] colunas) {
		try {
			modeloTabela = new DefaultTableModel(linhas, colunas) {
				@Override
				public boolean isCellEditable(int linha, int coluna) {
					return false;
				}
			};
			tableAlunos.setModel(modeloTabela);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "TelaPrincipal: " + e.getMessage());
		}
	}
	
	// Cria as colunas da tabela
	private String[] gerarColunasTabela() {
		String[] colunas = null;
		try {
			String colunaUm, colunaDois, colunaTres, colunaQuatro;
			colunaUm = "ID";
			colunaDois = "Nome";
			colunaTres = "Telefone";
			colunaQuatro = "Vencimento";
			colunas = new String[] {colunaUm, colunaDois, colunaTres, colunaQuatro};
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "TelaPrincipal: " + e.getMessage());
		}
		return colunas;
	}
	
	// Cria as linhas da tabela
	private Object[][] gerarLinhasTabela(ArrayList<AlunoDto> listaAlunos) {
		Object[][] dadosAlunos = null;
		try {
			int linhas = listaAlunos.size();
			int colunas = 4;
			dadosAlunos = new Object[linhas][colunas];
			for (int i = 0; i < dadosAlunos.length; i++) {
				for (int i2 = 0; i2 < dadosAlunos[0].length; i2++) {
					if (i2 == 0) {
						dadosAlunos[i][i2] = listaAlunos.get(i).getIdAluno();
					} else if (i2 == 1) {
						dadosAlunos[i][i2] = listaAlunos.get(i).getNome();
					} else if (i2 == 2) {
						dadosAlunos[i][i2] = listaAlunos.get(i).getTelefone();
					} else if (i2 == 3) {
						dadosAlunos[i][i2] = converterDateParaString(listaAlunos.get(i).getVencimento());
					}
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "TelaPrincipal: " + e.getMessage());
		}
		return dadosAlunos;
	}
	
	// Converter uma data do tipo Date para o tipo String
	private String converterDateParaString(Date data) {
		SimpleDateFormat dataFmt = null;
		try {
			dataFmt = new SimpleDateFormat("dd/MM/yyyy");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "TelaPrincipal: " + e.getMessage());
		}
		return dataFmt.format(data);
	}
	
	// Converte um objeto do tipo String em Date
	private Date converterStringParaDate(String dataTxt) {
		Date dataCvtd = null;
		try {
			SimpleDateFormat dataFmt = new SimpleDateFormat("dd/MM/yyyy");
			dataCvtd = dataFmt.parse(dataTxt);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "CadastroAluno: " + e.getMessage());
		}
		return dataCvtd;
	}
	
	// Permite editar os campos de texto
	private void permitirEdicaoDosCampos() {
		try {
			textNome.setEditable(true);
			textLogradouro.setEditable(true);
			textNumeroLog.setEditable(true);
			textBairro.setEditable(true);
			textCidade.setEditable(true);
			formattedTextCpf.setEditable(true);
			formattedTextCep.setEditable(true);
			formattedTextTelefone.setEditable(true);
			formattedTextVenc.setEditable(true);
			textSexo.setEditable(true);
			textTurma.setEditable(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "TelaPrincipal: " + e.getMessage());
		}
	}
	
	// Bloqueia a edição dos campos de texto
	private void bloquearEdicaoDosCampos() {
		try {
			textNome.setEditable(false);
			textLogradouro.setEditable(false);
			textNumeroLog.setEditable(false);
			textBairro.setEditable(false);
			textCidade.setEditable(false);
			formattedTextCpf.setEditable(false);
			formattedTextCep.setEditable(false);
			formattedTextTelefone.setEditable(false);
			formattedTextVenc.setEditable(false);
			textSexo.setEditable(false);
			textTurma.setEditable(false);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "TelaPrincipal: " + e.getMessage());
		}
	}
	
	// Altera os dados do aluno a partir do objeto AlunoDto
	private void alterarDadosAluno() {
		try {
			//Instância da classe AlunoDto
			AlunoDto alunoDto = new AlunoDto();
			
			TurmaDto turmaDto = retornarTurmaEscolhida(textTurma.getText());
			if (turmaDto == null) {
				JOptionPane.showMessageDialog(null, "Turma não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
			} else {
				alunoDto.setTurma(turmaDto);
				
				// Seta os atributos do objeto AlunoDto
				alunoDto.setNome(textNome.getText());
				alunoDto.setCpf(formattedTextCpf.getText());
				alunoDto.setSexo(textSexo.getText());
				alunoDto.setTelefone(formattedTextTelefone.getText());
				alunoDto.setVencimento(converterStringParaDate(formattedTextVenc.getText()));
				alunoDto.setIdAluno(Integer.parseInt(textId.getText()));
				
				// Instância da classe Endereco
				Endereco endAluno = new Endereco();
				
				// Seta os atributos do objeto Endereco
				endAluno.setBairro(textBairro.getText());
				endAluno.setCep(formattedTextCep.getText());
				endAluno.setCidade(textCidade.getText());
				endAluno.setLogradouro(textLogradouro.getText());
				endAluno.setNumeroLogradouro(Integer.parseInt(textNumeroLog.getText()));
				
				// Seta o atributo endereco do objeto AlunoDto
				alunoDto.setEndereco(endAluno);
				
				// Realiza a ação de alterar os dados do aluno;
				if(AlunoDao.alterarAlunoBD(alunoDto)) {
					JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
				} else {
					JOptionPane.showMessageDialog(null, "Alterações não realizadas!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "TelaPrincipal: " + e.getMessage());
		}
		
	}
	
	// Retorna o id da turma escolhida no cadastro do aluno
	private TurmaDto retornarTurmaEscolhida(String nome) {
		TurmaDto turmaDto = null;
		try {
			ArrayList<TurmaDto> turmas = TurmaDao.retornarTurmasBD();
			for (TurmaDto t : turmas) {
				if (t.getNome().equals(nome)) {
					turmaDto = t;
					break;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "CadastroAluno: " + e.getMessage());
		}
		return turmaDto;
	}
	
}
