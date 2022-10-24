package com.codeflex.springboot.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NamedParameterJdbcBookRepository extends JdbcProductRepository{
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

}
