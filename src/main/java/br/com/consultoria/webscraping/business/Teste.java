package br.com.consultoria.webscraping.business;

import br.com.consultoria.webscraping.Error;
import br.com.consultoria.webscraping.model.Filter;
import br.com.consultoria.webscraping.model.Imovel;
import br.com.consultoria.webscraping.repositoty.ImovelRepository;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Teste {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ImovelRepository repository;

    private List<Imovel> imoveis;
    private List<Error> erros;

    private List<String> linkPaginas;


    public void findPages(Filter filter, String link) throws IOException {

        try(final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {

            WebRequest requestSettings = new WebRequest(
                    new URL(link), HttpMethod.GET);

            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setCssEnabled(true);
            webClient.getCookieManager().setCookiesEnabled(true);
            webClient.getOptions().setRedirectEnabled(true);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());

            final HtmlPage page1 = webClient.getPage(requestSettings);

            HtmlButton dom = page1.getHtmlElementById("botaoDeBusca");

            HtmlSelect select = page1.getHtmlElementById("negocios");
            HtmlOption optionNegocios = select.getOptionByValue(filter.getNegocio());
            select.setSelectedAttribute(optionNegocios, true);

            if (!filter.getTipo().equals("")) {
                HtmlSelect selectTipo = page1.getHtmlElementById("tipos");
                HtmlOption optionTipo = selectTipo.getOptionByValue(filter.getTipo());
                selectTipo.setSelectedAttribute(optionTipo, true);
            }

            if (!filter.getEstado().equals("")) {
                HtmlSelect selectEstado = page1.getHtmlElementById("estados");
                HtmlOption optionEstado = selectEstado.getOptionByValue(filter.getEstado());
                selectEstado.setSelectedAttribute(optionEstado, true);
            }

            if (!filter.getCidade().equals("")) {
                HtmlSelect selectCidade = page1.getHtmlElementById("cidades");
                HtmlOption optionCidade = selectCidade.getOptionByValue(filter.getCidade());
                selectCidade.setSelectedAttribute(optionCidade, true);
            }

            if (!filter.getBairro().equals("")) {
                HtmlSelect selectBairro = page1.getHtmlElementById("bairros");
                HtmlOption optionBairro = selectBairro.getOptionByValue(filter.getBairro());
                selectBairro.setSelectedAttribute(optionBairro, true);
            }

            if (!filter.getQuartos().equals("")) {
                HtmlSelect selectQuarto = page1.getHtmlElementById("quartos");
                HtmlOption optionQuarto = selectQuarto.getOptionByValue(filter.getQuartos());
                selectQuarto.setSelectedAttribute(optionQuarto, true);
            }

            HtmlPage page = dom.click();
            boolean hasNext = true;
            int pageNumber = 1;

            this.linkPaginas = new ArrayList<>();
            this.linkPaginas.add(page.getUrl().toString());
            this.imoveis = new ArrayList<>();
            this.erros = new ArrayList<>();

            do {

                try {

                    pageNumber++;
                    HtmlAnchor htmlAnchor = page.getAnchorByHref("?pagina=" + pageNumber);

                    page = htmlAnchor.click();

                    this.linkPaginas.add(page.getUrl().toString());

                } catch (ElementNotFoundException e) {
                    hasNext = false;
                    this.erros.add(new Error(e, 1));
                    log.error("Erro ao tentar recuperar páginas: ", e);
                    webClient.close();
                } catch (Exception e) {
                    hasNext = false;
                    webClient.close();
                }

            } while (hasNext);


            log.info("Páginas........................: " + this.linkPaginas.size());

            webClient.getCurrentWindow().getJobManager().removeAllJobs();

        }

       scrapPages();
    }


    private void scrapPages() throws  IOException {

        int i = 0;

        for(String link : this.linkPaginas) {

            try(WebClient webClient = new WebClient(BrowserVersion.CHROME)) {

                try {

                    i++;

                    log.info("Página........................: " + i);
                    WebRequest requestSettings = new WebRequest(
                            new URL(link), HttpMethod.GET);

                    webClient.getOptions().setThrowExceptionOnScriptError(false);
                    webClient.getOptions().setCssEnabled(true);
                    webClient.getCookieManager().setCookiesEnabled(true);
                    webClient.getOptions().setRedirectEnabled(true);
                    webClient.getOptions().setJavaScriptEnabled(true);
                    webClient.setAjaxController(new NicelyResynchronizingAjaxController());

                    final HtmlPage page = webClient.getPage(requestSettings);


                    List<HtmlDivision> items = page.getByXPath("//div[@class='property__info-content']");

                    for (HtmlDivision div : items) {

                        try {

                            this.imoveis.add(configEntity(div, new Imovel(), webClient));


                        } catch (Exception e) {
                            log.error("Registro regeitado: " + div.getVisibleText(), e);
                        }
                    }

                    final List<WebWindow> windows = webClient.getWebWindows();

                    for (final WebWindow wd : windows) {

                        wd.getJobManager().removeAllJobs();
                        wd.setEnclosedPage(null);
                    }

                    System.gc();

                } catch (Exception e) {
                    log.error("Erro ao extratir dados da pagina", e);
                    this.erros.add(new Error(e, 2));

                } finally {
                    webClient.close();
                }
                webClient.getCurrentWindow().getJobManager().removeAllJobs();
            }
        }
    }


    private Imovel configEntity(HtmlDivision div, Imovel imovel, WebClient webClient) throws IOException{

        String [] infoArray = div.getVisibleText().split("\\r?\\n");

        int i = 0;

        while(i < infoArray.length) {

            if(i == 0) {
                imovel.setEndereco(infoArray[i]);
            } else if(i == 1) {
                imovel.setValor(extractValue(infoArray[i], 0)); imovel.setValorMetro(extractValue(infoArray[i], 2));
            } else {

                if(infoArray[i].contains("Ac.")) {
                    imovel.setDescricao(infoArray[i]);
                } else  if(infoArray[i].contains("Apartamento") || infoArray[i].contains("Casa") ||
                        infoArray[i].contains("Loja") || infoArray[i].contains("Kitnet") ||
                        infoArray[i].contains("Galpao") || infoArray[i].contains("Garagem") ||
                        infoArray[i].contains("Hotel-flat") || infoArray[i].contains("Hotel-flat") ||
                        infoArray[i].contains("Lote") || infoArray[i].contains("Predio") ||
                        infoArray[i].contains("Rural") || infoArray[i].contains("Sala")) {

                    String [] arrayArea = infoArray[i].trim().split(" ");

                    imovel.setAreaUtil(recurarInterioString(arrayArea[1]));

                } else if(infoArray[i].contains("Quartos")) {
                    String [] arrayQuartos = infoArray[i].trim().split(" ");
                    int quartos = recurarInterioString(arrayQuartos[0]);

                    imovel.setQuartos(quartos);
                } else if(infoArray[i].contains("Suítes")) {
                    String [] arraySuites = infoArray[i].trim().split(" ");
                    int suites = recurarInterioString(arraySuites[0]);

                    imovel.setSuites(suites);
                }  else if(infoArray[i].contains("Vagas")) {
                    String [] arrayVagas = infoArray[i].trim().split(" ");


                    imovel.setVagas(recurarInterioString(arrayVagas[0]));
                }

            }
            i++;
        }

        HtmlPage pagePhone = div.click();

        imovel.setLink(pagePhone.getUrl().toString());

        HtmlElement spanBotao = pagePhone.getFirstByXPath("//span[@class='more btn-ver-telefone ligueAgoraPeloAnunciante btn btn-enviar-email gtm-ver-telefone']");

        String phone = "";

        pagePhone = spanBotao.click();

        try {
            HtmlElement spansPhoneBeginParty = (HtmlElement) pagePhone.getByXPath("//span[@data-mask='(00) 00000']").get(0);
            phone = spansPhoneBeginParty.getVisibleText();

            HtmlElement spansPhoneFinalParty = (HtmlElement) pagePhone.getByXPath("//span[@data-mask='- 0000']").get(0);
            spansPhoneFinalParty.setAttribute("style", "display: inline;");

            webClient.waitForBackgroundJavaScript(2000);

            phone += spansPhoneFinalParty.getVisibleText();
        } catch (IndexOutOfBoundsException e) {
            log.error("Erro ao tentar obter telefone: ", e);
            phone = "";
        }

        imovel.setTelefone(phone);
        imovel.setDataCriacao(new Date());

        if(repository.searchByLink(pagePhone.getUrl().toString()) == null) {
            repository.save(imovel);
        }

        pagePhone.remove();
        pagePhone.cleanUp();


        log.info("Nao ha vitoria sem luta: " + phone);

        return imovel;
    }

    private int recurarInterioString(String string) {

        int area = 0;

        try {
            return Integer.parseInt(string);

        }catch (Exception e) {
            log.error(e.getStackTrace().toString());
            return area;
        }
    }

    private BigDecimal extractValue(String string, int i) {

        String [] valores = string.replaceAll("R\\$","").trim().split(" ");
        String valor = valores[i].replaceAll("\\.", "");

        BigDecimal bigDecimalCurrency = new BigDecimal(valor.toString());

        return bigDecimalCurrency;
    }

    public List<Imovel> getImoveis() {
        return imoveis;
    }

    public void setImoveis(List<Imovel> imoveis) {
        this.imoveis = imoveis;
    }

    public List<Error> getErros() {
        return erros;
    }

    public void setErros(List<Error> erros) {
        this.erros = erros;
    }

















    public void teste(Filter filter) throws Exception {

        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {

            WebRequest requestSettings = new WebRequest(
                    new URL("https://www.dfimoveis.com.br/"), HttpMethod.GET);

            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setCssEnabled(true);
            webClient.getCookieManager().setCookiesEnabled(true);
            webClient.getOptions().setRedirectEnabled(true);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
/*
            try {
                final History window = webClient.getWebWindows().get(0).getHistory();
                final Field f = window.getClass().getDeclaredField("ignoreNewPages_"); //NoSuchFieldException
                f.setAccessible(true);
                ((ThreadLocal<Boolean>) f.get(window)).set(Boolean.TRUE);
                log.debug("_dbff772d4d_ disabled history of Webclient");
            }
            catch (final Exception e) {
                log.warn("_66461112f7_ Can't disable history of Webclient");
            }
*/
            final HtmlPage page1 = webClient.getPage(requestSettings);

            HtmlButton dom = page1.getHtmlElementById("botaoDeBusca");

            HtmlSelect select = (HtmlSelect) page1.getHtmlElementById("negocios");
            HtmlOption optionNegocios = select.getOptionByValue(filter.getNegocio());
            select.setSelectedAttribute(optionNegocios, true);

            if (!filter.getTipo().equals("")) {
                HtmlSelect selectTipo = (HtmlSelect) page1.getHtmlElementById("tipos");
                HtmlOption optionTipo = selectTipo.getOptionByValue(filter.getTipo());
                selectTipo.setSelectedAttribute(optionTipo, true);
            }

            if (!filter.getEstado().equals("")) {
                HtmlSelect selectEstado = (HtmlSelect) page1.getHtmlElementById("estados");
                HtmlOption optionEstado = selectEstado.getOptionByValue(filter.getEstado());
                selectEstado.setSelectedAttribute(optionEstado, true);
            }

            if (!filter.getCidade().equals("")) {
                HtmlSelect selectCidade = (HtmlSelect) page1.getHtmlElementById("cidades");
                HtmlOption optionCidade = selectCidade.getOptionByValue(filter.getCidade());
                selectCidade.setSelectedAttribute(optionCidade, true);
            }

            if (!filter.getBairro().equals("")) {
                HtmlSelect selectBairro = (HtmlSelect) page1.getHtmlElementById("bairros");
                HtmlOption optionBairro = selectBairro.getOptionByValue(filter.getBairro());
                selectBairro.setSelectedAttribute(optionBairro, true);
            }

            if (!filter.getQuartos().equals("")) {
                HtmlSelect selectQuarto = (HtmlSelect) page1.getHtmlElementById("quartos");
                HtmlOption optionQuarto = selectQuarto.getOptionByValue(filter.getQuartos());
                selectQuarto.setSelectedAttribute(optionQuarto, true);
            }

            int pageNumber = 1;
            boolean hasNest = true;
            HtmlPage page2 = dom.click();
            this.imoveis = new ArrayList<>();
            this.erros = new ArrayList<>();
            //page1.remove();
            //page1.cleanUp();

            do {

                try {

                    List<HtmlDivision> items = page2.getByXPath("//div[@class='property__info-content']");

                    for (HtmlDivision div : items) {

                        try {

                            this.imoveis.add(configEntity(div, new Imovel(), webClient));

                        } catch (Exception e) {
                            log.error("Registro regeitado: " + div.getVisibleText(), e);
                        }
                    }

                    final List<WebWindow> windows = webClient.getWebWindows();

                    for (final WebWindow wd : windows) {

                        wd.getJobManager().removeAllJobs();
                    }

                    //log.info("Paginas Abertas: " + webClient.getWebWindows().size());

                    //page2.cleanUp();
                    //page2.remove();

                    pageNumber++;
                    //log.info("Paginas Abertas: " + webClient.getWebWindows().size());

                    HtmlAnchor htmlAnchor = page2.getAnchorByHref("?pagina=" + Integer.toString(pageNumber));

                    page2 = htmlAnchor.click();


                } catch (ElementNotFoundException e) {
                    hasNest = false;
                    this.erros.add(new Error(e, 1));
                    log.error("Erro codigo 1", e);

                } catch (IOException e) {
                    hasNest = false;
                    log.error("Erro codigo 2", e);
                    this.erros.add(new Error(e, 2));

                } catch (Exception e) {
                    hasNest = false;
                    log.error("Erro codigo 3", e);
                    this.erros.add(new Error(e, 3));

                } finally {
                    webClient.close();
                }

            } while(hasNest);

        }
    }

}
