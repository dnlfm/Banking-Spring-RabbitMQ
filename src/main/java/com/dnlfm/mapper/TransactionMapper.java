package com.dnlfm.mapper;

import com.dnlfm.domain.AccountBalance;
import com.dnlfm.domain.Transaction;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TransactionMapper {

    @Select("SELECT * FROM banking.transaction WHERE fk_account_id = #{accountId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "accountBalance",
                    column = "{accountId=fk_account_id, currencyType=currency_type}",
                    one = @One(select = "com.dnlfm.mapper.AccountBalanceMapper.findByAccountIdAndCurrencyType")),
            @Result(property = "amount", column = "amount"),
            @Result(property = "transactionDirection", column = "direction"),
            @Result(property = "description", column = "description"),
            @Result(property = "createdAt", column = "created_at")
    })
    List<Transaction> findByAccountId(@Param("accountId") long accountId);


    @Insert("INSERT INTO banking.transaction (fk_account_id, currency_type, amount, direction, description) VALUES (#{accountBalance.account.id}, #{accountBalance.currencyType}::CURRENCY_ENUM, #{amount}, #{transactionDirection}::DIRECTION_ENUM, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertTransaction(Transaction transaction);

}
