package org.tinygame.herostory.async;

/**
 * 异步操作接口
 */
public interface AsyncOperation {
    /**
     * 耗时的IO操作使用异步
     */
    void doAsync();

    /**
     * IO操作结果使用同步
     * <p>
     * 默认实现兼容无结果IO操作
     */
    default void doFinishSync() {
    }
}
