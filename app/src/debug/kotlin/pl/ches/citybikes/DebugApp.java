package pl.ches.citybikes;

import android.support.annotation.NonNull;
import pl.ches.citybikes.di.module.BuildDebugModule;
import pl.ches.citybikes.di.module.BuildTypedModule;

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
public class DebugApp extends App {

  @Override
  protected void init() {
    super.init();

    DebugAppInitializer.initStetho(this);
  }

  @NonNull
  @Override
  protected BuildTypedModule initBuildTypedModule() {
    return new BuildDebugModule();
  }

}
