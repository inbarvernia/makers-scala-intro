import org.scalatest.funsuite.AnyFunSuite

class FizzBuzzTest extends AnyFunSuite {
  test("lists the numbers up to one") {
    assert(FizzBuzz.generate(1) === "1")
  }

  test("lists the numbers up to two") {
    assert(FizzBuzz.generate(2) === "1, 2")
  }

  test("replaces three with Fizz") {
    assert(FizzBuzz.generate(3) === "1, 2, Fizz")
  }

  test("replaces five with Buzz") {
    assert(FizzBuzz.generate(5) === "1, 2, Fizz, 4, Buzz")
  }
}