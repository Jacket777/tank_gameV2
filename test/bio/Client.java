package bio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception{
        Socket s = new Socket("localhost",8888);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                s.getOutputStream()));
        bw.write("hello server");
        bw.newLine();
        bw.flush();
       // bw.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                s.getInputStream()));
        String msg = reader.readLine();
        System.out.println(msg);
    }
}
