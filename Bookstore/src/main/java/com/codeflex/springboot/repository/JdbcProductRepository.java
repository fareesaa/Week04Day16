package com.codeflex.springboot.repository;

import com.codeflex.springboot.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProductRepository implements ProductRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

/*    @Autowired
    LobHandler lobHandler;*/


    @Override
    public Product findById(long id) {
        return jdbcTemplate.queryForObject(
                "select * from books where id = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        (new Product(
                                rs.getString("name"),
                                rs.getInt("categoryid"),
                                rs.getDouble("price")
                        ))
        );
    }

    @Override
    public Product findByName(String name) {
        return null;
    }

    @Override
    public int saveProduct(Product product) {
        return jdbcTemplate.update(
                "insert into product (name,categoryid,price) values(?,?,?)",
                product.getName(), product.getCategoryId(),product.getPrice());
    }

    @Override
    public int updateProduct(Product product) {
        return jdbcTemplate.update(
                "insert into product (name,categoryid,price) values(?,?,?)",
                product.getName(), product.getCategoryId(),product.getPrice());
    }

    @Override
    public int deleteProductById(long id) {
        return jdbcTemplate.update(
                "delete from product where id = ?",
                id);
    }

    @Override
    public List<Product> findAllProducts() {
        return jdbcTemplate.query(
                "select * from product",
                (rs, rowNum) ->
                        new Product(
                                rs.getString("name"),
                                rs.getInt("categoryid"),
                                rs.getDouble("price")
                        )
        );
    }
    @Override
    public int deleteAllProducts() {
        return jdbcTemplate.update(
                "delete from product");
    }

    @Override
    public boolean isProductExist(Product product) {
        return false;
    }
}
