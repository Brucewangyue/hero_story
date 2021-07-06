package org.tinygame.herostory.async;

import org.tinygame.herostory.MainThreadProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class AsyncThreadProcessor {
    private AsyncThreadProcessor() {
    }

    static private ExecutorService es = Executors.newFixedThreadPool(8, r -> {
        Thread thread = new Thread(r);
        thread.setName("AsyncThreadProcessor");
        return thread;
    });

    static private AsyncThreadProcessor instance = new AsyncThreadProcessor();

    static public AsyncThreadProcessor getInstance() {
        return instance;
    }

    static public void process(AsyncOperation asyncOperation) {
        if (null == asyncOperation) return;

        es.submit(() -> {
            asyncOperation.doAsync();

            MainThreadProcessor.getInstance().process(asyncOperation::doFinishSync);
        });
    }
}
