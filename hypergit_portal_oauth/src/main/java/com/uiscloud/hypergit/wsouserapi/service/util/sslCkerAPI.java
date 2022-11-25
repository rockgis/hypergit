package com.uiscloud.hypergit.wsouserapi.service.util;

import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.InputStream;
import java.io.OutputStream;
//출처: https://sailing-ship.tistory.com/5891 [어느 개발자의 블로그:티스토리]

public class sslCkerAPI {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: "+sslCkerAPI.class.getName()+" <host> <port>");
            System.exit(1);
        }
        try {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(args[0], Integer.parseInt(args[1]));
            //sslsocket.setEnabledProtocols(new String[] {"TLSv1.2"}); // TLS설정 : TLSv1, TLSv1.1, TLSv1.2

            SSLParameters sslparams = new SSLParameters();
            //sslparams.setProtocols(new String[] {"TLSv1.2"}); // TLS설정 : TLSv1, TLSv1.1, TLSv1.2
            sslparams.setEndpointIdentificationAlgorithm("HTTPS");
            sslsocket.setSSLParameters(sslparams);

            InputStream in = sslsocket.getInputStream();
            OutputStream out = sslsocket.getOutputStream();

            // Write a test byte to get a reaction :)
            out.write(1);

            while (in.available() > 0) {
                System.out.print(in.read());
            }
            System.out.println("Successfully connected");

        } catch (Exception exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }
}
