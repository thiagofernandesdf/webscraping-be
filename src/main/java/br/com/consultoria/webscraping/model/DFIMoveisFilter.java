package br.com.consultoria.webscraping.model;

public class DFIMoveisFilter {

    private Combo negocio;
    private Combo tipo;
    private Combo estado;
    private Combo cidade;
    private Combo bairro;
    private Combo quartos;

    public Combo getNegocio() {
        return negocio;
    }

    public void setNegocio(Combo negocio) {
        this.negocio = negocio;
    }

    public Combo getTipo() {
        return tipo;
    }

    public void setTipo(Combo tipo) {
        this.tipo = tipo;
    }

    public Combo getEstado() {
        return estado;
    }

    public void setEstado(Combo estado) {
        this.estado = estado;
    }

    public Combo getCidade() {
        return cidade;
    }

    public void setCidade(Combo cidade) {
        this.cidade = cidade;
    }

    public Combo getBairro() {
        return bairro;
    }

    public void setBairro(Combo bairro) {
        this.bairro = bairro;
    }

    public Combo getQuartos() {
        return quartos;
    }

    public void setQuartos(Combo quartos) {
        this.quartos = quartos;
    }
}
