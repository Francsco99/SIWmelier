package it.uniroma3.siw.spring.model;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "vini")

public class Vino {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	//@Column(nullable=false)
	private String descrizione;
	
	private Float annoImbottigliamento;
	
	//@Column(nullable=false)
	private Float voto;
	
	private Float gradazione;
	
	private Float prezzo;
	
	//@Column(nullable=false)
	private String colore;
	
	//@Column(nullable=false)
	private Float effervescenza;
	
	//@Column(nullable=false)
	private Float corposita;
	
	private String immagine;
	
	@ManyToOne
	private Catalogo catalogo;
	
	@ManyToMany(mappedBy="vini")
	private List<Piatto> piatti;
	
	@ManyToOne
	private Produttore produttore;

	public Vino(String nome,String immagine,Float voto) {
		this.nome = nome;
		this.immagine = immagine;
		this.voto=voto;
	}
	
	public Vino() {
		this.piatti = new ArrayList<>();	
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

	public Float getAnnoImbottigliamento() {
		return annoImbottigliamento;
	}

	public void setAnnoImbottigliamento(Float annoImbottigliamento) {
		this.annoImbottigliamento = annoImbottigliamento;
	}

	public Float getVoto() {
		return voto;
	}

	public void setVoto(Float voto) {
		this.voto = voto;
	}

	public Float getGradazioneAlcolica() {
		return gradazione;
	}

	public void setGradazioneAlcolica(Float gradazioneAlcolica) {
		this.gradazione = gradazioneAlcolica;
	}

	public Float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Float prezzo) {
		this.prezzo = prezzo;
	}

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public Float getEffervescenza() {
		return effervescenza;
	}

	public void setEffervescenza(Float effervescenza) {
		this.effervescenza = effervescenza;
	}

	public Float getCorposita() {
		return corposita;
	}

	public void setCorposita(Float corposita) {
		this.corposita = corposita;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

}
