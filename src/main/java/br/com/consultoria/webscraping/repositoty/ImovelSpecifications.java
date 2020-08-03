package br.com.consultoria.webscraping.repositoty;

import br.com.consultoria.webscraping.model.Imovel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.MessageFormat;


public final class ImovelSpecifications implements Specification<Imovel> {


    public ImovelSpecifications(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate
            (Root<Imovel> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        Imovel i = new Imovel();
        i.setVagas(3);
        i.setSuites(2);

        //if(i.getVagas() > 0)
            Predicate vagaPredicate = builder.equal(root.get("vaga"), 0);

        if(i.getSuites() > 0) {
            Predicate suitesPredicate = builder.equal(root.get("suites"), 0);
        }

        return null;
    }


    public Predicate toPredicate1
            (Root<Imovel> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThan(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }

    public static Specification<Imovel> enderecoContais(String expression) {
        return (root, query, builder) -> builder.like(root.get("endereco"), contains(expression));
    }

    public static Specification<Imovel> lastNameContains(String expression) {
        return (root, query, builder) -> builder.like(root.get("lastName"), contains(expression));
    }

    private static String contains(String expression) {
        return MessageFormat.format("%{0}%", expression);
    }

    public static Specification<Imovel> get(Imovel imovel) {

        Specification<Imovel> specification = Specification
            .where(imovel.getEndereco() == null ? null : enderecoContais(imovel.getEndereco()));

        return specification;

    }

}
