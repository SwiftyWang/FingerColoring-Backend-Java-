package restlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class testJson {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		String str = FileUtils.readFileToString(new File("./Category.txt"));
		
		List<cate> list = new ArrayList<>();
		list= mapper.readValue(str, new ArrayList<cate>().getClass());
	}
	class cate{
		private Long c;
		private String n;
		private int max;
		public Long getC() {
			return c;
		}
		public void setC(Long c) {
			this.c = c;
		}
		public String getN() {
			return n;
		}
		public void setN(String n) {
			this.n = n;
		}
		public int getMax() {
			return max;
		}
		public void setMax(int max) {
			this.max = max;
		}
		
		
	}


}
