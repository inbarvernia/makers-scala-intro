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

  test("passes acceptanct criteria: replaces correctly up to 100") {
    assert(FizzBuzz.generate(100) === "1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz, 11, Fizz, 13, 14, FizzBuzz, 16, 17, Fizz, 19, Buzz, Fizz, 22, 23, Fizz, Buzz, 26, Fizz, 28, 29, FizzBuzz, 31, 32, Fizz, 34, Buzz, Fizz, 37, 38, Fizz, Buzz, 41, Fizz, 43, 44, FizzBuzz, 46, 47, Fizz, 49, Buzz, Fizz, 52, 53, Fizz, Buzz, 56, Fizz, 58, 59, FizzBuzz, 61, 62, Fizz, 64, Buzz, Fizz, 67, 68, Fizz, Buzz, 71, Fizz, 73, 74, FizzBuzz, 76, 77, Fizz, 79, Buzz, Fizz, 82, 83, Fizz, Buzz, 86, Fizz, 88, 89, FizzBuzz, 91, 92, Fizz, 94, Buzz, Fizz, 97, 98, Fizz, Buzz")
  }

}