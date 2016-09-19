package pl.ches.citybikes.di.component;

import android.content.Context;
import dagger.Component;
import pl.ches.citybikes.App;
import pl.ches.citybikes.di.module.AndroidModule;
import pl.ches.citybikes.di.module.BuildTypedModule;
import pl.ches.citybikes.di.module.CityBikesApiModule;
import pl.ches.citybikes.di.module.CommonModule;
import pl.ches.citybikes.di.module.DiskModule;
import pl.ches.citybikes.di.module.InteractorModule;
import pl.ches.citybikes.di.module.NextBikeApiModule;
import pl.ches.citybikes.di.scope.AppScope;
import pl.ches.citybikes.presentation.screen.main.MainComponent;
import pl.ches.citybikes.presentation.screen.main.stations.StationsComponent;

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@AppScope
@Component(modules = {
    AndroidModule.class,
    BuildTypedModule.class,
    CityBikesApiModule.class,
    CommonModule.class,
    DiskModule.class,
    InteractorModule.class,
    NextBikeApiModule.class
})
public interface AppComponent {

  // AndroidModule
  Context getContext();

  // Application
  void inject(App target);

  // Activity
  // ...
  // Fragment
  // ...

  // Subcomponents
  MainComponent plusMain();

  StationsComponent plustStations();

}
