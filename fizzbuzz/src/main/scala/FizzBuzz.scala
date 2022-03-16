import scala.collection.mutable.ArrayBuffer

object FizzBuzz {
  def generate(number: Int): String = {

    val nums = ArrayBuffer[String]("1")

    for (i <- 2 to number) {
      nums += i.toString()
    }

    val fizzedNums = nums.map(num => {if (num == "3") "Fizz" else num})

    val result = fizzedNums.mkString(", ")

    return result;
  }
}