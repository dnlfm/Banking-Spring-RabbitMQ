package com.dnlfm.mapper;

import com.dnlfm.domain.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {

    @Select("SELECT * FROM banking.account WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "customer", column = "fk_customer_id",
                    one = @One(select = "com.dnlfm.mapper.CustomerMapper.findById")),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "country", column = "country"),
    })
    Account findById(@Param("id") long id);

    @Select("SELECT * FROM banking.account")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "customer", column = "fk_customer_id",
                    one = @One(select = "org.mybatis.banking.CustomerMapper.findById")),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "country", column = "country"),
    })
    List<Account> findAll();

    @Insert("INSERT INTO banking.account (fk_customer_id, country) VALUES (#{customer.id}, #{country})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertAccount(Account account);

}
