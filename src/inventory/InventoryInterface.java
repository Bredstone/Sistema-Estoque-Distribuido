package inventory;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InventoryInterface extends Remote {
  /** 
   * Adiciona um novo produto ao estoque.
   * 
   * @param product - Produto a ser adicionado
   * @param qtd - Quantidade a ser adicionada
   * @return Entrada do estoque recém cadastrada
   */
  public InventoryEntryInterface addNewProduct(ProductInterface product, int qtd) throws RemoteException;

  /** 
   * Acrescenta a quantidade de um produto no estoque.
   * 
   * @param productID - Código de identificação do produto
   * @param qtd - Quantidade a ser adicionada
   * @return Entrada do estoque recém modificada
   */
  public InventoryEntryInterface addProductQtd(int productID, int qtd) throws RemoteException, IllegalArgumentException;

  /** 
   * Remove a quantidade de um produto do estoque.
   * 
   * @param productID - Código de identificação do produto
   * @param qtd - Quantidade a ser removida
   * @return Entrada do estoque recém modificada
   */
  public InventoryEntryInterface removeProductQtd(int productID, int qtd) throws RemoteException, IllegalArgumentException;

  /** 
   * Remove todas as ocorrências de um produto do estoque, assim como seus dados.
   * 
   * @param productID - Código de identificação do produto
   */
  public void purgeProduct(int productID) throws RemoteException, IllegalArgumentException;

    /**
   * Busca um produto pelo código identificador.
   * 
   * @param productID - Código de identificação do produto
   * @return Entrada do estoque, caso haja um item com o código correspondente ou {@code null}
   */
  public InventoryEntryInterface searchProductByID(int productID) throws RemoteException;

  /**
   * Busca um produto pelo nome.
   * 
   * @param productName - Nome do produto
   * @return Lista com as entradas do estoque correspondentes ou {@code null}
   */
  public List<InventoryEntryInterface> searchProductsByName(String productName) throws RemoteException;

  /**
   * Busca um produto pela descrição.
   * 
   * @param productDescription - Descrição do produto
   * @return Lista com as entradas do estoque correspondentes ou {@code null}
   */
  public List<InventoryEntryInterface> searchProductsByDescription(String productDescription) throws RemoteException;

  /** 
   * Edita as informações de um produto.
   * 
   * @param productID - Código de identificação do produto
   * @param product - Produto com as informações alteradas
   * @return Entrada do estoque recém modificada
   */
  public InventoryEntryInterface editProduct(int productID, ProductInterface product) throws RemoteException, IllegalArgumentException;
}