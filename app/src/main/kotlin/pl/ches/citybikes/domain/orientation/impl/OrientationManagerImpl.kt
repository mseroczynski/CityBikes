package pl.ches.citybikes.domain.orientation.impl

import android.hardware.Sensor
import android.hardware.SensorManager
import android.view.Surface
import android.view.WindowManager
import d
import pl.ches.citybikes.domain.orientation.Orientation3d
import pl.ches.citybikes.domain.orientation.OrientationManager
import pl.ches.citybikes.domain.orientation.internal.OrientationCalculator
import pl.ches.citybikes.domain.orientation.internal.math.Matrix4
import pl.ches.citybikes.domain.orientation.internal.rotation.MagAccelListener
import pl.ches.citybikes.domain.orientation.internal.rotation.RotationUpdateDelegate
import rx.AsyncEmitter
import rx.Observable
import v

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class OrientationManagerImpl
constructor(private val sensorManager: SensorManager?,
            private val windowManager: WindowManager,
            private val orientationCalculator: OrientationCalculator) : OrientationManager {

  //region OrientationManager
  override fun isSupported(): Boolean = sensorManager != null

  override fun orientation3dObs(): Observable<Orientation3d> = Observable.fromEmitter({ emitter ->
    if (sensorManager == null) return@fromEmitter

    val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    val magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    // Device rotation at the moment of initializing View (subscription should be recreated on each turn)
    val currentRotation = windowManager.defaultDisplay.rotation

    val listener = MagAccelListener(RotationUpdateDelegate { newMatrix ->

      v { "rotation {$currentRotation}" }
      v { "newMatrix {${newMatrix[0].format(2)}, ${newMatrix[1].format(2)}, ${newMatrix[2].format(2)}}" }

      when (currentRotation) {
        Surface.ROTATION_90 -> {
          d { "remapping ROTATION_90" }
          SensorManager.remapCoordinateSystem(newMatrix, SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, newMatrix)
        }
        Surface.ROTATION_270 -> {
          d { "remapping ROTATION_270" }
          SensorManager.remapCoordinateSystem(newMatrix, SensorManager.AXIS_MINUS_Y, SensorManager.AXIS_X, newMatrix)
        }
        else -> v { "remapping not needed"}
      }

      val derivedDeviceOrientation = floatArrayOf(0F, 0F, 0F)
      orientationCalculator.getOrientation(Matrix4(newMatrix), currentRotation, derivedDeviceOrientation)

      // Emit new orientation
      v { "orientation {${derivedDeviceOrientation[0].format(2)}, ${derivedDeviceOrientation[1].format(2)}, ${derivedDeviceOrientation[2].format(2)}}" }
      emitter.onNext(Orientation3d(derivedDeviceOrientation[0], derivedDeviceOrientation[1], derivedDeviceOrientation[2]))
    })

    // Register listeners
    sensorManager.registerListener(listener, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME) // SENSOR_DELAY_UI?
    sensorManager.registerListener(listener, magneticFieldSensor, SensorManager.SENSOR_DELAY_GAME) // SENSOR_DELAY_UI?

    // Free resources on unsubscribtion
    emitter.setCancellation {
      d { "cancelling subscription" }
      sensorManager.unregisterListener(listener, accelerometerSensor)
      sensorManager.unregisterListener(listener, magneticFieldSensor)
    }
  }, AsyncEmitter.BackpressureMode.LATEST)
  //endregion

  fun Float.format(digits: Int) = java.lang.String.format("%.${digits}f", this)!!

}
