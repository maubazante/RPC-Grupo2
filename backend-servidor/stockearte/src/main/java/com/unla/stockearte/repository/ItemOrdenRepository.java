package com.unla.stockearte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.stockearte.model.ItemOrden;

@Repository
public interface ItemOrdenRepository extends JpaRepository<ItemOrden, Long> {
}