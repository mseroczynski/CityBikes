package pl.ches.citybikes.presentation.screen.main.map;

import dagger.Subcomponent;

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Subcomponent
public interface MapComponent {

  void inject(MapFragment target);

  MapPresenter presenter();

}
