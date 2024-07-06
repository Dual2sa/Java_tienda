/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vista.FormClientes;
import vista.FormLogin;

/**
 *
 * @author david
 */
public class Empleados extends Thread {
    private String nombre, cargo, contraseña;
    
    private Empleados objEm;
    private FormLogin fmrLo;
    private ArrayList<Empleados> listaEmp = new ArrayList();
    public Empleados() {
    }
    

   /* private void Cargar(){
      
        try {
            Persistencia();
            validar(this.fmrLo.getTxtNombreAD().getText(), this.fmrLo.getTxtCargo().getText(),this.fmrLo.getTxtContraseñaAd().getText());
        } catch (IOException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
   

    public Empleados(String nombre, String cargo, String contraseña) {
        
        this.nombre = nombre;
        this.cargo = cargo;
        this.contraseña = contraseña;
    }
    

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    

 public ArrayList Persistencia() {
        ArrayList lista = new ArrayList();
        DataInputStream archivo;
        String cadena;

        try {
            archivo = new DataInputStream(new FileInputStream("Empleados.txt"));
            while ((cadena = archivo.readLine()) != null) {
                String campo[] = cadena.split(";");
                objEm = new Empleados();
                
                objEm.setNombre(campo[1]);
                objEm.setCargo(campo[2]);
                objEm.setContraseña(campo[3]);
                this.listaEmp.add(objEm);

            }

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error de E/S" + e.getMessage(), "Leer Archivo", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            Logger.getLogger(FormClientes.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }
    
    public boolean validar(String nombre, String cargo, String contraseña) throws FileNotFoundException, IOException {
        File archivo = new File("Empleados.txt");
        RandomAccessFile fichero = new RandomAccessFile(archivo, "rw");
        String cadena;
        if (fichero != null) {
            fichero.seek(0);
            while (fichero.getFilePointer() < fichero.length()) {
                cadena = fichero.readLine();

                String campo[] = cadena.split(";");
                if (nombre.equals(campo[0]) && cargo.equals(campo[1]) && contraseña.equals(campo[2])) {

                    fichero.close();
                    return true;
                }
            }
            return false;
        }
        return false;

    }
}
