package com.kelonstudios.wms.location.entity;

public enum LocationStatus {
    AVAILABLE,   // libre para usar
    OCCUPIED,    // tiene stock
    BLOCKED,     // no usable (incidencia)
    RESERVED     // reservada para una operación
}