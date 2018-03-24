import java.util.ArrayList;

public class Logging {
  private ArrayList<String> logs;

  public Logging() {
    logs = new ArrayList<String>();
  }

  public void addLog(String log) {
    logs.add(log);
  }

  public String toString() {
    String retVal = "Manifest:";
    for(String log : logs) {
      retVal += "\n "+ log;  
    }
    return retVal;
  }
}
