import scala.collection.mutable.ArrayBuffer

object FizzBuzz {
  def generate(number: Int): String = {

    val nums = ArrayBuffer[Int](1)

    for (i <- 2 to number) {
      nums += i
    }

    val result = nums.mkString(", ")

    return result;
  }
}