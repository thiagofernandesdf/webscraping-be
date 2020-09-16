package br.com.consultoria.webscraping.model;

public class Combo {

    private String name;
    private String value;

    public Combo() {
    }

    public Combo(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
