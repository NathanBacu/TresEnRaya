/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tresEnRaya;
import java.sql.Connection;

/**
 *
 * @author irlorth
 */
public class TestConexion {
    public static void main(String[] args) {
        try (Connection con = ConexionBD.obtenerConexion()) {
            if (con != null) {
                System.out.println("¡Conexión establecida con éxito!");
            }
        } catch (Exception e) {
            System.out.println("Error al conectar:");
            e.printStackTrace();
        }
    }
}
