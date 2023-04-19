package ro.unibuc.fmi.expensetracker.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.expensetracker.model.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByEmail(String email);

}