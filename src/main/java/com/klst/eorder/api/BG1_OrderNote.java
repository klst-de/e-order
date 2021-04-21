package com.klst.eorder.api;

import java.util.List;

/**
 * BG-1 ORDER NOTE
 * <p>
 * A group of business terms providing textual notes that are relevant for the order, 
 * together with an indication of the note subject.
 * <p>
 * Similar to EN16931 business group BG-1
 * <br>Order-X-No: 	21
 */
public interface BG1_OrderNote extends OrderNoteFactory {
	
	// getter
	public List<OrderNote> getOrderNotes();

	// factory method
//	public OrderNote createNote(String subjectCode, String content);
	default OrderNote createNote(String content) {
		return createNote((String)null, content);
	}

	// setter
	public void addNote(OrderNote note);
	default void addNote(String subjectCode, String content) {
		addNote(createNote(subjectCode, content));
	}
	default void addNote(String content) {
		addNote((String)null, content);
	}
	
}
