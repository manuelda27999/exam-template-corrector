package app.utilities;

//Excepción personalizada para controlar errores de manera más precisa
public class MyException extends Exception{
    public MyException(String message) {
        super(message);
    }
}
