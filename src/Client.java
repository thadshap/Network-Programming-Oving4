import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private byte[] buffer;

    public Client(DatagramSocket datagramSocket, InetAddress inetAddress) {
        this.datagramSocket = datagramSocket;
        this.inetAddress = inetAddress;
    }

    public void sendThenReceive(){
        Scanner scanner = new Scanner(System.in);
        String messageToSend = scanner.nextLine();
        while (!messageToSend.equals("")){
            try{
                buffer = messageToSend.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 1234);
                datagramSocket.send(datagramPacket);
                datagramSocket.receive(datagramPacket);
                String messageFromServer = new String(datagramPacket.getData(),0,datagramPacket.getLength());
                System.out.println("The server says you said: "+ messageFromServer);
                messageToSend = scanner.nextLine();
            }catch (IOException e){
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName("localhost");
        Client client = new Client(datagramSocket,inetAddress);
        System.out.println("Hey, you have contact with the server!\n" +
                           "In this program you can choose to either add or subtract two numbers.\n"+
                           "You can quit this program by pressing the Enter button.\n"+
                           "Enter a number:"
        );
        client.sendThenReceive();
    }
}
