package com.bladeDemo.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.*;

public class BlockingExecutor extends ThreadPoolExecutor {
    private static final Log log = LogFactory.getLog(BlockingExecutor.class);
    private final Semaphore semaphore;

    /**
     * Creates a BlockingExecutor which will block and prevent further submission to the pool when the specified queue
     * size has been reached.
     *
     * @param poolSize  the number of the threads in the pool
     * @param queueSize the size of the queue
     */
    public BlockingExecutor(final int poolSize, final int queueSize) {
        super(poolSize, poolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        // the semaphore is bounding both the number of tasks currently executing
        // and those queued up
        semaphore = new Semaphore(poolSize + queueSize);
    }

    /**
     * Executes the given task. This method will block when the semaphore has no permits i.e. when the queue has reached
     * its capacity.
     */
    @Override
    public void execute(final Runnable task) {
        boolean acquired = false;
        do {
            try {
                semaphore.acquire();
                acquired = true;
            } catch (final InterruptedException e) {
                log.warn("InterruptedException while acquiring the semaphore", e);
            }
        } while (!acquired);

        try {
            super.execute(task);
        } catch (final RejectedExecutionException e) {
            semaphore.release();
            throw e;
        }
    }

    /**
     * Method invoked upon completion of execution of the given Runnable, by the thread that executed the task. Releases
     * a semaphore permit.
     */
    @Override
    public void afterExecute(final Runnable r, final Throwable t) {
        super.afterExecute(r, t);
        semaphore.release();
    }
}
