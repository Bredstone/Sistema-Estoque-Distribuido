import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

import inventory.InventoryEntryInterface;
import inventory.InventoryInterface;
import inventory.Product;

public class Client {
  Scanner in;
  InventoryInterface inventory;

  public Client() {
    in = new Scanner(System.in);

    try {
      inventory = (InventoryInterface) Naming.lookup("rmi://127.0.1.1/EstoqueDistribuído");
    } catch (RemoteException e) {
      System.out.println("\nRemoteException: " + e.toString());  
    } catch (NotBoundException e) {
      System.out.println("\nNotBoundException: " + e.toString());  
    } catch (Exception e) {
      System.out.println("\nException: " + e.toString());  
    }
  }

  private void printProductInfo(InventoryEntryInterface entry) throws RemoteException{
    System.out.println("\tCódigo do produto: "    + entry.getProduct().getProductID());
    System.out.println("\tNome do produto: "      + entry.getProduct().getProductName());
    System.out.println("\tDescrição do produto: " + entry.getProduct().getProductDescription());
    System.out.println("\tPreço do produto: "     + entry.getProduct().getProductPrice());
    System.out.println("\tQuantidade: "           + entry.getQtd());
    System.out.println("\tAdicionado: "           + entry.getAddedOn());
    System.out.println("\tÚltima modificação: "   + entry.getLastModified());
    System.out.println("");
  }

  public void execute() {
    String op, productName, productDescription;
    int productID, qtd;
    float productPrice;

    try {
      System.out.println("Entre com um dos comandos a seguir:");
      System.out.println("\t\tadd_new <cód> <nome> <descrição> <preço> <qtd>");
      System.out.println("\t\tadd <cód> <qtd>");
      System.out.println("\t\tget_id <cód>");
      System.out.println("\t\tsair");
      System.out.println("");

      menu_while:
      while (true) {
        op = in.next();
        
        switch (op.toLowerCase()) {
          case "add_new":
            productID = in.nextInt();
            productName = in.next();
            productDescription = in.next();
            productPrice = in.nextFloat();
            qtd = in.nextInt();
            inventory.addProduct(new Product(productID, productName, productDescription, productPrice), qtd);

            System.out.println("Item adicionado com sucesso!");
            this.printProductInfo(inventory.searchProductByID(productID));
            break;

          case "add":
            productID = in.nextInt();
            qtd = in.nextInt();
            inventory.addProduct(inventory.searchProductByID(productID).getProduct(), qtd);

            System.out.println("Item adicionado com sucesso!");
            this.printProductInfo(inventory.searchProductByID(productID));
            break;
          
          case "get_id":
            productID = in.nextInt();

            this.printProductInfo(inventory.searchProductByID(productID));
            break;

          case "sair":
            System.out.println("Saindo do programa...");
            break menu_while;

          default:
            System.out.println("Opção Inválida!");
            break;
        }
      }
		
      in.close();
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Índice fora dos limites!");
    } catch (InputMismatchException e) {
      System.out.println("Valor inválido!");
    } catch (Exception e) {		
      System.out.println("Exception: " + e.toString()); 
    }
  }

  public static void main(String[] args) {
    Client c = new Client();
    c.execute();
  }
}