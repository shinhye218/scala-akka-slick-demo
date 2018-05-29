import actor.DemoActor
import akka.actor.{ActorSystem, Props}

/**
  * Created by BoA on 2018/4/20.
  */
object DemoApp extends App {

  implicit val system: ActorSystem = ActorSystem()
  val helloActor = system.actorOf(Props[DemoActor], name="demoActor")

  helloActor ! "start"

}
