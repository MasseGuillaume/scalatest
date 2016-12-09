import collection.mutable.Stack

// import org.scalatest._



object Main {
  def main(args: Array[String]): Unit = {
    org.scalatest.run(new SetSuite)  
  }
}

import org.scalatest.FunSuite

class SetSuite extends FunSuite {

  // import org.scalactic.Prettified.default

  test("An empty Set should have size 0") {
    Predef.assert(Set.empty.size == 0)
  }

  test("Invoking head on an empty Set should produce NoSuchElementException") {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }
}
