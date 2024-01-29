/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.whoisdomaininformationsearch;

//imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * WhoisDomainSearchApp.java
 * 15th November 2021
 * Advanced Computer Networks - Lab 6
 * @author: Ruby Lennon (x19128355)
 * Tasks Description - Write a java application that will connect to whois.internic.net on port 43 that will return the “whois” information for a domain name. 
 */

//@Ref - https://www.codejava.net/java-se/networking/java-socket-client-examples-tcp-ip
//Reference description - tutorial on how to create a Whois Socket client application

//main class
public class WhoisDomainSearchApp{
    //main method
    public static void main(String[] args) {
        //declare variable
        String hostname = "whois.internic.net";//string to store hostname
        int port = 43;//int to store port number
        String domainName;//string to store lookup domain name
        String searchAgain;//string to indicate if the user wants to do another search
        String response;//string to store server response
        
        //print the following when app first runs
        System.out.println("Whois Domain Name Search Application");
        System.out.println("To note: The Registry database contains only .COM, .NET, .EDU domains and Registrars.\n");
        
        do{//do the following code          
            Scanner scan = new Scanner(System.in);//create new Scanner instance
        
            do{//do the following code
                System.out.println("\nPlease enter a domain name to lookup:");
                //get string input from user and store to the domainName search variable
                domainName = scan.nextLine();
            }while(domainName.equalsIgnoreCase("") || domainName.equalsIgnoreCase(" "));//repeat if user inputs blank domainName
            
            //Create client socket, establish the connection with server (hostname: whois.internic.net) on port 43 
            try (Socket socket = new Socket(hostname, port)) {//try with resources           
                //Obtain output streams
                OutputStream outputStream = socket.getOutputStream();
                //declare PrintWriter and encase output stream, set append to true
                PrintWriter printWriter = new PrintWriter(outputStream, true);
                
                printWriter.println(domainName);//use user input domainName as parameter

                //obtain input streams
                InputStream inputStream = socket.getInputStream();
                
                //declare and create new buffered reader object and encase input stream
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                
                System.out.println("\n-- Server response start --\n");
                
                //while the server repsonse is not null
                while((response = bufferedReader.readLine()) != null){
                    System.out.println(response);//print the server response
                }
                
                System.out.println("\n-- Server response end --\n");
                
            }catch (UnknownHostException ex){//catch unknownhostexception
                System.out.println("Server not found: " + ex.getMessage());//print error
            }catch (IOException ex){//catch Input Output Exceptions
                System.out.println("I/O error: " + ex.getMessage());//print error
            }
            
            //input - ask user if they want to do another search
            System.out.println("\nPlease type yes to search again.");
            //get user input from next line and store in searchAgain string
            searchAgain = scan.nextLine();
            
        }while (searchAgain.equalsIgnoreCase("yes"));//run main method again if searchAgain value equals yes

        //out of loops
        System.out.println("Thanks for searching!");
    }
}
