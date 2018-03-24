public class Rank implements Comparable<Rank> {
  private String position;
  private int position_weight;

  public Rank() {
    this.position = "CREWMAN"; 
    this.position_weight = 0; 
  }

  public Rank(String position) {
    this.position = position;

    if(position == "CREWMAN") {
      position_weight = 0;
    } else if (position == "ENSIGN") {
      position_weight = 1;
    } else if (position == "LIEUTENANT") {
      position_weight = 2;
    } else if (position == "COMMANDER") {
      position_weight = 3;
    } else if (position == "CAPTAIN") {
      position_weight = 4;
    } else if (position == "VICE_ADMIRAL") {
      position_weight = 5;
    } else if (position == "ADMIRAL") {
      position_weight = 6;
    } else {
      position = "CREWMAN";
      position_weight = 0;
    }
  }

  public int getPositionWeight() {
    return position_weight;
  }

  public int compareTo(Rank r) {
    return Integer.compare(this.getPositionWeight(), r.getPositionWeight());
  }

  public String toString() { 
    return position; 
  }
}
