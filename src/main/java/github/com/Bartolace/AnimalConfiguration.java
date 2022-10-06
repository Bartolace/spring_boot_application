package github.com.Bartolace;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*implementar configuration usando bean
*
* chamado com
* @Autowire
* @Qualifier("nameBean")
*
* ou criar um annotation e implementar apenas com o @NameDaMetodoAnotation
*
* */

@Configuration
public class AnimalConfiguration{

    @Bean(name = "cachorro")
    public Animal cachorro(){
        return new Animal() {
            @Override
            public void fazerBarulho() {
                System.out.println("Au au au");
            }
        };
    }

    @Bean(name = "gato")
    public Animal gato(){
        return new Animal() {
            @Override
            public void fazerBarulho() {
                System.out.println("Miau miau miau");
            }
        };
    }


}
