package org.gefe.junit5.ejemplo.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Banco {
    private String nombre;

    List<Cuenta> cuentas;

    public Banco () {
        cuentas = new ArrayList<>();
    }

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getNombre() {return nombre;}

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public void addCuenta(Cuenta cuenta){
        cuentas.add(cuenta);
        cuenta.setBanco(this);
    }
    public void transferir(Cuenta origen, Cuenta destino, BigDecimal monto){

        origen.debito(monto);
        destino.credito(monto);
    }

}
