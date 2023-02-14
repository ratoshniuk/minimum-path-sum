package solution

object Main extends App {

  def prepare2dVector(): Vector[Vector[Int]] = {
    val file = scala.io.Source.fromURI(getClass.getResource(s"/data.txt").toURI)
    file.getLines().toVector.map(_.split(" ").toVector.map(_.toInt))
  }

  def traverseNodes(layers: Vector[Vector[Int]], maximum: Int, level: Int = 0, position: Int = 0, sum: Int = 0): Int = {
    if (level == maximum) {
      sum
    } else {
      val nextSum = sum + layers(level)(position)
      val nextLevel = level + 1
      val right = traverseNodes(layers, maximum, nextLevel, position, nextSum)
      val left = traverseNodes(layers, maximum, nextLevel, position + 1, nextSum)
      val currentValue = Math.min(right, left)
      currentValue
    }
  }

  val input = prepare2dVector()
  val startTime = System.currentTimeMillis()

  println(traverseNodes(input, input.size))
  println(s"elapsed - ${System.currentTimeMillis() - startTime} millis")
}

