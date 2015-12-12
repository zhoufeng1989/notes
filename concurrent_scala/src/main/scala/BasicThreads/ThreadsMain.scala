package BasicThreads

/**
 * Created by zhoufeng on 15/12/12.
 */
object ThreadsMain extends App {
  val thread = Thread.currentThread()
  val name = thread.getName
  val id = thread.getId
  println(s"I am the thread $id, with name $name.")
}
