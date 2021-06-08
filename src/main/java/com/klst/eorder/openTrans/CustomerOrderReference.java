package com.klst.eorder.openTrans;

import java.sql.Timestamp;
import java.util.logging.Logger;

import org.opentrans.xmlschema._2.CUSTOMERORDERREFERENCE;
import org.opentrans.xmlschema._2.ORDERDESCR;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.eorder.api.SupportingDocument;

/* CUSTOMERORDERREFERENCE:
    protected String orderid;               ==> docRefId
    protected String lineitemid;            ==> lineId
    protected String orderdate;             ==> ts
    protected List<ORDERDESCR> orderdescr; ORDERDESCR extends (bmecat)DtMLSTRING ==> description
    protected TypePARTYID customeridref;
   example:
			<CUSTOMER_ORDER_REFERENCE>
				<ORDER_ID>PLEX-141269</ORDER_ID>
				<LINE_ITEM_ID>1</LINE_ITEM_ID>
				<ORDER_DATE>2020-01-22</ORDER_DATE>
			<!--	<ORDER_DESCR/> --> 
			</CUSTOMER_ORDER_REFERENCE>

 */
public class CustomerOrderReference extends CUSTOMERORDERREFERENCE implements SupportingDocument {

	// SupportingDocumentFactory:
	@Override
	public SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description,
			Timestamp ts, String uri) {
		LOG.warning("ignored String uri");
		return create(docRefId, lineId, description, ts);
	}
	@Override
	public SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description,
			Timestamp ts, byte[] content, String mimeCode, String filename) {
		LOG.warning("ignored byte[] content, String mimeCode, String filename");
		return create(docRefId, lineId, description, ts);
	}
	static CustomerOrderReference create(String orderid, Reference lineitemid, String description, Timestamp orderdate) {
		return new CustomerOrderReference(orderid, lineitemid, description, orderdate); 
	}
	
	private static final Logger LOG = Logger.getLogger(CustomerOrderReference.class.getName());
	
	// factory
	static CustomerOrderReference create() {
		return new CustomerOrderReference(null); 
	}
	// copy factory
	static CustomerOrderReference create(CUSTOMERORDERREFERENCE object) {
		if(object instanceof CUSTOMERORDERREFERENCE && object.getClass()!=CUSTOMERORDERREFERENCE.class) {
			// object is instance of a subclass of CUSTOMERORDERREFERENCE, but not CUSTOMERORDERREFERENCE itself
			return (CustomerOrderReference)object;
		} else {
			return new CustomerOrderReference(object); 
		}
	}

	// copy ctor
	private CustomerOrderReference(CUSTOMERORDERREFERENCE object) {
		SCopyCtor.getInstance().invokeCopy(this, object);
	}
	private CustomerOrderReference(String orderid, Reference lineitemid, String description, Timestamp orderdate) {
		setOrderid(orderid);
		setLineReference(lineitemid);
		setSupportingDocumentDescription(description);
		setDate(orderdate);
	}
	
	private void setOrderid(String orderid) {
		super.setORDERID(orderid);
	}
	
	@Override
	public void setDocumentCode(String code) {
	}
	@Override
	public String getDocumentCode() {
		return null;
	}
	@Override
	public void setDocumentReference(Reference documentReference) {
		setOrderid(documentReference.getContent());
	}
	@Override
	public Reference getDocumentReference() {
		if(super.getORDERID()==null) return null;
		return new SimpleId(getORDERID());
	}

	@Override
	public void setLineReference(Reference lineReference) {
		if(lineReference==null) return;
		super.setLINEITEMID(lineReference.getContent());
	}
	@Override
	public Reference getLineReference() {
		if(super.getLINEITEMID()==null) return null;
		return new SimpleId(getLINEITEMID());
	}

	@Override
	public void setSupportingDocumentDescription(String text) {
		if(text==null) return;
		ORDERDESCR descr = new ORDERDESCR();
		descr.setValue(text);
		super.getORDERDESCR().add(descr);
	}
	@Override
	public String getSupportingDocumentDescription() {
		if(super.getORDERDESCR().isEmpty()) return null;
		return getORDERDESCR().get(0).getValue();
	}
	
	@Override
	public void setDate(Timestamp timestamp) {
		if(timestamp==null) return;
		super.setORDERDATE(DateTimeFormats.tsTodtDATETIME(timestamp));
	}
	@Override
	public Timestamp getDateAsTimestamp() {
		return super.getORDERDATE()==null ? null : DateTimeFormats.dtDATETIMEToTs(getORDERDATE());
	}
	
	// not defined in OT:	
	@Override
	public void setExternalDocumentLocation(String location) {
	}
	@Override
	public String getExternalDocumentLocation() {
		return null;
	}
	
	@Override
	public void setAttachedDocument(byte[] doc, String mimeCode, String filename) {
	}
	@Override
	public byte[] getAttachedDocument() {
		return null;
	}
	@Override
	public String getAttachedDocumentMimeCode() {
		return null;
	}
	@Override
	public String getAttachedDocumentFilename() {
		return null;
	}

}
