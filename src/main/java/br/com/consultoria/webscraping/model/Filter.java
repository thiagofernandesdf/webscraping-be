package br.com.consultoria.webscraping.model;

public class Filter {

    private String negocio;
    private String tipo;
    private String estado;
    private String cidade;
    private String bairro;
    private String quartos;

    public Filter() {
    }

    public Filter(String negocio, String tipo, String estado, String cidade, String bairro, String quartos) {
        this.negocio = negocio;
        this.tipo = tipo;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.quartos = quartos;
    }

    public String getNegocio() {
        return negocio;
    }

    public void setNegocio(String negocio) {
        this.negocio = negocio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getQuartos() {
        return quartos;
    }

    public void setQuartos(String quartos) {
        this.quartos = quartos;
    }
}
