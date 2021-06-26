package it.uniroma3.siw.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name="produttori")
public class Produttore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Lob
	private String descrizione;
	
	@OneToMany(mappedBy = "produttore")
	private List<Vino> viniProdotti;
	
	@ManyToMany(mappedBy="produttori")
	private List<Regione> regioni;

	public Produttore() {
		this.viniProdotti = new ArrayList<>();
		this.regioni = new ArrayList<>();
	}

	public Produttore(String nome, String descrizione) {
		this.nome = nome;
		this.descrizione = descrizione;
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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Vino> getViniProdotti() {
		return viniProdotti;
	}

	public void setViniProdotti(List<Vino> viniProdotti) {
		this.viniProdotti = viniProdotti;
	}

	public List<Regione> getRegioni() {
		return regioni;
	}

	public void setRegioni(List<Regione> regioni) {
		this.regioni = regioni;
	}
}
