public class Times
{
   private int hour;
   private int minute;
   private int second;
	
   public Times(int hour, int minute, int second)
   {
      this.hour = hour;
      this.minute = minute;
      this.second = second;
   }
   
   public int getHour() {
      return hour;}
   public int getMin() {
      return minute;}
   public int getSec() {
      return second;}
      
   public int getTotal(){
      int total = (hour * 3600) + (minute * 60) + second;
      return total;
   }
}