package dev.aoiroaoino.sandbox.stone

import java.io.{BufferedReader, FileNotFoundException, FileReader, Reader}
import javax.swing.{JFileChooser, JOptionPane, JScrollPane, JTextArea}

/**
 * リスト3.6
 */
class CodeDialog extends Reader {
  private var buffer: String = null
  private var pos: Int = 0

  override def read(cbuf: Array[Char], off: Int, len: Int): Int = {
    if (buffer == null) {
      val in = showDialog()
      if (in == null) {
        return -1
      } else {
        println(in)
        buffer = in + "\n"
        pos = 0
      }
    }
    var size = 0
    val length = buffer.length
    while(pos < length && size < len) {
      cbuf(off + size) = buffer.charAt(pos)
      size += 1
      pos += 1
    }
    if (pos == length) { buffer = null }
    size
  }

  override def close(): Unit = ()

  private def showDialog(): String = {
    val area = new JTextArea(20, 40)
    val pane = new JScrollPane(area)
    val result = JOptionPane.showOptionDialog(
      null,
      pane,
      "Input",
      JOptionPane.OK_CANCEL_OPTION,
      JOptionPane.PLAIN_MESSAGE,
      null,
      null,
      null
    )
    if (result == JOptionPane.OK_OPTION)
      area.getText()
    else
      null
  }
}

object CodeDialog {

  @throws[FileNotFoundException]
  def file(): Reader = {
    val chooser = new JFileChooser()
    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
      new BufferedReader(new FileReader(chooser.getSelectedFile))
    else
      throw new FileNotFoundException("no file specified")
  }
}
