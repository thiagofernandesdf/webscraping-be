package br.com.consultoria.webscraping.business;

import br.com.consultoria.webscraping.model.Combo;
import br.com.consultoria.webscraping.model.DFIMoveisFilter;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ComboBusiness {

    private WebClient webClient;
    private HtmlSelect selectNegocio;
    private HtmlSelect selectTipo;
    private HtmlSelect selectEstado;
    private HtmlSelect selectCidade;
    private HtmlSelect selectBairro;
    private HtmlSelect selectQuartos;
    private HtmlPage page;
    private List<Combo> comboNegocios = new ArrayList<>();
    private List<Combo> comboTipos = new ArrayList<>();
    private List<Combo> comboEstados = new ArrayList<>();
    private List<Combo> comboCidades = new ArrayList<>();
    private List<Combo> comboBairros = new ArrayList<>();
    private List<Combo> comboQuartos = new ArrayList<>();
    private List<Combo> combos = new ArrayList<>();

    public ComboBusiness() throws IOException {

        webClient = new WebClient(BrowserVersion.CHROME);
        WebRequest requestSettings = new WebRequest(
                new URL("https://www.dfimoveis.com.br/"), HttpMethod.GET);

        this.webClient.getOptions().setThrowExceptionOnScriptError(false);
        this.webClient.getOptions().setCssEnabled(true);
        this.webClient.getCookieManager().setCookiesEnabled(true);
        this.webClient.getOptions().setRedirectEnabled(true);
        this.webClient.getOptions().setJavaScriptEnabled(true);
        this.webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        this.page = webClient.getPage(requestSettings);

        this.selectNegocio = page.getHtmlElementById("negocios");
        this.selectTipo = page.getHtmlElementById("tipos");
        this.selectEstado = page.getHtmlElementById("estados");
        this.selectCidade = page.getHtmlElementById("cidades");
        this.selectBairro = page.getHtmlElementById("bairros");
        this.selectQuartos = page.getHtmlElementById("quartos");

        this.selectNegocio.getOptions().forEach(sn ->
                comboNegocios.add(new Combo(sn.getVisibleText(), sn.getText()))
        );
    }

    public void changeCombo(DFIMoveisFilter filter) throws IOException {

        combos = new ArrayList<>();

        if(filter.getNegocio() != null) {
            HtmlOption optionNegocio = selectNegocio.getOptionByValue(filter.getNegocio().getValue());
            this.selectNegocio.setSelectedAttribute(optionNegocio, true);
            this.page = selectNegocio.click();
            //webClient.waitForBackgroundJavaScript(1000 * 3);

            this.selectTipo = (HtmlSelect) page.getElementById("tipos");

            this.selectTipo.getOptions().forEach(t ->
                    //comboTipos.add(new Combo(sn.getVisibleText(), sn.getText()))
                    combos.add(new Combo(t.getVisibleText(), t.getValueAttribute()))
            );

        } else if(filter.getTipo() != null) {
            HtmlOption optionTipo = selectTipo.getOptionByValue(filter.getTipo().getValue());
            selectTipo.setSelectedAttribute(optionTipo, true);
            this.page = selectTipo.click();
            //webClient.waitForBackgroundJavaScript(1000 * 3);

            this.selectEstado = (HtmlSelect) page.getElementById("estados");

            this.selectEstado.getOptions().forEach(e ->
                    //comboEstados.add(new Combo(sn.getVisibleText(), sn.getText()))
                    combos.add(new Combo(e.getVisibleText(), e.getValueAttribute()))
            );

        } else if(filter.getEstado() != null) {
            HtmlOption optionEstado = selectEstado.getOptionByValue(filter.getEstado().getValue());
            selectEstado.setSelectedAttribute(optionEstado, true);
            this.page = selectEstado.click();
           // webClient.waitForBackgroundJavaScript(1000 * 3);

            this.selectCidade = (HtmlSelect) page.getElementById("cidades");

            this.selectCidade.getOptions().forEach(c ->
                    //comboCidades.add(new Combo(sn.getVisibleText(), sn.getText()))
                    combos.add(new Combo(c.getVisibleText(), c.getValueAttribute()))
            );
        } else if(filter.getCidade() != null) {
            HtmlOption optionCidade = selectCidade.getOptionByValue(filter.getCidade().getValue());
            selectCidade.setSelectedAttribute(optionCidade, true);
            this.page = selectCidade.click();
            webClient.waitForBackgroundJavaScript(1000 * 3);
            this.selectBairro = (HtmlSelect) page.getElementById("bairros");

            this.selectBairro.getOptions().forEach(b ->
                    //comboBairros.add(new Combo(sn.getVisibleText(), sn.getText()))
                    combos.add(new Combo(b.getVisibleText(), b.getValueAttribute()))
            );
            webClient.close();
        } else if(filter.getBairro() != null) {
            HtmlOption optionBairro = selectBairro.getOptionByValue(filter.getBairro().getValue());
            selectBairro.setSelectedAttribute(optionBairro, true);
            this.page = selectBairro.click();
          //  webClient.waitForBackgroundJavaScript(1000 * 3);
            this.selectQuartos = (HtmlSelect) page.getElementById("quartos");

            this.selectQuartos.getOptions().forEach(q ->
                    //comboQuartos.add(new Combo(sn.getVisibleText(), sn.getText()))
                    combos.add(new Combo(q.getVisibleText(), q.getValueAttribute()))

            );
            webClient.close();
        }
    }

    public List<Combo> getComboNegocios() {
        return comboNegocios;
    }

    public List<Combo> getComboTipos() {
        return comboTipos;
    }

    public List<Combo> getComboEstados() {
        return comboEstados;
    }

    public List<Combo> getComboCidades() {
        return comboCidades;
    }

    public List<Combo> getComboBairros() {
        return comboBairros;
    }

    public List<Combo> getComboQuartos() {
        return comboQuartos;
    }

    public List<Combo> getCombos() {
        return combos;
    }
}
