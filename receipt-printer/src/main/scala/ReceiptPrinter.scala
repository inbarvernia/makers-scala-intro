import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.ZoneId
import java.time.Clock

class CafeDetails (
                    val shopName: String,
                    val address: String,
                    val phone: String,
                    val prices: Map[String, Double]
                  )

class ReceiptPrinter(val cafe: CafeDetails, var order: Map[String, Int] = Map(), val clock: Clock = Clock.systemUTC()) {
  val instant = Instant.now(clock)
  val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault)
  val formattedInstant = formatter.format(instant)
  val formattedOrder = (orderMap: Map[String, Int], priceList: Map[String, Double]) => {
    var formattedOrderString = f""""""
    for ((item, quantity) <- orderMap) formattedOrderString += f"""$quantity x $item    ${priceList(item)}%2.2f"""
    formattedOrderString
  }
  val totalPrice = (orderMap: Map[String, Int], cafe: CafeDetails) => {
    var total: Double = 0.0
    for ((item, quantity) <- orderMap) total += (cafe.prices(item) * quantity)
    total
  }

  /**
   * This method should return a multiline string
   * representing a ReceiptPrinter receipt that should show
   * - shop name, address, phone number
   * - the date and time the receipt was created
   * - each item in the order, with the price. eg:
   *     2 x Blueberry Muffin       8.10
   *     1 x Cappuccino             3.85
   * - the total price
   * - the VAT (20% of total price)
   */

  def receipt: String = {
    println(f"""${cafe.shopName}, ${cafe.address}, ${cafe.phone}
               |$formattedInstant
               |${formattedOrder(order, cafe.prices)}
               |${totalPrice(order, cafe)}%2.2f""".stripMargin)

    f"""${cafe.shopName}, ${cafe.address}, ${cafe.phone}
       |$formattedInstant
       |${formattedOrder(order, cafe.prices)}
       |${totalPrice(order, cafe)}%2.2f""".stripMargin
  }
}