package sn.neldamoney.neldam.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.neldamoney.neldam.model.User;
import sn.neldamoney.neldam.repository.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private User userConnecte; // declaration de la variable pour la récupération du user connécté

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
        this.userConnecte=user; // on initie l'utilisateur connecté
        return UserPrinciple.build(user);
    }

    public User getUserConnecte(){
        return userConnecte;
    }
}
