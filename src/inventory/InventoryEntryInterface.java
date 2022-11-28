package inventory;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;

public interface InventoryEntryInterface extends Remote {
  public ProductInterface getProduct() throws RemoteException;
  public void setProduct(ProductInterface product) throws RemoteException;
  public int getQtd() throws RemoteException;
  public void setQtd(int qtd) throws RemoteException;
  public Timestamp getAddedOn() throws RemoteException;
  public void setAddedOn(Timestamp addedOn) throws RemoteException;
  public Timestamp getLastModified() throws RemoteException;
  public void setLastModified(Timestamp lastModified) throws RemoteException;
}