package sn.neldamoney.neldam.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(exclude = {"users","comptes"})// On met ceci pour la serialisation dans les deux sens
@Table(name = "partenaire",uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "ninea"
        })
})
public class Partenaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min=3, max = 50)
    private String ninea;

    @NotBlank
    @Size(min=5, max = 50)
    private String raisonsociale;

    @NotBlank
    @Size(min=5, max = 6)
    private String statut;

    @OneToMany( mappedBy = "partenaire")
    @JsonIgnoreProperties("partenaire")//pour pouvoir pointer dans les deux sens
    public List<User> users;

    @OneToMany(mappedBy = "partenaire" )
    @JsonIgnoreProperties("partenaire")
    public  List<Compte> comptes;

    public Partenaire(){}

    public  Partenaire(String ninea,String raisonsociale){
        this.ninea = ninea;
        this.raisonsociale = raisonsociale;
    }

    public int getId() {
        return id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNinea() {
        return ninea;
    }

    public void setNinea(String ninea) {
        this.ninea = ninea;
    }

    public String getRaisonsociale() {
        return raisonsociale;
    }

    public void setRaisonsociale(String raisonsociale) {
        this.raisonsociale = raisonsociale;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }


}
