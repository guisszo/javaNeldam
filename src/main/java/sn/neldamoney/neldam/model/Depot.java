package sn.neldamoney.neldam.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(exclude = {"compte","users"})
public class Depot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private  int montant;

    @NotBlank
    private  int mtn_avant_depot;

    @NotBlank
    private Date created_at;

    @JoinColumn(name = "compte_id",referencedColumnName = "id")
    @ManyToOne(optional = false)
    //@JsonIgnoreProperties("depots")
    private Compte compte;

    @JoinColumn(name = "caissier_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    //@JsonIgnoreProperties("depots")
    private User caissier;

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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
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
