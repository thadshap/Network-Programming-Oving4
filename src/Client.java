import java.io.IOException;
import java.net.*;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

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
                int firstNumber = parseInt(messageToSend);
                buffer = messageToSend.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 1234);
                datagramSocket.send(datagramPacket);
                datagramSocket.receive(datagramPacket);

                System.out.println("Enter a number:");
                messageToSend = scanner.nextLine();
                int secondNumber = parseInt(messageToSend);
                buffer = messageToSend.getBytes();
                datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 1234);
                datagramSocket.send(datagramPacket);
                datagramSocket.receive(datagramPacket);

                System.out.println("Write 'a' if you want to add or write 's' if you want to subtract: ");
                messageToSend = scanner.nextLine();
                buffer = messageToSend.getBytes();
                datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 1234);
                datagramSocket.send(datagramPacket);
                datagramSocket.receive(datagramPacket);
                String messageFromServer = new String(datagramPacket.getData(),0,datagramPacket.getLength());

                if(messageToSend.equals("a")){

                    System.out.println(firstNumber+" + "+secondNumber+" = "+messageFromServer);
                }
                else if(messageToSend.equals("s")){
                    System.out.println(firstNumber+" - "+secondNumber+" = "+messageFromServer);
                }
                else System.out.println("You have entered something wrong, please try again.");

                System.out.println("\nEnter a number:");
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
