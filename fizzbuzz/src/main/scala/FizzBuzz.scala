import scala.collection.mutable.ArrayBuffer

object FizzBuzz {
  def generate(number: Int): String = {

    val nums = ArrayBuffer[Int](1)

    for (i <- 2 to number) {
      nums += i
    }

    val fizzedNums: ArrayBuffer[String] = nums.map(num => if (num % 3 == 0) "Fizz" else if (num % 5 == 0) "Buzz" else num.toString())

    val result = fizzedNums.mkString(", ")

    return result;
  }
}