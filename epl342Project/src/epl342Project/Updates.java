package epl342Project;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Updates {
	enum type { 
		invalid,photos, albums, links, events,videos;

	} 
	int updateid;
	String   upType   ;   
    type upClass ;    //(photos, albums, links, events, video)
    int userid;
    LocalDateTime date;
    
    public Updates(Object[] updateRS) {
    	updateid=(int) updateRS[0];
    	upType=(String) updateRS[1];
  //  	System.out.println(updateRS[2].toString());
    	upClass=(type)type.valueOf( updateRS[2].toString());
   // 	System.out.println(upClass);
    	userid=(int) updateRS[3];
   	    date=((java.sql.Timestamp) updateRS[4]).toLocalDateTime();
    }
    
    
    public String toString() {
    	return updateid+" "+upType+" "+upClass+" "+userid;
    }
    
    
}
