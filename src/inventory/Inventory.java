package inventory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.net.URL;
import java.io.InputStream;

public class Inventory extends UnicastRemoteObject implements InventoryInterface {
  private List<InventoryEntryInterface> inventory;
  private int lastID;

  public Inventory() throws RemoteException {
    inventory = new ArrayList<InventoryEntryInterface>();
    lastID = 0;
  }

  private static String getJSONFromURL(String strUrl) {
    String jsonText = "";

    try {
      URL url = new URL(strUrl);
      InputStream is = url.openStream();

      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        jsonText += line + "\n";
      }

      is.close();
      bufferedReader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return jsonText;
  }

  public static Timestamp getDateTime() {
    try {
      String strJson = getJSONFromURL("http://worldtimeapi.org/api/timezone/America/Sao_Paulo");

      JSONParser parser = new JSONParser();
      Object object = parser.parse(strJson);
      JSONObject mainJsonObject = (JSONObject) object;

      return Timestamp.from(Instant.ofEpochSecond((Long) mainJsonObject.get("unixtime")));
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  @Override
  public synchronized InventoryEntryInterface addNewProduct(String productName, String productDescription, float productPrice, int qtd) throws RemoteException {
    Product product = new Product(++lastID, productName, productDescription, productPrice);
    Timestamp date = getDateTime();
    inventory.add(
        new InventoryEntry(product, qtd, date, date));

    return this.searchProductByID(lastID);
  }

  @Override
  public synchronized InventoryEntryInterface addProductQtd(int productID, int qtd)
      throws RemoteException, IllegalArgumentException {
    InventoryEntryInterface entry = this.searchProductByID(productID);

    if (entry == null)
      throw new IllegalArgumentException("Produto não encontrado!");

    int index = inventory.indexOf(entry);

    if (entry.getQtd() + qtd < 0)
      throw new IllegalArgumentException("Quantidade inválida!");

    entry.setQtd(entry.getQtd() + qtd);
    entry.setLastModified(getDateTime());

    inventory.set(index, entry);
    return entry;
  }

  @Override
  public synchronized InventoryEntryInterface removeProductQtd(int productID, int qtd)
      throws RemoteException, IllegalArgumentException {
    return this.addProductQtd(productID, -qtd);
  }

  @Override
  public synchronized void purgeProduct(int productID) throws RemoteException, IllegalArgumentException {
    InventoryEntryInterface entry = this.searchProductByID(productID);

    if (entry == null)
      throw new IllegalArgumentException("Produto não encontrado!");

    inventory.remove(entry);
  }

  @Override
  public synchronized InventoryEntryInterface searchProductByID(int productID) throws RemoteException {
    for (InventoryEntryInterface entry : inventory)
      if (entry.getProduct().getProductID() == productID)
        return entry;

    return null;
  }

  @Override
  public synchronized List<InventoryEntryInterface> searchProductsByName(String productName) throws RemoteException {
    List<InventoryEntryInterface> returnList = new ArrayList<InventoryEntryInterface>();
    for (InventoryEntryInterface entry : inventory)
      if (entry.getProduct().getProductName().toUpperCase().contains(productName.toUpperCase()))
        returnList.add(entry);

    return (returnList.size() == 0) ? null : returnList;
  }

  @Override
  public synchronized List<InventoryEntryInterface> searchProductsByDescription(String productDescription) throws RemoteException {
    List<InventoryEntryInterface> returnList = new ArrayList<InventoryEntryInterface>();
    for (InventoryEntryInterface entry : inventory)
      if (entry.getProduct().getProductDescription().toUpperCase().contains(productDescription.toUpperCase()))
        returnList.add(entry);

    return (returnList.size() == 0) ? null : returnList;
  }

  @Override
  public synchronized InventoryEntryInterface editProduct(int productID, String productName, String productDescription, float productPrice)
      throws RemoteException, IllegalArgumentException {
    Product product = new Product(productID, productName, productDescription, productPrice);
    InventoryEntryInterface entry = this.searchProductByID(productID);

    if (entry == null)
      throw new IllegalArgumentException("Produto não encontrado!");

    int index = inventory.indexOf(entry);
    entry.setProduct(product);
    entry.setLastModified(getDateTime());

    inventory.set(index, entry);
    return entry;
  }
}
