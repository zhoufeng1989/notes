package BasicThreads

/**
 * Created by zhoufeng on 15/12/12.
 */

object ThreadsUid extends App {
  var uid = 0L
  def getUniqueId() = this.synchronized {
    val newUid = uid + 1
    uid = newUid
    newUid
  }

  def printUniqueIds(n: Int): Unit = {
    val uids = for (i <- 0 until n) yield getUniqueId()
    log(s"Generated uids: $uids")
  }

  val t = thread {printUniqueIds(5)}
  printUniqueIds(5)
  t.join()
}
