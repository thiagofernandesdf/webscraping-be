package br.com.consultoria.webscraping.repositoty;

import br.com.consultoria.webscraping.model.Imovel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImovelDAO {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ImovelRepository repository;

    public Page<Imovel> getImoveisPorVagas(String searchTerm, int vargas) {

        PageRequest pageRequest = PageRequest.of(
                    1,
                    5,
                    Sort.Direction.ASC,
                searchTerm);



            repository.search(vargas, pageRequest).forEach(
                    i -> System.out.println("Endereço: "+ i.getEndereco() + " Vagas: " + i.getVagas())
            );

            return null;

    }

    public List<Imovel> getImoveis(Imovel imovel, int page, int size) {

        PageRequest pageRequest = PageRequest.of(
                1,
                10,
                    Sort.by("id"));

        ExampleMatcher matcher = ExampleMatcher

                .matchingAll()

                .withMatcher("endereco", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());


        repository.findAll(Example.of(imovel, matcher), pageRequest);

        return null;

    }

    public List<Imovel> getImoveis1(Imovel imovel, int page, int size) {

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.by("id"));

        ImovelSpecifications spec =
                new ImovelSpecifications(new SearchCriteria("valor", ">", 3000000));

        Page<Imovel> lista = repository.findAll(spec, pageRequest);

        lista.forEach( l -> log.info(l.toString()));

        return null;
    }

}
