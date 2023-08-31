package ru.kovshov.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kovshov.cloud.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
