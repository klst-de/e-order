package com.klst.eorder;

/**
 * BG-1 ORDER NOTE
 * <p>
 * A group of business terms providing textual notes that are relevant for the order, 
 * together with an indication of the note subject.
 * <p>
 * Similar to EN16931 business group BG-2
 */
public interface OrderNote extends OrderNoteFactory {
	
// setter:
//	void setCode(String code); // not public ==> use factory
//	void setNote(String content); // not public ==> use factory
	
// getter in BG1_OrderNote
	
	/**
	 * returns optional Invoice note subject code
	 * <p>
	 * To be chosen from the entries in UNTDID 4451
	 * @see <a href="https://unece.org/fileadmin/DAM/trade/untdid/d16b/tred/tred4451.htm">UNTDID 4451</a>
	 * <p>
	 * Cardinality: 	0..1
	 * <br>EN16931-ID: 	BT-21
	 * <br>Rule ID: 	
	 * <br>Request ID: 	R56
	 * 
	 * @return Text or null
	 */
	public String getCode();
	
	/**
	 * returns Invoice note
	 * <p>
	 * A textual note that gives unstructured information that is relevant to the Invoice as a whole.
	 * Such as the reason for any correction or assignment note in case the invoice has been factored
	 * <p>
	 * Cardinality: 	1..1
	 * <br>EN16931-ID: 	BT-22
	 * <br>Rule ID: 	
	 * <br>Request ID: 	R56
	 * 
	 * @return Text
	 */
	public String getNote();
	
}
