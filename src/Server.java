import java.net.URL;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.io.InputStream;              
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import inventory.Inventory;

public class Server {

  static float dolar;
  static float euro;

  public Server() {
    try {
      LocateRegistry.createRegistry(1099);
      Naming.rebind("EstoqueDistribuído", new Inventory());
    } catch (Exception e) {
      System.out.println("Nao registrou o objeto: " + e);
    }
  }
    
    public static String getJSONFromURL(String strUrl) {
        String jsonText = "";

        try {
            URL url = new URL(strUrl);
            InputStream is = url.openStream();

            BufferedReader bufferedReader = 
                            new BufferedReader(new InputStreamReader(is));
            
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


  public static void main(String[] args) {
    new Server();

        try {        

            String strJson = getJSONFromURL("https://economia.awesomeapi.com.br/json/last/USD-BRL");

            JSONParser parser = new JSONParser();
            Object object = parser.parse(strJson);
            JSONObject mainJsonObject = (JSONObject) object;

            JSONObject exchange = (JSONObject) mainJsonObject.get("USDBRL");

            float dolar = Float.valueOf((String) exchange.get("bid"));
            System.out.println("Dolar: " + dolar);


            strJson = getJSONFromURL("https://economia.awesomeapi.com.br/json/last/EUR-BRL");

            parser = new JSONParser();
            object = parser.parse(strJson);
            mainJsonObject = (JSONObject) object;

            exchange = (JSONObject) mainJsonObject.get("EURBRL");

            float euro = Float.valueOf((String) exchange.get("bid"));
            System.out.println("Euro: " + euro);

        } catch(Exception ex) {
          ex.printStackTrace();
    } 
         
  }
}

