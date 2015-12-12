package BasicThreads

/**
 * Created by zhoufeng on 15/12/12.
 */
import scala.collection.mutable
object SynchronizedPool extends App {
  private val tasks = mutable.Queue[() => Unit]()
  object Worker extends Thread {
      /*
        Generally, a JVM process does not stop when the main thread terminates.
        The JVM process terminates when all non-daemon threads terminate.
      */
      setDaemon(true)
    def poll(): () => Unit = tasks.synchronized {
      while (tasks.isEmpty) tasks.wait()
      tasks.dequeue()
    }

    override def run() = while (true) {
      val task = poll()
      task()
    }
  }
  Worker.start()

  def asynchronous(body: => Unit) = tasks.synchronized {
    tasks.enqueue(() => body)
    tasks.notify()
  }

  asynchronous { log("hello") }
  asynchronous { log("World") }
  Thread.sleep(1000)

}
