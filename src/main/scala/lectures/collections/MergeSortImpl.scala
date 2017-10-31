package lectures.collections

/**
  * Постарайтесь не использовать мутабильные коллекции и var
  * Подробнее о сортировке можно подсмотреть здесь - https://en.wikipedia.org/wiki/Merge_sort
  *
  */
object MergeSortImpl extends App {

  def mergeSort(data: Seq[Int]): Seq[Int] = {
    val middle = data.length / 2
    if (middle == 0) data
    else {
      val (left, right) = data.splitAt(middle)
      merge(mergeSort(left), mergeSort(right))
    }
  }

  private def merge(left: Seq[Int], right: Seq[Int]): Seq[Int] = (left, right) match {
    case (Nil, right) => right
    case (left, Nil) => left
    case (leftTop :: left1, rightTop :: right1) =>
      if (leftTop < rightTop) leftTop +: merge(left1, right)
      else rightTop +: merge(left, right1)
  }

  print(mergeSort(Seq(1, 2, 9, 4, 7, 3, 5, 8, 6)))

}
