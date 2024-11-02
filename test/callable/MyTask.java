package callable;

import java.util.concurrent.Callable;

public class MyTask implements Callable<Long> {

    @Override
    public Long call() throws Exception {
        long result  = 0L;
        for (long i = 0; i < 10L; i++) {
            result += i;
            Thread.sleep(500);
            System.out.println(i +" added!");

        }
        return result;
    }
}
