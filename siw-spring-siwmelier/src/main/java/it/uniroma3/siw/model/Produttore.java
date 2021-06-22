package it.uniroma3.siw.model;

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
	
	@OneToMany(mappedBy="produttore")
	private List<Vino> viniProdotti;
	
	@ManyToMany(mappedBy="produttori")
	private List<Regione> regioni;

	public Produttore() {
		this.viniProdotti = new ArrayList<>();
		this.regioni = new ArrayList<>();
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
}
