import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.util.InputMismatchException;
import java.util.List;
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
    System.out.println("\tPreço do produto: R$"   + String.format("%.2f", entry.getProduct().getProductPrice()));
    System.out.println("\tQuantidade: "           + entry.getQtd());
    System.out.println("\tAdicionado: "           + entry.getAddedOn());
    System.out.println("\tÚltima modificação: "   + entry.getLastModified());
    System.out.println("");
  }

  private void addNewProductForm() throws Exception {
    System.out.print("Código do produto: ");
    int productID = Integer.parseInt(in.nextLine());
    
    System.out.print("Nome do produto: ");
    String productName = in.nextLine();
    
    System.out.print("Descrição do produto: ");
    String productDescription = in.nextLine();

    System.out.print("Preço do produto: ");
    float productPrice = Float.parseFloat(in.nextLine());

    System.out.print("Quantidade no estoque: ");
    int qtd = Integer.parseInt(in.nextLine());

    inventory.addProduct(new Product(productID, productName, productDescription, productPrice), qtd);

    System.out.println("Item adicionado com sucesso!");
    this.printProductInfo(inventory.searchProductByID(productID));
  }

  private void addProductForm() throws Exception {
    System.out.print("Código do produto: ");
    int productID = Integer.parseInt(in.nextLine());

    System.out.print("Quantidade no estoque: ");
    int qtd = Integer.parseInt(in.nextLine());

    inventory.addProduct(inventory.searchProductByID(productID).getProduct(), qtd);

    System.out.println("Item adicionado com sucesso!");
    this.printProductInfo(inventory.searchProductByID(productID));
  }

  private void getProductByIdForm() throws RemoteException {
    System.out.print("Código do produto: ");
    InventoryEntryInterface entry = inventory.searchProductByID(Integer.parseInt(in.nextLine()));

    if (entry == null)
      System.out.println("Nenhum resultado encontrado!\n");
    else
      this.printProductInfo(entry);
  }

  private void getProductByNameForm() throws RemoteException {
    System.out.print("Nome do produto: ");
    List<InventoryEntryInterface> entryList = inventory.searchProductsByName(in.nextLine());

    if (entryList == null)
      System.out.println("Nenhum resultado encontrado!\n");
    else
      for (InventoryEntryInterface entry : entryList) 
        this.printProductInfo(entry);
  }

  private void getProductByDescriptionForm() throws RemoteException {
    System.out.print("Descrição do produto: ");
    List<InventoryEntryInterface> entryList = inventory.searchProductsByDescription(in.nextLine());

    if (entryList == null)
      System.out.println("Nenhum resultado encontrado!\n");
    else
      for (InventoryEntryInterface entry : entryList) 
        this.printProductInfo(entry);
  }

  public void execute() {
    String op;

    try {
      System.out.println("Entre com um dos comandos a seguir:");
      System.out.println("\t\tadd_new");
      System.out.println("\t\tadd");
      System.out.println("\t\tget_by_id");
      System.out.println("\t\tget_by_name");
      System.out.println("\t\tget_by_desc");
      System.out.println("\t\tsair");
      System.out.println("");

      menu_while:
      while (true) {
        op = in.nextLine();
        
        switch (op.toLowerCase()) {
          case "add_new":
            this.addNewProductForm();
            break;

          case "add":
            this.addProductForm();
            break;
          
          case "get_by_id":
            this.getProductByIdForm();
            break;
          
          case "get_by_name":
            this.getProductByNameForm();
            break;

          case "get_by_desc":
            this.getProductByDescriptionForm();
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