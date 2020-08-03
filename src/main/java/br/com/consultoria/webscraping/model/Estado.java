package br.com.consultoria.webscraping.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the estado database table.
 * 
 */
@Entity
@NamedQuery(name="Estado.findAll", query="SELECT e FROM Estado e")
public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private Integer ddd;

	private Integer ibge;

	private String nome;

	private Integer pais;

	private String uf;

	//bi-directional many-to-one association to CriteriosBusca
	@OneToMany(mappedBy="estado")
	private List<CriteriosBusca> criteriosBuscas;

	public Estado() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDdd() {
		return this.ddd;
	}

	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}

	public Integer getIbge() {
		return this.ibge;
	}

	public void setIbge(Integer ibge) {
		this.ibge = ibge;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getPais() {
		return this.pais;
	}

	public void setPais(Integer pais) {
		this.pais = pais;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public List<CriteriosBusca> getCriteriosBuscas() {
		return this.criteriosBuscas;
	}

	public void setCriteriosBuscas(List<CriteriosBusca> criteriosBuscas) {
		this.criteriosBuscas = criteriosBuscas;
	}

	public CriteriosBusca addCriteriosBusca(CriteriosBusca criteriosBusca) {
		getCriteriosBuscas().add(criteriosBusca);
		criteriosBusca.setEstado(this);

		return criteriosBusca;
	}

	public CriteriosBusca removeCriteriosBusca(CriteriosBusca criteriosBusca) {
		getCriteriosBuscas().remove(criteriosBusca);
		criteriosBusca.setEstado(null);

		return criteriosBusca;
	}

}