package com.unla.proveedorsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.unla.proveedorsys.model.ItemOrden;
import java.util.List;

@Repository
public interface ItemOrdenRepository extends JpaRepository<ItemOrden, Long> {
    
	List<ItemOrden> findByOrdenDeCompraId(Long ordenId);
    ItemOrden findByCodigoArticulo(String codigoArticulo);
}
