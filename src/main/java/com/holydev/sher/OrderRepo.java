package com.holydev.sher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT * FROM hack.orders", nativeQuery = true)
    List<Order> getAll();


    @Transactional
    @Query(value = "SELECT * FROM hack.orders ", nativeQuery = true)
    List<Order> getByBusyID();


    @Modifying
    @Transactional
    @Query(value = "UPDATE * FROM hack.orders SET status=:newStatus WHERE order_id=:id", nativeQuery = true)
    void updateOrder(@Param("newStatus") int newStatus, @Param("id") int id);
}
