package solution

object Main extends App {

  def prepare2dVector(): Vector[Vector[Int]] = {
    val file = scala.io.Source.fromURI(getClass.getResource(s"/data.txt").toURI)
    file.getLines().toVector.map(_.split(" ").toVector.map(_.toInt))
  }

  def traverseNodes(layers: Vector[Vector[Int]], memo: scala.collection.mutable.HashMap[(Int, Int), Int], maximum: Int, level: Int = 0, position: Int = 0, sum: Int = 0): Int = {
    memo.getOrElseUpdate(level -> position, {
      if (level == maximum) {
        // if there are no leafs and level is lowest -> return current sum, end of recursion
        sum
      } else {
        // updating state adding current child value
        val nextSum = sum + layers(level)(position)
        val nextLevel = level + 1
        // calculate sum with each new child
        val right = traverseNodes(layers, memo, maximum, nextLevel, position, nextSum)
        val left = traverseNodes(layers, memo, maximum, nextLevel, position + 1, nextSum)
        // looking for minimum sum
        val currentValue = Math.min(right, left)
        // memoization of current state to prevent from re-calculating
        memo.put(level -> position, currentValue)
        currentValue
      }
    })
  }

  def calculateMinimumPath(input: Vector[Vector[Int]]): Int = {
    val dictionary = scala.collection.mutable.HashMap.empty[Tuple2[Int, Int], Int]
    traverseNodes(input, dictionary, input.size)
  }

  val input = prepare2dVector()
  val startTime = System.currentTimeMillis()

  calculateMinimumPath(input)

  println(s"elapsed - ${System.currentTimeMillis() - startTime} millis")
}

