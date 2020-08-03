package br.com.consultoria.webscraping.model;

import java.util.List;

public class Output {

    private long size;
    private List<Imovel> imoveis;

    public Output() {
    }

    public Output(long size, List<Imovel> imoveis) {
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
