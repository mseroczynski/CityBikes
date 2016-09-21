package pl.ches.citybikes.util.extensions

import android.content.SharedPreferences
import com.f2prateek.rx.preferences.Preference

inline fun SharedPreferences.edit(func: SharedPreferences.Editor.() -> Unit) {
  val editor = this.edit()
  editor.func()
  editor.apply()
}

fun SharedPreferences.clear() {
  val editor = this.edit()
  editor.clear()
  editor.commit()
}

fun SharedPreferences.Editor.setString(pair: Pair<String, String>) =
    putString(pair.first, pair.second)

fun SharedPreferences.Editor.setLong(pair: Pair<String, Long>) =
    putLong(pair.first, pair.second)

fun SharedPreferences.Editor.setInt(pair: Pair<String, Int>) =
    putInt(pair.first, pair.second)

fun SharedPreferences.Editor.setBoolean(pair: Pair<String, Boolean>) =
    putBoolean(pair.first, pair.second)

fun SharedPreferences.Editor.setFloat(pair: Pair<String, Float>) =
    putFloat(pair.first, pair.second)

fun SharedPreferences.Editor.setStringSet(pair: Pair<String, Set<String>>) =
    putStringSet(pair.first, pair.second)

fun <T> Preference<T>.get() = get()!!