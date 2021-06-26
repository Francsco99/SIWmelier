package it.uniroma3.siw.spring.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "cataloghi")
public class Catalogo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Lob
	private String descrizione;
	
	@OneToMany(mappedBy = "catalogo")
	private List<Vino> vini;
	
	public Catalogo() {
		
	}
	
	public Catalogo(String nome, String descrizione) {
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

	public List<Vino> getVini() {
		return vini;
	}

	public void setVini(List<Vino> vini) {
		this.vini = vini;
	}
	
}
