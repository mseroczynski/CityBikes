package pl.ches.citybikes.data.disk.store

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.enums.SourceApi
import pl.ches.citybikes.data.disk.store.base.DataStore

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface AreaStore : DataStore<SourceApi, List<Area>>