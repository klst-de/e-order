package com.klst.eorder;

/**
 * abstract-factory, aka Kit for BG-1 ORDER NOTE
 * 
 * @see <a href="https://java-design-patterns.com/patterns/abstract-factory/">java-design-patterns</a> for patterns abstract-factory
 */
public interface OrderNoteFactory {
	
	/**
	 * factory for InvoiceNote BG-1
	 * 
	 * @param subjectCode optional
	 * @param content to be chosen from the entries in <a href="https://unece.org/fileadmin/DAM/trade/untdid/d16b/tred/tred4451.htm">UNTDID 4451</a>
	 * 
	 * @return BG-1 NOTE Object
	 */
	public OrderNote createNote(String subjectCode, String content);
	
}
