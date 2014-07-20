package creditCard;


public class CreditCardPreAuth {
	private String kettlerComanpyNumber = "";
	private String webOrderNumber = "";
	private String webOrderAmount = "";
	private String address1 = "";
	private String zip10 = "";
	private String creditCard = "";
	private String expireMonth = "";
	private String expireYear = "";
	private String securityCode = "";
	private String cardType = "";
	private boolean commercialCard = false;
	private String typeText = "";

	public String getKettlerComanpyNumber() {
		return kettlerComanpyNumber;
	}
	public void setKettlerComanpyNumber(String kettlerComanpyNumber) {
		this.kettlerComanpyNumber = kettlerComanpyNumber;
	}
	public String getWebOrderNumber() {
		return webOrderNumber;
	}
	public void setWebOrderNumber(String webOrderNumber) {
		this.webOrderNumber = webOrderNumber;
	}
	public String getWebOrderAmount() {
		return webOrderAmount;
	}
	public void setWebOrderAmount(String webOrderAmount) {
		this.webOrderAmount = webOrderAmount;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getZip10() {
		return zip10;
	}
	public void setZip10(String zip10) {
		this.zip10 = zip10;
	}
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public String getExpireMonth() {
		return expireMonth;
	}
	public void setExpireMonth(String expireMonth) {
		this.expireMonth = expireMonth;
	}
	public String getExpireYear() {
		return expireYear;
	}
	public void setExpireYear(String expireYear) {
		this.expireYear = expireYear;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public boolean isCommercialCard() {
		return commercialCard;
	}
	public void setCommercialCard(boolean commercialCard) {
		this.commercialCard = commercialCard;
	}
	public String getTypeText() {
		return typeText;
	}
	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}

	public String toString() {
		return  " webOrderAmount: "+webOrderAmount+
		" webOrderNumber: "+webOrderNumber+
		" address1: "+address1+
		" zip10: "+zip10+
		" creditCard: **** **** **** ****"+
		" expireMonth: "+expireMonth+
		" expireYear: "+expireYear+
		" securityCode: "+securityCode;
	}
}
