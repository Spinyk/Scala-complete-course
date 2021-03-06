package lectures.oop

import scala.collection.mutable

/**
  * BSTImpl - это бинарное дерево поиска, содержащее только значения типа Int
  *
  * * Оно обладает следующими свойствами:
  * * * * * левое поддерево содержит значения, меньшие значения родителя
  * * * * * правое поддерево содержит значения, большие значения родителя
  * * * * * значения, уже присутствующие в дереве, в него не добавляются
  * * * * * пустые значения (null) не допускаются
  *
  * * Завершите реализацию методов кейс класс BSTImpl:
  * * * * * Трейт BST и BSTImpl разрешается расширять любым образом
  * * * * * Изменять сигнатуры классов и методов, данные в условии, нельзя
  * * * * * Постарайтесь не использовать var и мутабильные коллекции
  * * * * * В задаче про распечатку дерева, нужно раскомментировать и реализовать метод toString()
  *
  * * Для этой структуры нужно реализовать генератор узлов.
  * * Генератор:
  * * * * * должен создавать дерево, содержащее nodesCount узлов.
  * * * * * не должен использовать переменные или мутабильные структуры.
  *
  */
trait BST {
  val value: Int
  val left: Option[BST]
  val right: Option[BST]

  def add(newValue: Int): BST

  def find(value: Int): Option[BST]

  def foreach(f: Option[BST] => Unit): Unit
}

case class BSTImpl(value: Int,
                   left: Option[BSTImpl] = None,
                   right: Option[BSTImpl] = None) extends BST {

  def add(newValue: Int): BSTImpl =
    if (value == newValue) this
    else if (value < newValue) BSTImpl(value, left, right.map(_.add(newValue)).orElse(Some(BSTImpl(newValue, None, None))))
    else BSTImpl(value, left.map(_.add(newValue)).orElse(Some(BSTImpl(newValue, None, None))), right)

  def find(value: Int): Option[BSTImpl] =
    if (value == this.value) Some(this)
    else if (value > this.value) right.flatMap(_.find(value))
    else left.flatMap(_.find(value))

  def foreach(f: Option[BST] => Unit): Unit = {
    def traverse(acc: List[Option[BSTImpl]]): Unit = acc match {
      case Nil => None
      case node :: rest =>
        f(node)
        traverse(rest ++ node.map(_.left).orElse(None) ++ node.map(_.right).orElse(None))
    }

    traverse(List(Some(this)))
  }

  override def toString = {
    def toLineView(bst: BSTImpl) = {
      val sb = new mutable.StringBuilder()
      bst.foreach(_ match {
        case Some(bst) => sb ++= s"${bst.value} "
        case None => sb ++= "None "
      })
      sb.toString()
    }

    def toStairView(line: String) = {
      def traverse(acc: List[String], ys: List[String], power: Int): List[String] = acc match {
        case Nil => ys
        case acc => traverse(
            acc = acc.drop(Math.pow(2, power).toInt),
            ys = ys :+ acc.take(Math.pow(2, power).toInt).foldLeft(new mutable.StringBuilder())((sb, s) => sb ++= s"$s ").toString,
            power = power + 1)
      }

      traverse(line.split(" ").toList, List(), 0)
    }

    def toTreeView(stair: Seq[String]) = {
      stair
        .zip((0 until stair.size).map("\t" * _).reverse)
        .map { case (line, tab) => s"$tab$line\n" }
        .foldLeft(new mutable.StringBuilder())(_ ++= _)
        .toString
    }

    (toLineView _ andThen toStairView andThen toTreeView) (this)
  }
}

object TreeTest extends App {

  def generate(nodesCounts: Int, root: BST): BST = {
    var tree = root
    for (i <- 1 to nodesCounts)
      tree = tree.add((Math.random() * maxValue).toInt)
    tree
  }

  val sc = new java.util.Scanner(System.in)
  val maxValue = 110000
  val nodesCount = sc.nextInt()

  val markerItem = (Math.random() * maxValue).toInt
  val markerItem2 = (Math.random() * maxValue).toInt
  val markerItem3 = (Math.random() * maxValue).toInt

  // Generate huge tree
  val root: BST = BSTImpl(maxValue / 2)
  val tree: BST = generate(nodesCount, root)

  // add marker items
  val testTree = tree.add(markerItem).add(markerItem2).add(markerItem3)

  // check that search is correct
  require(testTree.find(markerItem).isDefined)
  require(testTree.find(markerItem).isDefined)
  require(testTree.find(markerItem).isDefined)

  println(testTree)
}