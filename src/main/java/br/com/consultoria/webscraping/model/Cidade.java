package br.com.consultoria.webscraping.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cidade database table.
 * 
 */
@Entity
@NamedQuery(name="Cidade.findAll", query="SELECT c FROM Cidade c")
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private Integer ibge;

	@Column(name="lat_lon")
	private Integer latLon;

	private String nome;

	private Integer uf;

	//bi-directional many-to-one association to CriteriosBusca
	@OneToMany(mappedBy="cidade")
	private List<CriteriosBusca> criteriosBuscas;

	public Cidade() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIbge() {
		return this.ibge;
	}

	public void setIbge(Integer ibge) {
		this.ibge = ibge;
	}

	public Integer getLatLon() {
		return this.latLon;
	}

	public void setLatLon(Integer latLon) {
		this.latLon = latLon;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getUf() {
		return this.uf;
	}

	public void setUf(Integer uf) {
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
		criteriosBusca.setCidade(this);

		return criteriosBusca;
	}

	public CriteriosBusca removeCriteriosBusca(CriteriosBusca criteriosBusca) {
		getCriteriosBuscas().remove(criteriosBusca);
		criteriosBusca.setCidade(null);

		return criteriosBusca;
	}

}