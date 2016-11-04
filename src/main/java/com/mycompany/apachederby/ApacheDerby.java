/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.apachederby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author alumnoalumno
 */
public class ApacheDerby {
        public static void main(String[] args) {
        
        int opcion=0;
        Scanner sc = new Scanner(System.in);
        
        do{
            System.out.println("--------------------------------------");
            System.out.println("            Menú Principal");
            System.out.println("--------------------------------------");
            System.out.println("1. Lectura de Departamentos desde Apache Derby");
            System.out.println("2. Lectura de datos de Apellidos, Oficio y Salario de Empleados dep 30");
            System.out.println("3. Lectura de datos de Apellido del empleado con el máximo salario");
            System.out.println("0. Salir");
            System.out.print("Introduce la opción deseada: ");
            opcion = sc.nextInt();
            switch(opcion){
                case 1: {
                    leerDepartamentoFromApacheDerbyDatbase();
                    break;
                }
                case 2: {
                    leerEmpleadosFromApacheDerbyDatbase();
                    break;
                }
                case 3: {
                    leerEmpleadosDepartamentosFromApacheDerbyDatbase();
                    break;
                }
                default:{
                    break;
                }
                        

            }
            
            
        }while (opcion!=0);
        
        
    }
    
    private static void leerDepartamentoFromApacheDerbyDatbase(){
        
        try{
            /*
            Para acceder a una base de datos Derby necesitamos la librería derby-10.12.1.1.jar
            Dentro del paquete org.apache.derby.jdbc podremos encontrar la clase EmbeddedDriver
            En algunas ocasiones tenemos a nuestra disposición varios Drivers dependiendo si estamos
            utilizando la versión Embebida o como un servicio del sistema, tendremos que seleccionar la 
            clase (Driver) dependiendo de la instalación que hayamos realizado.
            */
            
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            
            Connection conexion= DriverManager.getConnection("jdbc:derby:C:/BD/ApacheDerby/ejemplo.db");
            
            Statement sentencia = conexion.createStatement();
            String sql = "SELECT * FROM departamentos";
            ResultSet result = sentencia.executeQuery(sql);
            
            while (result.next()){
                System.out.println("Id: "+result.getInt(1)+" Nombre: "+ result.getString(2)+" Localizacion: "+ result.getString(3));
                
            }
            result.close();
            sentencia.close();
            conexion.close();
        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    private static void leerEmpleadosFromApacheDerbyDatbase() {
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            
            Connection conexion= DriverManager.getConnection("jdbc:derby:C:/BD/ApacheDerby/ejemplo.db");
            
            Statement sentencia = conexion.createStatement();
            String sql = "SELECT apellido,oficio,salario FROM empleados WHERE dept_no=30";
            ResultSet result = sentencia.executeQuery(sql);
            
            while (result.next()){
                System.out.println("Apellido: "+result.getString(1)+" Oficio: "+ result.getString(2)+" Salario: "+ result.getDouble(3));
                
            }
            result.close();
            sentencia.close();
            conexion.close();
        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    private static void leerEmpleadosDepartamentosFromApacheDerbyDatbase() {
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            
            Connection conexion= DriverManager.getConnection("jdbc:derby:C:/BD/ApacheDerby/ejemplo.db");
            
            Statement sentencia = conexion.createStatement();
            /*
            En el caso de Apache Derby, este SGBD no acepta las consultas de agragado después de SELECT por lo
            cual hemos de realizarlo mediante la utilización de una sub SELECT
            */
            String sql = "SELECT empleados.apellido, departamentos.dnombre, empleados.salario FROM empleados,departamentos WHERE empleados.dept_no=departamentos.dept_no and empleados.salario = (Select max(empleados.salario) from empleados)";
            ResultSet result = sentencia.executeQuery(sql);
            
            while (result.next()){
                System.out.println("Apellido: "+result.getString(1)+" Departamento: "+ result.getString(2)+" Salario: "+ result.getDouble(3));
                
            }
            result.close();
            sentencia.close();
            conexion.close();
        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
}
