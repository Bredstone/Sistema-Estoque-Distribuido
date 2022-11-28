import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import inventory.Inventory;

public class Server {
  public Server() {
    try {
      LocateRegistry.createRegistry(1099);
      Naming.rebind("EstoqueDistribuído", new Inventory());
    } catch (Exception e) {
      System.out.println("Nao registrou o objeto: " + e);
    }
  }

  public static void main(String[] args) {
    new Server();
  }
}
