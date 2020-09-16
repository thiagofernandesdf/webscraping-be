package br.com.consultoria.webscraping;

public class Error extends Exception {

    private int cod;

    private String message;

    public Error() {}

    @Override
    public String getMessage() {
        this.message = super.getMessage();
        return this.message;
    }

    public Error(Exception e, int cod) {
        super(e);
        this.cod = cod;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

}
