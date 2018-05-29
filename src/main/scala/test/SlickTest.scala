package test

import dao.DemoTable
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery

/**
  * Created by BoA on 2018/4/20.
  */
object SlickTest extends App {

  //创建TableQuery对象（这里调用的是TableQuery的apply方法
  //没有显式地调用new
  val query = TableQuery[DemoTable]

  //forURL注册MySQL驱动器，传入URL，用户名及密码
  val db = Database.forURL("jdbc:mysql://localhost:3306/slick", "root","", driver = "com.mysql.jdbc.Driver")

  db.run(DBIO.seq(query.schema.create))
  db.close()

}
