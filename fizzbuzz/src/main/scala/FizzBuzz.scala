import scala.collection.mutable.ArrayBuffer

object FizzBuzz {
  def generate(number: Int): String = {

    (createIntArrayUpToNumber andThen mapToFizzBuzz andThen convertListToString)(number)
  }

  val createIntArrayUpToNumber = (num: Int) => {
    val nums = ArrayBuffer[Int]()

    for (i <- 1 to num) {
      nums += i
    }
    nums
  }

  val fizzBuzzify = (num: Int) => {
    if (num % 15 == 0) "FizzBuzz" else if (num % 3 == 0) "Fizz" else if (num % 5 == 0) "Buzz" else num.toString()
  }

  val mapToFizzBuzz = (arr: ArrayBuffer[Int]) => {
    arr.map(fizzBuzzify)
  }

  val convertListToString = (arr: ArrayBuffer[String]) => {
    arr.mkString(", ")
  }
}