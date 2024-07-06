/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import com.itextpdf.text.pdf.PdfPTable;
import controlador.Control;

/**
 *
 * @author David Santiago Aldana Pita
 */
public class Cajero extends Empleados implements Runnable {

    private Cliente cliente;
    private Control objctrl;
    private long initialTime;
    private long ventasT=0;
    private String nombre,id;
    private int NoFactu = 0;
    private ArrayList<Cajero> cajeros = new ArrayList<>();

    public Cajero() {

    }

    public ArrayList<Cajero> getCajeros() {
        return cajeros;
    }

    public long getVentasT() {
        return ventasT;
    }

    public void setVentasT(long ventasT) {
        this.ventasT = ventasT;
    }

    public void setCajeros(ArrayList<Cajero> cajeros) {
        this.cajeros = cajeros;
    }

    public Cajero(String nombre, String cargo, String contraseña) {
        super(nombre, cargo, contraseña);

    }
    public Cajero(String id,String nombre, long VentasT){
        this.id=this.id;
        this.nombre= this.nombre;
        this.ventasT= ventasT;
        
    }

    public Cajero(String nombre, Cliente cliente, long initialTime) {
        this.nombre = nombre;
        this.cliente = cliente;
        this.initialTime = initialTime;

    }

    @Override
    public void run() {

        
            JOptionPane.showMessageDialog(null, "El cajero " + this.nombre + " Comienzo a procesar la compra del cliente " + this.cliente.getNombre()
                + " en el tiempo " + (System.currentTimeMillis() - this.initialTime)
                + " seg");
        this.esperarXsegundos(this.cliente.getTime());
        //GenerarFactu(nombre, cliente);
        JOptionPane.showMessageDialog(null, "El cajero " + this.nombre + " termino de procesar al cliente " + this.cliente.getNombre() + " en el tiempo " + (System.currentTimeMillis() - this.initialTime) / 1000 + " seg ");
       
       
    }

    public void esperarXsegundos(long segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void GenerarFactu(String nombre,Cliente cliente)  {
        try {
                    Cajero auxCajero= new Cajero();
                    NoFactu = NoFactu + 1;
                    Document documento = new Document();
                    FileOutputStream ficheroPdf = new FileOutputStream("Factura " +NoFactu+".pdf");
                    PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
                    documento.open();
                    documento.add(new Paragraph("Supermercado Multinivel", FontFactory.getFont("arial", 40, Font.ITALIC, BaseColor.BLUE)));
                    documento.add(new Paragraph("\n" + "Factura No " + NoFactu, FontFactory.getFont("arial", 15, Font.ITALIC, BaseColor.BLACK)));
                    documento.add(new Paragraph("Cajero " + this.nombre, FontFactory.getFont("arial", 15, Font.ITALIC, BaseColor.BLACK)));
                    documento.add(new Paragraph("Cliente " + this.cliente.getNombre(), FontFactory.getFont("arial", 15, Font.ITALIC, BaseColor.BLACK)));
                    documento.add(new Paragraph("\n"));
                    PdfPTable tabla = new PdfPTable(2);
                    PdfPCell producto = new PdfPCell(new Phrase("PRODUCTOS"));
                    PdfPCell valor = new PdfPCell(new Phrase("Valor"));
                    tabla.addCell(producto);
                    tabla.addCell(valor);
                    for (int k = 0; k < this.cliente.getCarrito().size(); k++) {

                        tabla.addCell(" " + this.cliente.getCarrito().get(k).getNombre());
                        tabla.addCell("$" + this.cliente.getCarrito().get(k).getValor() + "");

                    }
                    documento.add(tabla);
                    documento.add(new Paragraph("\n Valor Total $" + this.cliente.getValor(), FontFactory.getFont("arial", 20, Font.ITALIC, BaseColor.BLACK)));
                    documento.add(new Paragraph("\n --------------GRACIAS POR SU COMPRA--------------", FontFactory.getFont("arial", 22, Font.ITALIC, BaseColor.BLACK)));
                    documento.close();
             
                   ventasT=ventasT+this.cliente.getValor();
                   auxCajero.setNombre(nombre);
                   auxCajero.setVentasT(ventasT);
                   cajeros.add(auxCajero);

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error de E/S" + e.getMessage(), "Leer Archivo", JOptionPane.ERROR_MESSAGE);
        }
        catch (DocumentException e){
            JOptionPane.showMessageDialog(null, "Error de E/S" + e.getMessage(), "Leer Archivo", JOptionPane.ERROR_MESSAGE);
        } 
         

    }

}
