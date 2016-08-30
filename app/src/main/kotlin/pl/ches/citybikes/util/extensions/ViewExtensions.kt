package pl.ches.citybikes.util.extensions

import android.app.Activity
import android.view.View
import android.widget.Toast
import android.support.v4.app.Fragment as SupportFragment

fun View.show(): Unit {
  visibility = View.VISIBLE
}

fun View.hide(): Unit {
  visibility = View.GONE
}

fun Activity.toast(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
