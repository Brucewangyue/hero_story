package org.tinygame.herostory.dao;

import org.tinygame.herostory.entity.Account;

public interface AccountDao {
    Account selectByUsername(String userName);
}
