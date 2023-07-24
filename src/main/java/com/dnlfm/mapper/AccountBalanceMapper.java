package com.dnlfm.mapper;

import com.dnlfm.domain.AccountBalance;
import com.dnlfm.domain.CurrencyType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountBalanceMapper {

    @Select("SELECT * FROM banking.account_balance WHERE fk_account_id = #{accountId}")
    @Results({
            @Result(property = "account", column = "fk_account_id",
                    one = @One(select = "com.dnlfm.mapper.AccountMapper.findById")),
            @Result(property = "currencyType", column = "currency_type"),
            @Result(property = "availableAmount", column = "available_amount")
    })
    List<AccountBalance> findByAccountId(@Param("accountId") long accountId);

    @Select("SELECT * FROM banking.account_balance WHERE fk_account_id = #{accountId} AND currency_type = #{currencyType}::CURRENCY_ENUM")
    @Results({
            @Result(property = "account", column = "fk_account_id",
                    one = @One(select = "com.dnlfm.mapper.AccountMapper.findById")),
            @Result(property = "currencyType", column = "currency_type"),
            @Result(property = "availableAmount", column = "available_amount")
    })
    AccountBalance findByAccountIdAndCurrencyType(@Param("accountId") long accountId, @Param("currencyType") CurrencyType currencyType);

    @Insert("INSERT INTO banking.account_balance (fk_account_id, currency_type) VALUES (#{account.id},#{currencyType}::CURRENCY_ENUM)")
    int insertAccountBalance(AccountBalance accountBalance);

}
