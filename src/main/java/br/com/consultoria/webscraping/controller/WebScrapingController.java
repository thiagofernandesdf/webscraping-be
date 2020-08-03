package br.com.consultoria.webscraping.controller;

import br.com.consultoria.webscraping.business.ScrapingService;
import br.com.consultoria.webscraping.model.Imovel;
import br.com.consultoria.webscraping.model.Input;
import br.com.consultoria.webscraping.model.Output;
import br.com.consultoria.webscraping.repositoty.ImovelDAO;
import br.com.consultoria.webscraping.repositoty.ImovelDAO1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api")
public class WebScrapingController {

    @Autowired
    private ScrapingService service;

    @Autowired
    private ImovelDAO1 dao;

    @PostMapping(value = "/list", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Output list(@RequestBody Input input) {

        List<Imovel> imoveis = dao.get(input.getImovel(), input.getFirstResult(), input.getPageSize());
        long count = dao.getCount();

        return new Output(count, imoveis);
    }


}
