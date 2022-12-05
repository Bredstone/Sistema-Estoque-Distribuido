import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
    System.out.println("\tPreço do produto: $"   + String.format("%.2f", entry.getProduct().getProductPrice()*Server.dolar));
    System.out.println("\tPreço do produto: €"   + String.format("%.2f", entry.getProduct().getProductPrice()*Server.euro));
    System.out.println("\tQuantidade: "           + entry.getQtd());
    System.out.println("\tAdicionado: "           + entry.getAddedOn());
    System.out.println("\tÚltima modificação: "   + entry.getLastModified() + "\n");
  }

  private void addNewProductForm() throws Exception {    
    System.out.print("Nome do produto: ");
    String productName = in.nextLine();
    
    System.out.print("Descrição do produto: ");
    String productDescription = in.nextLine();

    System.out.print("Preço do produto: R$");
    float productPrice = Float.parseFloat(in.nextLine().replace(",", "."));

    System.out.print("Quantidade no estoque: ");
    int qtd = Integer.parseInt(in.nextLine());

    InventoryEntryInterface entry = inventory.addNewProduct(
      new Product(0, productName, productDescription, productPrice), 
      qtd);

    System.out.println("Item adicionado com sucesso!");
    System.out.println("Código do produto: " + entry.getProduct().getProductID() + "\n");
  }

  private void addProductQtdForm() throws Exception {
    System.out.print("Código do produto: ");
    int productID = Integer.parseInt(in.nextLine());

    System.out.print("Quantidade adicionada: ");
    int qtd = Integer.parseInt(in.nextLine());

    InventoryEntryInterface entry = inventory.addProductQtd(productID, qtd);

    System.out.println("Item atualizado com sucesso!");
    System.out.println("Quantidade atual: " + entry.getQtd() + "\n");
  }

  private void rmProductQtdForm() throws Exception {
    System.out.print("Código do produto: ");
    int productID = Integer.parseInt(in.nextLine());

    System.out.print("Quantidade removida: ");
    int qtd = Integer.parseInt(in.nextLine());

    InventoryEntryInterface entry = inventory.removeProductQtd(productID, qtd);

    System.out.println("Item atualizado com sucesso!");
    System.out.println("Quantidade atual: " + entry.getQtd() + "\n");
  }

  private void purgeProductForm() throws Exception {
    System.out.print("Código do produto: ");
    int productID = Integer.parseInt(in.nextLine());

    System.out.print("Tem certeza que deseja remover este produto e todas as suas referências? (S/N): ");

    if (!in.nextLine().equalsIgnoreCase("S"))
      throw new IllegalArgumentException("Operação cancelada...");

    inventory.purgeProduct(productID);
    System.out.println("Item removido com sucesso!\n");
  }

  private void getProductByIdForm() throws Exception {
    System.out.print("Código do produto: ");
    InventoryEntryInterface entry = inventory.searchProductByID(Integer.parseInt(in.nextLine()));

    if (entry == null)
      throw new IllegalArgumentException("Nenhum resultado encontrado!");

    this.printProductInfo(entry);
  }

  private void getProductByNameForm() throws Exception {
    System.out.print("Nome do produto: ");
    List<InventoryEntryInterface> entryList = inventory.searchProductsByName(in.nextLine());

    if (entryList == null)
      throw new IllegalArgumentException("Nenhum resultado encontrado!");

    for (InventoryEntryInterface entry : entryList) 
      this.printProductInfo(entry);
  }

  private void getProductByDescriptionForm() throws Exception {
    System.out.print("Descrição do produto: ");
    List<InventoryEntryInterface> entryList = inventory.searchProductsByDescription(in.nextLine());

    if (entryList == null)
      throw new IllegalArgumentException("Nenhum resultado encontrado!");

    for (InventoryEntryInterface entry : entryList) 
      this.printProductInfo(entry);
  }

  private void editProductForm() throws Exception {    
    System.out.print("Código do produto: ");
    int productID = Integer.parseInt(in.nextLine());

    InventoryEntryInterface entry = inventory.searchProductByID(productID);

    if (entry == null)
      throw new IllegalArgumentException("Produto não encontrado!");

    System.out.println("Nome do produto: " + entry.getProduct().getProductName());
    System.out.print("Nome do produto (deixe em branco para manter): ");
    String productName = in.nextLine();
    
    System.out.println("Descrição do produto: " + entry.getProduct().getProductDescription());
    System.out.print("Descrição do produto (deixe em branco para manter): ");
    String productDescription = in.nextLine();

    System.out.print("Preço do produto: R$" + String.format("%.2f", entry.getProduct().getProductPrice()));
    System.out.print("Preço do produto: R$");
    float productPrice = Float.parseFloat(in.nextLine().replace(",", "."));

    inventory.editProduct(productID, 
      new Product(productID, 
        productName.isEmpty() ? entry.getProduct().getProductName() : productName, 
        productDescription.isEmpty() ? entry.getProduct().getProductDescription() : productDescription, 
        productPrice));

    System.out.println("Item modificado com sucesso!\n");
  }

  public void execute() {
    String op;

    System.out.println("Entre com um dos comandos a seguir:");
    System.out.println("\t1 - add_new\t\tAdicionar novo produto");
    System.out.println("\t2 - add_qtd\t\tAdicionar quantidade ao estoque");
    System.out.println("\t3 - rm_qtd\t\tRemover quantidade do estoque");
    System.out.println("\t4 - purge_prod\t\tRemover produto do estoque");
    System.out.println("\t5 - search_id\t\tBuscar pelo código");
    System.out.println("\t6 - search_name\t\tBuscar pelo nome");
    System.out.println("\t7 - search_desc\t\tBuscar pela descrição");
    System.out.println("\t8 - edit_prod\t\tEditar produto");
    System.out.println("\t9 - sair\n");

    menu_while:
    while (true) {
      try {
        System.out.print("Opção: ");
        op = in.nextLine();
        
        switch (op.toLowerCase()) {
          case "1":
          case "add_new":
          case "1 - add_new":
            this.addNewProductForm();
            break;

          case "2":
          case "add_qtd":
          case "2 - add_qtd":
            this.addProductQtdForm();
            break;

          case "3":
          case "rm_qtd":
          case "3 - rm_qtd":
            this.rmProductQtdForm();
            break;

          case "4":
          case "purge_prod":
          case "4 - purge_prod":
            this.purgeProductForm();
            break;
          
          case "5":
          case "search_id":
          case "5 - search_id":
            this.getProductByIdForm();
            break;
          
          case "6":
          case "search_name":
          case "6 - search_name":
            this.getProductByNameForm();
            break;

          case "7":
          case "search_desc":
          case "7 - search_desc":
            this.getProductByDescriptionForm();
            break; 

          case "8":
          case "edit_prod":
          case "8 - edit_prod":
            this.editProductForm();
            break; 

          case "9":
          case "sair":
          case "9 - sair":
            System.out.println("Saindo do programa...");
            break menu_while;

          default:
            System.out.println("Opção Inválida!\n");
            break;
        }
      } catch (NumberFormatException e) {
        System.out.println("Entrada inválida: " + e.getMessage() + "\n");
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage() + "\n");
      } catch (InputMismatchException e) {
        System.out.println("Valor inválido!\n");
      } catch (Exception e) {		
        System.out.println("Exception: " + e.toString()); 
      }
    }
  
    in.close();
    System.exit(0);
  }

  public static void main(String[] args) {
    Client c = new Client();
    c.execute();
  }
}