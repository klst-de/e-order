package com.klst.eorder.openTrans;

import java.util.ArrayList;
import java.util.List;

import org.opentrans.xmlschema._2.REMARKS;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.api.OrderNoteFactory;

/* implements BG-1 NOTE
 * 
 * also implements BG-25.BT-127 0..n IncludedNote.Content
 * 
 * super class REMARKS extends TypeMLSTRING64000 extends DtMLSTRING
 */
public class Remarks extends REMARKS implements OrderNote, OrderNoteFactory {

	@Override // Factory
	public OrderNote createNote(String subjectCode, String content) {
		return create(subjectCode, content);
	}
	static Remarks create(String subjectCode, String content) {
		return content==null? null : new Remarks(subjectCode, content);
	}
	static Remarks create(String content) {
		return create(null, content);
	}

	static List<OrderNote> getNotes(List<REMARKS> list) {
		List<OrderNote> result = new ArrayList<OrderNote>(list.size());
		list.forEach(note -> {
			result.add(Remarks.create(note));
		});
		return result;
	}

	// copy factory
	static Remarks create(REMARKS object) {
		if(object instanceof REMARKS && object.getClass()!=REMARKS.class) {
			// object is instance of a subclass of REMARKS, but not REMARKS itself
			return (Remarks)object;
		} else {
			return new Remarks(object); 
		}
	}
	
	private Remarks(String subjectCode, String content) {
		super();
		setCode(subjectCode);
		setNote(content);
	}
	// copy ctor
	private Remarks(REMARKS note) {
		super();
		if(note!=null) {
			SCopyCtor.getInstance().invokeCopy(this, note);
		}
	}

	public String toString() {
		return "["+(super.getType()==null ? "" : "SchemeID="+getCode())
			+" \""+getNote()+"\"]";
	}

	// BT-21 ++ 0..1 note subject code
	@Override
	public String getCode() {
		return super.getType();
	}

	void setCode(String code) {
		super.setType(code);
	}

	// BG-1 .BT-22  1..1 Invoice note
	// BG-25.BT-127 0..n IncludedNote.Content
	/*
	 * in in Order-X_Extended content is mapped to List
	 * @see https://github.com/klst-de/e-order/issues/3
	 */
	@Override
	public String getNote() {
		return super.getValue();
	}

	void setNote(String content) {
		super.setValue(content);
	}

}
