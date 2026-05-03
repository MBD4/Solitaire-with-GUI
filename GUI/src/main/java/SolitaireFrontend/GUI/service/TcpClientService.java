package SolitaireFrontend.GUI.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Service
public class TcpClientService {

    @Value("${c.backend.host}")
    private String host;

    @Value("${c.backend.port}")
    private int port;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    // Connects to the C server automatically when Spring Boot starts
    @PostConstruct
    public void connect() {
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Successfully connected to C backend at " + host + ":" + port);
        } catch (Exception e) {
            System.err.println("Failed to connect to C backend: " + e.getMessage());
        }
    }

    public String sendCommand(String command) {
        // Ensure we are connected before trying to send
        if (socket == null || socket.isClosed()) {
            connect();
        }

        try {
            // Send the command to the C server
            out.println(command);

            // Read and return the response
            return in.readLine();
        } catch (Exception e) {
            System.err.println("Error sending command: " + e.getMessage());
            return "{\"error\": \"Failed to communicate with the game server.\"}";
        }
    }

    // close the socket when the Spring application shuts down
    @PreDestroy
    public void disconnect() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
            System.out.println("Disconnected from C backend.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
