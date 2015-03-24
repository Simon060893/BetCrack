package com.utils.annotations;

import java.lang.annotation.*;

/**
 * Created by Катя on 30.01.2015.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActUser  {
    boolean isAdmin() default false;
}
