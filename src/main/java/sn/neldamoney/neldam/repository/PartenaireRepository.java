package sn.neldamoney.neldam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.neldamoney.neldam.model.Partenaire;

import java.util.Optional;

@Repository
public interface PartenaireRepository extends JpaRepository<Partenaire,Long> {
        @Override
    Optional<Partenaire> findById(Long Long);
}
