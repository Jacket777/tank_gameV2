package callable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFuture {
    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Long> future = executorService.submit(new MyTask());
        long result = future.get();//该方法阻塞的，等待结果返回
        System.out.println(" result: "+result);
        System.out.println(" finished ");


    }
}
