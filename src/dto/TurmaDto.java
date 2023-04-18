package dto;

import java.util.ArrayList;

public class TurmaDto {
	
	private int idTurma;
	private String nome;
	private double valor;
	private ArrayList<AlunoDto> alunos;
	
	public int getIdTurma() {
		return idTurma;
	}
	public void setIdTurma(int idTurma) {
		this.idTurma = idTurma;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public ArrayList<AlunoDto> getAlunos() {
		return alunos;
	}
	public void setAlunos(ArrayList<AlunoDto> alunos) {
		this.alunos = alunos;
	}
	
}
