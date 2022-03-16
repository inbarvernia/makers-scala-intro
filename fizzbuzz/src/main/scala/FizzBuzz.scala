import scala.collection.mutable.ArrayBuffer

object FizzBuzz {
  def generate(number: Int): String = {

    if (number == 1) "1"

    val nums = ArrayBuffer[Int]()

    for (i <- 1 to number) {
      nums += i
    }

    val fizzedNums: ArrayBuffer[String] = nums.map(num => if (num % 15 == 0) "FizzBuzz" else if (num % 3 == 0) "Fizz" else if (num % 5 == 0) "Buzz" else num.toString())

    val result = fizzedNums.mkString(", ")

    return result;
  }
}