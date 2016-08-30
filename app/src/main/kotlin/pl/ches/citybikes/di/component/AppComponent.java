package pl.ches.citybikes.di.component;

import dagger.Component;
import pl.ches.citybikes.App;
import pl.ches.citybikes.di.module.AndroidModule;
import pl.ches.citybikes.di.scope.AppScope;

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@AppScope
@Component(modules = {
    AndroidModule.class,
})
public interface AppComponent {

  // Application
  void inject(App target);

  // Activity
  // ...
  // Fragment
  // ...

}
