import scala.collection.mutable.ArrayBuffer

object FizzBuzz {
  def generate(number: Int): String = {

    val nums = createArrayBufferOneToNumber(number)

    val fizzedNums: ArrayBuffer[String] = nums.map(fizzBuzzify)

    val result = fizzedNums.mkString(", ")

    return result;
  }

  def createArrayBufferOneToNumber(num: Int): ArrayBuffer[Int] = {
    val nums = ArrayBuffer[Int]()

    for (i <- 1 to num) {
      nums += i
    }
    return nums
  }

  def fizzBuzzify(num: Int): String = {
    if (num % 15 == 0) "FizzBuzz" else if (num % 3 == 0) "Fizz" else if (num % 5 == 0) "Buzz" else num.toString()
  }
}