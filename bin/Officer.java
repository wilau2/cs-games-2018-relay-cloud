public class Officer implements Comparable<Officer> {
  private String name;
  private Rank rank;
  private String message_received;
  
  public Officer() {
    this.rank = new Rank();  
  }

  public Officer(Rank rank) {
    this.name = "Penguin";
    this.rank = rank;  
  } 

  public Officer(String name, Rank rank) {
    this.name = name;
    this.rank = rank;  
  }

  public String getName() {
    return name;
  }
  
  public Rank getRank() {
    return rank;
  }

  public void sendMessage(Officer otherOfficer, String message, boolean replyAuthorization, Logging logs) {
    if(this.compareTo(otherOfficer) == 1) {
      logs.addLog(message);
      otherOfficer.getMessage(message,replyAuthorization, this, logs);
    }
  }

  public void getMessage(String message_received, boolean replyAuthorization, Officer sender, Logging logs) {
    this.message_received = message_received;
    if(replyAuthorization) {
      this.sendMessage(sender,"Message received by " + this.toString(), false, logs);
      logs.addLog("Message received by " + this.toString());
    }
  }

  public String getMessageReceived() {
    return message_received;
  }

  public int compareTo(Officer otherOfficer) {
    return this.getRank().compareTo(otherOfficer.getRank());
  }

  public String toString() {
    return "My name is " + name + " and my rank is " + rank;  
  }
}
