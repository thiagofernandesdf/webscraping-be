package br.com.consultoria.webscraping.repositoty;

import br.com.consultoria.webscraping.model.Imovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ImovelRepository extends JpaRepository<Imovel, Long> , JpaSpecificationExecutor<Imovel> {


    //List<Imovel> findByName(String name);

    @Query("FROM Imovel i WHERE i.vagas = :vagas ")
    Page<Imovel> search(@Param("vagas") int vagas, Pageable pageable);

    @Query("FROM Imovel i WHERE LOWER(i.endereco) like %:endereco% ")
    Page<Imovel> buscarPorEndereco(@Param("endereco") String endereco, Pageable pageable);

    //@Query("FROM Imovel i WHERE i.link = :link ")
    //Page<Imovel> searchByLink(@Param("link") String link, Pageable pageable);
    @Query("FROM Imovel i WHERE i.link = :link ")
    Imovel searchByLink(@Param("link") String link);

}
