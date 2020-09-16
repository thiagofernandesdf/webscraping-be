package br.com.consultoria.webscraping.output;

import br.com.consultoria.webscraping.Error;

import java.util.List;

public class OutputFilter extends Output {

    public OutputFilter(Error error, int quantidadeImoveis) {
        super(error);
        this.quantidadeImoveis = quantidadeImoveis;
    }

    public OutputFilter(List<Error> erros, int quantidadeImoveis) {
        setErrors(erros);
        this.quantidadeImoveis = quantidadeImoveis;
    }

    public OutputFilter(Error error) {
        super(error);
    }

    public OutputFilter(int quantidadeImoveis) {
        this.quantidadeImoveis = quantidadeImoveis;
    }

    private int quantidadeImoveis;

    public int getQuantidadeImoveis() {
        return quantidadeImoveis;
    }

    public void setQuantidadeImoveis(int quantidadeImoveis) {
        this.quantidadeImoveis = quantidadeImoveis;
    }
}
