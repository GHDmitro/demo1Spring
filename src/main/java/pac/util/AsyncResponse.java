package pac.util;

import java.util.concurrent.*;

/**
 * Created by macbookair on 23.11.16.
 */
public class AsyncResponse<V> implements Future<V> {


    private V value;
    private Exception executionExecption;
    private boolean isCompletedExceptionally = false;
    private boolean isCancelled = false;
    private boolean isDone = false;
    private long checkCompletedInterval = 100;

    public AsyncResponse() {
    }

    public AsyncResponse(V value) {
        this.value = value;
    }

    public AsyncResponse(Throwable tr){
        this.executionExecption = new ExecutionException(tr);
        this.isCompletedExceptionally = true;
        this.isDone = true;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        this.isCancelled = true;
        this.isDone = true;
        return false;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public boolean isDone() {
        return this.isDone;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        block(0);

        if (isCancelled){
            throw new CancellationException();
        }
        if (isCompletedExceptionally){
            throw new ExecutionException(executionExecption);
        }
        if (isDone){
            return this.value;
        }

        throw new InterruptedException();

    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {

        long timeoutInMillis = unit.toMillis(timeout);
        block(timeoutInMillis);
        if (isCancelled){
            throw new CancellationException();
        }
        if (isCompletedExceptionally){
            throw new ExecutionException(executionExecption);
        }
        if (isDone){
            return this.value;
        }

        throw new InterruptedException();
    }

    public boolean complete(V value){

        this.value = value;
        this.isDone = true;

        return true;
    }

    public boolean completeExceptionally(Throwable tr){

        this.value = null;
        this.executionExecption = new ExecutionException(tr);
        this.isCompletedExceptionally = true;
        this.isDone = true;

        return true;
    }

    public void setCheckCompletedInterval(long mills) {
        this.checkCompletedInterval = mills;
    }

    private void block(long timeout) throws InterruptedException{
        long start = System.currentTimeMillis();

        while (!isDone && !isCancelled){
            if (timeout > 0){
                long currTime = System.currentTimeMillis();
                if (currTime > (start + timeout)){
                    break;
                }
            }
            Thread.sleep(checkCompletedInterval);
        }
    }
}




















