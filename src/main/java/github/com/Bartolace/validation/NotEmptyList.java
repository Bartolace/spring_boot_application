package github.com.Bartolace.validation;

import github.com.Bartolace.validation.constraintvalidation.NotEmptyListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//verificar em tempo de execução
@Target(ElementType.FIELD)//onde podemos colocar a annotation
@Constraint(validatedBy = NotEmptyListValidator.class )// diz que é uma validação
public @interface NotEmptyList {

    String message() default "A lista não pode ser vazia.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
