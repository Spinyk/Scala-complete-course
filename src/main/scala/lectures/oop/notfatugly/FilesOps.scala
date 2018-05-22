package lectures.oop.notfatugly

import java.security.MessageDigest

object FilesOps {
  def hash(s: String): String = {
    MessageDigest.getInstance("SHA-1").digest(s.getBytes("UTF-8")).map("%02x".format(_)).mkString
  }
}
