package com.klst.eorder.api;

/**
 * ORDER NOTE
 * <p>
 * A group of business terms providing textual notes that are relevant for the order
 * or order line, 
 * together with an indication of the note subject.
 * <p>
 * Similar to EN16931 business group BG-1
 * 
 * @see OrderNoteFactory
 */
public interface OrderNote extends OrderNoteFactory {
	
// setter:
//	void setCode(String code); // not public ==> use factory
//	void setNote(String content); // not public ==> use factory
	
// getter in BG1_OrderNote, OrderLine
//	public List<OrderNote> getOrderNotes();
//	public List<OrderNote> getNotes();

	/**
	 * returns note
	 * <p>
	 * A textual note that gives unstructured information that is relevant to the document as a whole.
	 * Such as the reason for any correction or assignment note in case the invoice has been factored
	 * <p>
	 * Cardinality: 	1..1
	 * <br>EN16931-ID: 	BT-22, BT-127
	 * <br>Rule ID: 	
	 * <br>Order-X-No: 	23, 39
	 * 
	 * @return Text
	 */
	public String getNote();

	/**
	 * returns optional note subject code
	 * <p>
	 * To be chosen from the entries in UNTDID 4451
	 * @see <a href="https://unece.org/fileadmin/DAM/trade/untdid/d16b/tred/tred4451.htm">UNTDID 4451</a>
	 * <p>
	 * Cardinality: 	0..1
	 * <br>EN16931-ID: 	BT-21
	 * <br>Rule ID: 	
	 * <br>Order-X-No: 	24, 40
	 * 
	 * @return subject code or null
	 */
	public String getCode();
	
	// 38: 0..1 EXTENDED Note Content Code, To be chosen from the entries in UNTDID xxx
	public void setNoteContentCode(String code);
	public String getNoteContentCode();


}
