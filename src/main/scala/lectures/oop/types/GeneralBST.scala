package lectures.oop.types

import lectures.matching.SortingStuff
import lectures.matching.SortingStuff.Watches

/**
  * Модифицируйте реализацию BSTImpl из предыдущего задания.
  * Используя тайп параметры и паттерн Type Class, реализуйте GeneralBSTImpl таким образом,
  * чтобы дерево могло работать с произвольным типом данных.
  *
  * Наследников GeneralBSTImpl определять нельзя.
  *
  * Создайте генератор для деревьев 3-х типов данных:
  * * * * float
  * * * * String
  * * * * Watches из задачи SortStuff. Большими считаются часы с большей стоимостью
  */

trait GeneralBST[T] {
  val value: T
  val left: Option[GeneralBST[T]]
  val right: Option[GeneralBST[T]]

  def add(newValue: T): GeneralBST[T]

  def find(value: T): Option[GeneralBST[T]]
}

class GeneralBSTImpl[T](val value: T,
                        val left: Option[GeneralBST[T]] = None,
                        val right: Option[GeneralBST[T]] = None)
                       (implicit ordering: Ordering[T]) extends GeneralBST[T] {

  override def find(value: T): Option[GeneralBST[T]] =
    if (ordering.equiv(value, this.value)) Some(this)
    else if (ordering.gt(value, this.value)) right.flatMap(_.find(value))
    else left.flatMap(_.find(value))

  override def add(newValue: T): GeneralBST[T] =
    if (ordering.equiv(value, newValue)) this
    else if (ordering.lt(value, newValue)) new GeneralBSTImpl[T](value, left, right.map(_.add(newValue)).orElse(Some(new GeneralBSTImpl[T](newValue, None, None))))
    else new GeneralBSTImpl[T](value, left.map(_.add(newValue)).orElse(Some(new GeneralBSTImpl[T](newValue, None, None))), right)

}

object FloatTreeTest extends App {

  import scala.Ordering.Implicits

  def generate(nodesCounts: Int, root: GeneralBST[Float]): GeneralBST[Float] = {
    var tree = root
    for (i <- 1 to nodesCounts)
      tree = tree.add((Math.random() * maxValue).toFloat)
    tree
  }

  val sc = new java.util.Scanner(System.in)
  val maxValue = 110000
  val nodesCount = sc.nextInt()

  val markerItem = (Math.random() * maxValue).toFloat
  val markerItem2 = (Math.random() * maxValue).toFloat
  val markerItem3 = (Math.random() * maxValue).toFloat

  // Generate huge tree
  val root: GeneralBST[Float] = new GeneralBSTImpl[Float](maxValue / 2)
  val tree: GeneralBST[Float] = generate(nodesCount, root)

  // add marker items
  val testTree = tree.add(markerItem).add(markerItem2).add(markerItem3)

  // check that search is correct
  require(testTree.find(markerItem).isDefined)
  require(testTree.find(markerItem).isDefined)
  require(testTree.find(markerItem).isDefined)
}

object StringTreeTest extends App {

  import scala.Ordering.Implicits

  def generate(nodesCounts: Int, root: GeneralBST[String]): GeneralBST[String] = {
    var tree = root
    for (i <- 1 to nodesCounts)
      tree = tree.add((Math.random() * maxValue).toString)
    tree
  }

  val sc = new java.util.Scanner(System.in)
  val maxValue = 11100000
  val nodesCount = sc.nextInt()

  val markerItem = (Math.random() * maxValue).toString
  val markerItem2 = (Math.random() * maxValue).toString
  val markerItem3 = (Math.random() * maxValue).toString

  // Generate huge tree
  val root: GeneralBST[String] = new GeneralBSTImpl[String]("rootString")
  val tree: GeneralBST[String] = generate(nodesCount, root)

  // add marker items
  val testTree = tree.add(markerItem).add(markerItem2).add(markerItem3)

  // check that search is correct
  require(testTree.find(markerItem).isDefined)
  require(testTree.find(markerItem).isDefined)
  require(testTree.find(markerItem).isDefined)
}

object WatchesTreeTest extends App {

  implicit def ordering[Watches]: Ordering[SortingStuff.Watches] = Ordering.by(watches => watches.cost)

  def generate(nodesCounts: Int, root: GeneralBST[Watches]): GeneralBST[Watches] = {
    var tree = root
    for (i <- 1 to nodesCounts)
      tree = tree.add(Watches("watches", (Math.random() * maxValue).toFloat))
    tree
  }

  val sc = new java.util.Scanner(System.in)
  val maxValue = 110000
  val nodesCount = sc.nextInt()

  val markerItem = Watches("watches", (Math.random() * maxValue).toFloat)
  val markerItem2 = Watches("watches", (Math.random() * maxValue).toFloat)
  val markerItem3 = Watches("watches", (Math.random() * maxValue).toFloat)

  // Generate huge tree
  val root: GeneralBST[Watches] = new GeneralBSTImpl[Watches](Watches("rootWatches", maxValue / 2))
  val tree: GeneralBST[Watches] = generate(nodesCount, root)

  // add marker items
  val testTree = tree.add(markerItem).add(markerItem2).add(markerItem3)

  // check that search is correct
  require(testTree.find(markerItem).isDefined)
  require(testTree.find(markerItem).isDefined)
  require(testTree.find(markerItem).isDefined)
}