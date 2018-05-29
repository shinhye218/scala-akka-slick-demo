package actor

import akka.actor.{Actor, ActorLogging, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.util.ByteString
import dao.DemoTable
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery

/**
  * Created by BoA on 2018/4/20.
  */
class DemoActor extends Actor with ActorLogging {

  import akka.pattern.pipe
  import context.dispatcher

  implicit val system: ActorSystem = ActorSystem()
  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))

  val http = Http(context.system)

  val url = "http://xxx.xxx.xxx.xxx:8080/scala/xml.do"

  override def preStart() = {
    http.singleRequest(HttpRequest(uri = url)).pipeTo(self)
  }

  def receive: Receive = {
    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>
        val db =  Database.forURL("jdbc:mysql://localhost:3306/slick", "root","", driver = "com.mysql.jdbc.Driver")

        var query = TableQuery[DemoTable]

        val xmlString = scala.xml.XML.loadString(body.utf8String)
        db.run(query.forceInsertAll(for {
          i <- xmlString \\ "BEAN"

        } yield {
          (
            None,
            i.attribute("DEALER_ID").get.text.toInt,
            Option(i.attribute("MODEL_CODE").get.text),
            Option(i.attribute("MODEL_CODE_AS").get.text),
            i.attribute("DEALER_NAME").get.text,
            i.attribute("BIZ_STATUS").get.text,
            i.attribute("DEALER_CODE").get.text,
            i.attribute("DEALER_SHORT_NAME").get.text,
            i.attribute("BIZ_ATTRIBUTE").get.text.toInt,
            i.attribute("PROVINCE").get.text,
            i.attribute("CITY").get.text,
            i.attribute("IS_EC_SO").get.text.toInt,
            i.attribute("COUNTY").get.text,
            i.attribute("ADDRESS").get.text,
            i.attribute("BIZ_PHONE").get.text,
            i.attribute("HOT_LINE").get.text,
            i.attribute("FAX_NO").get.text,
            i.attribute("COORDINATES_JD").get.text,
            i.attribute("COORDINATES_WD").get.text
          )
        }))

        db.close()

        log.info("Got Response, body: " + body.utf8String)
        }

    case resp @ HttpResponse(code, _, _, _) =>
      log.info("Request failed, response code: " + code)
      resp.discardEntityBytes()

    case _ =>
      log.info("hello scala...")
  }

}
