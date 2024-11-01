import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class TestLoad {
    public static void main(String[] args) {
        try{
            Apple a = new Apple(10);
            File f = new File("E:/a.data");
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(a);
            oos.flush();
            oos.close();
        }catch(Exception e){
            e.printStackTrace();
        }



    }
}
