package inventory;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InventoryEntryInterface extends Remote {
  public ProductInterface getProduct() throws RemoteException;
  public void setProduct(ProductInterface product) throws RemoteException;
  public int getQtd() throws RemoteException;
  public void setQtd(int qtd) throws RemoteException;
  public String getAddedOn() throws RemoteException;
  public void setAddedOn(String addedOn) throws RemoteException;
  public String getLastModified() throws RemoteException;
  public void setLastModified(String lastModified) throws RemoteException;
}