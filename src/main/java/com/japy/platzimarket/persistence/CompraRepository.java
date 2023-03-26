package com.japy.platzimarket.persistence;

import com.japy.platzimarket.domain.Purchase;
import com.japy.platzimarket.domain.repository.PurchaseRepository;
import com.japy.platzimarket.persistence.crud.CompraCrudRepository;
import com.japy.platzimarket.persistence.entity.Compra;
import com.japy.platzimarket.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {

    private final CompraCrudRepository compraCrudRepository;
    private final PurchaseMapper purchaseMapper;

    @Autowired
    public CompraRepository(CompraCrudRepository compraCrudRepository, PurchaseMapper purchaseMapper) {
        this.compraCrudRepository = compraCrudRepository;
        this.purchaseMapper = purchaseMapper;
    }

    @Override
    public List<Purchase> getAll() {
        return purchaseMapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String cliendId) {
        return compraCrudRepository.findByIdCliente(cliendId)
                .map(purchaseMapper::toPurchases);//map(compras -> purchaseMapper.toPurchases(compras)
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = purchaseMapper.toCompra(purchase);
        //A cada producto de nuestra compra, le vamos a asignar a que compra pertenecen
        compra.getProductos().forEach(producto -> producto.setCompra(compra));
        return purchaseMapper.toPurchase(compraCrudRepository.save(compra));
    }
}
