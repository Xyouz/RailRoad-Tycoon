package fileWriter

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

object  Writer {
  def write(msg : String, fileName : String) = {
    val file = new File(fileName)
    if (!file.exists()){
      file.getParentFile().mkdirs()
    }
    try {
      // file.mkdirs()
      // file.createNewFile()
    } catch {
      case e1 : IOException => e1.printStackTrace()
    }
    try {
      val pw = new PrintWriter(file)
      pw.write(msg)
      pw.close()
    } catch {
      case e : FileNotFoundException => e.printStackTrace()
    }
  }

  def createFolder(folderName : String){
    (new File(folderName)).mkdirs()
  }
}
