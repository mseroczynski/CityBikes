package pl.ches.citybikes.data.api.adapter

import pl.ches.citybikes.data.api.adapter.RetrofitException.Kind.*
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

/**
 * @param message message
 * @param url request URL which produced the error
 * @param response response object containing status code, headers, body, etc.
 * @param kind event kind which triggered this error
 * @param exception exception
 * @param retrofit Retrofit this request was executed on
 *
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class RetrofitException
constructor(override val message: String,
            val url: String?,
            val response: Response<*>?,
            val kind: Kind,
            val exception: Throwable?,
            val retrofit: Retrofit?) : RuntimeException(message, exception) {

  /**
   * HTTP response body converted to specified `type`. `null` if there is no
   * response.
   *
   * @throws IOException if unable to convert the body to the specified `type`.
   */
  @Throws(IOException::class)
  fun <T> getErrorBodyAs(type: Class<T>): T? {
    if (response == null || response.errorBody() == null) {
      return null
    }
    val converter = retrofit?.responseBodyConverter<T>(type, arrayOfNulls<Annotation>(0))
    return converter?.convert(response.errorBody())
  }

  companion object {
    fun httpError(url: String, response: Response<*>, retrofit: Retrofit): RetrofitException {
      return RetrofitException("${response.code()} ${response.message()}", url, response, HTTP, null, retrofit)
    }

    fun networkError(exception: IOException): RetrofitException {
      return RetrofitException(exception.message!!, null, null, NETWORK, exception, null)
    }

    fun unexpectedError(exception: Throwable): RetrofitException {
      return RetrofitException(exception.message!!, null, null, UNEXPECTED, exception, null)
    }
  }

  /**
   * Identifies the event kind which triggered a [RetrofitException]
   *
   * [NETWORK] - an [IOException] occurred while communicating to the server
   * [HTTP] - non-200 HTTP status code was received from the server
   * [UNEXPECTED] - internal error occurred while attempting to execute a request. It is best practice to
   * re-throw this exception so your application crashes
   */
  enum class Kind {
    NETWORK,
    HTTP,
    UNEXPECTED
  }
}