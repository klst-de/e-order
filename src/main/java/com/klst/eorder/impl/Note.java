package com.klst.eorder.impl;

import java.util.ArrayList;
import java.util.List;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.api.OrderNoteFactory;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.NoteType;

/* implements 21: BG-1 NOTE
 * 
 * Alternative is to use delivery instructions, 
 * but that's more applicable in a despatch message.
 * 
 * Also implements 37: BG-25.BT-127 0..n LINE NOTE
 */
public class Note extends NoteType implements OrderNote, OrderNoteFactory {

	@Override // Factory
	public OrderNote createNote(String subjectCode, String content) {
		return create(subjectCode, content);
	}
	static Note create(String subjectCode, String content) {
		return content==null? null : new Note(subjectCode, content);
	}
	static Note create(String content) {
		return create(null, content);
	}

	static List<OrderNote> getNotes(List<NoteType> list) {
		List<OrderNote> result = new ArrayList<OrderNote>(list.size());
		list.forEach(note -> {
			result.add(Note.create(note));
		});
		return result;
	}

	// copy factory
	static Note create(NoteType object) {
		if(object instanceof NoteType && object.getClass()!=NoteType.class) {
			// object is instance of a subclass of NoteType, but not NoteType itself
			return (Note)object;
		} else {
			return new Note(object); 
		}
	}
	
	private Note(String subjectCode, String content) {
		super();
		setCode(subjectCode);
		setNote(content);
	}
	// copy ctor
	private Note(NoteType note) {
		super();
		if(note!=null) {
			SCopyCtor.getInstance().invokeCopy(this, note);
		}
	}

	public String toString() {
		return "["+(super.getSubjectCode()==null ? "" : "SchemeID="+getCode())
			+" \""+getNote()+"\"]";
	}

	// 22, 38: 0..1 EXTENDED Note Content Code
	public void setNoteContentCode(String code) {
		SCopyCtor.getInstance().set(this, "contentCode", code);
	}
	public String getNoteContentCode() {
		return super.getContentCode()==null ? null : getContentCode().getValue();
	}
	
	// 24: BG-1.BT-21 0..1 Invoice note subject code
	@Override
	public String getCode() {
		return super.getSubjectCode()==null ? null : getSubjectCode().getValue();
	}
	void setCode(String code) {
		SCopyCtor.getInstance().set(this, "subjectCode", code);
	}

	// 23: BG-1.BT-22  1..1 Invoice note
	// 39: BG-25.BT-127 0..n IncludedNote.Content
	/*
	 * in in Order-X_Extended content is mapped to List
	 * @see https://github.com/klst-de/e-order/issues/3
	 */
	@Override
	public String getNote() {
//		return super.getContent()==null ? null : getContent().getValue();
		return super.getContent().isEmpty() ? null : getContent().get(0).getValue();
	}

	void setNote(String content) {
		if(content==null) return; // defensiv, sollte nicht vorkommen
//		super.setContent(Text.create(content));
		super.getContent().add(Text.create(content));
	}

}
