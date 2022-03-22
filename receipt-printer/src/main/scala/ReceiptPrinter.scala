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
  val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault)
  private val formatCafeInfo = (cafe: CafeDetails)  => f"${cafe.shopName}, ${cafe.address}, ${cafe.phone}"
  private def formatTime = formatter.format(Instant.now(clock))
  private def header: String = {
    f"""${formatCafeInfo(cafe)}
       |$formatTime
       |${"Item"}%-24s|${"Price"}""".stripMargin
  }
  private def formatItem(item: String, quantity: Int) = f"${quantity + " x " + item}%-24s|${quantity * cafe.prices(item)}%.2f"
  private def formattedOrder: String = {
    order.map({case (item, quantity) => formatItem(item, quantity)}).mkString(
      f"""
         |""".stripMargin)
  }
  private def totalPrice: Double = {
    var total: Double = 0.0
    for ((item, quantity) <- order) total += (cafe.prices(item) * quantity)
    total
  }
  private def vat: Double = totalPrice * 0.2
  private val footer: String = "Service not included :)"

  def receipt: String = {
    println(f"""$header
               |${formattedOrder}
               |Total: $totalPrice%.2f
               |VAT (20%%): $vat%.2f
               |$footer""".stripMargin)

    f"""$header
       |${formattedOrder}
       |Total: $totalPrice%.2f
       |VAT (20%%): $vat%.2f
       |$footer""".stripMargin
  }
}