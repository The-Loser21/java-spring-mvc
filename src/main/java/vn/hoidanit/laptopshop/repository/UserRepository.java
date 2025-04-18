package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.User;

// crud: create, update, delete, insert
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    List<User> findAll();

    List<User> findAllByEmail(String email);

    User findById(long id);

    User deleteById(long id);

    boolean existsByEmail(String email);
}
