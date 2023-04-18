package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import dao.AlunoDao;
import dao.ConexaoMySQL;
import dao.TurmaDao;
import dto.AlunoDto;
import dto.Endereco;
import dto.TurmaDto;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import java.awt.Canvas;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;

public class CadastroAluno extends JFrame {

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
	private JComboBox<String> seletorSexo;
	private JComboBox<String> seletorTurma;
	private SimpleDateFormat dataFmt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroAluno frame = new CadastroAluno();
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
	public CadastroAluno() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 690, 672);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(193, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Criam as JLabels
		JLabel lblNome = new JLabel("Nome");
		lblNome.setForeground(new Color(255, 255, 255));
		lblNome.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNome.setBounds(54, 251, 46, 14);
		contentPane.add(lblNome);
		
		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setForeground(new Color(255, 255, 255));
		lblTelefone.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTelefone.setBounds(54, 302, 53, 14);
		contentPane.add(lblTelefone);
		
		JLabel lblLogradouro = new JLabel("Logradouro");
		lblLogradouro.setForeground(new Color(255, 255, 255));
		lblLogradouro.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLogradouro.setBounds(212, 353, 72, 14);
		contentPane.add(lblLogradouro);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setForeground(new Color(255, 255, 255));
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCpf.setBounds(374, 302, 33, 14);
		contentPane.add(lblCpf);
		
		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setForeground(new Color(255, 255, 255));
		lblSexo.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSexo.setBounds(569, 251, 46, 14);
		contentPane.add(lblSexo);
		
		JLabel lblCep = new JLabel("CEP");
		lblCep.setForeground(new Color(255, 255, 255));
		lblCep.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCep.setBounds(54, 353, 33, 14);
		contentPane.add(lblCep);
		
		JLabel lblNumeroLog = new JLabel("Nº");
		lblNumeroLog.setForeground(new Color(255, 255, 255));
		lblNumeroLog.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNumeroLog.setBounds(569, 353, 23, 14);
		contentPane.add(lblNumeroLog);
		
		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setForeground(new Color(255, 255, 255));
		lblBairro.setFont(new Font("Arial", Font.PLAIN, 12));
		lblBairro.setBounds(54, 403, 46, 14);
		contentPane.add(lblBairro);
		
		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setForeground(new Color(255, 255, 255));
		lblCidade.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCidade.setBounds(374, 403, 46, 14);
		contentPane.add(lblCidade);
		
		JLabel lblVencimento = new JLabel("Vencimento");
		lblVencimento.setForeground(new Color(255, 255, 255));
		lblVencimento.setFont(new Font("Arial", Font.PLAIN, 12));
		lblVencimento.setBounds(484, 403, 78, 14);
		contentPane.add(lblVencimento);
		
		JLabel lblSalvar = new JLabel("");
		lblSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			// Estabelece a conexão com o banco de dados
			Connection conexao = ConexaoMySQL.conectarBD();
			boolean existenciaDeTurma = TurmaDao.verificarExistenciaDeTurmaBD();
			if (conexao != null) {
				if (existenciaDeTurma) {
					try {
						// Armazena os dados passados pelo usuário nas variáveis locais
						String nome = textNome.getText();
						String telefone = formattedTextTelefone.getText();
						String cpf = formattedTextCpf.getText();
						String cep = formattedTextCep.getText();
						String logradouro = textLogradouro.getText();
						String bairro = textBairro.getText();
						String cidade = textCidade.getText();
						String sexo = selecionarSexo(seletorSexo);
						TurmaDto turmaDto = retornarTurmaEscolhida(seletorTurma);
						int numeroLogradouro = Integer.parseInt(textNumeroLog.getText());
						Date venc = converterStringParaDate(formattedTextVenc.getText());
						
						// Instância da classe AlunoDto
						AlunoDto alunoDto = new AlunoDto();
						
						// Seta os dados no objeto AlunoDto
						alunoDto.setNome(nome);
						alunoDto.setTelefone(telefone);
						alunoDto.setCpf(cpf);
						alunoDto.setSexo(sexo);
						alunoDto.setVencimento(venc);
						
						// Instância da classe Endereco
						Endereco endAluno = new Endereco();
						
						// Seta as informações de endereço
						endAluno.setLogradouro(logradouro);
						endAluno.setBairro(bairro);
						endAluno.setCep(cep);
						endAluno.setCidade(cidade);
						endAluno.setNumeroLogradouro(numeroLogradouro);
						
						// Seta em alunoDto um objeto do tipo Endereco
						alunoDto.setEndereco(endAluno);
						// Seta a turma em alunoDto
						alunoDto.setTurma(turmaDto);
						
						// Transfere os dados da DTO para a DAO
						// Chama o método para cadastrar os dados do aluno
						if(AlunoDao.cadastrarAlunoBD(alunoDto)) {
							JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso!");
						} else {
							JOptionPane.showMessageDialog(null, "Erro ao cadastrar o aluno!", "Erro", JOptionPane.ERROR_MESSAGE);
						}
						
						// Limpa os campos de texto
						limparCampos();
						
						// Retorna a data atual
						formattedTextVenc.setText(retornarDataAtual());
						
						textNome.requestFocus();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Alguns dos campos foram preenchidos incorretamente", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Não é possível cadastrar o aluno, pois ainda não existe uma turma!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
		}}}
		);
		lblSalvar.setIcon(new ImageIcon(CadastroAluno.class.getResource("/imagens/salvar.png")));
		lblSalvar.setBounds(569, 551, 53, 50);
		lblSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(lblSalvar);
		
		// Sequência de comandos que criam os campos com formatação simples
		textNome = new JTextField();
		textNome.setFont(new Font("Arial", Font.PLAIN, 12));
		textNome.setBounds(54, 271, 497, 20);
		contentPane.add(textNome);
		textNome.setColumns(10);
		
		textLogradouro = new JTextField();
		textLogradouro.setFont(new Font("Arial", Font.PLAIN, 12));
		textLogradouro.setColumns(10);
		textLogradouro.setBounds(212, 372, 297, 20);
		contentPane.add(textLogradouro);
		
		textNumeroLog = new JTextField();
		textNumeroLog.setFont(new Font("Arial", Font.PLAIN, 12));
		textNumeroLog.setColumns(10);
		textNumeroLog.setBounds(569, 372, 53, 20);
		contentPane.add(textNumeroLog);
		
		textBairro = new JTextField();
		textBairro.setFont(new Font("Arial", Font.PLAIN, 12));
		textBairro.setColumns(10);
		textBairro.setBounds(54, 422, 297, 20);
		contentPane.add(textBairro);
		
		textCidade = new JTextField();
		textCidade.setFont(new Font("Arial", Font.PLAIN, 12));
		textCidade.setColumns(10);
		textCidade.setBounds(374, 422, 89, 20);
		contentPane.add(textCidade);
		
		// Cria o seletor de sexo
		seletorSexo = new JComboBox<>();
		seletorSexo.setBackground(new Color(255, 255, 255));
		seletorSexo.setSize(53, 20);
		seletorSexo.setLocation(569, 271);
		getContentPane().add(seletorSexo);
		seletorSexo.addItem("M");
		seletorSexo.addItem("F");
		
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
		formattedTextCpf.setBounds(374, 322, 248, 20);
		contentPane.add(formattedTextCpf);
		
		formattedTextTelefone = new JFormattedTextField(mascaraTelefone);
		formattedTextTelefone.setBounds(54, 322, 297, 20);
		contentPane.add(formattedTextTelefone);
		
		formattedTextCep = new JFormattedTextField(mascaraCep);
		formattedTextCep.setBounds(54, 372, 89, 20);
		contentPane.add(formattedTextCep);
		
		formattedTextVenc = new JFormattedTextField(mascaraVenc);
		formattedTextVenc.setBounds(484, 422, 138, 20);
		contentPane.add(formattedTextVenc);
		formattedTextVenc.setText(retornarDataAtual());
		
		JLabel lblAdicionarUsuario = new JLabel("");
		lblAdicionarUsuario.setIcon(new ImageIcon(CadastroAluno.class.getResource("/imagens/adicionar-usuario.png")));
		lblAdicionarUsuario.setBounds(276, 41, 140, 140);
		contentPane.add(lblAdicionarUsuario);
		
		seletorTurma = new JComboBox<String>();
		seletorTurma.setModel(new DefaultComboBoxModel(preencherSeletorDeTurmas()));
		seletorTurma.setFont(new Font("Arial", Font.PLAIN, 12));
		seletorTurma.setEditable(false);
		seletorTurma.setBackground(Color.WHITE);
		seletorTurma.setBounds(54, 472, 138, 22);
		contentPane.add(seletorTurma);
		
		JLabel lblTurma = new JLabel("Turma");
		lblTurma.setForeground(Color.WHITE);
		lblTurma.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTurma.setBounds(54, 453, 46, 14);
		contentPane.add(lblTurma);
	}
	
	// Limpa os campos de texto preenchidos
	private void limparCampos() {
		textNome.setText("");
		formattedTextTelefone.setText("");
		formattedTextCpf.setText("");
		formattedTextCep.setText("");
		textLogradouro.setText("");
		textBairro.setText("");
		textCidade.setText("");
		seletorSexo.setSelectedIndex(0);
		textNumeroLog.setText("");
	}
	
	// Retorna a data atual (conforme informado pelo sistema)
	private String retornarDataAtual() {
		String data = "";
		try {
			Calendar c = Calendar.getInstance();
			Date d = c.getTime();
			dataFmt = new SimpleDateFormat("dd/MM/yyyy");
			data = dataFmt.format(d);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "CadastroAluno: " + e.getMessage());
		}
		return data;
	}
	
	// Retorna a String M ou F de acordo com o que for selecionado pelo usuário
	private String selecionarSexo(JComboBox seletorSexo) {
		String sexo = "";
		if (seletorSexo.getSelectedItem().equals("M")) {
			sexo = "M";
		} else if (seletorSexo.getSelectedItem().equals("F")) {
			sexo = "F";
		}
		return sexo;
	}
	
	// Converte um objeto do tipo String em Date
	private Date converterStringParaDate(String dataTxt) {
		Date dataCvtd = null;
		try {
			dataFmt = new SimpleDateFormat("dd/MM/yyyy");
			dataCvtd = dataFmt.parse(dataTxt);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "CadastroAluno: " + e.getMessage());
		}
		return dataCvtd;
	}
	
	// Preenche o seletor com as turmas disponíveis
	private String[] preencherSeletorDeTurmas() {
		ArrayList<TurmaDto> objetoTurmas = TurmaDao.retornarTurmasBD();
		int qtdObjetoTurmas = objetoTurmas.size();
		String[] strTurmas = new String[qtdObjetoTurmas];
		try {
			for (int i = 0; i < strTurmas.length; i++) {
				strTurmas[i] = objetoTurmas.get(i).getNome();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "CadastroAluno: " + e.getMessage());
		}
		return strTurmas;
	}
	
	// Retorna o id da turma escolhida no cadastro do aluno
	private TurmaDto retornarTurmaEscolhida(JComboBox seletor) {
		TurmaDto turmaDto = null;
		try {
			String turma = seletor.getSelectedItem().toString();
			ArrayList<TurmaDto> turmas = TurmaDao.retornarTurmasBD();
			for (TurmaDto t : turmas) {
				if (t.getNome().equals(turma)) {
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
