package org.example.repositories;


import org.example.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// Репозиторий для работы с сущностями, людьми.
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
