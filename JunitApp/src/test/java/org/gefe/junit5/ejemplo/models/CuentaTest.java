package org.gefe.junit5.ejemplo.models;

import org.gefe.junit5.ejemplo.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Gime", new BigDecimal(1000.22));
        //cuenta.setPersona("Gime");
        String esperado = "Gime";
        String real = cuenta.getPersona();
        assertEquals(esperado, real,
                "el nombre de la cuenta no es el que se esperaba el nombre esperado es: " +esperado);
        assertTrue(real.equals("Gime"));

    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Gene", new BigDecimal("100.23"));

        //assertEquals(-100.23, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);


    }

    @Test
    void testReferenciaCuenta() {

        Cuenta cuenta = new Cuenta("John Taka", new BigDecimal("8000.40"));
        Cuenta cuenta2 = new Cuenta("John Taka", new BigDecimal("8000.40"));

        assertEquals(cuenta, cuenta2);

    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Andy", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));

        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());

    }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Andy", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));

        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());

    }


    @Test
    void testDineroInsuficienteException() {
        Cuenta cuenta = new Cuenta("Andy", new BigDecimal("1000.12345"));

        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero insuficiente";

        assertEquals(esperado, actual);
    }


    @Test
    void testTransferirDineroCuenta() {
        Cuenta cuenta1 = new Cuenta("Jhon Da", new BigDecimal("2000"));
        Cuenta cuenta2 = new Cuenta("Jhon recibe", new BigDecimal("2000"));

        Banco banco = new Banco();
        banco.setNombre("Banco del Este");
        banco.transferir(cuenta1, cuenta2, new BigDecimal(1000));

        assertEquals("1000", cuenta1.getSaldo().toPlainString());
        assertEquals("3000", cuenta2.getSaldo().toPlainString());

    }

    @Test
    void testRelacionBancoCuenta() {
        Cuenta cuenta1 = new Cuenta("Jhon Da", new BigDecimal("2000"));
        Cuenta cuenta2 = new Cuenta("Jhon Recibe", new BigDecimal("2000"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del Este");
        banco.transferir(cuenta1, cuenta2, new BigDecimal(1000));

        assertAll(
                ()->{assertEquals("1000", cuenta1.getSaldo().toPlainString());},
                ()->{assertEquals("3000", cuenta2.getSaldo().toPlainString());},
                ()->{assertEquals(2,banco.getCuentas().size());},
                ()->{assertEquals("Banco del Este", cuenta1.getBanco().getNombre());},
                ()->{assertEquals("Jhon Da",banco.getCuentas().stream().
                        filter(c -> c.getPersona().equals("Jhon Da")).
                        findFirst()
                        .get().getPersona());},
                ()->{assertTrue(banco.getCuentas().stream()
                        .filter(c -> c.getPersona().equals("Jhon Da"))
                        .findFirst().isPresent());},
                ()->{assertTrue(banco.getCuentas().stream()
                        .anyMatch(c -> c.getPersona().equals("Jhon Recibe") ));}
        );

    }
}