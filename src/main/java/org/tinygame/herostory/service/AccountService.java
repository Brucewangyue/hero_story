package org.tinygame.herostory.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tinygame.herostory.async.AsyncOperation;
import org.tinygame.herostory.async.AsyncThreadProcessor;
import org.tinygame.herostory.dao.AccountDao;
import org.tinygame.herostory.entity.Account;
import org.tinygame.herostory.utils.DbHelper;

import java.util.function.Function;

public class AccountService {
    static private Logger logger = LogManager.getLogger();
    static private AccountService instance = new AccountService();

    private AccountService() {
    }

    static public AccountService getInstance() {
        return instance;
    }

    static public void selectByUsername(String username, Function<Account, Void> func) {
        AsyncOperation asyncOperation = new AsyncOperation() {
            private Account account;

            @Override
            public void doAsync() {
                System.out.println("线程处理耗时IO操作：" + Thread.currentThread().getName());
                try (SqlSession sqlSession = DbHelper.getSqlSession()) {
                    AccountDao dao = sqlSession.getMapper(AccountDao.class);
                    account = dao.selectByUsername(username);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            @Override
            public void doFinishSync() {
                func.apply(account);
            }
        };

        AsyncThreadProcessor.process(asyncOperation);
    }

}
