package restlet;

import java.util.List;

public class jsonList {
	private List<cate> list;

	public List<cate> getList() {
		return list;
	}

	public void setList(List<cate> list) {
		this.list = list;
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
