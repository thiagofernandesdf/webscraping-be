package br.com.consultoria.webscraping.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_imovel database table.
 * 
 */
@Entity
@Table(name="tipo_imovel")
@NamedQuery(name="TipoImovel.findAll", query="SELECT t FROM TipoImovel t")
public class TipoImovel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String descricao;

	//bi-directional many-to-one association to CriteriosBusca
	@OneToMany(mappedBy="tipoImovel")
	private List<CriteriosBusca> criteriosBuscas;

	public TipoImovel() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<CriteriosBusca> getCriteriosBuscas() {
		return this.criteriosBuscas;
	}

	public void setCriteriosBuscas(List<CriteriosBusca> criteriosBuscas) {
		this.criteriosBuscas = criteriosBuscas;
	}

	public CriteriosBusca addCriteriosBusca(CriteriosBusca criteriosBusca) {
		getCriteriosBuscas().add(criteriosBusca);
		criteriosBusca.setTipoImovel(this);

		return criteriosBusca;
	}

	public CriteriosBusca removeCriteriosBusca(CriteriosBusca criteriosBusca) {
		getCriteriosBuscas().remove(criteriosBusca);
		criteriosBusca.setTipoImovel(null);

		return criteriosBusca;
	}

}