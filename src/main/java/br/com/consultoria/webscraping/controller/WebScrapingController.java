package br.com.consultoria.webscraping.controller;

import br.com.consultoria.webscraping.Error;
import br.com.consultoria.webscraping.business.ComboBusiness;
import br.com.consultoria.webscraping.business.ScrapingService;
import br.com.consultoria.webscraping.business.Teste;
import br.com.consultoria.webscraping.input.InputFind;
import br.com.consultoria.webscraping.model.Combo;
import br.com.consultoria.webscraping.model.DFIMoveisFilter;
import br.com.consultoria.webscraping.model.Filter;
import br.com.consultoria.webscraping.model.Imovel;

import br.com.consultoria.webscraping.output.Output;
import br.com.consultoria.webscraping.output.OutputCombo;
import br.com.consultoria.webscraping.output.OutputFilter;
import br.com.consultoria.webscraping.output.OutputFind;
import br.com.consultoria.webscraping.repositoty.ImovelDAO1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("api")
public class WebScrapingController {

    @Autowired
    private ScrapingService service;

    @Autowired
    private Teste teste;

    @Autowired
    private ImovelDAO1 dao;

    @Resource(name = "applicationScopedBean")
    private ComboBusiness comboBusiness;

    @CrossOrigin
    @PostMapping(value = "/list", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public OutputFind list(@RequestBody InputFind input) {

        List<Imovel> imoveis = dao.get(input.getImovel(), input.getFirstResult(), input.getPageSize());
        long count = dao.getCount();

        return new OutputFind(count, imoveis);
    }

    @CrossOrigin
    @PostMapping(value = "/comboNegocio", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public OutputCombo listComboNegocio() {

        try {
            return new OutputCombo(service.getCombosNegocio());
        } catch (IOException ioe){
            return new OutputCombo(new Error(ioe, 0));
        }

    }

    @CrossOrigin
    @PostMapping(value = "/combo", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public OutputCombo combo(@RequestBody DFIMoveisFilter filter) {

        try {
            comboBusiness.changeCombo(filter);
            System.out.println(comboBusiness.getCombos());
            return new OutputCombo(comboBusiness.getCombos());
        } catch (Exception ioe){
            return new OutputCombo(new Error(ioe, 0));
        }

    }


    @CrossOrigin
    @PostMapping(value = "/carregarBanco", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public OutputFilter carregarBanco(@RequestBody Filter filter) {

        try {

            //service.createScrapeFilter(filter);
            //teste.teste(filter);
            teste.findPages(filter,"https://www.dfimoveis.com.br/");

            return new OutputFilter(teste.getErros(), teste.getImoveis().size());

        } catch (Exception ioe){
            return new OutputFilter(teste.getErros(), teste.getImoveis().size());
        }
    }
}
