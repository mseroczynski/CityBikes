package pl.ches.citybikes;

import android.support.multidex.MultiDexApplication;
import pl.ches.citybikes.di.component.AppComponent;
import pl.ches.citybikes.di.component.DaggerAppComponent;
import pl.ches.citybikes.di.module.AndroidModule;

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
    AppInitializer.initCalligraphy();

    appComponent = initAppComponent();
    appComponent.inject(this);
  }

  public AppComponent initAppComponent() {
    return DaggerAppComponent.builder()
                             .androidModule(new AndroidModule(this))
                             .build();
  }

}
