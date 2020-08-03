package br.com.consultoria.webscraping.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the criterios_busca database table.
 * 
 */
@Entity
@Table(name="criterios_busca")
@NamedQuery(name="CriteriosBusca.findAll", query="SELECT c FROM CriteriosBusca c")
public class CriteriosBusca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	//bi-directional many-to-one association to Cidade
	@ManyToOne
	@JoinColumn(name="id_cidade")
	private Cidade cidade;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="id_estado")
	private Estado estado;

	//bi-directional many-to-one association to Site
	@ManyToOne
	@JoinColumn(name="id_site")
	private Site site;

	//bi-directional many-to-one association to TipoImovel
	@ManyToOne
	@JoinColumn(name="id_tipo_imovel")
	private TipoImovel tipoImovel;

	//bi-directional many-to-one association to TipoServico
	@ManyToOne
	@JoinColumn(name="id_tipo_servico")
	private TipoServico tipoServico;

	//bi-directional many-to-one association to Imovel
	@OneToMany(mappedBy="criteriosBusca")
	private List<Imovel> imovels;

	public CriteriosBusca() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cidade getCidade() {
		return this.cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public TipoImovel getTipoImovel() {
		return this.tipoImovel;
	}

	public void setTipoImovel(TipoImovel tipoImovel) {
		this.tipoImovel = tipoImovel;
	}

	public TipoServico getTipoServico() {
		return this.tipoServico;
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	public List<Imovel> getImovels() {
		return this.imovels;
	}

	public void setImovels(List<Imovel> imovels) {
		this.imovels = imovels;
	}

	public Imovel addImovel(Imovel imovel) {
		getImovels().add(imovel);
		imovel.setCriteriosBusca(this);

		return imovel;
	}

	public Imovel removeImovel(Imovel imovel) {
		getImovels().remove(imovel);
		imovel.setCriteriosBusca(null);

		return imovel;
	}

}