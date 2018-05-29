import akka.actor.ActorSystem
import akka.http.scaladsl.{Http}
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

/**
  * Created by BoA on 2018/4/17.
  */
object example {

  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val url = "http://xxx.xxx.xxx.xxx:8080/scala/xml.do"


    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url, method = HttpMethods.GET))

    responseFuture.onComplete {
      case Success(res) => {
        println(res)
        Unmarshal(res.entity).to[String].map { json_str =>
          Right {
            println("post resutl: ", json_str)
          }
        }
      }
      case Failure(error)   => sys.error(error.getMessage)
    }

  }

}