package com.klst.eorder.api;

/**
 * abstract-factory, aka Kit for BG-1 ORDER NOTE
 * 
 * @see <a href="https://java-design-patterns.com/patterns/abstract-factory/">java-design-patterns</a> for patterns abstract-factory
 */
public interface OrderNoteFactory {
	
	/**
	 * factory for document Note BG-1 or line Note BG-25.BT-127
	 * 
	 * @param content  
	 * @param subjectCode - optional, to be chosen from the entries in 
	 * <a href="https://unece.org/fileadmin/DAM/trade/untdid/d16b/tred/tred4451.htm">UNTDID 4451</a>
	 * 
	 * @return NOTE Object
	 * @see OrderNote
	 */
	public OrderNote createNote(String subjectCode, String content);
	
}
