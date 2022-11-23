package github.com.Bartolace.exception;

//Essa classe herda o runtime para podermos instanciar novas mensagens padronizadas

public class RegraNegocioException  extends  RuntimeException{

    public RegraNegocioException(String message){
        super(message);
    }
}
