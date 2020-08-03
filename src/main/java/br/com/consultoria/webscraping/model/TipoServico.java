package br.com.consultoria.webscraping.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_servico database table.
 * 
 */
@Entity
@Table(name="tipo_servico")
@NamedQuery(name="TipoServico.findAll", query="SELECT t FROM TipoServico t")
public class TipoServico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String tipo;

	//bi-directional many-to-one association to CriteriosBusca
	@OneToMany(mappedBy="tipoServico")
	private List<CriteriosBusca> criteriosBuscas;

	public TipoServico() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<CriteriosBusca> getCriteriosBuscas() {
		return this.criteriosBuscas;
	}

	public void setCriteriosBuscas(List<CriteriosBusca> criteriosBuscas) {
		this.criteriosBuscas = criteriosBuscas;
	}

	public CriteriosBusca addCriteriosBusca(CriteriosBusca criteriosBusca) {
		getCriteriosBuscas().add(criteriosBusca);
		criteriosBusca.setTipoServico(this);

		return criteriosBusca;
	}

	public CriteriosBusca removeCriteriosBusca(CriteriosBusca criteriosBusca) {
		getCriteriosBuscas().remove(criteriosBusca);
		criteriosBusca.setTipoServico(null);

		return criteriosBusca;
	}

}