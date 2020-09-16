package br.com.consultoria.webscraping;

import br.com.consultoria.webscraping.business.ComboBusiness;
import br.com.consultoria.webscraping.business.ScrapingService;
import br.com.consultoria.webscraping.model.Combo;
import br.com.consultoria.webscraping.model.DFIMoveisFilter;
import br.com.consultoria.webscraping.model.Filter;
import br.com.consultoria.webscraping.model.Imovel;
import br.com.consultoria.webscraping.repositoty.ImovelDAO;
import br.com.consultoria.webscraping.repositoty.ImovelDAO1;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

//@SpringBootTest
class WebScrapingApplicationTests {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ScrapingService service;

	@Autowired
	private ImovelDAO imovelDAO;

	@Autowired
	private ImovelDAO1 imovelDAO1;

	@Resource(name = "sessionScopedBean")
	private ComboBusiness comboBusiness;

	@Test
	void combos() throws IOException {
		DFIMoveisFilter filter = new DFIMoveisFilter();
		filter.setNegocio(new Combo("LANCAMENTO","LANCAMENTO"));


		comboBusiness.changeCombo(filter);
		//comboBusiness.getComboTipos().forEach(cf -> System.out.println(cf.getName()));

	}

	//@Test
	public void getCombosNegocio() throws IOException {

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

			HtmlSelect selectNegocio = (HtmlSelect) page1.getElementById("negocios");

			selectNegocio.getOptions().forEach(o ->
					print1(o)
			);

		}
	}

	void print1(HtmlOption option) {
		log.info(option.getText());
		log.info(option.getVisibleText());
	}

	//@Test
	void testes() {

		Imovel imovel = new Imovel();
		//imovel.setEndereco("sqsw");
		imovel.setVagas(2);

		List<Imovel> l = imovelDAO1.get(imovel, 1, 10);


	}

	//@Test
	void call() throws IOException {

		Filter filter = new Filter("VENDA", "APARTAMENTO", "DF","BRASILIA","SUDOESTE","4+");
		//Filter filter = new Filter("VENDA", "IMOVEIS", "DF","TODOS","","");

		//service.createScrapeFilter(filter);
	}

	//@Test
	void tester() throws IOException {

		Document doc = Jsoup.connect("https://en.wikipedia.org/").get();

		log.info(doc.title());

		Elements newsHeadlines = doc.select("#mp-itn b a");

		for (Element headline : newsHeadlines) {
			log.info("%s\n\t%s",
					headline.attr("title"), headline.absUrl("href"));
		}

	}

	//@Test
	void whenReturnTitle() throws IOException {

		Document doc = Jsoup.connect("https://www.dfimoveis.com.br/").get();

		Element content = doc.getElementById("div-gpt-ad-1503551917218-0");

		Elements contents = doc.getElementsByTag("div-gpt-ad-1503551917218-0");
/*
System.out.println("1" + elements);
		for(Element element : elements) {
			Elements divs = content.getElementsByTag("col-md-4 mb-3");
			System.out.println(divs.toString());
		}
		System.out.println("2");
*/
	}

	//@Test
	void whenSubmit() throws IOException {

		Connection.Response response =
				Jsoup.connect("https://www.dfimoveis.com.br/busca-por-codigo")
						.userAgent("Mozilla/5.0")
						.timeout(10 * 1000)
						.method(Connection.Method.POST)
						.data("Negocio", "ALUGUEL")
						.data("Tipo", "APARTAMENTO")
						.data("Estado", "DF")
						.data("Cidade", "BRASILIA")
						.data("Quartos", "3")
						.followRedirects(true)
						.execute();

		Document document = response.parse();

		Map<String, String> loginCookies = response.cookies();

		Document doc = Jsoup.connect("https://www.dfimoveis.com.br/busca")
				.cookies(loginCookies)
				.get();

		Element element = document.getElementById("resultadoDaBuscaDeImoveis");

		Elements elements = document.getElementsByTag("li");


		log.info(elements.toString());


	}

	//@Test
	void tested1(){
		log.info("oiiiiiiiiii");
	}


	//@Test
	public void submittingFormDFImoveis() throws Exception {

		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {

			webClient.addRequestHeader("Referer", "https://www.dfimoveis.com.br/");

			WebRequest requestSettings = new WebRequest(
					new URL("https://www.dfimoveis.com.br/"), HttpMethod.GET);

			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setCssEnabled(true);
			webClient.getCookieManager().setCookiesEnabled(true);
			webClient.getOptions().setRedirectEnabled(true);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());

			// Get the first page
			final HtmlPage page1 = webClient.getPage(requestSettings);

			// Get the form that we are dealing with and within that form,
			// find the submit button and the field that we want to change.

			HtmlButton dom = page1.getHtmlElementById("botaoDeBusca");

			HtmlSelect select = (HtmlSelect) page1.getHtmlElementById("negocios");
			HtmlOption optionNegocios = select.getOptionByValue("VENDA");
			select.setSelectedAttribute(optionNegocios, true);

			HtmlSelect selectTipo = (HtmlSelect)page1.getHtmlElementById("tipos");
			HtmlOption optionTipo = selectTipo.getOptionByValue("APARTAMENTO");
			selectTipo.setSelectedAttribute(optionTipo, true);

			HtmlSelect selectEstado = (HtmlSelect)page1.getHtmlElementById("estados");
			HtmlOption optionEstado = selectEstado.getOptionByValue("DF");
			selectEstado.setSelectedAttribute(optionEstado, true);


			//selectEstado.click();

			HtmlSelect selectCidade =(HtmlSelect) page1.getHtmlElementById("cidades");
			HtmlOption optionCidade = selectCidade.getOptionByValue("BRASILIA");
			selectCidade.setSelectedAttribute(optionCidade, true);

			//selectCidade.click();

			HtmlSelect selectBairro = (HtmlSelect)page1.getHtmlElementById("bairros");
			HtmlOption optionBairro = selectBairro.getOptionByValue("SUDOESTE");
			selectBairro.setSelectedAttribute(optionBairro, true);

			HtmlSelect selectQuarto = (HtmlSelect)page1.getHtmlElementById("quartos");
			HtmlOption optionQuarto = selectQuarto.getOptionByValue("4+");
			selectQuarto.setSelectedAttribute(optionQuarto, true);


			log.info(page1.asText());



			webClient.addWebWindowListener( new WebWindowListener() {
				@Override
				public void webWindowOpened(WebWindowEvent webWindowEvent) {
					log.info(webWindowEvent.toString());
				}

				public void webWindowContentChanged(WebWindowEvent event) {

					HtmlPage page2 = (HtmlPage) event.getNewPage();

					DomElement div = page2.getElementById("resultadoDaBuscaDeImoveis");
					DomNodeList<HtmlElement> lista = div.getElementsByTagName("ul");

					lista.forEach(a -> {

					int i = 0;
						for(DomNode dn : a.getElementsByTagName("li")) {
							log.info("---------------> "+ i	);
							log.info("---------------> " + dn.asText());
						i++;
						}

					});

					HtmlElement b = page2.getBody();

					List<HtmlElement> l = b.getElementsByAttribute("div", "class","container");

					l.forEach(
							x -> {
								DomNodeList<HtmlElement> dnl = x.getElementsByTagName("section");

								dnl.forEach(y -> {

														if(y.getAttribute("class").equals("result-search")) {
															DomNodeList<HtmlElement> dn = y.getElementsByTagName("div");
															dn.forEach(z -> {
																if(z.getAttribute("class").equals("result-search__content")) {

																}
															});
														}

												});
							}

					);

					l.forEach(x -> log.info("------------------------> " + x.getNodeName()));

					//HtmlDivision div = page2.getHtmlElementById("resultadoDaBuscaDeImoveis")

//					DomElement de = domElement.getLastElementChild();

					log.info("---> "+ page2.asText());

					/*
					for (DomNode element : lista) {
						log.info(element.getLocalName());
					}*/
				}

				@Override
				public void webWindowClosed(WebWindowEvent webWindowEvent) {
					log.info(webWindowEvent.toString());
				}

			});

			dom.click();
			webClient.waitForBackgroundJavaScript(1000);



		}
	}




	//@Test
	public void submittingForm1() throws Exception {


		try (final WebClient webClient = new WebClient()) {


			WebRequest requestSettings = new WebRequest(
					new URL("https://www.wimoveis.com.br/"), HttpMethod.GET);

			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setCssEnabled(false);

			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(true);
			webClient.getOptions().setRedirectEnabled(true);
			// Get the first page
			final HtmlPage page1 = webClient.getPage(requestSettings);

			// Get the form that we are dealing with and within that form,
			// find the submit button and the field that we want to change.
			final HtmlForm form = page1.getForms().get(0);

			final Iterable<DomElement> i = form.getChildElements();

			HtmlPage page2 = null;

			for(DomElement de : i) {

				if(de.getNodeName().equals("div")) {
					for(DomElement div : de.getChildElements()) {
						if(div.getAttribute("class").equals("apply-filters")) {
							DomNodeList<HtmlElement> dnle = div.getElementsByTagName("button");

							if (dnle.iterator().hasNext()) {
								HtmlElement button =  dnle.iterator().next();
								//page2 = button.click();

								WebWindow window = page1.getEnclosingWindow();
								button.click();
								while(window.getEnclosedPage() == page1) {
									// The page hasn't changed.
									Thread.sleep(500);
								}
// This loop above will wait until the page changes.
								//page2 = window.getEnclosedPage();

								DomElement body = page2.getElementById("body-listado");


								for(DomElement divr : page2.getElementsByName("body")) {
									log.info("---> " + divr.getId());
								}

								log.info(page2.toString());
							}
						}
					}
				}

				log.info(de.getNodeName());
				log.info(de.getTagName());
				log.info(de.getLocalName());
								log.info(i.toString());
			}

			//final HtmlTextInput textField = form.getInputByName("userid");

			// Change the value of the text field
			//textField.type("root");

			// Now submit the form by clicking the button and get back the second page.
			//final HtmlPage page2 = button.click();

		}
	}




	//@Test
	public void submittingForm3() throws Exception {

		try (final WebClient webClient = new WebClient()) {

			// Get the first page
			final HtmlPage page1 = webClient.getPage("https://www.wimoveis.com.br/");

			// Get the form that we are dealing with and within that form,
			// find the submit button and the field that we want to change.
			final HtmlForm form = page1.getForms().get(0);

			final HtmlSubmitInput button = form.getInputByName("submitbutton");
			final HtmlTextInput textField = form.getInputByName("userid");

			// Change the value of the text field
			textField.type("root");

			// Now submit the form by clicking the button and get back the second page.
			final HtmlPage page2 = button.click();
		}
	}




	//@Test
	public void submittingFormDFImoveisClean() throws Exception {

		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {

				//webClient.addRequestHeader("Referer", "https://www.dfimoveis.com.br/");

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
			HtmlOption optionNegocios = select.getOptionByValue("VENDA");
			select.setSelectedAttribute(optionNegocios, true);

			HtmlSelect selectTipo = (HtmlSelect)page1.getHtmlElementById("tipos");
			HtmlOption optionTipo = selectTipo.getOptionByValue("APARTAMENTO");
			selectTipo.setSelectedAttribute(optionTipo, true);

			HtmlSelect selectEstado = (HtmlSelect)page1.getHtmlElementById("estados");
			HtmlOption optionEstado = selectEstado.getOptionByValue("DF");
			selectEstado.setSelectedAttribute(optionEstado, true);

			HtmlSelect selectCidade =(HtmlSelect) page1.getHtmlElementById("cidades");
			HtmlOption optionCidade = selectCidade.getOptionByValue("GAMA");
			selectCidade.setSelectedAttribute(optionCidade, true);

			HtmlSelect selectBairro = (HtmlSelect)page1.getHtmlElementById("bairros");
			HtmlOption optionBairro = selectBairro.getOptionByValue("");
			selectBairro.setSelectedAttribute(optionBairro, true);

			HtmlSelect selectQuarto = (HtmlSelect)page1.getHtmlElementById("quartos");
			HtmlOption optionQuarto = selectQuarto.getOptionByValue("2");
			selectQuarto.setSelectedAttribute(optionQuarto, true);

			HtmlPage page2 = dom.click();

			webClient.waitForBackgroundJavaScript(1000 * 4);

			DomElement div = page2.getElementById("resultadoDaBuscaDeImoveis");
			DomNodeList<HtmlElement> lista = div.getElementsByTagName("ul");

			//final HtmlDivision divsss = (HtmlDivision) page1.getByXPath("//div[@class='property__info-content']").get(0);


			final DomNode div2 = page2.querySelector("div#container");

			log.info("--> " + div2);

			//HtmlUnorderedList ul =

			lista.forEach(a -> {
				int i = 0;
				for(DomElement dn : a.getElementsByTagName("li")) {

					for(DomElement dom1 : dn.getChildElements()) {

						if(dom1.getNodeName().equals("div")){

							for(DomElement htmlDiv : dom1.getElementsByTagName("div")) {

								HtmlDivision ah = (HtmlDivision ) htmlDiv;

								if(ah.getAttribute("class").equals("property__info")) {

									for (DomElement de : ah.getElementsByTagName("div")) {

										if (de.getAttribute("class").equals("property__info-content")) {

											for (DomElement de1 : de.getElementsByTagName("div")) {

												if (de1.getAttribute("class").equals("property__info-content")) {
													log.info("--> " + de1);
												}
											}


										}
									}
								}
							}

						}


					}

					//log.info("---------------> "+ i	);
					//log.info("---------------> "+ dn.getVisibleText());
					//log.info("---------------> "+ dn.getLocalName());
					//log.info("---------------> "+ dn.getChildren());
					//print(dn);
					i++;
				}
			});
/*
			HtmlAnchor htmlAnchor = page2.getAnchorByHref("?pagina=4");
			HtmlPage page3 = htmlAnchor.click();
			log.info(page3.asText());

 */
		}
	}


	void print(DomElement de) {

		for(DomElement d : de.getElementsByTagName("div")) {

			log.info("classe ----------> " + d.getAttribute("class"));

			// && d.getAttribute("class").equals("property__info-content")
			if(d.getTagName().equals("div")) {
				log.info(de.getTagName());
				log.info(de.getNodeValue());
				log.info(" --> "+ de.getChildElements());
			}

		}



	}










	@Test
	public void agoraVai() throws Exception {

		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {

			//webClient.addRequestHeader("Referer", "https://www.dfimoveis.com.br/");

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
			HtmlOption optionNegocios = select.getOptionByValue("VENDA");
			select.setSelectedAttribute(optionNegocios, true);

			HtmlSelect selectTipo = (HtmlSelect) page1.getHtmlElementById("tipos");
			HtmlOption optionTipo = selectTipo.getOptionByValue("APARTAMENTO");
			selectTipo.setSelectedAttribute(optionTipo, true);

			HtmlSelect selectEstado = (HtmlSelect) page1.getHtmlElementById("estados");
			HtmlOption optionEstado = selectEstado.getOptionByValue("DF");
			selectEstado.setSelectedAttribute(optionEstado, true);

			HtmlSelect selectCidade = (HtmlSelect) page1.getHtmlElementById("cidades");
			HtmlOption optionCidade = selectCidade.getOptionByValue("BRASILIA");
			selectCidade.setSelectedAttribute(optionCidade, true);

			HtmlSelect selectBairro = (HtmlSelect) page1.getHtmlElementById("bairros");
			HtmlOption optionBairro = selectBairro.getOptionByValue("SUDOESTE");
			selectBairro.setSelectedAttribute(optionBairro, true);

			HtmlSelect selectQuarto = (HtmlSelect) page1.getHtmlElementById("quartos");
			HtmlOption optionQuarto = selectQuarto.getOptionByValue("4+");
			selectQuarto.setSelectedAttribute(optionQuarto, true);

			HtmlPage page2 = dom.click();

			webClient.waitForBackgroundJavaScript(1000 * 4);


			DomElement ul = page2.getElementById("resultadoDaBuscaDeImoveis");

			List<HtmlElement> divList = ul.getElementsByTagName("div");

			for(HtmlElement div : divList) {

				if(div.getAttribute("class").equals("property__info-content")) {


					String info = div.getVisibleText();

					String [] infoArray = info.split("\\r?\\n");

					log.info(infoArray[0]);
					log.info(infoArray[1]);
					log.info(infoArray[2]);
					log.info(infoArray[3]);
					log.info(infoArray[4]);
					//log.info(infoArray[5]);

					HtmlPage p = div.click();
					webClient.waitForBackgroundJavaScript(1000 * 4);

					log.info("-----------"+p.getTitleText());



					List<HtmlElement> divPhoneList = p.getBody().getElementsByTagName("span");

					for(HtmlElement divPhone : divPhoneList) {

						if(divPhone.getAttribute("data-mask").equals("(00) 0000")) {
							log.info("----- PHONE -----> "+divPhone.getVisibleText());
						}

						if(divPhone.getAttribute("class").equals("more btn-ver-telefone ligueAgoraPeloAnunciante btn btn-enviar-email gtm-ver-telefone")){

							HtmlPage p2 = divPhone.click();
							webClient.waitForBackgroundJavaScript(1000 * 3);

							//log.info("---------> "+divPhone.getVisibleText());

							List<HtmlElement> divPhone2 = p2.getBody().getElementsByTagName("span");

							for(HtmlElement hideElements : divPhone2) {
								if(hideElements.getAttribute("class").equals("complete")) {
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



				}

			}





		}
	}


}

