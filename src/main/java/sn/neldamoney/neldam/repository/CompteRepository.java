package sn.neldamoney.neldam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.neldamoney.neldam.model.Compte;

import java.util.Optional;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {

    @Override
    Optional<Compte> findById(Long aLong);
}
