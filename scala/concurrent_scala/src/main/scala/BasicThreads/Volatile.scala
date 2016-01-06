package BasicThreads

/**
 * Created by zhoufeng on 15/12/30.
 */
class Page(val txt: String, var position: Int)
object Volatile extends App {
  val pages = for (i <- 1 to 100) yield new Page("Na" * (100000 - 20 * i) + "Batman!", -1)
  // The modification to found will be seen in other threads immediately.
  @volatile var found = false
  for (page <- pages) yield thread {
    var i = 0
    while (i < page.txt.length && !found) {
      if (page.txt(i) == '!') {
        page.position = i
        found = true
      }
      else {
        i += 1
      }
    }
  }
  while(!found) {}
  log(s"results: ${pages.map(_.position)}")
}
