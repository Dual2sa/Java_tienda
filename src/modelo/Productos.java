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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vista.FormClientes;

/**
 *
 * @author david
 */
public class Productos {

    private String nombre;
    private long  tiempo;
    private long Valor;

    public Productos() {
    }

    public Productos(String nombre, long Valor, long tiempo) {
        this.nombre = nombre;
        this.Valor = Valor;
        this.tiempo = tiempo;

    }

    

    public String getNombre() {
        return nombre;
    }

    public long getValor() {
        return Valor;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setValor(long Valor) {
        this.Valor = Valor;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }



}
