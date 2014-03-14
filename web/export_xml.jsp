<?xml version="1.0" encoding="UTF-8"?>
<%-- La línea anterior debe ir siempre la primera si se genera un XML --%>

<%@page import="java.util.ArrayList"%>
<%@page import="paquete.producto.Producto"%>

<%-- Se informa que el contenido va a ser XML --%>
<%@page contentType="text/xml" pageEncoding="UTF-8"%>

<productos>
<% 
    ArrayList<Producto> productosList = (ArrayList)request.getAttribute("productosList"); 
    for(Producto producto: productosList) {
        out.println("<producto>");
        out.println("<id>"+producto.getId()+"</id>");
        out.println("<nombre>"+producto.getNombre()+"</nombre>");
        out.println("<descripcion>"+producto.getDescripcion()+"</descripcion>");
        out.println("<precio>"+producto.getPrecio()+"</precio>");
        out.println("<imagen>"+producto.getImagen()+"</imagen>");
        out.println("</producto>");
    }
%>
</productos>
