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

  override def toString = {
    def traverse(acc: List[BSTImpl])(sb: mutable.StringBuilder): Unit = acc match {
      case Nil => None
      case node :: rest => traverse(rest ++ node.left ++ node.right)(sb ++= s"${node.value} \n${node.left.map(_.value)} ${node.right.map(_.value)} \n")
    }

    val sb = new mutable.StringBuilder()
    traverse(List(this))(sb)
    sb.toString
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