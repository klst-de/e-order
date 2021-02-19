package com.klst.eorder.impl;

import java.util.ArrayList;
import java.util.List;

import com.klst.einvoice.reflection.CopyCtor;
import com.klst.eorder.OrderNote;
import com.klst.eorder.OrderNoteFactory;

import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.NoteType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.CodeType;

/* implements BG-1 NOTE
 * 
 * also implements BG-25.BT-127 0..n IncludedNote.Content
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
			CopyCtor.invokeCopy(this, note);
		}
	}

	public String toString() {
		return "["+(super.getSubjectCode()==null ? "" : "SchemeID="+getCode())
			+" \""+getNote()+"\"]";
	}

	// BT-21 ++ 0..1 Invoice note subject code
	@Override
	public String getCode() {
		return super.getSubjectCode()==null ? null : getSubjectCode().getValue();
	}

	void setCode(String code) {
		if(code==null) return; 
		CodeType subjectCode = new CodeType();
		subjectCode.setValue(code);
		super.setSubjectCode(subjectCode);
	}

	// BG-1 .BT-22  1..1 Invoice note
	// BG-25.BT-127 0..n IncludedNote.Content 
	@Override
	public String getNote() {
		return super.getContent()==null ? null : getContent().getValue();
	}

	void setNote(String content) {
		if(content==null) return; // defensiv, sollte nicht vorkommen
		super.setContent(Text.create(content));
	}

}
