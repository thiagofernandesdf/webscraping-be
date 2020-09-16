package br.com.consultoria.webscraping.output;

import br.com.consultoria.webscraping.Error;

import java.util.ArrayList;
import java.util.List;

public class Output {

    public Output() {}

    public Output(Error error) {
        this.getErrors().add(error);
    }

    private List<Error> errors = new ArrayList<>();

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
