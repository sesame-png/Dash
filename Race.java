public class Race{
   private String name;
   private Dates date;
   private String type;
   private double distance;
   private Times time;
   private String tags;
      
   public Race() {
      name = "Race Name";
      type = "race type";
      distance = 0.0;
      tags = "tags";
   }
   
   public Race(String nm, Dates dt, String tp, double di, Times ti, String tg) {
      name = nm;
      date = dt;
      type = tp;
      distance = di;
      time = ti;
      tags = tg;
   }
   
   public void setName(String nm) {
      name = nm;
   }
   public void setDate(Dates dt) {
      date = dt;
   }
   public void setType(String tp) {
      type = tp;
   }
   public void setDistance(double di) {
      distance = di;
   }
   public void setTime(Times ti) {
      time = ti;
   }
   public void setTags(String tg){
      tags = tg;
   }
   
   public String getName() {
      return name;
   }
   public Dates getDate() {
      return date;
   }
   public String getType() {
      return type;
   }
   public double getDistance() {
      return distance;
   }
   public Times getTime() {
      return time;
   }
   public String getTags() {
      return tags;
   }
}