/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    @FXML
    private Button Button1;
    @FXML
    private Button Button2;
    @FXML
    private Button Button3;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML
    public void b1(ActionEvent event){
        if(Button1.getText().equals("Start Task 1"))
        {
            Button1.setText("End Task 1");
            startTask1();
        }
        else
        {
            Button1.setText("Start Task 1");
            stopTask1();
        }
        
    }
    public void startTask1() {
        System.out.println("start task 1");
        if (task1 == null) {
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
        }
    }
    
    public void stopTask1(){
        task1.end();
        task1 = null;
        textArea.appendText("Task 1 stopped \n");
    }
    
//    @Override
//    public void notify(String message) {
//        if (message.equals("Task1 done.")) {
//            task1 = null;
//            Button1.setText("Start Task 1");
//        }
//        if (message.equals("Task2 done.")) {
//            task2 = null;
//            Button2.setText("Start Task 2");
//        }
//        if (message.equals("Task3 done.")) {
//            task3 = null;
//            Button3.setText("Start Task 3");
//        }
//        textArea.appendText(message + "\n");
//    }

    @FXML
    public void b2(ActionEvent event){
        if(Button2.getText().equals("Start Task 2"))
        {
            Button2.setText("End Task 2");
            startTask2(event);
        }
        else
        {
            Button2.setText("Start Task 2");
            stopTask2();
        }
        
    }
    public void startTask2(ActionEvent event) {
        System.out.println("start task 2");
        if (task2 == null) {
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
                if (message.equals("Task2 done.")) {
                    task2 = null;
                    Button2.setText("Start Task 2");
                }
            });
            
            task2.start();
        }        
    }
    
    public void stopTask2(){
        task2.end();
        textArea.appendText("Task 2 stopped \n");
        task2 = null;

    }
    @FXML
    public void b3(ActionEvent event){
    if(Button3.getText().equals("Start Task 3"))
        {
            Button3.setText("End Task 3");
            startTask3(event);
        }
    else
        {
            Button3.setText("Start Task 3");
            stopTask3();
        }
        
    }
    public void startTask3(ActionEvent event) {
        System.out.println("start task 3");
        if (task3 == null) {
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                String message = (String)evt.getNewValue();
                textArea.appendText(message + "\n");
                if (message.equals("Task2 done.")) {
                    task2 = null;
                    Button3.setText("Start Task 2");
                }
            });
            
            task3.start();
        }
    } 
    
    public void stopTask3(){
        task3.end();
        textArea.appendText("Task 3 stopped \n");
        task3 = null;

    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
            Button1.setText("Start Task 1");
        }
        if (message.equals("Task2 done.")) {
            task2 = null;
            Button2.setText("Start Task 2");
        }
        if (message.equals("Task3 done.")) {
            task3 = null;
            Button3.setText("Start Task 3");
        }
        textArea.appendText(message + "\n");
    }
}
