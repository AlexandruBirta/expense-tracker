package ro.unibuc.fmi.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.expensetracker.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}