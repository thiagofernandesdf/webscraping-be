package br.com.consultoria.webscraping.output;

import br.com.consultoria.webscraping.model.Imovel;

import java.util.List;

public class OutputFind extends Output {

    private long size;
    private List<Imovel> imoveis;

    public OutputFind() {
    }

    public OutputFind(long size, List<Imovel> imoveis) {
        this.size = size;
        this.imoveis = imoveis;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public List<Imovel> getImoveis() {
        return imoveis;
    }

    public void setImoveis(List<Imovel> imoveis) {
        this.imoveis = imoveis;
    }
}
