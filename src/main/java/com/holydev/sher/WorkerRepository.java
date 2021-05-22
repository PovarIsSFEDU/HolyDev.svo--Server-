package com.holydev.sher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE * FROM hack.workers SET position=:newPosition WHERE number=:number", nativeQuery = true)
    void updatePosition(@Param("newPosition") String newPosition, @Param("number") int number);

    @Query(value = "SELECT * FROM hack.employees", nativeQuery = true)
    List<Worker> getAll();


    @Transactional
    @Query(value = "SELECT * FROM hack.employees WHERE type=:type AND status=true", nativeQuery = true)
    List<Worker> getFreeByID(@Param("type") int type);

    @Transactional
    @Query(value = "SELECT * FROM hack.employees WHERE id=:id", nativeQuery = true)
    List<Worker> getByID(@Param("id") int type);


    @Transactional
    @Query(value = "SELECT * FROM hack.employees WHERE username=:user", nativeQuery = true)
    Worker getByUserName(@Param("user") String username);
}
