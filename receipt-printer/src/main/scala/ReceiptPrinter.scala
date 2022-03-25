import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.ZoneId
import java.time.Clock
import scala.collection.mutable.AbstractMap
import scala.collection.mutable.LinkedHashMap

class CafeDetails (
                    val shopName: String,
                    val address: String,
                    val phone: String,
                    val prices: LinkedHashMap[String, Double]
                  )

trait ReceiptPrinterFactoryBase {
  def create(cafe: CafeDetails, order: AbstractMap[String, Int], clock: Clock): ReceiptPrinterBase
}

object ReceiptPrinterFactory extends ReceiptPrinterFactoryBase {
  def create(cafe: CafeDetails, order: AbstractMap[String, Int], clock: Clock): ReceiptPrinterBase = {
    return new ReceiptPrinter(cafe, order, clock)
  }
}

trait ReceiptPrinterBase {
  def receipt: String
}

class ReceiptPrinter(val cafe: CafeDetails, var order: AbstractMap[String, Int] = new LinkedHashMap, val clock: Clock = Clock.systemUTC()) extends ReceiptPrinterBase {
  private val timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault)
  private val formatCafeInfo = (cafe: CafeDetails)  => f"${cafe.shopName}, ${cafe.address}, ${cafe.phone}"
  private def formatTime = timeFormatter.format(Instant.now(clock))
  private def header: String = {
    f"""${formatCafeInfo(cafe)}
       |$formatTime
       |${"Item"}%-24s|${"Price"}""".stripMargin
  }
  private def itemPrice(item: String): Double = cafe.prices(item)
  private val priceForQuantity = (item: String, quantity: Int) => itemPrice(item) * quantity
  private val formatItem = (item: String, quantity: Int) => f"${quantity + " x " + item}%-24s|${priceForQuantity(item, quantity)}%.2f"
  private def formattedOrder: String = {
    order.map({case (item, quantity) => formatItem(item, quantity)}).mkString(
      f"""
         |""".stripMargin)
  }
  private def totalPrice: Double = {
    order.map({case (item, quantity) => priceForQuantity(item, quantity)}).fold(0.0)((a, b) => a + b)
  }
  private def vat: Double = totalPrice * 0.2
  private val formattedTotals = f"""Total: $totalPrice%.2f
                                |VAT (20%%): $vat%.2f""".stripMargin
  private val footer: String = "Service not included :)"

  def receipt: String = {
//    println(f"""$header
//               |$formattedOrder
//               |$formattedTotals
//               |$footer""".stripMargin)

    f"""$header
       |$formattedOrder
       |$formattedTotals
       |$footer""".stripMargin
  }
}

class Till(val cafe: CafeDetails, val clock: Clock = Clock.systemUTC(), val printerFactory: ReceiptPrinterFactoryBase = ReceiptPrinterFactory) {
  private val menuHeader: String = f"""Menu:
                              |${"Item"}%-25s|${"Price"}""".stripMargin
  private val formatMenuItem = (item: String, price: Double) => f"$item%-25s|$price%.2f"
  private val formattedMenu = (cafe: CafeDetails) => cafe.prices.map({case (item, price) => formatMenuItem(item, price)}).mkString(
    f"""
       |""".stripMargin)
  def displayMenu: String = {
//    println(f"""$menuHeader
//               |${formattedMenu(cafe)}""".stripMargin)
    f"""$menuHeader
       |${formattedMenu(cafe)}""".stripMargin
  }
  var order: LinkedHashMap[String, Int] = new LinkedHashMap[String, Int]
  private def isNotOnMenu(item: String): Boolean = cafe.prices.get(item) == None
  private val isInOrder = (item: String, order: AbstractMap[String, Int]) => order.get(item) != None
  def addToOrder(item: String) = {
    if (isNotOnMenu(item)) throw new NotOnMenuException("Item not in menu")
    else if (isInOrder(item, order)) order(item) = order(item) + 1
    else order += (item -> 1)
  }
  def finaliseOrder: String = {
    val printer = printerFactory.create(cafe, order, clock)
//    println(printer.receipt)
    printer.receipt
  }
}

class NotOnMenuException(info: String) extends Exception(info) {}