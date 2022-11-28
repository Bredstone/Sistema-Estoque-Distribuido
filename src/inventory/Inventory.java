package inventory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Inventory extends UnicastRemoteObject implements InventoryInterface {
  List<InventoryEntryInterface> inventory;

  public Inventory() throws RemoteException {
    inventory = new ArrayList<InventoryEntryInterface>();
  }

  @Override
  public void addProduct(ProductInterface product, int qtd) throws RemoteException, Exception {
    InventoryEntryInterface entry = this.searchProductByID(product.getProductID());

    if (entry == null) {
      entry = new InventoryEntry(product, qtd, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
      inventory.add(entry);
    } else if (entry.getProduct().equals(product)) {
      int index = inventory.indexOf(entry);
      entry.setQtd(entry.getQtd() + qtd);
      entry.setLastModified(new Timestamp(System.currentTimeMillis()));

      inventory.set(index, entry);
    } else {
      throw new Exception("Código de produto já cadastrado!");
    }
  }

  @Override
  public void removeProduct(ProductInterface product, int qtd) throws RemoteException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void purgeProduct(ProductInterface product) throws RemoteException {
    // TODO Auto-generated method stub
    
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
    // return inventory.stream().filter(inventoryEntry -> 
    //   inventoryEntry.getProduct().getProductName().toUpperCase().contains(productName.toUpperCase()))
    //     .collect(Collectors.toList());
    return null;
  }

  @Override
  public List<InventoryEntryInterface> searchProductsByDescription(String productDescription) throws RemoteException {
    // return inventory.stream().filter(inventoryEntry -> 
    //   inventoryEntry.getProduct().getProductDescription().toUpperCase().contains(productDescription.toUpperCase()))
    //     .collect(Collectors.toList());
    return null;
  }

  @Override
  public void editProduct(int productID, ProductInterface product) throws RemoteException, Exception {
    if (product.getProductID() != productID)
      throw new Exception("O código do produto não pode ser alterado!");
    
    InventoryEntryInterface entry = this.searchProductByID(productID);

    if (entry == null) {
      throw new Exception("Código de produto não encontrado!");
    } else {
      int index = inventory.indexOf(entry);
      entry.setProduct(product);
      entry.setLastModified(new Timestamp(System.currentTimeMillis()));

      inventory.set(index, entry);
    }
  }
}
