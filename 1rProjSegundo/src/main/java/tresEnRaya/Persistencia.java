/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tresEnRaya;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author irlor
 */
public class Persistencia {
    
    public void registrarVictoria(String nombre) {
        String sql = "INSERT INTO Ranking (nombre, victorias) VALUES (?, 1) "
                   + "ON DUPLICATE KEY UPDATE victorias = victorias + 1";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar la victoria: " + e.getMessage());
        }
    }
    
    public ArrayList<String> obtenerRanking() {
        
        ArrayList<String> arrayVictorias = new ArrayList<>();
        String sql = "SELECT nombre, victorias FROM Ranking ORDER BY victorias DESC";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                arrayVictorias.add(rs.getString("nombre") + " - " + rs.getInt("victorias") + " victorias");
            }

        } catch (SQLException e) {
            System.err.println("Error al leer ranking: " + e.getMessage());
        }

        return arrayVictorias;
    }
    
}
