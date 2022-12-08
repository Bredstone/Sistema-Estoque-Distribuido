package inventory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class InventoryEntry extends UnicastRemoteObject implements InventoryEntryInterface {
  private ProductInterface product;        // Produto
  private int qtd;                // Quantidade de unidades
  private String addedOn;      // Data de inserção do produto
  private String lastModified; // Data da última modificação do produto

  public InventoryEntry(ProductInterface product, int qtd, String addedOn, String lastModified) throws RemoteException {
    this.product = product;
    this.qtd = qtd;
    this.addedOn = addedOn;
    this.lastModified = lastModified;
  }

  public ProductInterface getProduct() throws RemoteException {
    return this.product;
  }

  public void setProduct(ProductInterface product) throws RemoteException {
    this.product = product;
  }

  public int getQtd() throws RemoteException {
    return this.qtd;
  }

  public void setQtd(int qtd) throws RemoteException {
    this.qtd = qtd;
  }

  public String getAddedOn() throws RemoteException {
    return this.addedOn;
  }

  public void setAddedOn(String addedOn) throws RemoteException {
    this.addedOn = addedOn;
  }

  public String getLastModified() throws RemoteException {
    return this.lastModified;
  }

  public void setLastModified(String lastModified) throws RemoteException {
    this.lastModified = lastModified;
  }
}
