package pl.rzeznicki.wypozyczalnia_aut_backend.security.model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.rzeznicki.wypozyczalnia_aut_backend.exception.ServiceException;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.UserEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ServiceException(500, "User Not Found with email: " + email));

    return UserDetailsImpl.build(user);
  }
}
