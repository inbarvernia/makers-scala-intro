import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import scala.collection.mutable.LinkedHashMap

class ReceiptPrinterSpec extends AnyWordSpec with Matchers {
  val coffeeConnectionCafe = new CafeDetails(
    "The Coffee Connection",
    "123 Lakeside Way",
    "16503600708",
    LinkedHashMap(
      "Cafe Latte" -> 4.75,
      "Flat White" -> 4.75,
      "Cappuccino" -> 3.85,
      "Single Espresso" -> 2.05,
      "Double Espresso" -> 3.75,
      "Americano" -> 3.75,
      "Cortado" -> 4.55,
      "Tea" -> 3.65,
      "Choc Mudcake" -> 6.40,
      "Choc Mousse" -> 8.20,
      "Affogato" -> 14.80,
      "Tiramisu" -> 11.40,
      "Blueberry Muffin" -> 4.05,
      "Chocolate Chip Muffin" -> 4.05,
      "Muffin Of The Day" -> 4.55
    )
  )
    val miniCoffeeConnection = new CafeDetails(
      "The Coffee Connection",
      "123 Lakeside Way",
      "16503600708",
      LinkedHashMap(
        "Cafe Latte" -> 4.75,
        "Single Espresso" -> 2.05,
        "Double Espresso" -> 3.75,
        "Blueberry Muffin" -> 4.05,
        "Chocolate Chip Muffin" -> 4.05,
        "Muffin Of The Day" -> 4.55
      )
  )

  "A ReceiptPrinter" should {
    "format a receipt" which {
      "contains the name of the cafe" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map("Cafe Latte" -> 1)
        )
        printer.receipt should include ("The Coffee Connection")
      }
      "contains the address of the cafe" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map("Cafe Latte" -> 1)
        )
        printer.receipt should include ("123 Lakeside Way")
      }
      "contains the phone number of the cafe" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map("Cafe Latte" -> 1)
        )
        printer.receipt should include ("16503600708")
      }
      "contains the date and time the receipt was created" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map("Cafe Latte" -> 1),
          Clock.fixed(Instant.parse("2022-03-18T16:15:00.00Z"), ZoneId.systemDefault())
        )
        printer.receipt should include ("18/03/2022 16:15")
      }
      "contains an item from the order" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map("Cafe Latte" -> 1)
        )
        printer.receipt should include ("1 x Cafe Latte")
      }
      "contains the price of an item from the order" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map("Cafe Latte" -> 1)
        )
        printer.receipt should include ("4.75")
      }
      "contains multiple items and prices from order" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map(
            "Cafe Latte" -> 2,
            "Tiramisu" -> 1
          )
        )
        printer.receipt should include ("2 x Cafe Latte")
        printer.receipt should include ("9.50")
        printer.receipt should include ("1 x Tiramisu")
        printer.receipt should include ("11.40")
      }
      "contains total order price" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map(
            "Cafe Latte" -> 2,
            "Tiramisu" -> 1
          )
        )
        printer.receipt should include ("20.90")
      }
      "contains VAT on the order" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map(
            "Cafe Latte" -> 2,
            "Tiramisu" -> 1
          )
        )
        printer.receipt should include ("4.18")
      }
      "outputs the correct layout" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map(
            "Cafe Latte" -> 2,
            "Tiramisu" -> 1
          ),
          Clock.fixed(Instant.parse("2022-03-18T16:15:00.00Z"), ZoneId.systemDefault())
        )
        printer.receipt should be(f"""The Coffee Connection, 123 Lakeside Way, 16503600708
                                     |18/03/2022 16:15
                                     |Item                    |Price
                                     |2 x Cafe Latte          |9.50
                                     |1 x Tiramisu            |11.40
                                     |Total: 20.90
                                     |VAT (20%%): 4.18
                                     |Service not included :)""".stripMargin)
      }
    }
  }
  "A Till" should {
    "show the menu" which {
      "contains a formatted string of all menu items and prices" in {
        val till = new Till(miniCoffeeConnection)
        till.displayMenu should be(
          f"""Menu:
             |Item                     |Price
             |Cafe Latte               |4.75
             |Single Espresso          |2.05
             |Double Espresso          |3.75
             |Blueberry Muffin         |4.05
             |Chocolate Chip Muffin    |4.05
             |Muffin Of The Day        |4.55""".stripMargin)
      }
    }
  }
}