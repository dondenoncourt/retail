package com.kettler.controller.retail
class EmailUsCommand implements Serializable {
    static def TYPES = [
                parts : 'Parts department',
                general : 'General questions and comments',
                separator1 : '----  Product-specific questions or interested in becoming a dealer ----',
                toys : 'Toys',
                fitness : 'Fitness',
                patio : 'Patio',
                tabletennis : 'Table Tennis',
                bikes : 'Bikes',
                contract : 'Contract']
    static def EMAILS = [
                parts : 'parts@kettlerusa.com',
                general : 'info@kettlerusa.com',
                toys : 'toys@kettlerusa.com',
                fitness : 'fitness@kettlerusa.com',
                patio : 'patio@kettlerusa.com',
                tabletennis : 'tabletennis@kettlerusa.com',
                bikes : 'bikes@kettlerusa.com',
                contract : 'contract@kettlerusa.com']
	String emailType
	String name
	String email
	String addr1
	String addr2
	String city
	String state
	String zip
	String phone
    String company
	String comment

    static constraints = {
        name blank:false 
    	email email:true, blank:false 
    	comment blank:false
        emailType inList:EMAILS.keySet() as List
    }
}
