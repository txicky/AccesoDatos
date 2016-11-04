/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.accesoh2;

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
public class H2 {
    public static void main(String[] args) {
        
        int opcion=0;
        Scanner sc = new Scanner(System.in);
        
        do{
            System.out.println("--------------------------------------");
            System.out.println("            Menú Principal");
            System.out.println("--------------------------------------");
            System.out.println("1. Lectura de Departamentos desde H2");
            System.out.println("2. Lectura de datos de Apellidos, Oficio y Salario de Empleados dep 30");
            System.out.println("3. Lectura de datos de Apellido del empleado con el máximo salario");
            System.out.println("0. Salir");
            System.out.print("Introduce la opción deseada: ");
            opcion = sc.nextInt();
            switch(opcion){
                case 1: {
                    leerDepartamentoFromH2Datbase();
                    break;
                }
                case 2: {
                    leerEmpleadosFromH2Datbase();
                    break;
                }
                case 3: {
                    leerEmpleadosDepartamentosFromH2Datbase();
                    break;
                }
                default:{
                    break;
                }
                        

            }
            
            
        }while (opcion!=0);
        
        
    }
    
    private static void leerDepartamentoFromH2Datbase(){
        
        try{
            /* 
            Para acceder a la base de datos H2 necesitamos referenciar la librería h2-1.4.191.jar que nos 
            proporcionará la clase org.h2.Driver para poder acceder a la misma
            */
            Class.forName("org.h2.Driver");
            
            Connection conexion= DriverManager.getConnection("jdbc:h2:C:/BD/H2/Ejemplo.db","USUARIO_BD","PASSWORD_USUARIO");
            
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

    private static void leerEmpleadosFromH2Datbase() {
        try{
            Class.forName("org.h2.Driver");
            
            Connection conexion= DriverManager.getConnection("jdbc:h2:C:/BD/H2/Ejemplo.db","USUARIO_BD","PASSWORD_USUARIO");
            
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

    private static void leerEmpleadosDepartamentosFromH2Datbase() {
        try{
            Class.forName("org.h2.Driver");
            
            Connection conexion= DriverManager.getConnection("jdbc:h2:C:/BD/H2/Ejemplo.db","USUARIO_BD","PASSWORD_USUARIO");
            
            Statement sentencia = conexion.createStatement();
            String sql = "SELECT empleados.apellido, departamentos.dnombre, empleados.salario FROM empleados, departamentos WHERE empleados.dept_no=departamentos.dept_no AND empleados.salario = (SELECT MAX(empleados.salario) FROM empleados)";
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
