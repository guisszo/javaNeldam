package sn.neldamoney.neldam.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(exclude = {"compte","users"})
public class Depot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private  int montant;


    private  int mtn_avant_depot;

    private LocalDateTime created_at;

    @JoinColumn(name = "compte_id",referencedColumnName = "id")
    @ManyToOne(optional = false)
    //@JsonIgnoreProperties("depots")
    private Compte compte;

    @JoinColumn(name = "caissier_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    //@JsonIgnoreProperties("depots")
    private User caissier;

    public  Depot (){}

    public  Depot (int montant, int mtn_avant_depot){
        this.montant = montant;
        this.mtn_avant_depot = mtn_avant_depot;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public int getMtn_avant_depot() {
        return mtn_avant_depot;
    }

    public void setMtn_avant_depot(int mtn_avant_depot) {
        this.mtn_avant_depot = mtn_avant_depot;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public User getCaissier() {
        return caissier;
    }

    public void setCaissier(User caissier) {
        this.caissier = caissier;
    }
}
