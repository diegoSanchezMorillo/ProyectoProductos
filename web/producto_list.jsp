<%@page import="paquete.producto.Producto"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Lista de productos</h1>
        <table border="1">
            <tr>
                <th>Nombre</th>
                <th>Descripcion</th>
                <th>Precio</th>
             
            </tr>
        <% 
            ArrayList<Producto> productosList = (ArrayList)request.getAttribute("productosList"); 
            for(Producto producto: productosList) {
                out.println("<tr>");
                out.println("<td>"+producto.getNombre()+"</td>");
                out.println("<td>"+producto.getDescripcion()+"</td>");
                 out.println("<td>"+producto.getPrecio()+"</td>");
               
                
                //Enlace para editar el registro
                String editLink = "Main?action=E&id="+producto.getId();
                out.println("<td><a href='"+editLink+"'>Editar</td>");
                //Enlace para eliminar el registro con confirmación por parte del usuario
                String deleteLink = "Main?action=D&id="+producto.getId();
                String deleteConfirmText = "Confirme que desea eliminar el producto:\\n"+producto.getNombre()+" "+producto.getDescripcion();
                out.println("<td><a href='"+deleteLink+"' onCLick='return confirm(\""+deleteConfirmText+"\")'>Suprimir</td>");
                
                out.println("</tr>");
            }
        %>
        </table>
        <br>
        <form method="get" action="Main">
            <input type="hidden" name="action" value="I">
            <input type="submit" value="Nuevo Producto">
        </form>
        <form method="get" action="Main" target="_blank">
            <input type="hidden" name="action" value="X">
            <input type="submit" value="Exportar XML">
        </form>
    </body>
</html>
