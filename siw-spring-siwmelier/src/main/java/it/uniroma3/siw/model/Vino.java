package it.uniroma3.siw.model;

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
	
	@Column(nullable=false)
	private String descrizione;
	
	private String annoImbottigliamento;
	
	@Column(nullable=false)
	private String voto;
	
	private String gradazioneAlcolica;
	
	private String prezzo;
	
	@Column(nullable=false)
	private Colore colore;
	
	@Column(nullable=false)
	private Effervescenza effervescenza;
	
	@Column(nullable=false)
	private Corposita corposita;
	
	@ManyToOne
	private Catalogo catalogo;
	
	@ManyToMany(mappedBy="vini")
	private List<Piatto> piatti;
	
	@ManyToOne
	private Produttore produttore;

	
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

	public String getAnnoImbottigliamento() {
		return annoImbottigliamento;
	}

	public void setAnnoImbottigliamento(String annoImbottigliamento) {
		this.annoImbottigliamento = annoImbottigliamento;
	}

	public String getVoto() {
		return voto;
	}

	public void setVoto(String voto) {
		this.voto = voto;
	}

	public String getGradazioneAlcolica() {
		return gradazioneAlcolica;
	}

	public void setGradazioneAlcolica(String gradazioneAlcolica) {
		this.gradazioneAlcolica = gradazioneAlcolica;
	}

	public String getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(String prezzo) {
		this.prezzo = prezzo;
	}

	public Colore getColore() {
		return colore;
	}

	public void setColore(Colore colore) {
		this.colore = colore;
	}

	public Effervescenza getEffervescenza() {
		return effervescenza;
	}

	public void setEffervescenza(Effervescenza effervescenza) {
		this.effervescenza = effervescenza;
	}

	public Corposita getCorposita() {
		return corposita;
	}

	public void setCorposita(Corposita corposita) {
		this.corposita = corposita;
	}

}
