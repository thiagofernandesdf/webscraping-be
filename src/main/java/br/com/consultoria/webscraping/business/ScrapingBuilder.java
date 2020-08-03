package br.com.consultoria.webscraping.business;

import com.gargoylesoftware.htmlunit.*;

import java.net.MalformedURLException;
import java.net.URL;

public class ScrapingBuilder {

    private WebClient webClient;

    public ScrapingBuilder() throws Exception {

        webClient = new WebClient(BrowserVersion.CHROME);

        

        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(true);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

    }

    public ScrapingBuilder withUrl(String url) throws MalformedURLException {

        WebRequest requestSettings = new WebRequest(
                new URL("https://www.dfimoveis.com.br/"), HttpMethod.GET);

        return this;
    }

}
