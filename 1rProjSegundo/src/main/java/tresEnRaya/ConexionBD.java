/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tresEnRaya;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author irlorth
 */
public class ConexionBD {

    // Ajusta el puerto (5432 para Postgres, 3306 para MySQL) y el nombre de tu base de datos
    private static final String URL = "jdbc:mysql://localhost:33062/TresEnRaya";
    private static final String USUARIO = "admin";
    private static final String PASSWORD = "super3";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
