import scala.collection.mutable.ArrayBuffer

object FizzBuzz {
  def generate(number: Int): String = {

    (createIntArrayUpToNumber andThen mapToFizzBuzz andThen convertListToString)(number)
  }

  val createIntArrayUpToNumber = (num: Int) => {
    (1 to num).toBuffer
  }

  val fizzBuzzify = (num: Int) => {
    if (num % 15 == 0) "FizzBuzz" else if (num % 3 == 0) "Fizz" else if (num % 5 == 0) "Buzz" else num.toString()
  }

  val mapToFizzBuzz = (arr: Iterable[Int]) => {
    arr.map(fizzBuzzify)
  }

  val convertListToString = (arr: Iterable[String]) => {
    arr.mkString(", ")
  }
}