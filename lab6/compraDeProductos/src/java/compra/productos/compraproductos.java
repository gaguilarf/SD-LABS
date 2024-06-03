/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package compra.productos;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author JOSUE
 */
@WebService(serviceName = "compraproductos")
public class compraproductos {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "comprasProductos")
    public String comprasProductos(@WebParam(name = "CantidadPan") int CantidadPan, @WebParam(name = "CantidadQueso") int CantidadQueso, @WebParam(name = "CantidadPlatanos") int CantidadPlatanos, @WebParam(name = "CantidadNaranjas") int CantidadNaranjas) {
        //TODO write your implementation code here:
        String message = "";
        double total = 0;
        if(CantidadPan<1||CantidadQueso<1||CantidadPlatanos<1||CantidadNaranjas<1){
            message += "Ingreso incorrecto de las cantidades de los productos";
        }else{
            message += "\n";
            total += CantidadPan*0.5;
            message+="Pan: "+CantidadPan+"Unidades --> Subtotal: "+CantidadPan*0.5;
            total += CantidadPan*2.5;
            message+="Queso: "+CantidadQueso+"Unidades --> Subtotal: "+CantidadQueso*0.5;
            total += CantidadPan*0.4;
            message+="Platanos: "+CantidadPlatanos+"Unidades --> Subtotal: "+CantidadPlatanos*0.5;
        }
        return message;
    }
}
