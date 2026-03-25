package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Connection connection = DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUser(),
                DBConfig.getPassword()
        );  Statement statement = connection.createStatement()) {

            System.out.println("Introduce la letra: ");
            String letra = sc.nextLine();

            //String para la consulta
            String sql = "SELECT * from empleado " +
                    "WHERE NOMBRE LIKE ?";

            //Prepared statement para introducir la letra
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%"+letra+"%");

            ResultSet resultSet = preparedStatement.executeQuery();

            //Recorre todos los nombres e imprime los que cumplan la condición
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                double salario = resultSet.getDouble("salario");
                System.out.println("\nID: " + id);
                System.out.println("Nombre: " + nombre);
                System.out.println("Salario: " + salario);
            }
        } catch (SQLException e){
            System.out.println("ERROR --> "+e.getMessage());
        }
    }
}
