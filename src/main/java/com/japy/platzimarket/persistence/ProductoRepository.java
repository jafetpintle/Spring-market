package com.japy.platzimarket.persistence;

import com.japy.platzimarket.domain.Category;
import com.japy.platzimarket.domain.Product;
import com.japy.platzimarket.domain.repository.ProductRepository;
import com.japy.platzimarket.persistence.crud.ProductoCrudRepository;
import com.japy.platzimarket.persistence.entity.Categoria;
import com.japy.platzimarket.persistence.entity.Producto;
import com.japy.platzimarket.persistence.mapper.ProductMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {
    private ProductoCrudRepository productoCrudRepository;
    private ProductMapper mapper;

    @Override
    public List<Product> getAll(){
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return mapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        //Optional of porque retornamos una lista y queremos usar un optional
        return Optional.of(mapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        //Como no tengo un mapper que me convierta de optinal, hay que implementar un map
        return productos.map(prods -> mapper.toProducts(prods));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findById(productId).map(producto -> mapper.toProduct(producto));
    }

    @Override
    public Product save(Product product) {
        Producto producto = mapper.toProducto(product);
        return mapper.toProduct(productoCrudRepository.save(producto));
    }


    @Override
    public void delete(int productId){
        productoCrudRepository.deleteById(productId);
    }


}
