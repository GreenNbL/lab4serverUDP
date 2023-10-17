package org.example;
import java.net.*;
import java.io.*;

import static java.lang.Math.*;

public class UDPServer {
    public final static int DEFAULT_PORT = 8001;//определение порта сервера
    int x,y,z;
    double solution;
    DatagramSocket s = null;
    byte[] buf = new byte[512];//буфер для приема/передачи дейтаграммы
    private void GetData(DatagramSocket s ,DatagramPacket recvPacket) throws IOException {
        s.receive(recvPacket);//помещение полученного содержимого в объект дейтаграммы
        String cmd = new String(recvPacket.getData()).trim();//извлечение команды из пакета
        System.out.println("x= " + cmd+";");
        x=(int)Integer.parseInt(cmd);

        s.receive(recvPacket);//помещение полученного содержимого в объект дейтаграммы
        cmd = new String(recvPacket.getData()).trim();//извлечение команды из пакета
        System.out.println("y= " + cmd+";");
        y=(int)Integer.parseInt(cmd);

        s.receive(recvPacket);//помещение полученного содержимого в объект дейтаграммы
        cmd = new String(recvPacket.getData()).trim();//извлечение команды из пакета
        System.out.println("z= " + cmd+";");
        z=(int)Integer.parseInt(cmd);
    }
    private void SolveProblem()
    {
        solution=abs(cos((double)x)- pow(E,y))*(1+z+z*z/2+z*z*z/3);
        System.out.println("solution= " + solution+";");
        String string = String.valueOf(solution);
        buf = string .getBytes();
    }
    private void SendProblem(DatagramSocket s ,DatagramPacket sendPacket ) throws IOException {
        s.send(sendPacket);//послать сами данные
    }

    public void runServer() throws IOException {//метод сервера runServer
       //создание объекта DatagramSocket
        try {

            s = new DatagramSocket(DEFAULT_PORT);//привязка сокета к реальному объекту с портом DEFAULT_PORT
            System.out.println("UDPServer: Started on " + s.getLocalAddress() + ":"
                    + s.getLocalPort());//вывод к консоль сообщения
                DatagramPacket recvPacket = new DatagramPacket(buf, buf.length);//создание объекта дейтаграммы для получения данных
                GetData( s ,recvPacket);
                SolveProblem();
                DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, recvPacket.getAddress(), recvPacket.getPort());//формирование объекта
                SendProblem(s ,sendPacket );
            System.out.println("UDPServer: Stopped");
        }
        finally {
            if (s != null) {
                s.close();//закрытие сокета сервера
            }
        }
    }
    public static void main(String[] args) {//метод main
        try {
            UDPServer udpSvr = new UDPServer();//создание объекта udpSvr
            udpSvr.runServer();//вызов метода объекта runServer
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
