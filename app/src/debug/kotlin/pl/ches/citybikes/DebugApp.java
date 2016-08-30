package pl.ches.citybikes;

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
public class DebugApp extends App {

  @Override
  protected void init() {
    super.init();

    DebugAppInitializer.initStetho(this);
  }

}
