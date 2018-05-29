package dao

import slick.jdbc.MySQLProfile.api._

/**
  * Created by BoA on 2018/4/23.
  */
// 定义一个Test表
//表中包含两列，分别是id,name
class DemoTable(tag: Tag) extends Table[
  (
      Option[Int],
      Int,
      Option[String],
      Option[String],
      String,
      String,
      String,
      String,
      Int,
      String,
      String,
      Int,
      String,
      String,
      String,
      String,
      String,
      String,
      String
    )
  ](tag, "slick_table") {
  def id                 = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
  def dealer_id          = column[Int]("dealer_id")
  def model_code         = column[Option[String]]("model_code", O.Length(1024))
  def model_code_as      = column[Option[String]]("model_code_as", O.Length(1024))
  def dealer_name        = column[String]("dealer_name")
  def biz_status         = column[String]("biz_status")
  def dealer_code        = column[String]("dealer_code")
  def dealer_short_name  = column[String]("dealer_short_name")
  def biz_attribute      = column[Int]("biz_attribute")
  def province           = column[String]("province")
  def city               = column[String]("city")
  def is_ec_so           = column[Int]("is_ec_so")
  def county             = column[String]("county")
  def address            = column[String]("address")
  def biz_phone          = column[String]("biz_phone")
  def hot_line           = column[String]("hot_line")
  def fax_no             = column[String]("fax_no")
  def coordinates_jd     = column[String]("coordinates_jd")
  def coordinates_wd     = column[String]("coordinates_wd")
  // Every table needs a * projection with the same type as the table's type parameter
  //每个Table中都应该有*方法，它的类型必须与前面定义的类型参数(Int, String)一致
  def * = (
    id,
    dealer_id,
    model_code,
    model_code_as,
    dealer_name,
    biz_status,
    dealer_code,
    dealer_short_name,
    biz_attribute,
    province,
    city,
    is_ec_so,
    county,
    address,
    biz_phone,
    hot_line,
    fax_no,
    coordinates_jd,
    coordinates_wd
  )

}