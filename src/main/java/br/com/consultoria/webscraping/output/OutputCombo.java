package br.com.consultoria.webscraping.output;

import br.com.consultoria.webscraping.Error;
import br.com.consultoria.webscraping.model.Combo;

import java.util.ArrayList;
import java.util.List;

public class OutputCombo extends Output {

    public OutputCombo() {}

    public OutputCombo(Error error) {
        this.getErrors().add(error);
    }

    public OutputCombo(List<Combo> combos) {
        this.combos = combos;
    }

    private List<Combo> combos = new ArrayList<>();

    public List<Combo> getCombos() {
        return combos;
    }

    public void setCombos(List<Combo> combos) {
        this.combos = combos;
    }
}
