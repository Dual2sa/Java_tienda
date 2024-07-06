package controlador;

import com.itextpdf.text.DocumentException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import modelo.*;
import vista.*;

/**
 *
 * @author David Santiago Aldana Pita
 */
public class Control implements ActionListener {

    JLabel cajero[] = new JLabel[3];
    JLabel[] fila = new JLabel[6];
    private FormSupermercado fmrSu;
    private Ventas frmVentas;
    private FormLogin fmrAd;
    private FormClientes frmCl;
    private Empleados objAd;
    private Productos objPro;
    private Cliente objcliente;
    private Cajero objCaje;
    
    private Supermercado forSuper;
    private ArrayList<Cliente> listaCli = new ArrayList();
    private ArrayList<Cajero> cajeros = new ArrayList<>();
    private ArrayList<Productos> carrito = new ArrayList(); //Carrito de compras
    private int id_pro = 0, bandC = 0, Time = 0, bandE = 0;

    private long Valor = 0;

    public Control() {
        frmVentas = new Ventas();
        objPro = new Productos();
        objcliente = new Cliente();
        forSuper = new Supermercado();
        fmrSu = new FormSupermercado();
        fmrAd = new FormLogin();
        frmCl = new FormClientes();
       
        this.fmrSu.getBtnGenerar();
        this.fmrAd.setVisible(true);
       this.forSuper.setVisible(true);
        this.forSuper.setLocationRelativeTo(null);
        this.forSuper.getjMenuEmpleados().addActionListener(this);
        this.forSuper.getjMenuCli().addActionListener(this);
        this.forSuper.getjMenuSuper().addActionListener(this);
        this.forSuper.getjMenuSalir().addActionListener(this);
        this.frmVentas.getBtnVentas().addActionListener(this);
        this.fmrAd.getBtnIngresarAD().addActionListener(this);

        this.frmCl.getBtnAdiccionar().setEnabled(false);
        this.frmCl.getBtnIngresar().addActionListener(this); //Ingredar cliente
        this.frmCl.getBtnAgregar().addActionListener(this); //Agregar al carrito
        this.frmCl.getBtnAgregar().setEnabled(false);
        this.frmCl.getBtnAdiccionar().addActionListener(this); //agregar cliente
        this.frmCl.getBrnFila().addActionListener(this);
        this.frmCl.getBrnFila().setEnabled(false);
        this.frmCl.getjComboProdu().setEnabled(false);

        this.fmrSu.getBtnEmpezar().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource() == forSuper.getjMenuEmpleados()) {

            this.fmrAd.setVisible(true);
            this.fmrAd.setLocationRelativeTo(null);
        }
        if (e.getSource() == forSuper.getjMenuCli()) {
            this.frmCl.setVisible(true);
            this.frmCl.setLocationRelativeTo(null);
        }
        if (e.getSource() == forSuper.getjMenuSuper()) {
            this.fmrSu.setVisible(true);
            this.fmrSu.setLocationRelativeTo(null);
        }

