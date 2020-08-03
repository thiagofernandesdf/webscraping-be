package br.com.consultoria.webscraping.business;

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
import java.util.List;

@Component
public class ScrapingService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private List<Imovel> imovels;

    @Autowired
    private ImovelRepository repository;


    public void createScrapeFilter(Filter filter) throws IOException {

        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {

            WebRequest requestSettings = new WebRequest(
                    new URL("https://www.dfimoveis.com.br/"), HttpMethod.GET);

            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setCssEnabled(true);
            webClient.getCookieManager().setCookiesEnabled(true);
            webClient.getOptions().setRedirectEnabled(true);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());

            final HtmlPage page1 = webClient.getPage(requestSettings);

            HtmlButton dom = page1.getHtmlElementById("botaoDeBusca");

            HtmlSelect select = (HtmlSelect) page1.getHtmlElementById("negocios");
            HtmlOption optionNegocios = select.getOptionByValue(filter.getNegocio());
            select.setSelectedAttribute(optionNegocios, true);

            HtmlSelect selectTipo = (HtmlSelect) page1.getHtmlElementById("tipos");
            HtmlOption optionTipo = selectTipo.getOptionByValue(filter.getTipo());
            selectTipo.setSelectedAttribute(optionTipo, true);

            HtmlSelect selectEstado = (HtmlSelect) page1.getHtmlElementById("estados");
            HtmlOption optionEstado = selectEstado.getOptionByValue(filter.getEstado());
            selectEstado.setSelectedAttribute(optionEstado, true);

            HtmlSelect selectCidade = (HtmlSelect) page1.getHtmlElementById("cidades");
            HtmlOption optionCidade = selectCidade.getOptionByValue(filter.getCidade());
            selectCidade.setSelectedAttribute(optionCidade, true);

            HtmlSelect selectBairro = (HtmlSelect) page1.getHtmlElementById("bairros");
            HtmlOption optionBairro = selectBairro.getOptionByValue(filter.getBairro());
            selectBairro.setSelectedAttribute(optionBairro, true);

            HtmlSelect selectQuarto = (HtmlSelect) page1.getHtmlElementById("quartos");
            HtmlOption optionQuarto = selectQuarto.getOptionByValue(filter.getQuartos());
            selectQuarto.setSelectedAttribute(optionQuarto, true);

            webClient.waitForBackgroundJavaScript(1000 * 4);

            HtmlPage page2 = dom.click();

            toScrape(page2, webClient);
        }

    }

    private void toScrape(HtmlPage page, WebClient webClient) throws IOException {

        DomElement ul = page.getElementById("resultadoDaBuscaDeImoveis");

        List<Imovel> imoveis = new ArrayList<>();

        List<HtmlElement> divList = ul.getElementsByTagName("div");

        for(HtmlElement div : divList) {


            if(div.getAttribute("class").equals("property__info-content")) {

                Imovel imovel = new Imovel();

                String info = div.getVisibleText();

                String [] infoArray = info.split("\\r?\\n");

/*
                log.info(infoArray[0]); //end
                log.info(infoArray[1]); //valor e metro quadrado
                log.info(infoArray[2]); //ac permuta
                log.info(infoArray[3]); //tamanho
                log.info(infoArray[4]); //quartos
                log.info(infoArray[5]); //suites
                log.info(infoArray[6]); //vagas
*/
                configEntity(infoArray, imovel);
                String phone = "";

                HtmlPage p = div.click();
                webClient.waitForBackgroundJavaScript(1000 * 4);

                log.info("-----------"+p.getTitleText());



                List<HtmlElement> divPhoneList = p.getBody().getElementsByTagName("span");

                for(HtmlElement divPhone : divPhoneList) {

                    if(divPhone.getAttribute("data-mask").equals("(00) 0000")) {
                        phone = divPhone.getVisibleText();
                    }

                    if(divPhone.getAttribute("class").equals("more btn-ver-telefone ligueAgoraPeloAnunciante btn btn-enviar-email gtm-ver-telefone")){

                        HtmlPage p2 = divPhone.click();
                        webClient.waitForBackgroundJavaScript(1000 * 4);

                        //log.info("---------> "+divPhone.getVisibleText());

                        List<HtmlElement> divPhone2 = p2.getBody().getElementsByTagName("span");

                        for(HtmlElement hideElements : divPhone2) {
                            if(hideElements.getAttribute("class").equals("complete")) {
                                phone += hideElements.getVisibleText();
                                log.info("------");
                                log.info("---------------------------> " + hideElements.getVisibleText());
                                log.info("toString: "+hideElements.toString());
                                log.info("value: "+hideElements.getNodeValue());
                                log.info("tag name: "+hideElements.getTagName());
                                log.info("node name: "+hideElements.getNodeName());
                                break;
                            }
                        }

                        break;
                    }
                }

                imovel.setTelefone(phone);

                repository.save(imovel);
                //imoveis.add(imovel);
            }
        }
        this.setImovels(imoveis);
    }

    /*
        log.info(infoArray[0]); //end
        log.info(infoArray[1]); //valor e metro quadrado
        log.info(infoArray[2]); //ac permuta
        log.info(infoArray[3]); //tamanho
        log.info(infoArray[4]); //quartos
        log.info(infoArray[5]); //suites
        log.info(infoArray[6]); //vagas
    */

    /*
2020-07-20 17:36:45 - HikariPool-1 - Thread starvation or clock leap detected (housekeeper delta=1m14s346ms289µs364ns).
2020-07-20 17:36:45 - SQSW 303, SUDOESTE, BRASILIA Sudoeste, Brasilia SQSW 303
2020-07-20 17:36:46 - R$1.500.000 m² R$9.804
2020-07-20 17:36:47 - Ac. Permuta
2020-07-20 17:36:49 - Apartamento 153 m²
2020-07-20 17:36:52 - 4 Quartos
2020-07-20 17:36:54 - 3 Suítes
2020-07-20 17:36:56 - 2 Vagas
 */
    private void configEntity(String [] infoArray, Imovel imovel) {

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
    }

    private int recurarInterioString(String string) {

        int area = 0;

        try {
            return area = Integer.parseInt(string);

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

    private boolean testScrap() {
        return false;
    }

    public List<Imovel> getImovels() {
        return imovels;
    }

    public void setImovels(List<Imovel> imovels) {
        this.imovels = imovels;
    }
}