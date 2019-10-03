package sn.neldamoney.neldam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sn.neldamoney.neldam.model.*;
import sn.neldamoney.neldam.repository.*;
import sn.neldamoney.neldam.services.UserDetailsServiceImpl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
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
    UserDetailsServiceImpl userDetailsService;// On fait une sorte d'injection de dependance

    @Autowired
    PartenaireRepository partenaireRepository;
    @Autowired
    CompteRepository compteRepo;
    @Autowired
    DepotRepository depotRepository;


    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
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

        /******************** Insertion de l'utilisateur ********************************/

           User u = new User(uform.getNomcomplet(),uform.getUsername(),uform.getEmail(),uform.getPassword(),
                   uform.getTel(),uform.getAdresse(),uform.getStatut(),uform.getImage_name());
           u.setPassword(passwordEncoder.encode(u.getPassword()));
           u.setCreated_at(LocalDateTime.now());
           u.setUpdated_at(LocalDateTime.now());
           u.setStatut(this.etat);
           Set<Role> roles = new HashSet<>();
           Role role= new Role();
           role.setId((long) 4);
           roles.add(role);
           u.setCompte(c);// ici on lui passe l'entité Compte pour la récupération de l'id
           u.setPartenaire(p);//on donne l'objet partenaire pour qu'il recupere l'Id
           u.setRoles(roles);

           return userRepository.save(u);

    }

/*############################# Fin de l'insertion d'un utilisateur par un super_admin/admin ###########################*/

    /********************************** Insertion d'un utilisateur simple par le super_admin ********************************/

    @PostMapping(value = "/adduser", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_PARTENAIRE','ROLE_PARTENAIRE_ADMIN')")
    public String addUser(@RequestBody UtilisateurForm uform){
    User thisUser = userDetailsService.getUserConnecte();//On recupère ici l'utilisateur qui est connecté

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
        u.setCompte(thisUser.getCompte());// ici on lui passe l'entité Compte pour la récupération de l'id
        u.setPartenaire(thisUser.getPartenaire());//on donne l'objet partenaire pour qu'il recupere l'Id
        u.setRoles(roles);
        userRepository.save(u);

        return "utilisateur inséré";
    }

    /********************************** Insertion d'un depot par le caissier *********************************/

    @PostMapping(value = "/depot", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_CAISSIER')")
    public String depot(@RequestBody UtilisateurForm uform) throws Exception {
        User caissier = userDetailsService.getUserConnecte();

        Depot d = new Depot(uform.getMontant(),uform.getMtn_avant_depot());

        Compte c = compteRepo.findCompteByNumcompte(uform.getNumcompte()).orElseThrow(
                ()->new Exception("Ce compte n'existe pas !")
        );//recherche du numero de compte saisi
         if (uform.getMontant() < 75000){
             return "le montant doit etre supérieur ou égal à 75000 F";

         }

         //Mise à jour du solde du compte
         int soldec = c.getSolde();//recuperation du solde du resultat de recherche
            c.setSolde(soldec + uform.getMontant());
            compteRepo.save(c);
            //Insertion dans la table depot

            d.setMontant(uform.getMontant());
            d.setMtn_avant_depot(soldec);
            d.setCreated_at(LocalDateTime.now());
            d.setCaissier(caissier);
            d.setCompte(c);
            depotRepository.save(d);


        return "Le depot a bien été effectué";
    }

    /*############################# Fin de l'insertion d'un Depot ###########################*/

    /********************************** Bloquer/Debloquer un utilisateur *********************************/

    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PutMapping(value = "/update/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public  String update(@PathVariable(value = "id") int id) throws Exception {


       User util = userRepository.findById((long) id).orElseThrow(
               ()-> new Exception("cet utilisateur n'existe pas")
       );

       String stat = util.getPartenaire().getStatut();
       //note: Modifier cette fonction après récupération des roles
       if (stat .equals(this.etat) ){
           util.getPartenaire().setStatut("bloque");
           util.setStatut("bloque");
       }else {
           util.getPartenaire().setStatut(this.etat);
           util.setStatut(this.etat);
       }
       userRepository.save(util);

       return "Utilisateur mis a jour";
    }
}
