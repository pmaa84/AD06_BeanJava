package jugadores.cliente;

import jugadores.bean.JugadoresBean;
import jugadores.bean.JugadoresBean.BDModificadaEvent;
import jugadores.bean.JugadoresBean.BDModificadaListener;

public class ClienteJugadores implements BDModificadaListener {

    JugadoresBean jugadores;

    ClienteJugadores() {
        try {
            jugadores = new JugadoresBean();
            jugadores.addBDModificadaListener((BDModificadaListener) this);
        } catch (Exception ex) {
            System.out.println("Error instanciando componente: " + ex.getMessage());
        }
    }

    public void consultarJugador(String nFicha) {
        jugadores.consultarJugador(nFicha);
        System.out.println("Apodo: " + jugadores.getApodo());
        System.out.println("Equipo: " + jugadores.getEquipo());
        System.out.println("Agente: " + jugadores.getAgente());
        System.out.println("Posicion: " + jugadores.getPosicion());
        System.out.println("Goles: " + jugadores.getGoles());
        System.out.println("");
    }

    public void insertarJugador(String nFicha, String nEquipo, String nAgente, String nApodo, String nPosicion) {
        jugadores.setFicha(nFicha);
        jugadores.setEquipo(nEquipo);
        jugadores.setAgente(nAgente);
        jugadores.setApodo(nApodo);
        jugadores.setPosicion(nPosicion);
        try {
            jugadores.insertarJugador();
        } catch (Exception ex) {
            System.out.println("Error insertando jugador" + ex.getMessage());
        }
    }

    public void incrementaGolesAjugador(String nFicha) {
        try {
            jugadores.incrementaGolesAjugador(nFicha);
        } catch (Exception ex) {
            System.out.println("Error incrementando goles de jugador" + ex.getMessage());
        }
    }

    public void borrarJugador(String nFicha) {
        try {
            jugadores.borrarJugador(nFicha);
        } catch (Exception ex) {
            System.out.println("Error borrando jugador" + ex.getMessage());
        }
    }

    public void capturarBDRegistroInsertado(BDModificadaEvent ev) {
        System.out.println("Se ha añadido un nuevo jugador.");
    }

    public void capturarBDRegistroModificado(BDModificadaEvent ev) {
        System.out.println("Se han incrementado los goles de un jugador.");
    }

    public void capturarBDRegistroBorrado(BDModificadaEvent ev) {
        System.out.println("Se ha borrado un jugador.");
    }

}
