package sn.neldamoney.neldam.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(exclude = "partenaire")
@Table(name = "compte", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "numcompte"
        })
})
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min = 5, max = 20)
    private String numcompte;

    private  int solde;

    private LocalDateTime created_at;

    @JoinColumn(name = "partenaire_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonIgnoreProperties("comptes")
    private Partenaire partenaire;

    @OneToMany(mappedBy = "compte")
    @JsonIgnoreProperties("compte")
    public List<User> users;

    public Compte (){}

    public Compte (String numcompte, int solde, Partenaire partenaire){

        this.numcompte = numcompte;
        this.solde = solde;
        this.partenaire = partenaire;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumcompte() {
        return numcompte;
    }

    public void setNumcompte(String numcompte) {
        this.numcompte = numcompte;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Partenaire getPartenaire() {
        return partenaire;
    }

    public void setPartenaire(Partenaire partenaire) {
        this.partenaire = partenaire;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
