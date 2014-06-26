///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                                                       //
// Name:蔡佳蓁                                                                                                           //
//                                                                                                                       //
// Student ID:F74004096                                                                                                  //
//                                                                                                                       //
//   Description : A program to parse real-price housing information in our country                                      //
//				and find the average of all sale prices matching some condition.                         //
//                                                                                                                       //
//                                                                                                                       //
// Input Argument : url, area, road name, year                                                                           //
//				ex : http://www.datagarage.io/api/5365dee31bc6e9d9463a0057 文山區 辛亥路 103             // 
//                                                                                                                       //
// Output : the average of all sale prices matching the condition                                                        //
//                                                                                                                       //
//                                                                                                                       //
// Date : 2014/6/26                                                                                                      //
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.*;

public class TocHw3 {

	public static void main(String[] args) {
        
    try
	{
		// read json data
		String s = args[0];
		URL url = new URL(s);
		InputStreamReader input = new InputStreamReader(url.openStream(),"UTF-8"); 
		JSONTokener jsonTokener = new JSONTokener(input);
		JSONArray jsonArr = new JSONArray(jsonTokener);

		String str1 = args[1];
		String str2 = args[2];
		int sum = 0;
		int num = 0;
		
		// parse data and confirm if they match the condition or not
		for (int i = 0; i < jsonArr.length(); i++) 
		{
			JSONObject first = jsonArr.getJSONObject(i);
			long year = (long) first.getLong("交易年月");
			long price = (long) first.getLong("總價元");
			Pattern pattern1 = Pattern.compile(".*"+str1+".*");
			Pattern pattern2 = Pattern.compile(".*"+str2+".*");
			Matcher matcher1 = pattern1.matcher((CharSequence) first.get("鄉鎮市區"));
			Matcher matcher2 = pattern2.matcher((CharSequence) first.get("土地區段位置或建物區門牌"));
			if((matcher1.matches())&&(matcher2.matches()))
			{
				if(year>(Integer.parseInt(args[3])*100))
				{
					num++;
					sum+=price;
					//System.out.println("鄉鎮市區 : "+first.get("鄉鎮市區")+"  土地區段位置或建物區門牌 : "+first.get("土地區段位置或建物區門牌")+"  交易年月 : "+year);
				}
			}
		}
		long avg_price = sum/num;
		// print out the average of the price which match the condition 
		System.out.println(avg_price);
	}
	catch(IOException e)
	{
	System.out.println("IOException.");
	System.out.println(e.getMessage());
	}
	catch(JSONException e)
	{
	System.out.println("JSONException.");
	System.out.println(e.getMessage());
	}
    	
    }
}
