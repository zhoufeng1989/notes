package BasicThreads

/**
 * Created by zhoufeng on 15/12/12.
 */
object ThreadsCreation extends App {
  class MyThread extends Thread {
    override def run(): Unit = {
      val name = this.getName()
      val id = this.getId()
      println(s"thread $id running, with name $name")
    }
  }
  val thread = new MyThread
  thread.start()
  // join operation puts the main thread into the waiting state until thread terminates.
  thread.join()
  println("New Thread joined")
}
