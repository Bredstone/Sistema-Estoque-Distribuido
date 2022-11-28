package inventory;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProductInterface extends Remote {
  public int getProductID() throws RemoteException;
  public void setProductID(int productID) throws RemoteException;
  public String getProductName() throws RemoteException;
  public void setProductName(String productName) throws RemoteException;
  public String getProductDescription() throws RemoteException;
  public void setProductDescription(String productDescription) throws RemoteException;
  public float getProductPrice() throws RemoteException;
  public void setProductPrice(float productPrice) throws RemoteException;
}