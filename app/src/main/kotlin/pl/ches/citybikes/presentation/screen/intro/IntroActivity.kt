package pl.ches.citybikes.presentation.screen.intro

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.maxcruz.reactivePermissions.ReactivePermissions
import com.maxcruz.reactivePermissions.entity.Permission
import pl.ches.citybikes.R
import pl.ches.citybikes.presentation.screen.main.MainActivity

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class IntroActivity : AppCompatActivity() {

  val reactive: ReactivePermissions = ReactivePermissions(this, REQUEST_CODE)

  val location = Permission(
      Manifest.permission.ACCESS_FINE_LOCATION,
      R.string.app_name,
      false // If the user deny this permission, block the app
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    reactive.observeResultPermissions().subscribe { event ->
      if (event.second) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
      }
    }
    reactive.evaluate(listOf(location))
  }

  override fun onRequestPermissionsResult(code: Int, permissions: Array<String>, results: IntArray) {
    if (code == REQUEST_CODE) reactive.receive(permissions, results)
  }

  companion object {
    // Define a code to request the permissions
    const val REQUEST_CODE = 10
  }

}
