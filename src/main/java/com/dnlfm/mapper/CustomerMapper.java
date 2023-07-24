package com.dnlfm.mapper;

import com.dnlfm.domain.Customer;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CustomerMapper {

    @Select("SELECT id, name, email FROM banking.customer")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
    })
    Customer allCustomers();

    @Select("SELECT id, name, email FROM banking.customer WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
    })
    Customer findById(@Param("id") long id);

    @Insert("INSERT INTO banking.customer (name, email) VALUES (#{name}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCustomer(Customer customer);

}
