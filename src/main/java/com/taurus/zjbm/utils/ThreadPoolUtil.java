package com.taurus.zjbm.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {

    private ThreadPoolUtil() {

    }

    /**
     * 创建默认池
     */
    public static ThreadPoolExecutor buildPool() {

        return new ThreadPoolExecutor(30, 60, 150, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }

    public static ThreadPoolExecutor buildPool(final int maxSize) {

        if (maxSize > 30) {
            return new ThreadPoolExecutor(30, maxSize, 150, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        } else {
            return new ThreadPoolExecutor(maxSize, maxSize + 1, 150, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(maxSize));
        }

    }

    /**
     *
     *                     final ThreadPoolExecutor executor1 = ThreadPoolUtil.buildPool(1);
     *                     try {
     *                         final Runnable task = new Runnable() {
     *                             @Override
     *                             public void run() {
     *                                // 业务逻辑执行
     *                             }
     *                         };
     *                         executor1.execute(task);
     *                     } catch (final Exception e) {
     *                         log.error(e.getMessage(), e);
     *                     }
     */

}
