package callable;

import java.util.concurrent.FutureTask;

public class FutureTaskTest {
    public static void main(String[] args) throws Exception{
        FutureTask<Long>ft = new FutureTask<>(new MyTask());
        new Thread(ft).start();
        Long result = ft.get();
        System.out.println(result);
    }
}
