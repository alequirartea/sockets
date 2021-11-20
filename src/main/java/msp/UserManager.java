package msp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

public class UserManager {

    public static final String CLASS_NAME = UserManager.class.getSimpleName();
    public static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private HashMap<String, Socket> connections;

    public UserManager() {
        super();
        connections = new  HashMap<String, Socket>();
    }

    public boolean connect(String user, Socket socket) {
        boolean result = true;

       Socket s = connections.put(user,socket);
       if( s != null) {
           result = false;
       }
       return result;
    }

    public void disconnect(String user, Socket socket) {
        connections.remove(user, socket);
    }

    public Socket get(String user) {

        Socket s = connections.get(user);

        return s;
    }

    public void send(String message, String username) {

        Collection<Socket> conexiones = connections.values();

                boolean users = connections.containsKey(username);

                if (users == true) {

                    PrintWriter output = null;
                    try {
                        output = new PrintWriter(get(username).getOutputStream(), true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    output.println(message);
                    }

                if (username.equalsIgnoreCase("all")) {
                     for (Socket s: conexiones) {
                         PrintWriter output = null;
                         try {
                             output = new PrintWriter(s.getOutputStream(), true);
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                         output.println(message);
                     }

                }
    }

    public void list() {
        System.out.println("USUARIOS: ");
        connections.forEach((k, v) -> {
            System.out.println(k);

        });
        System.out.println("------------");

    }





}
