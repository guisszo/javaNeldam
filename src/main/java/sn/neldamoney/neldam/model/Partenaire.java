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
@EqualsAndHashCode(exclude = "users")// On met ceci pour la serialisation dans les deux sens
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
    private int ninea;

    @NotBlank
    @Size(min=5, max = 50)
    private String raisonsociale;

    @OneToMany(mappedBy = "partenaire")
    @JsonIgnoreProperties("partenaire")//pour pouvoir pointer dans les deux sens
    public List<User> users;

    public Partenaire(){}

    public  Partenaire(int ninea,String raisonsociale){
        this.ninea = ninea;
        this.raisonsociale = raisonsociale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNinea() {
        return ninea;
    }

    public void setNinea(int ninea) {
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

    @NotBlank
    @Size(min=5, max = 6)
    private String statut;
}
