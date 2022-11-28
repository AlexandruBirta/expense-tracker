package ro.unibuc.fmi.expensetracker.repository;

import ro.unibuc.fmi.expensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}