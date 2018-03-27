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

  def foreach(f: BST => Unit): Unit
}

object BSTImpl {
  private val powers = {
    val powersOf2 = (0 to 20).map(Math.pow(2, _))
    val sums = scala.collection.mutable.ArrayBuffer(0d)
    powersOf2.foreach(e => sums append (sums.last + e))
    sums.toSet
  }
}

case class BSTImpl(value: Int,
                   left: Option[BSTImpl] = None,
                   right: Option[BSTImpl] = None) extends BST {

  import BSTImpl.powers

  def add(newValue: Int): BSTImpl =
    if (value == newValue) this
    else if (value < newValue) BSTImpl(value, left, right.map(_.add(newValue)).orElse(Some(BSTImpl(newValue, None, None))))
    else BSTImpl(value, left.map(_.add(newValue)).orElse(Some(BSTImpl(newValue, None, None))), right)

  def find(value: Int): Option[BSTImpl] =
    if (value == this.value) Some(this)
    else if (value > this.value) right.flatMap(_.find(value))
    else left.flatMap(_.find(value))

  def foreach(f: BST => Unit): Unit = {
    def traverse(acc: List[BSTImpl]): Unit = acc match {
      case Nil => None
      case node :: rest =>
        f(node)
        traverse(rest ++ node.left ++ node.right)
    }

    traverse(List(this))
  }

  override def toString = {
    val sb = new mutable.StringBuilder()
    var counter = 0
    this.foreach(bst => {
      sb ++= s"${if (powers.contains(counter.toDouble)) "\n" else ""}${bst.value} "
      counter = counter + 1
    })
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