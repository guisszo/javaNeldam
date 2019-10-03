package sn.neldamoney.neldam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.neldamoney.neldam.model.Compte;
import sn.neldamoney.neldam.model.Depot;

import java.util.Optional;

@Repository
public interface DepotRepository extends JpaRepository<Depot, Long> {



}
