package inventory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Inventory extends UnicastRemoteObject implements InventoryInterface {
  private List<InventoryEntryInterface> inventory;
  private int lastID;

  public Inventory() throws RemoteException {
    inventory = new ArrayList<InventoryEntryInterface>();
    lastID = 0;
  }

  @Override
  public InventoryEntryInterface addNewProduct(ProductInterface product, int qtd) throws RemoteException {
    product.setProductID(++lastID);
    inventory.add(
      new InventoryEntry(product, qtd, 
      new Timestamp(System.currentTimeMillis()), 
      new Timestamp(System.currentTimeMillis())));
    
    return this.searchProductByID(lastID);
  }

  @Override
  public InventoryEntryInterface addProductQtd(int productID, int qtd) throws RemoteException, IllegalArgumentException {
    InventoryEntryInterface entry = this.searchProductByID(productID);
    
    if (entry == null)
      throw new IllegalArgumentException("Produto não encontrado!");

    int index = inventory.indexOf(entry);

    if (entry.getQtd() + qtd < 0)
      throw new IllegalArgumentException("Quantidade inválida!");

    entry.setQtd(entry.getQtd() + qtd);
    entry.setLastModified(new Timestamp(System.currentTimeMillis()));

    inventory.set(index, entry);
    return entry;
  }

  @Override
  public InventoryEntryInterface removeProductQtd(int productID, int qtd) throws RemoteException, IllegalArgumentException {
    return this.addProductQtd(productID, -qtd);
  }

  @Override
  public void purgeProduct(int productID) throws RemoteException, IllegalArgumentException {
    InventoryEntryInterface entry = this.searchProductByID(productID);

    if (entry == null)
      throw new IllegalArgumentException("Produto não encontrado!");
    
    inventory.remove(entry);
  }

  @Override
  public InventoryEntryInterface searchProductByID(int productID) throws RemoteException {
    for (InventoryEntryInterface entry : inventory)
      if (entry.getProduct().getProductID() == productID)
        return entry;
    
    return null;
  }

  @Override
  public List<InventoryEntryInterface> searchProductsByName(String productName) throws RemoteException {
    List<InventoryEntryInterface> returnList = new ArrayList<InventoryEntryInterface>();
    for (InventoryEntryInterface entry : inventory)
      if (entry.getProduct().getProductName().toUpperCase().contains(productName.toUpperCase()))
        returnList.add(entry);
    
    return (returnList.size() == 0) ? null : returnList;
  }

  @Override
  public List<InventoryEntryInterface> searchProductsByDescription(String productDescription) throws RemoteException {
    List<InventoryEntryInterface> returnList = new ArrayList<InventoryEntryInterface>();
    for (InventoryEntryInterface entry : inventory)
      if (entry.getProduct().getProductDescription().toUpperCase().contains(productDescription.toUpperCase()))
        returnList.add(entry);
    
    return (returnList.size() == 0) ? null : returnList;
  }

  @Override
  public InventoryEntryInterface editProduct(int productID, ProductInterface product) throws RemoteException, IllegalArgumentException {
    if (product.getProductID() != productID)
      throw new IllegalArgumentException("O código do produto não pode ser alterado!");
    
    InventoryEntryInterface entry = this.searchProductByID(productID);

    if (entry == null)
      throw new IllegalArgumentException("Produto não encontrado!");

    int index = inventory.indexOf(entry);
    entry.setProduct(product);
    entry.setLastModified(new Timestamp(System.currentTimeMillis()));

    inventory.set(index, entry);
    return entry;
  }
}
