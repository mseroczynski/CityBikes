package pl.ches.citybikes.presentation.screen.main.stations;

import dagger.Subcomponent;

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Subcomponent
public interface StationsComponent {

  void inject(StationsFragment target);

  StationsPresenter presenter();

}
