package br.com.consultoria.webscraping.repositoty;

import br.com.consultoria.webscraping.model.Imovel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImovelDAO1 {

    @Autowired
    private EntityManager em;

    private long count;

    public List<Imovel> get(Imovel imovel, int firstResult, int sizePage) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Imovel> cq = cb.createQuery(Imovel.class);

        Root<Imovel> from = cq.from(Imovel.class);

        List<Predicate> predicates = new ArrayList<>();

        if (imovel.getVagas() != null) {
            predicates.add(cb.equal(from.get("vagas"), imovel.getVagas()));
        }

        if (imovel.getSuites() != null) {
            predicates.add(cb.equal(from.get("suites"), imovel.getSuites()));
        }

        if (imovel.getAreaUtil() != null) {
            predicates.add(cb.ge(from.get("areaUtil"), imovel.getAreaUtil()));
        }

        if (imovel.getValorMetro() != null) {
            predicates.add(cb.ge(from.get("valor_metro"), imovel.getValorMetro()));
        }

        if (imovel.getElevador() != null) {
            predicates.add(cb.equal(from.get("elevador"), imovel.getElevador()));
        }

        if (imovel.getEndereco() != null) {
            predicates.add(cb.like(cb.lower(from.get("endereco")), "%" + imovel.getEndereco().toLowerCase() + "%"));
        }

        if (imovel.getDescricao() != null) {
            predicates.add(cb.like(cb.lower(from.get("descricao")), "%" + imovel.getDescricao() + "%"));
        }

        if (imovel.getQuartos() != null) {

            if (imovel.getQuartos() >= 4) {
                predicates.add(cb.ge(from.get("quartos"), 4));
            } else {
                predicates.add(cb.ge(from.get("quartos"), imovel.getQuartos()));
            }
        }

        CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
        cqCount.select(cb.count(cqCount.from(Imovel.class)));
        em.createQuery(cqCount);

        cqCount.where(predicates.toArray(new Predicate[0]));

        this.count = em.createQuery(cqCount).getSingleResult();


        cq.where(predicates.toArray(new Predicate[0]));


        CriteriaQuery<Imovel> select = cq.select(from);


        TypedQuery<Imovel> typedQuery = em.createQuery(select);

        typedQuery.setFirstResult((firstResult - 1) * sizePage);
        typedQuery.setMaxResults(sizePage);

        select.orderBy(cb.asc(from.get("id")));


        return typedQuery.getResultList();
    }

    public long getCount() {
        return count;
    }
}
