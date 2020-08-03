package br.com.consultoria.webscraping.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pais database table.
 * 
 */
@Entity
@Table(name="pais")
@NamedQuery(name="Pai.findAll", query="SELECT p FROM Pai p")
public class Pai implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private Integer bacen;

	private String nome;

	@Column(name="nome_pt")
	private String nomePt;

	private String sigla;

	public Pai() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getBacen() {
		return this.bacen;
	}

	public void setBacen(Integer bacen) {
		this.bacen = bacen;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomePt() {
		return this.nomePt;
	}

	public void setNomePt(String nomePt) {
		this.nomePt = nomePt;
	}

	public String getSigla() {
		return this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

}