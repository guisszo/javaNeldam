package sn.neldamoney.neldam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sn.neldamoney.neldam.model.*;
import sn.neldamoney.neldam.repository.CompteRepository;
import sn.neldamoney.neldam.repository.PartenaireRepository;
import sn.neldamoney.neldam.repository.RoleRepository;
import sn.neldamoney.neldam.repository.UserRepository;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UtilisateurController {

//    int genererInt(int Min, int Max){
//        Random random = new Random();
//        int Codegen;
//        Codegen = Min+random.nextInt(Max-Min);
//        return Codegen;
//    }
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
    @Autowired
    CompteRepository compteRepo;

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
     public User add(@RequestBody  UtilisateurForm  uform){

        /******************** Creation du Partenaire********************************/

        Partenaire p = new Partenaire(uform.getNinea(),uform.getRaisonsociale());
        p.setStatut(this.etat);
        partenaireRepository.save(p);
        /******************** Creation du Compte********************************/
        Compte c = new Compte(uform.getNumcompte(),uform.getSolde(),uform.getPartenaire());

        SimpleDateFormat formater = new SimpleDateFormat("yyyyMM-ddhhmmss");//210902 251763
        Date now=new Date();
        String codeGen=formater.format(now);
        //String Compte_rand= String.valueOf(genererInt((int) 100000,(int) 99999))+"codeGen";
        c.setNumcompte(codeGen);
        c.setSolde(0);
        c.setCreated_at(LocalDateTime.now());
        c.setPartenaire(p);
        compteRepo.save(c);
        /**#####################################################################**/
        /******************** Insertion de l'utilisateur ********************************/
           //Insertion de l'utilisateur
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
           u.setPartenaire(p);//on donne l'objet partenaire pour qu'il recupere l'Id
           u.setRoles(roles);

           return userRepository.save(u);

    }


}
