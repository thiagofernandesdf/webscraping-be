package br.com.consultoria.webscraping.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the site database table.
 * 
 */
@Entity
@NamedQuery(name="Site.findAll", query="SELECT s FROM Site s")
public class Site implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String url;

	//bi-directional many-to-one association to CriteriosBusca
	@OneToMany(mappedBy="site")
	private List<CriteriosBusca> criteriosBuscas;

	public Site() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<CriteriosBusca> getCriteriosBuscas() {
		return this.criteriosBuscas;
	}

	public void setCriteriosBuscas(List<CriteriosBusca> criteriosBuscas) {
		this.criteriosBuscas = criteriosBuscas;
	}

	public CriteriosBusca addCriteriosBusca(CriteriosBusca criteriosBusca) {
		getCriteriosBuscas().add(criteriosBusca);
		criteriosBusca.setSite(this);

		return criteriosBusca;
	}

	public CriteriosBusca removeCriteriosBusca(CriteriosBusca criteriosBusca) {
		getCriteriosBuscas().remove(criteriosBusca);
		criteriosBusca.setSite(null);

		return criteriosBusca;
	}

}