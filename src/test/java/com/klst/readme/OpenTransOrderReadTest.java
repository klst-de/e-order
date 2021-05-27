package com.klst.readme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.klst.edoc.api.BusinessParty;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.PostalAddress;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.eorder.api.AbstactTransformer;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.BG2_ProcessControl;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.SupportingDocument;
import com.klst.eorder.impl.Amount;
import com.klst.eorder.impl.ID;
import com.klst.eorder.impl.Percent;
import com.klst.eorder.impl.Quantity;
import com.klst.eorder.impl.UnitPriceAmount;
import com.klst.marshaller.CioTransformer;
import com.klst.marshaller.OpenTransTransformer;
import com.klst.test.CommonUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OpenTransOrderReadTest extends Constants {

	private static final String LOG_PROPERTIES = "testLogging.properties";
	private static LogManager logManager = LogManager.getLogManager(); // Singleton
	private static Logger LOG = null;
	private static void initLogger() {
    	URL url = OpenTransOrderReadTest.class.getClassLoader().getResource(LOG_PROPERTIES);
    	if(url==null) {
			LOG = Logger.getLogger(OpenTransOrderReadTest.class.getName());
			LOG.warning("keine "+LOG_PROPERTIES);
    	} else {
    		try {
    	        File file = new File(url.toURI()); //NPE wenn "testLogging.properties" nicht gefunden
    			logManager.readConfiguration(new FileInputStream(file));
    		} catch (IOException | URISyntaxException e) {
    			LOG = Logger.getLogger(OpenTransOrderReadTest.class.getName());
    			LOG.warning(e.getMessage());
    		}
    	}
		LOG = Logger.getLogger(OpenTransOrderReadTest.class.getName());		
	}

	static private AbstactTransformer cioTransformer;
	static private AbstactTransformer otTransformer;
	static private AbstactTransformer transformer;

    @BeforeClass
    public static void staticSetup() {
    	initLogger();
    }

	Object object;
	private File testFile;
	
	@Before 
    public void setup() {
	   	cioTransformer = CioTransformer.getInstance();
	   	otTransformer = OpenTransTransformer.getInstance();
	   	object = null;
    }

	static private final String ORDER_ID = "PLEX-141269";
	static private final Timestamp ORDER_DATE = Timestamp.valueOf("2020-01-22"+_HMS);
	static private final IAmount TOTAL_AMOUNT = new Amount(new BigDecimal(1080.25));
	ArrayList<ExpectedLine> expected() {
		ArrayList<ExpectedLine> lines = new ArrayList<ExpectedLine>(11);
		ExpectedLine line = new ExpectedLine();
		line.id = "1";
		line.qty = new Quantity(C62, new BigDecimal(2000));
		line.upa = new UnitPriceAmount(new BigDecimal(5.16));
		line.upq = new Quantity(null, new BigDecimal(100));
		line.lna = new Amount(line.upa.getValue().multiply(line.qty.getValue()).divide(line.upq.getValue()));
		line.name = "BLISTOM25K";
		line.desc = "BLISTO K M25 noir, Bouchon de ferm. PA GFK 20pcs";
		line.pdi = OrderLine.YES;
		line.sid = new ID("7611577104836", EAN);
		line.sai = "G4525220";
		line.bai = "907216725";
		line.tcc = TaxCategoryCode.ExemptFromTax;
		line.delivery = Timestamp.valueOf("2020-01-30"+_HMS);
		// TODO CUSTOMER_ORDER_REFERENCE
/* Benutzerdefinierte Erweiterung, wie können diese abgebildet werden? :
		<ITEM_UDX>
				<UDX.JA.LABEL_TEXT_LINE1 lang="deu">BLISTO K (sz)</UDX.JA.LABEL_TEXT_LINE1>
				<UDX.JA.LABEL_TEXT_LINE1 lang="fra">BLISTO K (nr)</UDX.JA.LABEL_TEXT_LINE1>
				<UDX.JA.LABEL_TEXT_LINE1 lang="eng">BLISTO K (bl)</UDX.JA.LABEL_TEXT_LINE1>
				<UDX.JA.LABEL_TEXT_LINE1 lang="ita"/>
				<UDX.JA.LABEL_TEXT_LINE2>M25</UDX.JA.LABEL_TEXT_LINE2>
				<UDX.JA.BUYER_LINE_ITEM_ID>1</UDX.JA.BUYER_LINE_ITEM_ID>
		</ITEM_UDX>
 */
		lines.add(line);
		
		line = new ExpectedLine();
		line.id = "2";
		line.sai = "D4816931";
		line.sid = new ID("7601577560732", EAN);
		line.bai = "916850027";
		line.name = "BLISTOM16MSATEX";
		line.desc = "Bst EX d/e MS M16 6-pans 25pcs, Bouch. de f. laiton";
		line.qty = new Quantity(C62, new BigDecimal(25));
		line.upa = new UnitPriceAmount(new BigDecimal(60.69));
		line.upq = new Quantity(null, new BigDecimal(100));
		line.lna = new Amount(line.upa.getValue().multiply(line.qty.getValue()).divide(line.upq.getValue()));
		line.pdi = OrderLine.YES;
		line.delivery = Timestamp.valueOf("2020-01-30"+_HMS);
		lines.add(line);
		
		line = new ExpectedLine();
		line.id = "3";
		line.sai = "D7875920";
		line.sid = new ID("7611577099484", EAN);
		line.bai = "926001075";
		line.name = "GGMM75IN";
		line.desc = "GGM INOX V4A M75, Contre-écrou INOX V4A 1pce";
		line.qty = new Quantity(C62, new BigDecimal(1));
		line.upa = new UnitPriceAmount(new BigDecimal(2855));
		line.upq = new Quantity(null, new BigDecimal(100));
		line.lna = new Amount(line.upa.getValue().multiply(line.qty.getValue()).divide(line.upq.getValue()));
		line.pdi = OrderLine.YES;
		line.delivery = Timestamp.valueOf("2020-01-30"+_HMS);
		lines.add(line);
		
		// TODO rest
		
		return lines;
	}

	@Test
    public void cioEX01() {
		testFile = CommonUtils.getTestFile(TESTDIR+"ORDER-X_EX01_ORDER_FULL_DATA-BASIC.xml");
		transformer = otTransformer;
		CoreOrder cio = null;
		// toModel:
		if(transformer.isValid(testFile)) {
			// expected not valid
		}
		// not valid
		transformer = cioTransformer;
		if(transformer.isValid(testFile)) {
			try {
				InputStream is = new FileInputStream(testFile);
				object = transformer.unmarshal(is);
				LOG.info(">>>>"+object);
				Class<?> type = Class.forName(com.klst.marshaller.CioTransformer.CONTENT_TYPE_NAME); // CrossIndustryOrder aus jar laden
				// dynamisch:
				cio = CoreOrder.class.cast(type.getConstructor(object.getClass()).newInstance(object));
			} catch (Exception ex) {
				ex.printStackTrace();
				LOG.severe(ex.getMessage());
			}
		}
		assertEquals(BG2_ProcessControl.PROFILE_BASIC, cio.getProfile());
		assertEquals(DocumentNameCode.Order, cio.getDocumentCode());
		
		assertEquals("BUYER_REF_BU123", cio.getBuyerReferenceValue());
		assertEquals(3, cio.getLines().size());
	}

	private CoreOrder getCoreOrder(File testFile) {
		try {
			InputStream is = new FileInputStream(testFile);
			object = transformer.unmarshal(is);
			LOG.info(">>>>"+object);
			Class<?> type = Class.forName(com.klst.marshaller.OpenTransTransformer.CONTENT_TYPE_NAME); // openTrans.Order aus jar laden
			return CoreOrder.class.cast(type.getConstructor(object.getClass()).newInstance(object));
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.severe(ex.getMessage());
		}
		return null;
	}

	// ein paar Beispiele: https://github.com/zdavatz/yopenedi/tree/master/yopenedi_dokumentation/Musternachrichten%20OpenTrans%202.1
	@Test
    public void pldOrder() {
		File testFile = CommonUtils.getTestFile(TESTDIR+"PLD_257444_Order_example.XML");
		transformer = otTransformer;
		CoreOrder co = null;	
		if(!transformer.isValid(testFile)) return; // not valid
		co = getCoreOrder(testFile);
		
		// keine Methode für <GENERATION_DATE>2020-01-22T07:35:18.6258</GENERATION_DATE>
		assertEquals(ORDER_ID, co.getId());
		assertEquals(ORDER_DATE, co.getIssueDateAsTimestamp());
		assertEquals("fra", co.getLanguage().get(0));
		
		BusinessParty seller = co.getSeller();
		LOG.info("seller:"+seller);
		BusinessParty buyer = co.getBuyer();
		LOG.info("buyer:"+buyer);

		ArrayList<ExpectedLine> exp = expected();
		List<OrderLine> ol = co.getLines();
		assertEquals(11, ol.size());
//		OrderLine line = co.getLines().get(0);
//		assertEquals("1", line.getId());               // BT-126 1..1 <LINE_ITEM_ID>1</LINE_ITEM_ID>
///*
//			<PRODUCT_ID>
//				<bmecat:SUPPLIER_PID type="supplier_specific">G4525220</bmecat:SUPPLIER_PID>
//				<bmecat:INTERNATIONAL_PID type="ean">7611577104836</bmecat:INTERNATIONAL_PID>
//				<bmecat:BUYER_PID type="buyer_specific">907216725</bmecat:BUYER_PID>
//				<bmecat:DESCRIPTION_SHORT>BLISTOM25K</bmecat:DESCRIPTION_SHORT>
//				<bmecat:DESCRIPTION_LONG>BLISTO K M25 noir, Bouchon de ferm. PA GFK 20pcs</bmecat:DESCRIPTION_LONG>
//			</PRODUCT_ID>		
// */
//		assertEquals("G4525220", line.getSellerAssignedID());     // BG-31.BT-155 0..1 ohne type/Schema
//		assertEquals("907216725", line.getBuyerAssignedID());     // BG-31.BT-156 0..1
//		List<Identifier> stdId = line.getStandardIdentifier();    // BG-31.BT-157 0..n
//		assertEquals("ean", stdId.get(0).getSchemeIdentifier());
//		assertEquals("7611577104836", stdId.get(0).getContent());
//		assertEquals("BLISTOM25K", line.getItemName());           // BG-31.BT-153 1..1 Artikelname
//		assertEquals("BLISTO K M25 noir, Bouchon de ferm. PA GFK 20pcs", line.getDescription()); // BG-31.BT-154
///*
//			<QUANTITY>2000</QUANTITY>
//			<bmecat:ORDER_UNIT>C62</bmecat:ORDER_UNIT>		
// */
//		assertEquals("2000.0000C62", line.getQuantity().toString());       // BT-129 1..1 bestellte Menge
//		assertEquals(TaxCategoryCode.ExemptFromTax, line.getTaxCategory()); // BG-30.BT-151 1..1 Code der UStCat
		for(int i=0; i<ol.size(); i++) {
			OrderLine l = ol.get(i);
			LOG.info("line "+i + ":"+l);
			
			if(i>=exp.size()) continue;
			
			ExpectedLine e = exp.get(i);
			assertEquals(e.id, l.getId());
			if(e.note==null) {
				assertTrue(l.getNotes().isEmpty());
			} else {
				assertEquals(e.note.toString(), l.getNotes().get(0).toString());
			}
			assertEquals(e.qty.toString(), l.getQuantity().toString());
			LOG.info("upa expected:"+e.upa + " =?= "+new UnitPriceAmount(l.getUnitPriceAmount().getValue()));
			assertEquals(e.upa.toString(), new UnitPriceAmount(l.getUnitPriceAmount().getValue()).toString());
			assertEquals(e.lna.toString(), l.getLineTotalAmount().toString());
			assertEquals(e.upq.toString(), l.getUnitPriceQuantity().toString());
			assertEquals(e.name, l.getItemName());
			assertEquals(e.desc, l.getDescription());
			assertEquals(e.sid.toString(), l.getStandardIdentifier().get(0).toString());
			assertEquals(e.sai, l.getSellerAssignedID());
			assertEquals(e.bai, l.getBuyerAssignedID());
			assertEquals(e.pdi, l.isPartialDeliveryAllowed());
			assertEquals(e.delivery, l.getDeliveryDateAsTimestamp());
			assertEquals(e.tcc, l.getTaxCategory());
//		<CUSTOMER_ORDER_REFERENCE>
//			<ORDER_ID>PLEX-141269</ORDER_ID>
//			<LINE_ITEM_ID>1</LINE_ITEM_ID>
//			<ORDER_DATE>2020-01-22</ORDER_DATE>
//		</CUSTOMER_ORDER_REFERENCE>
			List<SupportingDocument> sdList = l.getReferencedProductDocuments();
			LOG.info("CUSTOMER_ORDER_REFERENCE:"+l.getReferencedProductDocuments()
			+ sdList.get(0).getLineReference()
			+ sdList.get(0).getDateAsTimestamp()
			);
			assertEquals(ORDER_ID, sdList.get(0).getDocumentReference().getName());
			assertEquals(ORDER_DATE, sdList.get(0).getDateAsTimestamp());
		}

		assertEquals(EUR, co.getDocumentCurrency());
		assertEquals(TOTAL_AMOUNT.toString(), co.getLineNetTotal().toString());
		LOG.info("fertig.\n");
	}
	
	@Test
    public void otOrder() {
		File testFile = CommonUtils.getTestFile(TESTDIR+"sample_order_opentrans_2_1.xml");
		transformer = otTransformer;
		CoreOrder co = null;	
		if(transformer.isValid(testFile)) {
			co = getCoreOrder(testFile);
		}
		
		BusinessParty seller = co.getSeller();
		LOG.info("seller:"+seller);
		BusinessParty buyer = co.getBuyer();
		LOG.info("buyer:"+buyer);
//	<ADDRESS>
//		<bmecat:NAME>ADDRESS.NAME</bmecat:NAME>
//		<bmecat:NAME2>ADDRESS.NAME2</bmecat:NAME2>
//		<bmecat:NAME3>ADDRESS.NAME3</bmecat:NAME3>
//		<bmecat:DEPARTMENT>ADDRESS.DEPARTMENT</bmecat:DEPARTMENT> <----------- ContactInfoExt.ContactDepartment TODO
		assertEquals("ADDRESS.NAME", buyer.getAddress().getAddressLine1());
		assertEquals("ADDRESS.NAME2", buyer.getAddress().getAddressLine2());
		assertEquals("ADDRESS.NAME3", buyer.getAddress().getAddressLine3());
		LOG.info("VATRegistrationId/Umsatzsteuer-ID:"+buyer.getVATRegistrationId()); // mit vorangestelltem Ländercode
		// ??? Steuernummer : Steuernummer
// Das Format der Steuernummer beim Finanzamt variiert ab und an  von Bundesland zu Bundesland. Sie beinhaltet mal 10 und mal 11 Zahlen, die durch Schrägstriche in 3 Teile zerlegt wird. 
		
		
		LOG.info("getIssueDateAsTimestamp:"+co.getIssueDateAsTimestamp());
		// 2009-05-13 07:20:00.0 test ohne Time:
		assertEquals("20090513", DateTimeFormats.tsToCCYYMMDD(co.getIssueDateAsTimestamp()));
		assertEquals("2009-05-13T07:20:00+02:00", DateTimeFormats.tsTodtDATETIME(co.getIssueDateAsTimestamp()));
		assertNotNull(co.getDeliveryDateAsTimestamp());
		assertEquals("20090520", DateTimeFormats.tsToCCYYMMDD(co.getDeliveryDateAsTimestamp()));
		
		PostalAddress pa = buyer.getAddress();
				pa.setAddressLine1("AddressLine1");
		LOG.info("pa.AddressLine1:"+pa.getAddressLine1());
		
		String a ="a";
		List<OrderLine> lines = co.getLines();
		assertEquals(1, lines.size());
		OrderLine line = lines.get(0); // die einzige Zeile
		assertEquals("1", line.getId());               // BT-126 1..1 Kennung der Position
		assertEquals(1, line.getNotes().size());	   // BT-127 0..1 Freitext zur Position
		assertEquals(a, line.getNotes().get(0).getNote()); // <REMARKS type="customRemark" lang="deu">a</REMARKS>
		// TODO unklar BG.25.BT-128 0..1 Objektkennung
		LOG.info("getQuantity:"+line.getQuantity());   // BT-129 1..1 bestellte Menge
		assertEquals(0, new BigDecimal(1).compareTo(line.getQuantity().getValue(RoundingMode.UNNECESSARY)));
		assertEquals("EA", line.getQuantity().getUnitCode()); // BT-130 1..1
		// BT-131 1..1 Nettobetrag der Position <PRICE_LINE_AMOUNT>1111</PRICE_LINE_AMOUNT>
		assertEquals(0, new BigDecimal(1111).compareTo(line.getLineTotalAmount().getValue(RoundingMode.UNNECESSARY)));
		// TODO BT-132 0..1 Referenz zur Bestellposition , nicht in CIO, nur in CIOR/CIOC
		// BT-133 0..1 Buchungsreferenz des Käufers <bmecat:ACCOUNTING_INFO> ...
		assertEquals(a, line.getBuyerAccountingReference());
		
		// BG.26 0..1 POSITIONSZEITRAUM : BT-134 0..1 Anfangsdatum + BT-135
		IPeriod lineDeliveryPeriod = line.getDeliveryPeriod();
		assertEquals("20200130", DateTimeFormats.tsToCCYYMMDD(lineDeliveryPeriod.getStartDateAsTimestamp()));
		assertEquals(DateTimeFormats.ymdToTs("2020-02-15"), lineDeliveryPeriod.getEndDateAsTimestamp());
		
		// BG-27 0..n ABSCHLÄGE AUF EBENE DER POSITION, BG-28 0..n LINE CHARGES / ZUSCHLÄGE
		// BG-27.BT-136 Betrag des Abschlags
		//      ...
		// BG-27.BT-140 Code
		// BG-28.BT-141 Betrag des Zuschlags
		//      ...
		// BG-28.BT-145 Code
		List<AllowancesAndCharges> aoc = line.getAllowancesAndCharges();
		assertEquals(2, aoc.size());  // zwei elemente, beide sind BG-27 ABSCHLÄGE
		assertTrue(aoc.get(0).isAllowance());
//		LOG.info(">>>>>>"+aoc.get(0).getPercentage().setScale(Percent.SCALE, RoundingMode.HALF_UP));
		assertEquals(new BigDecimal(0.0800).setScale(Percent.SCALE, RoundingMode.HALF_UP)
			   , aoc.get(0).getPercentage().setScale(Percent.SCALE, RoundingMode.HALF_UP));
		assertEquals(a, aoc.get(0).getReasonText());
// IST allowOrCharge:[allowance, AmountWithoutTax:null, AssessmentBase:null, %rate:0.07999999821186066, tax:null/null, tax%:null, Reasoncode:small_order, ReasonText:a]
/* SOLL:
					<ALLOW_OR_CHARGE type="allowance">
						<ALLOW_OR_CHARGE_SEQUENCE>1</ALLOW_OR_CHARGE_SEQUENCE>
						<ALLOW_OR_CHARGE_NAME>a</ALLOW_OR_CHARGE_NAME>
						<ALLOW_OR_CHARGE_TYPE>small_order</ALLOW_OR_CHARGE_TYPE>
						<ALLOW_OR_CHARGE_DESCR>a</ALLOW_OR_CHARGE_DESCR>
						<ALLOW_OR_CHARGE_VALUE>
							<AOC_PERCENTAGE_FACTOR>0.08</AOC_PERCENTAGE_FACTOR>
						</ALLOW_OR_CHARGE_VALUE>
					</ALLOW_OR_CHARGE>
		
 */
		// BG-29 1..1 DETAILINFORMATIONEN ZUM PREIS
		// BG-29.BT-146 1..1 UnitPriceAmount
		assertEquals(0, new BigDecimal(0.0).compareTo(line.getUnitPriceAmount().getValue(RoundingMode.UNNECESSARY)));
		// BG-29.BT-147 0..1 PriceDiscount   !!! nicht in OT?
		// BG-29.BT-148 0..1 GrossPrice      !!! nicht in OT?
		
		// BG-29.BT-149 0..1 base quantity, UnitPriceQuantity
		// BG-29.BT-150 0..1 Item price unit
		IQuantity qty = line.getUnitPriceQuantity();
		LOG.info("UnitPriceQuantity:"+qty); // <bmecat:PRICE_QUANTITY>1</bmecat:PRICE_QUANTITY>
//		LOG.info("UnitPriceQuantity:"+qty.getValue());
//		LOG.info("UnitPriceQuantity:"+qty.getUnitCode());

		// BG-30 1..1 UMSATZSTEUERINFORMATIONEN
		// BG-30.BT-151 1..1 Code der Umsatzsteuerkategorie
		assertEquals(TaxCategoryCode.StandardRate, line.getTaxCategory());
		// BG-30.BT-152 0..1 item VAT rate
		LOG.info("TaxRate:"+line.getTaxRate());

		// BG-31 1..1 ARTIKELINFORMATIONEN
		assertEquals(a, line.getItemName()); // BG-31.BT-153 1..1 Artikelname <bmecat:DESCRIPTION_SHORT lang="deu">a
		// BG-31.BT-154 0..1 Artikelbeschreibung : <bmecat:DESCRIPTION_LONG lang="eng">a</bmecat:DESCRIPTION_LONG>
		assertEquals(a, line.getDescription()); 
		assertEquals(a, line.getSellerAssignedID()); // BG-31.BT-155 0..1 ohne type/Schema : upc
		assertEquals(a, line.getBuyerAssignedID());  // BG-31.BT-156 0..1
		List<Identifier> stdIDs = line.getStandardIdentifier(); // BG-31.BT-157 0..n
		LOG.info("StandardIdentifier:"+stdIDs.get(0));          // ... mit type/Schema
		assertEquals("gtin", stdIDs.get(0).getSchemeIdentifier());
		assertEquals(a, stdIDs.get(0).getContent());
		assertEquals(1, stdIDs.size());
		// BG-31.BT-159 0..1 Artikelherkunftsland fehlt in OT, ===> "countryoforigin" als ARTIKELATTRIBUTE
		assertEquals("IT", line.getCountryOfOrigin());
		
		// BG-32 0..n ARTIKELATTRIBUTE
		// BT-160 1..1 Artikelattributname + BT-161 1..1 Wert
		Properties attributes = line.getItemAttributes();
		assertEquals(2, attributes.size());
		assertEquals(a, attributes.getProperty(a));
 
		LOG.info("\n");
	}
	
}
