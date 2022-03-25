import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import scala.collection.mutable.LinkedHashMap

class ReceiptPrinterSpec extends AnyWordSpec with Matchers with MockFactory {
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
          LinkedHashMap("Cafe Latte" -> 1)
        )
        printer.receipt should include ("The Coffee Connection")
      }
      "contains the address of the cafe" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          LinkedHashMap("Cafe Latte" -> 1)
        )
        printer.receipt should include ("123 Lakeside Way")
      }
      "contains the phone number of the cafe" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          LinkedHashMap("Cafe Latte" -> 1)
        )
        printer.receipt should include ("16503600708")
      }
      "contains the date and time the receipt was created" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          LinkedHashMap("Cafe Latte" -> 1),
          Clock.fixed(Instant.parse("2022-03-18T16:15:00.00Z"), ZoneId.systemDefault())
        )
        printer.receipt should include ("18/03/2022 16:15")
      }
      "contains an item from the order" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          LinkedHashMap("Cafe Latte" -> 1)
        )
        printer.receipt should include ("1 x Cafe Latte")
      }
      "contains the price of an item from the order" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          LinkedHashMap("Cafe Latte" -> 1)
        )
        printer.receipt should include ("4.75")
      }
      "contains multiple items and prices from order" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          LinkedHashMap(
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
          LinkedHashMap(
            "Cafe Latte" -> 2,
            "Tiramisu" -> 1
          )
        )
        printer.receipt should include ("20.90")
      }
      "contains VAT on the order" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          LinkedHashMap(
            "Cafe Latte" -> 2,
            "Tiramisu" -> 1
          )
        )
        printer.receipt should include ("4.18")
      }
      "outputs the correct layout" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          LinkedHashMap(
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
    "add item to order" which {
      "accepts a menu item and adds it to empty order" in {
        val till = new Till(miniCoffeeConnection)
        till.addToOrder("Muffin Of The Day")
        till.order should contain only ("Muffin Of The Day" -> 1)
//        Other useful tests for same thing:
//        till.order should contain only ("Muffin Of The Day" -> 1)
//        till.order should have size 1
      }
      "throws an error if item is not on the menu" in {
        val till = new Till(miniCoffeeConnection)
        an [Exception] should be thrownBy till.addToOrder("Baked Alaska")
      }
    }
    "finalise order" which {
      "prints out the receipt statement" in {
        val stoppedClock = Clock.fixed(Instant.parse("2022-03-18T16:15:00.00Z"), ZoneId.systemDefault())
        val mockPrinterFactory = mock[ReceiptPrinterFactoryBase]
        val mockPrinter = mock[ReceiptPrinterBase]
        val till = new Till(miniCoffeeConnection, stoppedClock, mockPrinterFactory)
        till.addToOrder("Muffin Of The Day")
        till.addToOrder("Cafe Latte")

                (mockPrinterFactory.create(_,_,_)).expects(till.cafe, till.order, stoppedClock).returning(mockPrinter).once()
                (mockPrinter.receipt _).expects().returning(f"""The Coffee Connection, 123 Lakeside Way, 16503600708
                                                                 |18/03/2022 16:15
                                                                 |Item                    |Price
                                                                 |1 x Muffin Of The Day   |4.55
                                                                 |1 x Cafe Latte          |4.75
                                                                 |Total: 9.30
                                                                 |VAT (20%%): 1.86
                                                                 |Service not included :)""".stripMargin).anyNumberOfTimes() // Added .anyNumberOfTimes due to using println for visibility, .once would be more accurate
//        (() => mockPrinterFactory.create(_,_,_))
//          .stubs(till.cafe, till.order, stoppedClock)
//          .returning(mockPrinter)
//          .once()
//        (() => mockPrinter.receipt)
//          .stubs()
//          .returning(f"""The Coffee Connection, 123 Lakeside Way, 16503600708
//                        |18/03/2022 16:15
//                        |Item                    |Price
//                        |1 x Muffin Of The Day   |4.55
//                        |1 x Cafe Latte          |4.75
//                        |Total: 9.30
//                        |VAT (20%%): 1.86
//                        |Service not included :)""".stripMargin)
//          .once()
        till.finaliseOrder should be(f"""The Coffee Connection, 123 Lakeside Way, 16503600708
                                        |18/03/2022 16:15
                                        |Item                    |Price
                                        |1 x Muffin Of The Day   |4.55
                                        |1 x Cafe Latte          |4.75
                                        |Total: 9.30
                                        |VAT (20%%): 1.86
                                        |Service not included :)""".stripMargin)
      }
    }
  }
}