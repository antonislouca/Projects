package applicationVersionTwo;
public class FAQ {
    private int id;
	private String question;
	private String answer;
	FAQ(int ID,String Q,String A){
		this.setFAQ(ID);
		this.setFAQ_Q(Q);
		this.setFAQ_A(A);
	}
	public int getFAQ() {
		return id;
	}
	public void setFAQ(int ID) {
		this.id = ID;
	}
	public String getFAQ_Q() {
		return this.question;
	}
	public void setFAQ_Q(String fAQ_Q) {
		this.question=fAQ_Q;
	}
	public String getFAQ_A() {
		return this.answer;
	}
	public void setFAQ_A(String fAQ_A) {
		this.answer = fAQ_A;
	}
	
	public String toString() {
		return String.format("Q%d:\n%s\nAnswer:\n%s", 
							id, question, answer);
	}
}

