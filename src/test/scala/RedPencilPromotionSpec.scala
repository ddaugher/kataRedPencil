import org.scalatest.{BeforeAndAfter, FunSuite}

class RedPencilPromotionSpec extends FunSuite with BeforeAndAfter {

  var impl: RedPencilPromotion = _

  before {
    impl = new RedPencilPromotion
  }

  test("creating new promotion should initialize price to zero") {
    assert(impl.price === 0.00)
  }

  test("setting price on promotion saves new price") {
    impl.price = 10.00
    assert(impl.price === 10.00)
  }

  test("should be able to initialize promotion back to zero") {
    impl.price = 10.00
    impl.initialize()
    assert(impl.price === 0.0)
  }

  test("creating new promotion should initialize duration to 30") {
    assert(impl.duration === 30)
  }

  test("should be able to set duration and retrieve new value") {
    impl.setNewDuration(45)
    assert(impl.duration === 45)
  }

  test("should reduce price by 5 percent") {
    impl.price = 100
    impl.reducePriceByPercentage(5)
    assert(impl.price === 95)
  }

  test("should throw exception when less than 5 percent") {
    intercept[IllegalReduction] {
      impl.reducePriceByPercentage(4)
    }
  }

  test("should throw exception when more than 30 percent") {
    intercept[IllegalReduction] {
      impl.reducePriceByPercentage(31)
    }
  }

  test("should not reduce price if duration is less than 30") {
    impl.price = 100.0
    impl.setNewDuration(29)
    intercept[IllegalDuration] {
      impl.reducePriceByPercentage(20)
    }
  }

  test("should reset duration after reduction") {
    impl.setNewDuration(30)
    impl.reducePriceByPercentage(20)
    assert(impl.duration === 0)
  }

  test("should reset back to previous price after 30 days") {
    impl.price = 100
    impl.reducePriceByPercentage(10)
    assert(impl.price === 90)
    impl.setNewDuration(30)
    assert(impl.price === 100)
  }

  test("should end promotion when price increase occurs") {
    impl.price = 100
    impl.reducePriceByPercentage(10)
    assert(impl.price === 90)
    impl.duration = 15
    assert(impl.duration === 15)
    impl.setNewPrice(95)
    assert(impl.price === 95)
    assert(impl.duration === 0)
  }

  test("should not prolong promotion with further price reduction") {
    impl.price = 100
    impl.reducePriceByPercentage(10)
    assert(impl.price === 90)
    impl.duration = 30
    impl.reducePriceByPercentage(10)
    assert(impl.duration === 30)
    assert(impl.price === 81)
  }

}