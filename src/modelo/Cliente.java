/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vista.FormClientes;
/**
 *
 * @author david
 */
public class Cliente {
    private Productos objPro;
    private ArrayList<Productos> listaPro = new ArrayList();
    private String Nombre;
    private long id;
    private int Time;
    private long Valor;
    private ArrayList<Productos> carrito = new ArrayList();
    
    
   
    private FormClientes objForm;
   
    public Cliente() {
    }
    public void Cliente2(){
        objPro= new Productos();
        Persistencia();
        
    }
    public ArrayList<Productos> getListaPro() {
        return listaPro;
    }

    public void setListaPro(ArrayList<Productos> listaPro) {
        this.listaPro = listaPro;
    }

    public Productos getObjPro() {
        return objPro;
    }

    public void setObjPro(Productos objPro) {
        this.objPro = objPro;
    }
   

    public Cliente(String Nombre, long id) {
        this.Nombre = Nombre;
        this.id = id;
    }

        
        public Cliente(String Nombre,long id, ArrayList carrito) {
        this.Nombre = Nombre;
        this.id = id;
        this.carrito = carrito;
        
    }

    public ArrayList<Productos> getCarrito() {
        return carrito;
    }

    public void setCarrito(ArrayList<Productos> carrito) {
        this.carrito = carrito;
    }
    
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public int getTime() {
        return Time;
    }

    public void setTime(int Time) {
        this.Time = Time;
    }

    public long getValor() {
        return Valor;
    }

    public void setValor(long Valor) {
        this.Valor = Valor;
    }
public ArrayList Persistencia(){
        ArrayList lista= new ArrayList();
        DataInputStream archivo;
        
        String cadena;
        try{
            
            archivo= new DataInputStream(new FileInputStream("Productos.txt"));
            while((cadena=archivo.readLine())!=null){
                objPro= new Productos();
                String campo[]= cadena.split(";");
                this.objPro.setNombre(campo[0]);
                this.objPro.setValor(Long.parseLong(campo[1]));
                this.objPro.setTiempo(Long.parseLong(campo[2]));
                this.listaPro.add(objPro);
                
                lista.add(campo[0]);
            }
            
        }catch(FileNotFoundException e){
           JOptionPane.showMessageDialog(null,"Error de E/S"+e.getMessage(),"Leer Archivo", JOptionPane.ERROR_MESSAGE );
        }catch(IOException e){
             Logger.getLogger(FormClientes.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
        
    }

}
