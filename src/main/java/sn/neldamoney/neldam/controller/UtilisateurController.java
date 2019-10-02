package sn.neldamoney.neldam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sn.neldamoney.neldam.model.Partenaire;
import sn.neldamoney.neldam.model.Role;
import sn.neldamoney.neldam.model.User;
import sn.neldamoney.neldam.model.UtilisateurForm;
import sn.neldamoney.neldam.repository.PartenaireRepository;
import sn.neldamoney.neldam.repository.RoleRepository;
import sn.neldamoney.neldam.repository.UserRepository;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UtilisateurController {
    private String etat;
    public UtilisateurController(){
        this.etat = "actif";

    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PartenaireRepository partenaireRepository;

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
     public User add(@RequestBody  UtilisateurForm  uform){

        //ajout d'un partenaire
        Partenaire p = new Partenaire(uform.getNinea(),uform.getRaisonsociale());
        p.setStatut(this.etat);
        partenaireRepository.save(p);
    //recuperation de l'ind du partenaire

           //ajout d'un utilisateur
           User u = new User(uform.getNomcomplet(),uform.getUsername(),uform.getEmail(),uform.getPassword(),
                   uform.getTel(),uform.getAdresse(),uform.getStatut(),uform.getImage_name());
           u.setPassword(passwordEncoder.encode(u.getPassword()));
           u.setCreated_at(LocalDateTime.now());
           u.setUpdated_at(LocalDateTime.now());
           u.setStatut(this.etat);
           Set<Role> roles = new HashSet<>();
           Role role= new Role();
           role.setId(uform.getRole());
           roles.add(role);
           u.setPartenaire(p);
           u.setRoles(roles);

           return userRepository.save(u);

    }


}
