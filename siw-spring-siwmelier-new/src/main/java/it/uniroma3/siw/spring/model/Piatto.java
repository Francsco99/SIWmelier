package it.uniroma3.siw.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "piatti")
public class Piatto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	private String immagine;
	
	@ManyToMany
	private List<Vino> vini;
	
	public Piatto() {
		this.setVini(new ArrayList<>());
	}
	
	public Piatto(String nome) {
		this.nome = nome;
		this.setVini(new ArrayList<>());
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

	public List<Vino> getVini() {
		return vini;
	}

	public void setVini(List<Vino> vini) {
		this.vini = vini;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

}
