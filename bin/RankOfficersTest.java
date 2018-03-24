public class RankOfficersTest {
  public static void main(String[] args) {
    Officer hank = new Officer("Hank", new Rank("CREWMAN"));
    Officer tim = new Officer("Tim", new Rank("COMMANDER"));

    Logging logging = new Logging();
    tim.sendMessage(hank,"I'm sending you am message", true, logging);

    System.out.println(hank);
    System.out.println(hank.compareTo(tim));
    System.out.println(logging);
  }
}
