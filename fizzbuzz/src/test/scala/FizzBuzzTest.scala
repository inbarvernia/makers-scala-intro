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

  test("replaces multiples of three with Fizz") {
    assert(FizzBuzz.generate(6) === "1, 2, Fizz, 4, Buzz, Fizz")
  }

  test("replaces multiples of five with Buzz") {
    assert(FizzBuzz.generate(10) === "1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz")
  }

  test("replaces fifteen with FizzBuzz") {
    assert(FizzBuzz.generate(15) === "1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz, 11, Fizz, 13, 14, FizzBuzz")
  }

  test("replaces multiples of fifteen with FizzBuzz") {
    assert(FizzBuzz.generate(30) ===  "1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz, 11, Fizz, 13, 14, FizzBuzz, 16, 17, Fizz, 19, Buzz, Fizz, 22, 23, Fizz, Buzz, 26, Fizz, 28, 29, FizzBuzz")
  }
}