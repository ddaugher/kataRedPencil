class RedPencilPromotion {

  var price: Double = 0.0
  var previousPrice: Double = 0.0
  var duration: Int = 30

  def setNewPrice(n: Double) {
    price = n
    duration = 0
  }
  def setNewDuration(n: Int) {
    duration = n
    if (duration >= 30) {
      price = previousPrice
    }
  }

  def initialize() {
    price = 0.0
    duration = 30
  }

  def reducePriceByPercentage(percentage: Int) {

    if (percentage < 5 || percentage > 30) throw new IllegalReduction
    if (duration < 30) throw new IllegalDuration

    val newPrice: Double = (100 - percentage) / 100.00 * price

    if (newPrice < price && price < previousPrice) {
    } else {
      previousPrice = price
      duration = 0
    }

    price = newPrice
  }
}
