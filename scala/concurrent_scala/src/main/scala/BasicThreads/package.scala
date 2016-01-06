/**
 * Created by zhoufeng on 15/12/12.
 */
package object BasicThreads {
  def thread(body: =>Unit): Thread = {
    val t = new Thread {
      override def run() = body
    }
    t.start()
    t
  }

  def log(msg: String): Unit = {
    println(s"${Thread.currentThread.getName}: $msg")
  }
}
