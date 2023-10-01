package ru.kovshov.cloud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kovshov.cloud.model.User;

@Repository
public interface TestH2Repository extends JpaRepository<User, Long> {
}
