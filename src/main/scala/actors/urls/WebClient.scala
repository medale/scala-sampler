package actors.urls

import com.ning.http.client.AsyncHttpClient
import scala.concurrent.Future
import java.util.concurrent.Executor
import scala.concurrent.Promise

/**
 * Example originated from Coursera Reactive Programming
 * class - Roland Kuhn lecture.
 */
object WebClient {

  case class BadStatus(code: Int) extends Throwable

  private val client = new AsyncHttpClient

  def get(url: String)(implicit exec: Executor): Future[String] = {
    val f = client.prepareGet(url).execute();
    val p = Promise[String]()
    f.addListener(new Runnable {
      def run = {
        val response = f.get
        if (response.getStatusCode < 400)
          p.success(response.getResponseBodyExcerpt(131072))
        else p.failure(BadStatus(response.getStatusCode))
      }
    }, exec)
    p.future
  }

  def shutdown() = {
    client.close()
  }
}
