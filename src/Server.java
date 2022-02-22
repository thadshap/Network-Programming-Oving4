import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import static java.lang.Integer.parseInt;

public class Server {
    private DatagramSocket datagramSocket;
    private byte[] buffer=new byte[256];

    public Server(DatagramSocket datagramSocket){
        this.datagramSocket = datagramSocket;
    }

    public void receiveThenSend(){
        while(true){
            try{
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                InetAddress inetAddress = datagramPacket.getAddress();
                int port = datagramPacket.getPort();
                String messageFromClient = new String (datagramPacket.getData(),0,datagramPacket.getLength());
                int firstNumber = parseInt(messageFromClient);
                System.out.println("First number from client: "+ messageFromClient);
                datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                datagramSocket.send(datagramPacket);

                datagramSocket.receive(datagramPacket);
                inetAddress = datagramPacket.getAddress();
                port = datagramPacket.getPort();
                messageFromClient = new String (datagramPacket.getData(),0,datagramPacket.getLength());
                int secondNumber = parseInt(messageFromClient);
                System.out.println("Second number from client: "+ messageFromClient);
                datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                datagramSocket.send(datagramPacket);

                datagramSocket.receive(datagramPacket);
                inetAddress = datagramPacket.getAddress();
                port = datagramPacket.getPort();
                messageFromClient = new String (datagramPacket.getData(),0,datagramPacket.getLength());
                System.out.println("Addition(a) or subtraction(s): "+ messageFromClient);

                String sendResult;
                if (messageFromClient.equals("a")){
                    int result = firstNumber+secondNumber;
                    sendResult=Integer.toString(result);
                    buffer = sendResult.getBytes();
                    datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                    datagramSocket.send(datagramPacket);
                }
                else if(messageFromClient.equals("s")){
                    int result = firstNumber-secondNumber;
                    sendResult=Integer.toString(result);
                    buffer = sendResult.getBytes();
                    datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                    datagramSocket.send(datagramPacket);
                }
                else System.out.println("The client has entered something wrong.");

            }catch (IOException e){
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String [] args) throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(1234);
        Server server = new Server(datagramSocket);
        server.receiveThenSend();
    }

    /**
     * import javax.script.ScriptEngine;
     * import javax.script.ScriptEngineManager;
     * import javax.script.ScriptException;
     *
     * public class test {
     *     public static void main(String[] args) throws ScriptException {
     *         ScriptEngineManager mgr = new ScriptEngineManager();
     *         ScriptEngine engine = mgr.getEngineByName("JavaScript");
     *         String foo = "40+2";
     *         System.out.println(engine.eval(foo));
     *
     *     }
     * }
     */
}
