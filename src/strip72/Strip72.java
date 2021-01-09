package strip72;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * <p>Title: Strip72</p>
 * <p>Description: Clears all data past column 72</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: IBM</p>
 * @author Christopher Brooker
 * @version 1.0
 */

public class Strip72 extends JFrame {
  public static JFrame parent;
  public Strip72() throws HeadlessException {
    super("Strip 72:");
    parent=this;
  }
  public static void main(String[] args) throws HeadlessException {
    Strip72 myStrip72 = new Strip72();
    JFileChooser jfc=new JFileChooser();
    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    myStrip72.setSize(800,600);

    myStrip72.show();
    int returnVal = jfc.showOpenDialog(parent);
    if (returnVal == JFileChooser.APPROVE_OPTION)
    {
      //JOptionPane.showMessageDialog(null,
      //                              "You just selected " +
      //                              jfc.getSelectedFile().getName());
      File folder = jfc.getSelectedFile();
      File[] files = folder.listFiles();
      String fileList = "";
      for (int i = 0; i < files.length; i++) {

        try {
          String oldFileName=files[i].getAbsolutePath();
          File newFile=new File(files[i].getAbsolutePath() + "_Strip72.txt");
          BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
          BufferedReader br = new BufferedReader(new FileReader(files[i]));
          do {
            String tempLine = br.readLine();
            //System.out.println(tempLine);
            tempLine = tempLine.substring(0, 72) +
                tempLine.substring(tempLine.length());
            //System.out.println(tempLine);
            bw.write(tempLine);
            bw.newLine();
          }
          while (br.ready());
          br.close();
          bw.close();
          files[i].delete();
          newFile.renameTo(new File(oldFileName));
        }
        catch (FileNotFoundException fnfe) {
          fnfe.printStackTrace();
        }
        catch (IOException ioe) {
          ioe.printStackTrace();
        }
      } //end for
      JOptionPane.showMessageDialog(parent,""+files.length+" files have had their sequence numbers removed!"
                                    ,"Operation Complete",JOptionPane.INFORMATION_MESSAGE);

    } //end if
    else
    {
      JOptionPane.showMessageDialog(null,
                                    "You just cancelled");
    }

  }

  /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e)
  {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING)
    {
      System.exit(0);
    }
  }


}
