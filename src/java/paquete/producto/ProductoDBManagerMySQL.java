

package paquete.producto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego Sanchez Morillo
 * @version 0.1.0
 * @date 2014-02-26
 */
public class ProductoDBManagerMySQL {

    private static Connection connection;

        public static void connect(String databaseServer, String databaseName, String databaseUser, String databasePassword) {
        String strConection = "jdbc:mysql://" + databaseServer + "/" + databaseName;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(strConection, databaseUser, databasePassword);
            createTables();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1049) {
                Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                        "Database not found: " + strConection + " - " + databaseUser + "/" + databasePassword);
                createDatabase(databaseServer, databaseName, databaseUser, databasePassword);
                Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                        "Database created");
                try {
                    connection = DriverManager.getConnection(strConection, databaseUser, databasePassword); 
                    createTables();
                } catch (SQLException ex1) {
                    Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean isConnected() {
        if(connection!=null) {
            return true;
        } else {
            return false;
        }
    }

    private static void createDatabase(String databaseServer, String databaseName, String databaseUser, String databasePassword) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String strConecction = "jdbc:mysql://" + databaseServer;
            connection = DriverManager.getConnection(strConecction, databaseUser, databasePassword);
            Statement statement = connection.createStatement();
            String sql = "CREATE DATABASE " + databaseName;
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void createTables() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS producto ("
                    + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(50), "
                    + "descripcion VARCHAR(100), "
                    + "precio VARCHAR(50), "
                    + "imagen VARCHAR(50))";
            Statement statement = connection.createStatement();
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertProducto(Producto producto) {
     

        try {
            String sql = "INSERT INTO producto "
                    //No se incluye el ID, ya que es autonumérico
                    + "(nombre, descripcion, precio, imagen) "
                    + "VALUES ("
                    + "'" + producto.getNombre() + "', "
                    + "'" + producto.getDescripcion() + "', "
                    + "'" + producto.getPrecio()+ "', "
                    + "'" + producto.getImagen() + "')";
            Statement statement = connection.createStatement();
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Producto> getProductosList() {
        ArrayList<Producto> productosList = new ArrayList();
        try {
            String sql = "SELECT * FROM producto";
            Statement statement = connection.createStatement();
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            ResultSet rs = statement.executeQuery(sql);
            boolean result = rs.isBeforeFirst();
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
            while (rs.next()) {
                int columnIndex = 1;
                int id = rs.getInt(columnIndex++);
                String nombre = rs.getString(columnIndex++);
                String descripcion = rs.getString(columnIndex++);
                String precio = rs.getString(columnIndex++); 
                String imagen = rs.getString(columnIndex++);
                            
                Producto producto = new Producto(id, nombre, descripcion, precio, imagen);
                productosList.add(producto);
            }
            return productosList;
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Producto getProductoByID(int productoId) {
        Producto producto = null;
        try {
            String sql = "SELECT * FROM producto WHERE id=" + productoId;
            Statement statement = connection.createStatement();
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            ResultSet rs = statement.executeQuery(sql);
            boolean result = rs.isBeforeFirst();
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
            if (rs.next()) {
                int columnIndex = 1;
                int id = rs.getInt(columnIndex++);
                String nombre = rs.getString(columnIndex++);
                String descripcion = rs.getString(columnIndex++);
                String precio = rs.getString(columnIndex++);
                String imagen = rs.getString(columnIndex++);  
                producto = new Producto(id, nombre, descripcion, precio, imagen);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return producto;
    }

    public static void updateProducto(Producto producto) {
   
        try {
            String sql = "UPDATE producto SET "
                    + "nombre='" + producto.getNombre() + "', "
                    + "descripcion='" + producto.getDescripcion() + "', "
                    + "precio='" + producto.getPrecio() + "', "
                    + "imagen='" + producto.getImagen() + "', "
                    + "WHERE id=" + producto.getId();
            Statement statement = connection.createStatement();
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteProductoById(String id) {
        try {
            String sql = "DELETE FROM producto WHERE id=" + id;
            Statement statement = connection.createStatement();
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
