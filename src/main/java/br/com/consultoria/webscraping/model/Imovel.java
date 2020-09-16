package br.com.consultoria.webscraping.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the imovel database table.
 * 
 */
@Entity
@NamedQuery(name="Imovel.findAll", query="SELECT i FROM Imovel i")
public class Imovel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="valor_metro")
	private BigDecimal valorMetro;

	private Integer cep;

	private String descricao;

	private Boolean elevador;

	private String endereco;

	private String link;

	private Integer quartos;

	private String site;

	private String telefone;

	private Integer vagas;

	private BigDecimal valor;

	@Column(name="data_criacao")
	private Date dataCriacao;

	@Column(name = "area_util")
	private Integer areaUtil;

	@Column(name = "suites")
	private Integer suites;

	//bi-directional many-to-one association to CriteriosBusca
	@ManyToOne
	@JoinColumn(name="id_criterio_busca", nullable = true)
	private CriteriosBusca criteriosBusca;

	public Imovel() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public BigDecimal getValorMetro() {
		return valorMetro;
	}

	public void setValorMetro(BigDecimal valorMetro) {
		this.valorMetro = valorMetro;
		this.valorMetro.setScale(2);
	}

	public Integer getCep() {
		return this.cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getElevador() {
		return this.elevador;
	}

	public void setElevador(Boolean elevador) {
		this.elevador = elevador;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getQuartos() {
		return this.quartos;
	}

	public void setQuartos(Integer quartos) {
		this.quartos = quartos;
	}

	public String getSite() {
		return this.site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Integer getVagas() {
		return this.vagas;
	}

	public void setVagas(Integer vagas) {
		this.vagas = vagas;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {

		this.valor = valor;
		this.valor.setScale(2);
	}

	public CriteriosBusca getCriteriosBusca() {
		return this.criteriosBusca;
	}

	public void setCriteriosBusca(CriteriosBusca criteriosBusca) {
		this.criteriosBusca = criteriosBusca;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getAreaUtil() {
		return areaUtil;
	}

	public void setAreaUtil(Integer areaUtil) {
		this.areaUtil = areaUtil;
	}

	public Integer getSuites() {
		return suites;
	}

	public void setSuites(Integer suites) {
		this.suites = suites;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Override
	public String toString() {
		return "Imovel{" +
				"id=" + id +
				", valorMetro=" + valorMetro +
				", cep=" + cep +
				", descricao='" + descricao + '\'' +
				", elevador=" + elevador +
				", endereco='" + endereco + '\'' +
				", link='" + link + '\'' +
				", quartos=" + quartos +
				", site='" + site + '\'' +
				", telefone='" + telefone + '\'' +
				", vagas=" + vagas +
				", valor=" + valor +
				", dataCriacao=" + dataCriacao +
				", areaUtil=" + areaUtil +
				", suites=" + suites +
				", criteriosBusca=" + criteriosBusca +
				'}';
	}
}