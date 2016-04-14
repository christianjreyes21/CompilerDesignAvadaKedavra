package AnalysisPhase.LexicalAnalysis;

public class Token {

	String tokenName;
	String tokenAttribute;
	int lineNumber;
	public String getTokenName() {
		return tokenName;
	}
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}
	public String getTokenAttribute() {
		return tokenAttribute;
	}
	public void setTokenAttribute(String tokenAttribute) {
		this.tokenAttribute = tokenAttribute;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	
	
}
