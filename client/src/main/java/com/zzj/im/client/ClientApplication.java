package com.zzj.im.client;

import com.zzj.im.client.client.WebSocketClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class ClientApplication implements CommandLineRunner
{
    public static void main( String[] args )
    {
        new SpringApplication(ClientApplication.class).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.connect();
        System.out.println("等待输入命令: ");
        while(true){
            String command = readCommand();
            if(null!=command && command.equals("exit")){
                System.exit(-1);
                return ;
            }
        }
    }

    //读取命令
    private String readCommand(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    //连接服务器
    private void connect(){
        URI uri = URI.create("ws://localhost:8081/chat");
        WebSocketClient webSocketClient = new WebSocketClient(uri);
        webSocketClient.connect();
    }
}
