package mx.tecnm.backend.api.models;

import java.math.BigDecimal;

public record MetodosPago(int id, String nombre, BigDecimal comision) {
}
