package jugadores.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class JugadoresBean implements Serializable {

    public JugadoresBean() throws Exception {
        recargarFilas();
    }

    protected String Ficha;

    public String getFicha() {
        return Ficha;
    }

    public void setFicha(String Ficha) {
        this.Ficha = Ficha;
    }

    protected String Equipo;

    public String getEquipo() {
        return Equipo;
    }

    public void setEquipo(String Equipo) {
        this.Equipo = Equipo;
    }

    protected String Agente;

    public String getAgente() {
        return Agente;
    }

    public void setAgente(String Agente) {
        this.Agente = Agente;
    }

    protected String Apodo;

    public String getApodo() {
        return Apodo;
    }

    public void setApodo(String Apodo) {
        this.Apodo = Apodo;
    }

    protected String Posicion;

    public String getPosicion() {
        return Posicion;
    }

    public void setPosicion(String Posicion) {
        this.Posicion = Posicion;
    }

    protected int Goles;

    public int getGoles() {
        return Goles;
    }

    public void setGoles(int Goles) {
        this.Goles = Goles;
    }

    private class Jugador {

        String Ficha;
        String Equipo;
        String Agente;
        String Apodo;
        String Posicion;
        int Goles;

        public Jugador() {
        }

        public Jugador(String nFicha, String nAgente, String nEquipo, String nApodo, String nPosicion, int nGoles) {
            this.Ficha = nFicha;
            this.Equipo = nEquipo;
            this.Agente = nAgente;
            this.Apodo = nApodo;
            this.Posicion = nPosicion;
            this.Goles = nGoles;
        }
    }

    private Connection getConnection() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //REVISAR CONEXIÓN
            return DriverManager.getConnection("jdbc:mysql://localhost/jugadores", "root", "");
        } catch (SQLException ex) {
            System.out.println("Error SQLException:" + ex.getMessage());
            throw ex;
        } catch (ClassNotFoundException ex) {
            System.out.println("Error ClassNotFoundException:" + ex.getMessage());
            throw ex;
        }
    }

    private List<Jugador> jugadores = new ArrayList<Jugador>();

    private void recargarFilas() throws Exception {
        //IMPLEMENTAR: Hace la consulta sobre la BBDD y almacena los resultados sobre un vector
        ResultSet rs = null;
        Connection con = getConnection();
        Statement s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM JUGADORES");
        while (rs.next()) {
            Jugador j = new Jugador(
                    rs.getString("FICHA"),
                    rs.getString("AGENTE"),
                    rs.getString("EQUIPO"),
                    rs.getString("APODO"),
                    rs.getString("POSICION"),
                    rs.getInt("GOLES"));
            jugadores.add(j);
        }

        if (!jugadores.isEmpty()) {
            Jugador j = jugadores.get(0);
            this.Ficha = j.Ficha;
            this.Equipo = j.Equipo;
            this.Apodo = j.Apodo;
            this.Posicion = j.Posicion;
            this.Goles = j.Goles;
            rs.close();
            con.close();
        }
    }

    /* Consultar un jugador por su ficha (nFicha)
     * y actualiza las propiedades del JugadoresBean  
     */
    public void consultarJugador(String nFicha) {
        //IMPLEMENTAR
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM JUGADORES WHERE FICHA = ?");
            ps.setString(1, nFicha);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                this.Ficha = rs.getString("FICHA");
                this.Equipo = rs.getString("EQUIPO");
                this.Agente = rs.getString("AGENTE");
                this.Apodo = rs.getString("APODO");
                this.Posicion = rs.getString("POSICION");
                this.Goles = rs.getInt("GOLES");
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error consultando jugador: " + e.getMessage());
        }

    }

    /* Inserta un nuevo jugadro en la bbdd con los datos actuales del
        * jugadoresBean 
     */
    public void insertarJugador() throws Exception {
        //IMPLEMENTAR
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO JUGADORES (FICHA, EQUIPO, AGENTE, APODO, POSICION, GOLES) VALUES (?, ?, ?, ?, ?, 0)");
        ps.setString(1, this.Ficha);
        ps.setString(2, this.Equipo);
        ps.setString(3, this.Agente);
        ps.setString(4, this.Apodo);
        ps.setString(5, this.Posicion);
        // inserta con 0 goles por defecto
        int inserted = ps.executeUpdate();

        if (inserted == 1) {
            recargarFilas();
            receptor.capturarBDRegistroInsertado(new BDModificadaEvent(this));
        }

        con.close();
    }

    public void incrementaGolesAjugador(String nFicha) throws Exception {
        //IMPLEMENTAR
        Connection con = getConnection();
        PreparedStatement select = con.prepareStatement("SELECT GOLES FROM JUGADORES WHERE FICHA = ?");
        select.setString(1, nFicha);
        ResultSet rs = select.executeQuery();

        if (rs.next()) {
            int golesActuales = rs.getInt("GOLES");
            int nuevosGoles = golesActuales + 1;

            PreparedStatement update = con.prepareStatement("UPDATE JUGADORES SET GOLES = ? WHERE FICHA = ?");
            update.setInt(1, nuevosGoles);
            update.setString(2, nFicha);

            int filasActualizadas = update.executeUpdate();

            if (filasActualizadas == 1) {
                recargarFilas();
                receptor.capturarBDRegistroModificado(new BDModificadaEvent(this));
            }
            update.close();
        } else {
            System.out.println("No se encontró el jugador con ficha: " + nFicha);
        }

        rs.close();
        select.close();
        con.close();
    }

    public void borrarJugador(String nFicha) throws Exception {
        //IMPLEMENTAR
        Connection con = getConnection();
        PreparedStatement s = con.prepareStatement("DELETE FROM JUGADORES WHERE FICHA = ?");
        s.setString(1, nFicha);
        int filasBorradas = s.executeUpdate();
        if (filasBorradas == 1) {
            receptor.capturarBDRegistroBorrado(new BDModificadaEvent(this));
        }
        con.close();
    }

    public class BDModificadaEvent extends java.util.EventObject {

        public BDModificadaEvent(Object source) {
            super(source);
        }
    }

    public interface BDModificadaListener extends EventListener {

        public void capturarBDRegistroInsertado(BDModificadaEvent ev);

        public void capturarBDRegistroModificado(BDModificadaEvent ev);

        public void capturarBDRegistroBorrado(BDModificadaEvent ev);
    }

    private BDModificadaListener receptor;

    public void addBDModificadaListener(BDModificadaListener receptor) {
        this.receptor = receptor;
    }

}
