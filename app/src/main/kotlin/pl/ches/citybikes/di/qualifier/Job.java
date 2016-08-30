package pl.ches.citybikes.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface Job {
}