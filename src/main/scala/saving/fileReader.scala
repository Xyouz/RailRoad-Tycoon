package fileReader

import scala.io.Source
import java.io.File

object  Reader {
  def read(fileName : String) = {
    Source.fromFile(fileName).mkString
  }

  def read(file : File) = {
    Source.fromFile(file).mkString
  }
}
