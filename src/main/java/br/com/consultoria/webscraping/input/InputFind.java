package br.com.consultoria.webscraping.input;

import br.com.consultoria.webscraping.model.Imovel;

public class InputFind {

    private Imovel imovel;
    private Integer firstResult;
    private Integer pageSize;

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Integer getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