        if (e.getSource() == forSuper.getjMenuSalir()) {
            System.exit(0);
        }
        // -------- Boton ingresar Cliente
        if (e.getSource() == frmCl.getBtnIngresar()) {

            if (frmCl.getTxtNombre().getText().isEmpty() || frmCl.getTxtId().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Existen campos vacios ", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {

                String nom = frmCl.getTxtNombre().getText();
                String numero = frmCl.getTxtId().getText();
                if (!ValidarText(nom)) {
                    JOptionPane.showMessageDialog(null, "El Nombre esta mal escrito", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                if (!ValidarNumeros(numero)) {
                    JOptionPane.showMessageDialog(null, "El numero ingresado no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    objcliente = new Cliente();

                    objcliente.setNombre(frmCl.getTxtNombre().getText());
                    objcliente.setId(Long.parseLong(frmCl.getTxtId().getText()));

                    frmCl.getBtnAgregar().setEnabled(true);
                    frmCl.getjComboProdu().setEnabled(true);
                    frmCl.getBrnFila().setEnabled(true);

                }
            }

        }

        // ----------------------------- Agregar al carrito
        if (e.getSource() == frmCl.getBtnAgregar()) {
            this.frmCl.getBtnAdiccionar().setEnabled(true);

            Productos auxPro = new Productos();

            //Dependiendo el caso, es un producto diferente
            switch (frmCl.getjComboProdu().getSelectedIndex()) {

                case 0: {
                    this.frmCl.getTxtCarrito().setText(this.frmCl.getTxtCarrito().getText() + this.frmCl.getjComboProdu().getItemAt(0)
                            + " Valor " + this.frmCl.getListaPro().get(0).getValor() + "\n");
                    Time += this.frmCl.getListaPro().get(0).getTiempo();
                    Valor += this.frmCl.getListaPro().get(0).getValor();
                    this.id_pro = 0;
                    break;
                }

                case 1: {
                    this.frmCl.getTxtCarrito().setText(this.frmCl.getTxtCarrito().getText() + this.frmCl.getjComboProdu().getItemAt(1)
                            + " Valor " + this.frmCl.getListaPro().get(1).getValor() + "\n");
                    Time += this.frmCl.getListaPro().get(1).getTiempo();
                    Valor += this.frmCl.getListaPro().get(1).getValor();
                    this.id_pro = 1;
                    break;
                }

                case 2: {
                    this.frmCl.getTxtCarrito().setText(this.frmCl.getTxtCarrito().getText() + this.frmCl.getjComboProdu().getItemAt(2)
                            + " Valor " + this.frmCl.getListaPro().get(2).getValor() + "\n");
                    Time += this.frmCl.getListaPro().get(2).getTiempo();
                    Valor += this.frmCl.getListaPro().get(2).getValor();
                    this.id_pro = 2;
                    break;
                }

                case 3: {
                    this.frmCl.getTxtCarrito().setText(this.frmCl.getTxtCarrito().getText() + this.frmCl.getjComboProdu().getItemAt(3)
                            + " Valor " + this.frmCl.getListaPro().get(3).getValor() + "\n");
                    Time += this.frmCl.getListaPro().get(3).getTiempo();
                    Valor += this.frmCl.getListaPro().get(3).getValor();
                    this.id_pro = 3;
                    break;
                }

                case 4: {
                    this.frmCl.getTxtCarrito().setText(this.frmCl.getTxtCarrito().getText() + this.frmCl.getjComboProdu().getItemAt(4)
                            + " Valor " + this.frmCl.getListaPro().get(4).getValor() + "\n");
                    Time += this.frmCl.getListaPro().get(4).getTiempo();
                    Valor += this.frmCl.getListaPro().get(4).getValor();
                    this.id_pro = 4;
                    break;
                }

                case 5: {
                    this.frmCl.getTxtCarrito().setText(this.frmCl.getTxtCarrito().getText() + this.frmCl.getjComboProdu().getItemAt(5)
                            + " Valor " + this.frmCl.getListaPro().get(5).getValor() + "\n");
                    Time += this.frmCl.getListaPro().get(5).getTiempo();
                    Valor += this.frmCl.getListaPro().get(5).getValor();
                    this.id_pro = 5;
                    break;
                }

                case 6: {
                    this.frmCl.getTxtCarrito().setText(this.frmCl.getTxtCarrito().getText() + this.frmCl.getjComboProdu().getItemAt(6)
                            + " Valor " + this.frmCl.getListaPro().get(6).getValor() + "\n");
                    Time += this.frmCl.getListaPro().get(6).getTiempo();
                    Valor += this.frmCl.getListaPro().get(6).getValor();
                    this.id_pro = 6;
                    break;
                }

                case 7: {
                    this.frmCl.getTxtCarrito().setText(this.frmCl.getTxtCarrito().getText() + this.frmCl.getjComboProdu().getItemAt(7)
                            + " Valor " + this.frmCl.getListaPro().get(7).getValor() + "\n");
                    Time += this.frmCl.getListaPro().get(7).getTiempo();
                    Valor += this.frmCl.getListaPro().get(7).getValor();
                    this.id_pro = 7;
                    break;
                }

            }

            //Guardar datos del producto
            auxPro.setNombre(frmCl.getListaPro().get(this.id_pro).getNombre());
            auxPro.setTiempo(frmCl.getListaPro().get(this.id_pro).getTiempo());
            auxPro.setValor(frmCl.getListaPro().get(this.id_pro).getValor());

            //Añadir objeto al array del carrito de compras
            carrito.add(auxPro);

        }

        // ----------------------------- Adicionar cliente ------------------------
        if (e.getSource() == frmCl.getBtnAdiccionar()) {
            frmCl.getjComboProdu().setEnabled(false);
            frmCl.getBtnAgregar().setEnabled(false);
            this.frmCl.getBtnAdiccionar().setEnabled(false);
            if (frmCl.getTxtNombre().getText().length() != 0 && frmCl.getTxtId().getText().length() != 0) {
                
                
                Cliente auxCliente = new Cliente();
                auxCliente.setNombre(objcliente.getNombre());
                auxCliente.setId(objcliente.getId());
                auxCliente.setCarrito(carrito);
                auxCliente.setTime(Time);
                auxCliente.setValor(Valor);
                
                listaCli.add(auxCliente);
//mostrar();
                //System.out.println("\n");
                frmCl.getTxtNombre().setText("");
                frmCl.getTxtId().setText("");
                JOptionPane.showMessageDialog(null, "Cliente Añadido Correctamente\n");
                //Limpiar campos
                frmCl.getTxtNombre().setText("");
                frmCl.getTxtId().setText("");
                frmCl.getTxtCarrito().setText("");
                Time = 0;
                Valor = 0;

            } else {
                JOptionPane.showMessageDialog(null, "Error al añadir cliente\n");
            }
        }

        // --------------------- Ingresar datos ------------------------
        if (e.getSource() == fmrAd.getBtnIngresarAD()) {

            try {
                if (this.fmrAd.validar(this.fmrAd.getTxtNombreAD().getText(), this.fmrAd.getTxtCargo().getText(), this.fmrAd.getTxtContraseñaAd().getText())) {
                    JOptionPane.showMessageDialog(this.fmrAd, "Ingresando al sistema");

                    llenar();
                    Administracion();
                    this.fmrAd.getTxtNombreAD().setText("");
                    this.fmrAd.getTxtCargo().setText("");
                    this.fmrAd.getTxtContraseñaAd().setText("");

                } else {
                    JOptionPane.showMessageDialog(this.fmrAd, "Datos incorrectos intente de nuevo");
                }
            } catch (IOException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            }
            //llenar();
        }
        // -------------------------- Realizar fila --------------------------
        if (e.getSource() == frmCl.getBrnFila()) {

            frmCl.getTxtNombre().setText("");
            frmCl.getTxtId().setText("");
            frmCl.getTxtCarrito().setText("");

            llenarJlabels();

            fmrSu.setLocationRelativeTo(null);
            fmrSu.setVisible(true);

        }

        if (e.getSource() == this.fmrSu.getBtnEmpezar()) {
            long initialTime = System.currentTimeMillis();

            Cajero hilo = new Cajero(this.cajeros.get(0).getNombre(), this.listaCli.get(0), initialTime);
            Cajero hilo2 = new Cajero(this.cajeros.get(1).getNombre(), this.listaCli.get(1), initialTime);
            Cajero hilo3 = new Cajero(this.cajeros.get(2).getNombre(), this.listaCli.get(2), initialTime);
            vaciar();

            // this.objCaje.GenerarFactu(this.cajeros.get(0).getNombre(),this.listaCli.get(0));
            /*// this.objCaje.GenerarFactu(this.cajeros.get(2).getNombre(),this.listaCli.get(2));
             */
            hilo.start();
            hilo2.start();
            hilo3.start();

            fmrSu.getLblDispo1().setBackground(Color.red);
            fmrSu.getLblDispo2().setBackground(Color.red);
            fmrSu.getLblDispo3().setBackground(Color.red);
            this.listaCli.clear();

        }
        if (e.getSource() == this.fmrSu.getBtnGenerar()) {

        }
    }

    private void llenarJlabels() {

        fila[0] = this.fmrSu.getjLabel1();
        fila[1] = this.fmrSu.getjLabel2();
        fila[2] = this.fmrSu.getjLabel3();
        fila[3] = this.fmrSu.getjLabel4();
        fila[4] = this.fmrSu.getjLabel5();
        fila[5] = this.fmrSu.getjLabel6();
        for (int i = 0; i < listaCli.size(); i++) {
            fila[i].setText(this.listaCli.get(i).getNombre());
        }

    }

    public void llenar() { //llenar los label del formulario
        int band = 0; //bandera para saber si encontro un cajero

        //Hace el vector de cajeros con los label
        cajero[0] = this.fmrSu.getLblCajero1();
        cajero[1] = this.fmrSu.getLblCajero2();
        cajero[2] = this.fmrSu.getLblCajero3();

        for (int j = 0; j < this.fmrAd.getListaEmp().size(); j++) { //ciclo para recorrer la lista de empleados
            if (band == 0) {

                String aux = fmrAd.getTxtCargo().getText().toLowerCase();//asigna aux2

                int comp = aux.compareToIgnoreCase("cajero");//compara aux´s

                if (comp == 0) {//si son iguales entra
                    objCaje = new Cajero();
                    String name = fmrAd.getTxtNombreAD().getText();
                    String contra = fmrAd.getTxtContraseñaAd().getText();
                    cajero[bandC].setText(this.fmrAd.getTxtNombreAD().getText());
                    objCaje.setNombre(name);
                    objCaje.setCargo(aux);
                    objCaje.setContraseña(contra);
                    
                    this.fmrSu.getLblDispo1().setBackground(Color.green);
                    this.fmrSu.getLblDispo2().setBackground(Color.green);
                    this.fmrSu.getLblDispo3().setBackground(Color.green);
                    cajeros.add(objCaje);
                    band++;
                    bandC++;
                }
            }
        }
    }

    public void Administracion() { //llenar los label del formulario
        int banAd = 0; //bandera para saber si encontro un cajero

        for (int j = 0; j < this.fmrAd.getListaEmp().size(); j++) { //ciclo para recorrer la lista de empleados
            if (banAd == 0) {

                String aux = fmrAd.getTxtCargo().getText().toLowerCase();//asigna aux2

                int comp = aux.compareToIgnoreCase("gerente");//compara aux´s

                if (comp == 0) {//si son iguales entra
                    frmVentas.setVisible(true);
                    frmVentas.setLocationRelativeTo(null);
                    banAd++;

                }
            }
        }
    }

    public boolean ValidarNumeros(String datos) {

        return datos.matches("[0-9]*");
    }

    public boolean ValidarText(String datos) {

        return datos.matches("[a-z]*");
    }

    public void vaciar() {
        fila[0] = this.fmrSu.getjLabel1();
        fila[1] = this.fmrSu.getjLabel2();
        fila[2] = this.fmrSu.getjLabel3();
        fila[3] = this.fmrSu.getjLabel4();
        fila[4] = this.fmrSu.getjLabel5();
        fila[5] = this.fmrSu.getjLabel6();

        for (int i = 0; i < fila.length; i++) {
            fila[i].setText("");
        }
    }

    public ArrayList<Cliente> getListaCli() {
        return listaCli;
    }

    public void setListaCli(ArrayList<Cliente> listaCli) {
        this.listaCli = listaCli;
    }
   /* public static void connect(){
        String url="jdbc:mysql://localhost:3306/supermercadomulti";
        String user="root";
        String pass="";
        System.out.println(" ");
       /* try{
            
        }
    }*/

}
