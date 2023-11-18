package com.example.ejercicio23.Configuraciones;

public class Transacciones {
    public static final String NameDatabase = "PM01DBE2.3";

    public static String tblFotografias = "fotografia";

    public static final String id = "id";
    public static final String descripcion = "descripcion";
    public static final String foto = "foto";

    public static final String CreateTableFotografia = "CREATE TABLE " + tblFotografias +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+"descripcion TEXT,foto BLOB)";

    public static final String DropTableFotografia = "DROP TABLE " + tblFotografias;
}
