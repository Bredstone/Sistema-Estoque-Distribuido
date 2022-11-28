package inventory;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InventoryInterface extends Remote {
  /** 
   * Adiciona um produto ao estoque.
   * 
   * @param product - Produto a ser adicionado
   * @param qtd - Quantidade a ser adicionada
   */
  public void addProduct(ProductInterface product, int qtd) throws RemoteException, Exception;

  /** 
   * Remove um produto do estoque.
   * 
   * @param product - Produto a ser removido
   * @param qtd - Quantidade a ser removida
   */
  public void removeProduct(ProductInterface product, int qtd) throws RemoteException;

  /** 
   * Remove todas as ocorrências de um produto do estoque, assim como seus dados.
   * 
   * @param product - Produto a ser removido
   */
  public void purgeProduct(ProductInterface product) throws RemoteException;

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
   */
  public void editProduct(int productID, ProductInterface product) throws RemoteException, Exception;
}