package pl.ches.citybikes.data.disk.store.base

import rx.Observable

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
interface DataStore<K, V> {

  fun put(value: V)

  fun get(key: K): V?

  fun delete(value: V)

  fun clear()

  fun remove(key: K) {
    val entry = get(key)
    entry?.let { delete(entry) }
  }

  fun putObs(value: V): Observable<V> = Observable.create { subscriber ->
    put(value)
    if (!subscriber.isUnsubscribed) {
      subscriber.onNext(value)
      subscriber.onCompleted()
    }
  }

  fun getObs(key: K): Observable<V> = Observable.create { subscriber ->
    val value: V? = get(key)
    if (!subscriber.isUnsubscribed) {
      subscriber.onNext(value)
      subscriber.onCompleted()
    }
  }

  fun deleteObs(key: K): Observable<V?> = Observable.create { subscriber ->
    remove(key)
    if (!subscriber.isUnsubscribed) {
      subscriber.onCompleted()
    }
  }

}