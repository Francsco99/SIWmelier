package it.uniroma3.siw.spring.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "regioni")
public class Regione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@ManyToMany
	private List<Regione> produttori;

	public Regione() {
		
	}
	
	public Regione(String nome) {
		this.nome=nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
