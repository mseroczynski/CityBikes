package pl.ches.citybikes;

import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import pl.ches.citybikes.di.component.AppComponent;
import pl.ches.citybikes.di.component.DaggerAppComponent;
import pl.ches.citybikes.di.module.AndroidModule;
import pl.ches.citybikes.di.module.BuildTypedModule;
import pl.ches.citybikes.di.module.CityBikesApiModule;
import pl.ches.citybikes.di.module.CommonModule;
import pl.ches.citybikes.di.module.DiskModule;
import pl.ches.citybikes.di.module.InteractorModule;
import pl.ches.citybikes.di.module.NextBikeApiModule;

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
public class App extends MultiDexApplication {

  protected static AppComponent appComponent;

  public static AppComponent component() {
    return appComponent;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    init();
  }

  protected void init() {
    AppInitializer.initVectorDrawables();
    AppInitializer.initDBFlow(this);
    AppInitializer.initCalligraphy();
    AppInitializer.initTimber();

    appComponent = initAppComponent();
    appComponent.inject(this);
  }

  public AppComponent initAppComponent() {
    return DaggerAppComponent.builder()
                             .androidModule(new AndroidModule(this))
                             .buildTypedModule(initBuildTypedModule())
                             .cityBikesApiModule(new CityBikesApiModule())
                             .commonModule(new CommonModule())
                             .diskModule(new DiskModule())
                             .interactorModule(new InteractorModule())
                             .nextBikeApiModule(new NextBikeApiModule())
                             .build();
  }

  @NonNull
  protected BuildTypedModule initBuildTypedModule() {
    return new BuildTypedModule();
  }

}
