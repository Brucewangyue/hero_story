package org.tinygame.herostory.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tinygame.herostory.dao.AccountDao;
import org.tinygame.herostory.entity.Account;
import org.tinygame.herostory.utils.DbHelper;

public class AccountService {
    static private Logger logger = LogManager.getLogger();
    static private AccountService instance = new AccountService();

    private AccountService() {
    }

    static public AccountService getInstance() {
        return instance;
    }

    static public Account selectByUsername(String username) {
        try (SqlSession sqlSession = DbHelper.getSqlSession()) {
            return dao.selectByUsername(username);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
