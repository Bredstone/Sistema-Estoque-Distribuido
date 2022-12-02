package inventory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Product extends UnicastRemoteObject implements ProductInterface {
  private int productID;             // Código de identificação do produto
  private String productName;        // Nome do produto
  private String productDescription; // Descrição do produto
  private float productPrice;        // Valor unitário do produto
  private float productPriceDolar;        // Valor unitário do produto em dolar
  private float productPriceEuro;        // Valor unitário do produto em euro

  public Product(int productID, String productName, String productDescription, float productPrice, float productPriceDolar, float productPriceEuro) throws RemoteException {
    this.setProductID(productID);
    this.setProductName(productName);
    this.setProductDescription(productDescription);
    this.setProductPrice(productPrice);
    this.setProductPriceDolar(productPriceDolar);
    this.setProductPriceEuro(productPriceDolar);
  }

  public int getProductID() throws RemoteException {
    return this.productID;
  }

  public void setProductID(int productID) throws RemoteException {
    this.productID = productID;
  }

  public String getProductName() throws RemoteException {
    return this.productName;
  }

  public void setProductName(String productName) throws RemoteException {
    this.productName = productName;
  }

  public String getProductDescription() throws RemoteException {
    return this.productDescription;
  }

  public void setProductDescription(String productDescription) throws RemoteException {
    this.productDescription = productDescription;
  }

  public float getProductPrice() throws RemoteException {
    return this.productPrice;
  }

  public void setProductPrice(float productPrice) throws RemoteException {
    this.productPrice = productPrice;
  }

  public float getProductPriceDolar() throws RemoteException {
    return this.productPriceDolar;
  }

  public void setProductPriceDolar(float productPriceDolar) throws RemoteException {
    this.productPriceDolar = productPriceDolar;
  }

  public float getProductPriceEuro() throws RemoteException {
    return this.productPriceEuro;
  }

  public void setProductPriceEuro(float productPriceEuro) throws RemoteException {
    this.productPriceEuro = productPriceEuro;
  }
}
