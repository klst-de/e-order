package com.klst.eorder.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import com.klst.ebXml.reflection.SCopyCtor;
import com.klst.edoc.api.IAmount;
import com.klst.edoc.api.IPeriod;
import com.klst.edoc.api.IQuantity;
import com.klst.edoc.api.Identifier;
import com.klst.edoc.api.IdentifierExt;
import com.klst.edoc.api.Reference;
import com.klst.edoc.untdid.DateTimeFormats;
import com.klst.edoc.untdid.DocumentNameCode;
import com.klst.edoc.untdid.TaxCategoryCode;
import com.klst.edoc.untdid.TaxTypeCode;
import com.klst.eorder.api.AllowancesAndCharges;
import com.klst.eorder.api.CoreOrder;
import com.klst.eorder.api.OrderLine;
import com.klst.eorder.api.OrderNote;
import com.klst.eorder.api.SupportingDocument;

import un.unece.uncefact.data.standard.qualifieddatatype._128.PackageTypeCodeType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.DocumentLineDocumentType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.LineTradeAgreementType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.LineTradeDeliveryType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.LineTradeSettlementType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.ProductCharacteristicType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.ProductClassificationType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.ReferencedDocumentType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.SpatialDimensionType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.SpecifiedPeriodType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.SupplyChainEventType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.SupplyChainPackagingType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.SupplyChainTradeLineItemType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.TradeAccountingAccountType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.TradeAllowanceChargeType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.TradePriceType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128.TradeSettlementLineMonetarySummationType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.DateTimeType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.IDType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.IndicatorType;
import un.unece.uncefact.identifierlist.standard.iso.isotwo_lettercountrycode.secondedition2006.ISOTwoletterCountryCodeContentType;

/*
BG-25 1..n LINE
BT-126 1..1 line identifier
BT-127 0..1 line note
BT-128 0..1 line object identifier
       0..1 Scheme identifier
BT-129 1..1 quantity
BT-130 1..1 quantity unit of measure code
BT-131 1..1 line net amount
BT-132 0..1 Referenced purchase order line reference
BT-133 0..1 line Buyer accounting reference

BG-26  0..1 LINE PERIOD
...
BG-27  0..n LINE ALLOWANCES
...
BG-28  0..n LINE CHARGES
...
BG-29 ++ 1..1 PRICE DETAILS               ------> ram:SpecifiedLineTradeAgreement 
BT-146 +++ 1..1 Item net price                                ram:NetPriceProductTradePrice ram:ChargeAmount
BT-147 +++ 0..1 Item price discount
BT-148 +++ 0..1 Item gross price
BT-149 +++ 0..1 Item price base quantity                      ram:NetPriceProductTradePrice ram:BasisQuantity
BT-150 +++ 0..1 Item price base quantity unit of measure code ram:NetPriceProductTradePrice ram:BasisQuantity

BG-30 ++ 1..1 LINE VAT INFORMATION
...
BG-31 ++ 1..1 ITEM INFORMATION            ------> ram:SpecifiedTradeProduct
...
BG-32 +++ 0..n ITEM ATTRIBUTES

0   CODE	Profile	  LINE
33  SCT_LINE	BASIC	  DOCUMENT LINE
34  SCT_LINE	BASIC	  (ASSOCIATED DOCUMENT LINE)
35  SCT_LINE	BASIC	  Document Line ID (here Order Line ID)
36  SCT_LINE	BASIC	  Line status code
37  SCT_LINE	BASIC	  LINE NOTE
38  SCT_LINE	EXTENDED  Line Note Content Code                               TODO
39  SCT_LINE	BASIC	  Line Note Content
40  SCT_LINE	BASIC	  Line Note Subject Code
41  SCT_LINE	BASIC	  ITEM (TRADE PRODUCT)
42  SCT_LINE	EXTENDED  Item (Trade Product) ID
43  SCT_LINE	BASIC	  Item (Trade Product) Global ID
44  SCT_LINE	BASIC	  Item (Trade Product) Global ID Scheme ID
45  SCT_LINE	BASIC	  Item (Trade Product) Seller Assigned ID
46  SCT_LINE	BASIC	  Item (Trade Product) Buyer Assigned ID
47  SCT_LINE	EXTENDED  Item (Trade Product) Industry Assigned ID
48  SCT_LINE	EXTENDED  Item (Trade Product) Model Name ID
49  SCT_LINE	BASIC	  Item (Trade Product) Name
50  SCT_LINE	COMFORT	  Item (Trade Product) Description
51  SCT_LINE	COMFORT	  Item (Trade Product) Batch ID (lot ID)
52  SCT_LINE	COMFORT	  Item (Trade Product) Brand Name
53  SCT_LINE	EXTENDED  Item (Trade Product) Model Name
54  SCT_LINE	COMFORT	  ITEM ATTRIBUTES / PRODUCT CHARACTERISTICS
55  SCT_LINE	COMFORT	  Item (Trade Product) Characteristics Code
56  SCT_LINE	COMFORT	  Item (Trade Product) Characteristics Description
57  SCT_LINE	EXTENDED  Item (Trade Product) Characteristics Value as Measure
58  SCT_LINE	EXTENDED  Item (Trade Product) Characteristics Value as Measure, Unit of measure
59  SCT_LINE	COMFORT	  Item (Trade Product) Characteristics Value as Text
60  SCT_LINE	COMFORT	  (Item (Trade Product) Classification Class Code) / BG-31.BT-158
61  SCT_LINE	COMFORT	  Item (Trade Product) Classification Class Code
62  SCT_LINE	COMFORT	  Item (Trade Product) Classification Class Code scheme ID
63  SCT_LINE	COMFORT	  Item (Trade Product) Classification Class Code Scheme ID Version
64  SCT_LINE	COMFORT	  Item (Trade Product) Classification Class Name
65  SCT_LINE	COMFORT	  Item (Trade Product) Instances
66  SCT_LINE	COMFORT	  Item (Trade Product) Instances Batch ID
67  SCT_LINE	COMFORT	  Item (Trade Product) Instances Supplier Serial ID

68  SCT_LINE	COMFORT	  Packaging
69  SCT_LINE	COMFORT	  Packaging TypeCode
70  SCT_LINE	COMFORT	  Packaging Dimension
71  SCT_LINE	COMFORT	  Packaging Dimension Width
72  SCT_LINE	COMFORT	  Packaging Dimension Width UnitCode
73  SCT_LINE	COMFORT	  Packaging Dimension Length
74  SCT_LINE	COMFORT	  Packaging Dimension Length UnitCode
75  SCT_LINE	COMFORT	  Packaging Dimension Height
76  SCT_LINE	COMFORT	  Packaging Dimension Height UnitCode

77  SCT_LINE	COMFORT	  Item (Trade Product) Origin Country
78  SCT_LINE	COMFORT	  Item (Trade Product) Origin Country ID

79  SCT_LINE	COMFORT	  ADDITIONAL REFERENCED PRODUCT DOCUMENT
80  SCT_LINE	COMFORT	  Additional Referenced Product Document - ID
81  SCT_LINE	COMFORT	  Additional Referenced Product Document - External document location
82  SCT_LINE	COMFORT	  Additional Referenced Product Document - Type Code
83  SCT_LINE	COMFORT	  Additional Referenced Product Document - Description
84  SCT_LINE	COMFORT	  Additional Referenced Product Document - Attached document
85  SCT_LINE	COMFORT	  Additional Referenced Product Document - Attached document Mime code
86  SCT_LINE	COMFORT	  Additional Referenced Product Document- Attached document Filename

87  SCT_LINE	EXTENDED  REFERENCED PRODUCT
88  SCT_LINE	EXTENDED  Referenced Product ID
89  SCT_LINE	EXTENDED  Referenced Product Global ID
90  SCT_LINE	EXTENDED  Referenced Product Global ID Scheme IDScheme
91  SCT_LINE	EXTENDED  Referenced Product Seller Assigned ID
92  SCT_LINE	EXTENDED  Referenced Product Buyer Assigned ID
93  SCT_LINE	EXTENDED  Referenced Product Industry Assigned ID
94  SCT_LINE	EXTENDED  Referenced Product Name
95  SCT_LINE	EXTENDED  Referenced Product Description
96  SCT_LINE	EXTENDED  Referenced Product Unit Quantity
97  SCT_LINE	EXTENDED  Referenced Product UnitCode for Unit Quantity
98  SCT_LINE	BASIC	  SUBSTITUTED PRODUCT 
99  SCT_LINE	EXTENDED  Substituted Product ID
100 SCT_LINE	BASIC	  Substituted Product Global ID
101 SCT_LINE	BASIC	  Substituted Product Global ID Scheme IDScheme
102 SCT_LINE	BASIC	  Substituted Product Seller Assigned ID
103 SCT_LINE	BASIC	  Substituted Product Buyer Assigned ID
104 SCT_LINE	EXTENDED  Substituted Product Industry Assigned ID
105 SCT_LINE	BASIC	  Substituted Product Name
106 SCT_LINE	COMFORT	  Substituted Product Description
107 SCT_LINE_TA BASIC	  LINE TRADE AGREEMENT
108 SCT_LINE_TA EXTENDED  Minimum Orderable Quantity
109 SCT_LINE_TA EXTENDED  Unit of Measure of Minimum Orderable Quantity
110 SCT_LINE_TA EXTENDED  Maximum Orderable Quantity
111 SCT_LINE_TA EXTENDED  Unit of Measure of Maximum Orderable Quantity
112 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator)
113 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner ID
114 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner Global ID
115 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner Global ID Shceme ID
116 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner Name
117 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator) - Contact
118 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator) - Person Name
119 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator) - Department Name
120 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator) Contact - Type
121 SCT_LINE_TA EXTENDED  (Line Buyer Requisitioner (originator) - telephone number)
122 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator) - telephone number
123 SCT_LINE_TA EXTENDED  (Line Buyer Requisitioner (originator) Contact -Fax number)
124 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator) Contact -Fax number
125 SCT_LINE_TA EXTENDED  (Line Buyer Requisitioner (originator) - email address)
126 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator) - email address
127 SCT_LINE_TA BASIC	  (REFERENCED BUYER ORDER LINE REFERENCE)
128 SCT_LINE_TA BASIC	  Referenced Buyer Order line reference
129 SCT_LINE_TA COMFORT	  QUOTATION REFERENCE
130 SCT_LINE_TA COMFORT	  Quotation Reference ID
131 SCT_LINE_TA COMFORT	  Quotation Reference LineID
132 SCT_LINE_TA EXTENDED  (Quotation Reference Date)
133 SCT_LINE_TA EXTENDED  Quotation Reference Date
134 SCT_LINE_TA EXTENDED  Date format
135 SCT_LINE_TA EXTENDED  CONTRACT REFERENCE
136 SCT_LINE_TA EXTENDED  Contract Reference ID
137 SCT_LINE_TA EXTENDED  Contract Reference LineID
138 SCT_LINE_TA EXTENDED  (Contract Reference Date)
139 SCT_LINE_TA EXTENDED  Contract Reference Date
140 SCT_LINE_TA EXTENDED  Date format
141 SCT_LINE_TA COMFORT	  ADDITIONAL REFERENCED DOCUMENT
142 SCT_LINE_TA COMFORT	  Additional Referenced Document - ID
143 SCT_LINE_TA COMFORT	  Additional Referenced Document - External document location
144 SCT_LINE_TA COMFORT	  Additional Referenced Document - Line ID
145 SCT_LINE_TA COMFORT	  Additional Referenced Document - Type Code
146 SCT_LINE_TA COMFORT	  Additional Referenced Document - Description
147 SCT_LINE_TA COMFORT	  Additional Referenced Document - Attached document
148 SCT_LINE_TA COMFORT	  Additional Referenced Document - Attached document Mime code
149 SCT_LINE_TA COMFORT	  Additional Referenced Document- Attached document Filename
150 SCT_LINE_TA COMFORT	  Additional Referenced Document - Reference Type Code
151 SCT_LINE_TA EXTENDED  (Additional Referenced Document -  Date)
152 SCT_LINE_TA EXTENDED  Additional Referenced Document -  Date
153 SCT_LINE_TA EXTENDED  Date format
154 SCT_LINE_TA COMFORT	  (OBJECT IDENTIFIER FOR INVOICE LINE)
155 SCT_LINE_TA COMFORT	  Order line object identifier
156 SCT_LINE_TA COMFORT	  Order line object identifier - Typecode
157 SCT_LINE_TA COMFORT	  Order line object identifier - TypecodeAd BR (
158 SCT_LINE_TA COMFORT	  (Gross Price)
159 SCT_LINE_TA COMFORT	  Gross Price
160 SCT_LINE_TA COMFORT	  Gross Price Base quantity
161 SCT_LINE_TA COMFORT	  Gross Price Unit Code for base quantity
162 SCT_LINE_TA COMFORT	  (((Item price discount)))
163 SCT_LINE_TA COMFORT	  ((Item price discount))
164 SCT_LINE_TA COMFORT	  (Item price discount)
165 SCT_LINE_TA EXTENDED  Item price discount CalculationPercent
166 SCT_LINE_TA EXTENDED  Item price discount BasisAmount
167 SCT_LINE_TA COMFORT	  Item price discount
168 SCT_LINE_TA COMFORT	  Item price discount Reason Code
169 SCT_LINE_TA COMFORT	  Item price discount Reason
170 SCT_LINE_TA COMFORT	  (((Item price charge)))
171 SCT_LINE_TA COMFORT	  ((Item price charge))
172 SCT_LINE_TA COMFORT	  (Item price charge)
173 SCT_LINE_TA EXTENDED  Item price charge CalculationPercent
174 SCT_LINE_TA EXTENDED  Item price discount BasisAmount
175 SCT_LINE_TA COMFORT	  Item price charge amount
176 SCT_LINE_TA COMFORT	  Item price charge Reason Code
177 SCT_LINE_TA COMFORT	  Item price charge Reason
178 SCT_LINE_TA BASIC	  (Net Price)
179 SCT_LINE_TA BASIC	  Net Price
180 SCT_LINE_TA BASIC	  Net Price Base quantity
181 SCT_LINE_TA BASIC	  Unit Code for base quantity
182 SCT_LINE_TA EXTENDED  Minimum Quantity for this Unit Price
183 SCT_LINE_TA EXTENDED  UnitCode for  Minimum Quantity
184 SCT_LINE_TA EXTENDED  Maximum Quantity for this Unit Price
185 SCT_LINE_TA EXTENDED  UnitCode for  Maximum Quantity
186 SCT_LINE_TA EXTENDED  INCLUDED TRADE TAX
187 SCT_LINE_TA EXTENDED  Included Tade Tax Calculated Amount
188 SCT_LINE_TA EXTENDED  Included Tade Tax type code on line level
189 SCT_LINE_TA EXTENDED  Included Tade Tax Exemption Reason
190 SCT_LINE_TA EXTENDED  Included Tade Tax Type (category) Code
191 SCT_LINE_TA EXTENDED  Included Tade Tax Exemption Reason Code
192 SCT_LINE_TA EXTENDED  Included Tade Tax Type rate
193 SCT_LINE_TA COMFORT	  (CATALOGUE REFERENCED DOC)
194 SCT_LINE_TA COMFORT	  Catalogue Referenced Doc ID
195 SCT_LINE_TA COMFORT	  Catalogue Referenced Doc LineID
196 SCT_LINE_TA EXTENDED  (Catalog Reference Date)
197 SCT_LINE_TA EXTENDED  Catalog Reference Date
198 SCT_LINE_TA EXTENDED  Date format
199 SCT_LINE_TA BASIC	  (BLANKET ORDER REFERENCED LINE ID)
200 SCT_LINE_TA BASIC	  Blanket Order Referenced Line ID
201 SCT_LINE_TA EXTENDED  ULTIMATE CUSTOMER ORDER REFERENCED DOCUMENT
202 SCT_LINE_TA EXTENDED  Ultimate Customer Order Referenced Doc ID
203 SCT_LINE_TA EXTENDED  Ultimate Customer Order Referenced Doc LineID
204 SCT_LINE_TA EXTENDED  (Ultimate Customer Order Date)
205 SCT_LINE_TA EXTENDED  Ultimate Customer Order Date
206 SCT_LINE_TA EXTENDED  Date format
207 SCT_LINE_TD BASIC	  LINE TRADE DELIVERY
208 SCT_LINE_TD BASIC	  Partial Delivery Allowed Indicator
209 SCT_LINE_TD BASIC	  Partial Delivery Allowed Indicator Value
210 SCT_LINE_TD BASIC	  Requested Quantity
211 SCT_LINE_TD BASIC	  Unit of measure Code for Requested quantity
212 SCT_LINE_TD BASIC	  Agreed Quantity
213 SCT_LINE_TD BASIC	  Unit of measure Code for Agreed quantity
214 SCT_LINE_TD COMFORT	  Package Quantity
215 SCT_LINE_TD COMFORT	  Unit of measure Code for Package quantity
216 SCT_LINE_TD COMFORT	  Per Package Quantity
217 SCT_LINE_TD COMFORT	  Unit of measure Code for Per Package quantity
218 SCT_LINE_TD EXTENDED  SHIP TO PARTY
219 SCT_LINE_TD EXTENDED  Ship To ID
220 SCT_LINE_TD EXTENDED  Ship To Global ID
221 SCT_LINE_TD EXTENDED  SchemeID
222 SCT_LINE_TD EXTENDED  Ship To Name
223 SCT_LINE_TD EXTENDED  (Ship to Legal ID)
224 SCT_LINE_TD EXTENDED  Ship to Legal ID
225 SCT_LINE_TD EXTENDED  Ship to Legal ID scheme ID
226 SCT_LINE_TD EXTENDED  Ship to Trading Name
227 SCT_LINE_TD EXTENDED  SHIP TO CONTACT
228 SCT_LINE_TD EXTENDED  SHIP TO Contact - Person Name
229 SCT_LINE_TD EXTENDED  SHIP TO Contact - Department Name
230 SCT_LINE_TD EXTENDED  SHIP TO Contact - Type
231 SCT_LINE_TD EXTENDED  (SHIP TO Contact - telephone number)
232 SCT_LINE_TD EXTENDED  SHIP TO Contact - telephone number
233 SCT_LINE_TD EXTENDED  (SHIP TO Contact -Fax number)
234 SCT_LINE_TD EXTENDED  SHIP TO Contact -Fax number
235 SCT_LINE_TD EXTENDED  (SHIP TO Contact - email address)
236 SCT_LINE_TD EXTENDED  SHIP TO Contact - email address
237 SCT_LINE_TD EXTENDED  SHIP TO POSTAL ADDRESS
238 SCT_LINE_TD EXTENDED  Ship To Address / Postal Code
239 SCT_LINE_TD EXTENDED  Ship To Address / Line One
240 SCT_LINE_TD EXTENDED  Ship To Address / Line Two
241 SCT_LINE_TD EXTENDED  Ship To Address / Line Three
242 SCT_LINE_TD EXTENDED  Ship To Address / City Name
243 SCT_LINE_TD EXTENDED  Ship To Country Code
244 SCT_LINE_TD EXTENDED  Ship To Country Subdivision
245 SCT_LINE_TD EXTENDED  SHIP TO ELECTRONIC ADDRESS
246 SCT_LINE_TD EXTENDED  SHIP TO Electronic Address ID
247 SCT_LINE_TD EXTENDED  SHIP TO Electronic Address Scheme ID
248 SCT_LINE_TD EXTENDED  SHIP TO TAX REGISTRATION
249 SCT_LINE_TD EXTENDED  SHIP TO VAT Identifier
250 SCT_LINE_TD EXTENDED
251 SCT_LINE_TD EXTENDED  ULTIMATE SHIP TO PARTY
252 SCT_LINE_TD EXTENDED  Ultimate Ship To ID
253 SCT_LINE_TD EXTENDED  Ultimate Ship To Global ID
254 SCT_LINE_TD EXTENDED  SchemeID
255 SCT_LINE_TD EXTENDED  Ultimate Ship To Name
256 SCT_LINE_TD EXTENDED  (ULTIMATE SHIP TO LEGAL)
257 SCT_LINE_TD EXTENDED  Ultimate Ship to Legal ID
258 SCT_LINE_TD EXTENDED  Ultimate Ship to Legal ID scheme ID
259 SCT_LINE_TD EXTENDED  Ultimate Ship to Trading Name
260 SCT_LINE_TD EXTENDED  ULTIMATE SHIP TO CONTACT
261 SCT_LINE_TD EXTENDED  Ultimate Ship TO Contact - Person Name
262 SCT_LINE_TD EXTENDED  Ultimate Ship TO Contact - Department Name
263 SCT_LINE_TD EXTENDED  Ultimate Ship TO Contact - Type
264 SCT_LINE_TD EXTENDED  (Ultimate Ship TO Contact - telephone number)
265 SCT_LINE_TD EXTENDED  Ultimate Ship TO Contact - telephone number
266 SCT_LINE_TD EXTENDED  (Ultimate Ship TO Contact -Fax number)
267 SCT_LINE_TD EXTENDED  Ultimate Ship TO Contact -Fax number
268 SCT_LINE_TD EXTENDED  (Ultimate Ship TO Contact - email address)
269 SCT_LINE_TD EXTENDED  Ultimate Ship TO Contact - email address
270 SCT_LINE_TD EXTENDED  ULTIMATE SHIP TO POSTAL ADDRESS
271 SCT_LINE_TD EXTENDED  Ultimate Ship To Address / Postal Code
272 SCT_LINE_TD EXTENDED  Ultimate Ship To Address / Line One
273 SCT_LINE_TD EXTENDED  Ultimate Ship To Address / Line Two
274 SCT_LINE_TD EXTENDED  Ultimate Ship To Address / Line Three
275 SCT_LINE_TD EXTENDED  Ultimate Ship To Address / City Name
276 SCT_LINE_TD EXTENDED  Ultimate Ship To Country Code
277 SCT_LINE_TD EXTENDED  Ultimate Ship To Country Subdivision
278 SCT_LINE_TD EXTENDED  ULTIMATE SHIP TO ELECTRONIC ADDRESS
279 SCT_LINE_TD EXTENDED  Ultimate Ship TO Electronic Address Scheme ID
280 SCT_LINE_TD EXTENDED  Ultimate Ship TO Electronic Address Scheme ID
281 SCT_LINE_TD EXTENDED  ULTIMATE SHIP TO TAX REGISTRATION
282 SCT_LINE_TD EXTENDED  Ultimate Ship TO VAT Identifier
283 SCT_LINE_TD EXTENDED
284 SCT_LINE_TD COMFORT	  LINE REQUESTED PICK UP DATE - PERIOD
285 SCT_LINE_TD COMFORT	  Requested Pick up Date
286 SCT_LINE_TD COMFORT
287 SCT_LINE_TD COMFORT	  Date format
288 SCT_LINE_TD EXTENDED  Unit Quantity to be pick up in this event
289 SCT_LINE_TD EXTENDED  Unit of measure Code for Unit Quantity to be pick up in this event
290 SCT_LINE_TD COMFORT	  Requested Pick up Period
291 SCT_LINE_TD COMFORT	  Start Date
292 SCT_LINE_TD COMFORT
293 SCT_LINE_TD COMFORT	  Date format
294 SCT_LINE_TD COMFORT	  End Date
295 SCT_LINE_TD COMFORT
296 SCT_LINE_TD COMFORT	  Date format
297 SCT_LINE_TD COMFORT	  LINE REQUESTED DELIVERY DATE - PERIOD
298 SCT_LINE_TD COMFORT	  Requested Delivery Date
299 SCT_LINE_TD COMFORT
300 SCT_LINE_TD COMFORT	  Date format
301 SCT_LINE_TD EXTENDED  Unit Quantity to be delivered in this event
302 SCT_LINE_TD EXTENDED  Unit of measure Code for Unit Quantity to be delivered
303 SCT_LINE_TD COMFORT	  Requested Delivery Period
304 SCT_LINE_TD COMFORT	  Start Date
305 SCT_LINE_TD COMFORT
306 SCT_LINE_TD COMFORT	  Date format
307 SCT_LINE_TD COMFORT	  End Date
308 SCT_LINE_TD COMFORT
309 SCT_LINE_TD COMFORT	  Date format
310 SCT_LINE_TS BASIC	  LINE TRADE SETTLEMENT
311 SCT_LINE_TS COMFORT	  LINE VAT
312 SCT_LINE_TS EXTENDED  VAT Calculated Amount
313 SCT_LINE_TS COMFORT	  VAT type code on line level
314 SCT_LINE_TS EXTENDED  VAT Exemption Reason
315 SCT_LINE_TS COMFORT	  Tax Type (category) Code
316 SCT_LINE_TS EXTENDED  VAT Exemption Reason Code
317 SCT_LINE_TS COMFORT	  Tax Type (category) rate

318 SCT_LINE_TS COMFORT	  LINE ALLOWANCES
319 SCT_LINE_TS COMFORT	  Charges and Allowances line Indicator
320 SCT_LINE_TS COMFORT	  Line Allowances indicator value
321 SCT_LINE_TS COMFORT	  line allowance percentage
322 SCT_LINE_TS COMFORT	  line allowance base amount
323 SCT_LINE_TS COMFORT	  line allowance amount
324 SCT_LINE_TS COMFORT	  line allowance reason code
325 SCT_LINE_TS COMFORT	  line allowance reason
326 SCT_LINE_TS COMFORT	  LINE CHARGES
327 SCT_LINE_TS COMFORT	  Charges and Allowances line Indicator
328 SCT_LINE_TS COMFORT	  Line Charges indicator value
329 SCT_LINE_TS COMFORT	  line charge percentage
330 SCT_LINE_TS COMFORT	  line charge base amount
331 SCT_LINE_TS COMFORT	  line charge amount
332 SCT_LINE_TS COMFORT	  line charge reason code
333 SCT_LINE_TS COMFORT	  line charge reason

334 SCT_LINE_TS BASIC	  LINE TOTAL AMOUNTS
335 SCT_LINE_TS BASIC	  Line Total Amount (without VAT)
336 SCT_LINE_TS EXTENDED  Line Total Charges (without VAT)
337 SCT_LINE_TS EXTENDED  Line Total Allowance Amount (without VAT)
338 SCT_LINE_TS EXTENDED  Line Total Tax amount
339 SCT_LINE_TS EXTENDED  Line Total Charges and Allowances Amount (without VAT)
340 SCT_LINE_TS COMFORT	  (IORDER LINE BUYER ACCOUNTING REFERENCE)
341 SCT_LINE_TS COMFORT	  Order line Buyer accounting reference
342 SCT_LINE_TS EXTENDED  TypeCode


 */
/**
 * BG-25 ORDER LINE
 * <p>
 * A group of business terms providing information on individual order line.
 * <p>
 * Similar to EN16931 business group BG-25
 */
public class SupplyChainTradeLineItem extends SupplyChainTradeLineItemType implements OrderLine {

	@Override
	public OrderLine createOrderLine(String id, IQuantity quantity, IAmount lineTotalAmount,
			IAmount priceAmount, String itemName, TaxCategoryCode taxCat, BigDecimal percent) {
		return create(this.order, id, quantity, lineTotalAmount, (UnitPriceAmount)priceAmount, itemName, taxCat, percent);
	}

	static SupplyChainTradeLineItem create(CoreOrder order, String id, IQuantity quantity, IAmount lineTotalAmount, 
			UnitPriceAmount priceAmount, String itemName, TaxCategoryCode taxCat, BigDecimal percent) {
		SupplyChainTradeLineItem orderLine =  new SupplyChainTradeLineItem(id, quantity, lineTotalAmount, priceAmount, itemName, taxCat, percent);
		orderLine.order = order;
		return orderLine;
	}

	// copy factory
	static SupplyChainTradeLineItem create(SupplyChainTradeLineItemType object, CoreOrder order) {
		SupplyChainTradeLineItem res;
		if(object instanceof SupplyChainTradeLineItemType && object.getClass()!=SupplyChainTradeLineItemType.class) {
			// object is instance of a subclass of SupplyChainTradeLineItemType, but not SupplyChainTradeLineItemType itself
			res = (SupplyChainTradeLineItem)object;
		} else {
			res = new SupplyChainTradeLineItem(object); 
		}
		res.order = order;
		return res;
	}

	private static final Logger LOG = Logger.getLogger(SupplyChainTradeLineItem.class.getName());
	private static final String FIELD_associatedDocumentLineDocument = "associatedDocumentLineDocument";
	private static final String FIELD_specifiedTradeProduct = "specifiedTradeProduct";
	private static final String FIELD_lineID = "lineID";
	private static final String FIELD_id = "id";
	private static final String FIELD_name = "name";
	private static final String FIELD_description = "description";
	private static final String FIELD_sellerAssignedID = "sellerAssignedID";
	private static final String FIELD_buyerAssignedID = "buyerAssignedID";
	private static final String FIELD_substitutedReferencedProduct = "substitutedReferencedProduct";
	private static final String FIELD_grossPriceProductTradePrice = "grossPriceProductTradePrice";
	private static final String FIELD_netPriceProductTradePrice = "netPriceProductTradePrice";
	
	private CoreOrder order; // order this orderLine belongs to
	private TradeTax tradeTax;

	
	// copy ctor
	private SupplyChainTradeLineItem(SupplyChainTradeLineItemType line) {
		super();
		if(line!=null) {
			SCopyCtor.getInstance().invokeCopy(this, line);
			LOG.fine("copy ctor:"+this);
		}
		this.order = null;
		tradeTax = specifiedLineTradeSettlement.getApplicableTradeTax()==null ? null 
				: TradeTax.create(specifiedLineTradeSettlement.getApplicableTradeTax());
	}
	
	private SupplyChainTradeLineItem(String id, IQuantity quantity, IAmount lineTotalAmount, 
		UnitPriceAmount priceAmount, String itemName, TaxCategoryCode taxCat, BigDecimal percent) {
		super.setAssociatedDocumentLineDocument(new DocumentLineDocumentType()); // mit id
		super.setSpecifiedLineTradeAgreement(new LineTradeAgreementType()); // mit setUnitPriceAmount
		super.setSpecifiedLineTradeDelivery(new LineTradeDeliveryType()); // mit quantity
		super.setSpecifiedLineTradeSettlement(new LineTradeSettlementType());
// optional		super.setSpecifiedTradeProduct(new TradeProductType()); // mit ItemName
		setId(id);
		setQuantity(quantity);
		setLineTotalAmount(lineTotalAmount);
		setUnitPriceAmount(priceAmount);
		setItemName(itemName);
		if(taxCat!=null) setTaxCategoryAndRate(taxCat, percent);
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("[Id:"+getId()); 
		stringBuilder.append(", ItemName:");
		stringBuilder.append(getItemName()==null ? "null" : getItemName());
		stringBuilder.append(", Quantity:");
		stringBuilder.append(getQuantity()==null ? "null" : getQuantity());
		stringBuilder.append(", LineTotalAmount:");
		stringBuilder.append(getLineTotalAmount()==null ? "null" : getLineTotalAmount());
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

/*
	<ram:IncludedSupplyChainTradeLineItem>                                <!-- 33: DOCUMENT LINE
		<ram:AssociatedDocumentLineDocument>                              <!-- 34:
			<ram:LineID>1</ram:LineID>                                    <!-- 35: BG.25.BT-126 1..1
			<ram:IncludedNote>                                            <!-- 37: BT-127 0..1 line note
				<ram:Content>Content of Note</ram:Content>
				<ram:SubjectCode>AAI</ram:SubjectCode>
			</ram:IncludedNote>
		</ram:AssociatedDocumentLineDocument>
		<ram:SpecifiedTradeProduct>
			<ram:GlobalID schemeID="0160">1234567890123</ram:GlobalID>
			<ram:SellerAssignedID>987654321</ram:SellerAssignedID>
			<ram:BuyerAssignedID>654987321</ram:BuyerAssignedID>
			<ram:Name>Product Name</ram:Name>                             <!-- BG-31.BT-153 0..1
		</ram:SpecifiedTradeProduct>
		<ram:SpecifiedLineTradeAgreement>
			<ram:BuyerOrderReferencedDocument>
				<ram:LineID>1</ram:LineID>
			</ram:BuyerOrderReferencedDocument>
			<ram:NetPriceProductTradePrice>
				<ram:ChargeAmount>10.00</ram:ChargeAmount>
				<ram:BasisQuantity unitCode="C62">1.00</ram:BasisQuantity>
			</ram:NetPriceProductTradePrice>
			<ram:BlanketOrderReferencedDocument>
				<ram:LineID>2</ram:LineID>
			</ram:BlanketOrderReferencedDocument>
		</ram:SpecifiedLineTradeAgreement>
		<ram:SpecifiedLineTradeDelivery>
			<ram:PartialDeliveryAllowedIndicator>
				<udt:Indicator>true</udt:Indicator>
			</ram:PartialDeliveryAllowedIndicator>
			<ram:RequestedQuantity unitCode="C62">6</ram:RequestedQuantity> <!-- BT-129+BT-130 1..1
		</ram:SpecifiedLineTradeDelivery>
		<ram:SpecifiedLineTradeSettlement>
			<ram:SpecifiedTradeSettlementLineMonetarySummation>
				<ram:LineTotalAmount>60.00</ram:LineTotalAmount>            <!-- BT-131 1..1
			</ram:SpecifiedTradeSettlementLineMonetarySummation>
		</ram:SpecifiedLineTradeSettlement>
	</ram:IncludedSupplyChainTradeLineItem>
 */
	// 35: BG.25.BT-126 1..1
	@Override
	public String getId() {
		return super.getAssociatedDocumentLineDocument().getLineID().getValue();
	}
	void setId(String id) {
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_associatedDocumentLineDocument, id);
		SCopyCtor.getInstance().set(getAssociatedDocumentLineDocument(), FIELD_lineID, id);
	}

	// 36: 0..1
	@Override
	public String getStatus() {
		return super.getAssociatedDocumentLineDocument().getLineStatusCode().getValue();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStatus(String status) {
		if(this.order.getDocumentCode()==DocumentNameCode.Order) {
			LOG.warning(WARN_ORDERLINEID + "ignore status:'"+status+"'.");
			return;
		}
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_associatedDocumentLineDocument, status);
		SCopyCtor.getInstance().set(getAssociatedDocumentLineDocument(), "lineStatusCode", status);
	}

	// 37: BT-127 0..1/n Freitext zur Rechnungsposition : ram:IncludedNote
	// 38  SCT_LINE	EXTENDED  Line Note Content Code          TODO später
	// 39  SCT_LINE	BASIC	  Line Note Content
	// 40  SCT_LINE	BASIC	  Line Note Subject Code
	@Override // getter
	public List<OrderNote> getNotes() {
		DocumentLineDocumentType dld = super.getAssociatedDocumentLineDocument();
		if(dld==null) return null;
		// delegieren:
		return Note.getNotes(dld.getIncludedNote());
	}
	@Override
	public OrderNote createNote(String subjectCode, String content) {
		return Note.create(subjectCode, content);
	}
	@Override
	public void addNote(OrderNote note) {
		DocumentLineDocumentType dld = super.getAssociatedDocumentLineDocument();
		dld.getIncludedNote().add((Note)note);
	}
	
	// 41: BG-31 SpecifiedTradeProduct ----------------------------------------------
	
	// 42: 0..1 SpecifiedTradeProduct.ID
	@Override
	public void setProductID(String id) {
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_specifiedTradeProduct, id);
		SCopyCtor.getInstance().set(getSpecifiedTradeProduct(), FIELD_id, id);
	}
	@Override
	public String getProductID() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return super.getSpecifiedTradeProduct().getID().getValue();
	}

	// 43: BG-31.BT-157 0..n Item (Trade Product) Global ID
	// 44:                   Item (Trade Product) Global ID Scheme ID
	@Override
	public Identifier createStandardIdentifier(String globalID, String schemeID) {
		return new ID(globalID, schemeID);
	}
	@Override
	public void addStandardIdentifier(Identifier id) {
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_specifiedTradeProduct, id);
		super.getSpecifiedTradeProduct().getGlobalID().add((ID)id);
	}
	@Override
	public List<Identifier> getStandardIdentifier() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		List<IDType> list = getSpecifiedTradeProduct().getGlobalID();
		List<Identifier> result = new ArrayList<Identifier>(list.size());
		list.forEach(id -> {
			result.add(new ID(id));
		});
		return result;
	}

	// 45: BG-31.BT-155 0..1 SpecifiedTradeProduct.sellerAssignedID
	@Override
	public void setSellerAssignedID(String id) {
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_specifiedTradeProduct, id);
		SCopyCtor.getInstance().set(getSpecifiedTradeProduct(), FIELD_sellerAssignedID, id);
	}
	@Override
	public String getSellerAssignedID() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return new ID(super.getSpecifiedTradeProduct().getSellerAssignedID()).getContent();
	}

	// 46: BG-31.BT-156 0..1 SpecifiedTradeProduct.buyerAssignedID
	@Override
	public void setBuyerAssignedID(String id) {
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_specifiedTradeProduct, id);
		SCopyCtor.getInstance().set(getSpecifiedTradeProduct(), FIELD_buyerAssignedID, id);		
	}
	@Override
	public String getBuyerAssignedID() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return new ID(super.getSpecifiedTradeProduct().getBuyerAssignedID()).getContent();
	}

	// 47: 0..1 SpecifiedTradeProduct.industryAssignedID
//	@Override
//	public void setIndustryID(String id) {
//		Mapper.newFieldInstance(this, FIELD_specifiedTradeProduct, id);
//		Mapper.set(getSpecifiedTradeProduct(), "industryAssignedID", id);
//	}
//	@Override
//	public String getIndustryID() {
//		if(super.getSpecifiedTradeProduct()==null) return null;
//		return super.getSpecifiedTradeProduct().getIndustryAssignedID().getValue();
//	}
	
	// 48: 0..1 SpecifiedTradeProduct.modelID
//	@Override
//	public void setModelID(String id) {
//		Mapper.newFieldInstance(this, FIELD_specifiedTradeProduct, id);
//		Mapper.set(getSpecifiedTradeProduct(), "modelID", id);
//	}
//	@Override
//	public String getModelID() {
//		if(super.getSpecifiedTradeProduct()==null) return null;
//		return super.getSpecifiedTradeProduct().getModelID().getValue();
//	}
	
	// 49: BG-31.BT-153 1..1 SpecifiedTradeProduct.Name
	void setItemName(String text) { // not public, user factory
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_specifiedTradeProduct, text);
		SCopyCtor.getInstance().set(getSpecifiedTradeProduct(), FIELD_name, text);
	}
	@Override
	public String getItemName() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return Text.create(super.getSpecifiedTradeProduct().getName()).getValue();
	}

	// 50: BG-31.BT-154 0..1 Item description
	@Override
	public void setDescription(String text) {
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_specifiedTradeProduct, text);
		SCopyCtor.getInstance().set(getSpecifiedTradeProduct(), FIELD_description, text);
	}
	@Override
	public String getDescription() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return Text.create(super.getSpecifiedTradeProduct().getDescription()).getValue();
	}

	// 51: 0..1 SpecifiedTradeProduct.batchID
	@Override
	public void setBatchID(String id) {
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_specifiedTradeProduct, id);
		SCopyCtor.getInstance().set(getSpecifiedTradeProduct(), "batchID", id);
	}
	@Override
	public String getBatchID() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return super.getSpecifiedTradeProduct().getBatchID().getValue();
	}

	// 52: 0..1 SpecifiedTradeProduct.brandName
//	@Override
//	public void setBrandName(String name) {
//		Mapper.newFieldInstance(this, FIELD_specifiedTradeProduct, name);
//		Mapper.set(getSpecifiedTradeProduct(), "brandName", name);
//	}
//	@Override
//	public String getBrandName() {
//		if(super.getSpecifiedTradeProduct()==null) return null;
//		return super.getSpecifiedTradeProduct().getBrandName().getValue();
//	}
	
	// 53: 0..1 SpecifiedTradeProduct.modelName
//	@Override
//	public void setModelName(String name) {
//		Mapper.newFieldInstance(this, FIELD_specifiedTradeProduct, name);
//		Mapper.set(getSpecifiedTradeProduct(), "modelName", name);
//	}
//	@Override
//	public String getModelName() {
//		if(super.getSpecifiedTradeProduct()==null) return null;
//		return super.getSpecifiedTradeProduct().getModelName().getValue();
//	}

	// 54: BG-32 0..n ITEM ATTRIBUTES, 
	// 55+59: BT-160 + BT-161 Characteristics Code + Value as Text
	@Override
	public void addItemAttribute(String name, String value) {
		if(name==null) return; // darf nicht sein, denn BT-160 + BT-161 sind mandatory
		// TODO Mapper nutzen: ?????????????
//	    protected List<ProductCharacteristicType> applicableProductCharacteristic;
		ProductCharacteristicType productCharacteristics = new ProductCharacteristicType();
		productCharacteristics.getDescription().add(Text.create(name)); //nur eine wg. 1..1
		productCharacteristics.getValue().add(Text.create(value)); //nur eine wg. 1..1
		specifiedTradeProduct.getApplicableProductCharacteristic().add(productCharacteristics);
		super.setSpecifiedTradeProduct(specifiedTradeProduct);
	}
	@Override
	public Properties getItemAttributes() {
		List<ProductCharacteristicType> productCharacteristics = specifiedTradeProduct.getApplicableProductCharacteristic();
		Properties result = new Properties();
		productCharacteristics.forEach(pc -> {
			result.put(pc.getDescription().get(0).getValue(), pc.getValue().get(0).getValue());			
		});
		return result;
	}

//	56  SCT_LINE	COMFORT	  Item (Trade Product) Characteristics Description
//	57  SCT_LINE	EXTENDED  Item (Trade Product) Characteristics Value as Measure
//	58  SCT_LINE	EXTENDED  Item (Trade Product) Characteristics Value as Measure, Unit of measure
 //	59  SCT_LINE	COMFORT	  Item (Trade Product) Characteristics Value as Text
	
	// 60: BG-31.BT-158 0..n Item classification identifier designatedProductClassification
//	61  SCT_LINE	COMFORT	  Item (Trade Product) Classification Class Code
//	62  SCT_LINE	COMFORT	  Item (Trade Product) Classification Class Code scheme ID
//	63  SCT_LINE	COMFORT	  Item (Trade Product) Classification Class Code Scheme ID Version
//	64  SCT_LINE	COMFORT	  Item (Trade Product) Classification Class Name
	/* ProductClassificationType CodeType classCode (value, listID, listVersionID)
	                             TextType className
value:  A code for classifying the item by its type or nature.
listID: The identification scheme identifier of the Item classification identifier
listVersionID: The version of the identification scheme.
Usage:
Classification codes are used to allow grouping of similar items for a various purposes e.g. 
public procurement (CPV), e- Commerce (UNSPSC) etc.
The identification scheme shall be chosen from the entries in UNTDID 7143 .

 Bsp. ORDER-X_EX01_ORDER_FULL_DATA-COMFORT.xml :
     ...
    <ram:DesignatedProductClassification>
    <ram:ClassCode listID="TST">Class_code</ram:ClassCode>
         <ram:ClassName>Name Class Codification</ram:ClassName> <!-- text zu "TST", TST nicht in UNTDID 7143
    </ram:DesignatedProductClassification>

realistisches Beispiel:
    <ram:DesignatedProductClassification>
    <ram:ClassCode listID="EN">4047247110051</ram:ClassCode>
         <ram:ClassName>EN==EAN==European Article Number</ram:ClassName>
    </ram:DesignatedProductClassification>

	 */
	@Override
	public IdentifierExt createClassificationIdentifier(String classCode, String listID, String listVersionID, String idText) {
		// ignore idText TODO - entweder Code oder Text
		return new Code(classCode, listID, listVersionID);
	}
	@Override
	public void addClassificationIdentifier(IdentifierExt id) {
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_specifiedTradeProduct, id);
		ProductClassificationType productClassificationType = new ProductClassificationType();
		productClassificationType.setClassCode((Code)id);
		super.getSpecifiedTradeProduct().getDesignatedProductClassification().add(productClassificationType);		
	}
	@Override
	public List<IdentifierExt> getClassifications() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		List<ProductClassificationType> list = getSpecifiedTradeProduct().getDesignatedProductClassification();
		List<IdentifierExt> result = new ArrayList<IdentifierExt>(list.size());
		list.forEach(producClass -> {
			IdentifierExt idExt= new Code(producClass.getClassCode());
			//idExt.setIdText(producClass.getClassName().getValue());
			result.add(idExt);
		});
		return result;
	}

	// 65: SCT_LINE	COMFORT	  Item (Trade Product) Instances
//	66  SCT_LINE	COMFORT	  Item (Trade Product) Instances Batch ID
//	67  SCT_LINE	COMFORT	  Item (Trade Product) Instances Supplier Serial ID
	@Override
	public void addTradeProductInstance(String batchId, String serialId) {
		TradeProductInstance tpi = TradeProductInstance.create(batchId, serialId);
		if(tpi!=null) {
			SCopyCtor.getInstance().newFieldInstance(this, FIELD_specifiedTradeProduct, tpi);
			super.getSpecifiedTradeProduct().getIndividualTradeProductInstance().add(tpi);		
		}
	}
	@Override
	public List<TradeProductInstance> getTradeProductInstances() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return TradeProductInstance.getTradeProductInstances(getSpecifiedTradeProduct().getIndividualTradeProductInstance());
	}

	// 68: SCT_LINE	COMFORT	  Packaging
/* Exmpl
        <ram:ApplicableSupplyChainPackaging>
             <ram:TypeCode>7B</ram:TypeCode> <!-- 7B ==	wooden case 
             <ram:LinearSpatialDimension>
                  <ram:WidthMeasure unitCode="MTR">5</ram:WidthMeasure>
                  <ram:LengthMeasure unitCode="MTR">3</ram:LengthMeasure>
                  <ram:HeightMeasure unitCode="MTR">1</ram:HeightMeasure>
             </ram:LinearSpatialDimension>
        </ram:ApplicableSupplyChainPackaging>
 */
	@Override
	public void setPackaging(String code, IQuantity width, IQuantity length, IQuantity height) {
		SpatialDimensionType sd = null;
		if(width==null && length==null && height==null) {
			//
		} else {
			sd = new SpatialDimensionType();
			if(width instanceof Measure) {
				sd.setWidthMeasure((Measure)width);
			}
			if(length instanceof Measure) {
				sd.setLengthMeasure((Measure)length);
			}
			if(height instanceof Measure) {
				sd.setHeightMeasure((Measure)height);
			}
		}
		if(sd==null && code==null) return;
		
		SupplyChainPackagingType scp = new SupplyChainPackagingType();
		scp.setLinearSpatialDimension(sd);
		if(code!=null) {
			PackageTypeCodeType ptc = new PackageTypeCodeType();
			ptc.setValue(code);
			scp.setTypeCode(ptc);
		}
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_specifiedTradeProduct, scp);
		super.getSpecifiedTradeProduct().setApplicableSupplyChainPackaging(scp);		
	}
	// 69: SCT_LINE	COMFORT	  Packaging TypeCode
	@Override
	public String getPackagingCode() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		PackageTypeCodeType ptc = 
		getSpecifiedTradeProduct().getApplicableSupplyChainPackaging()==null
		? null
		: getSpecifiedTradeProduct().getApplicableSupplyChainPackaging().getTypeCode();
				
		return ptc==null ? null : ptc.getValue();
	}
//	70  SCT_LINE	COMFORT	  Packaging Dimension
//	71  SCT_LINE	COMFORT	  Packaging Dimension Width
//	72  SCT_LINE	COMFORT	  Packaging Dimension Width UnitCode
//	73  SCT_LINE	COMFORT	  Packaging Dimension Length
//	74  SCT_LINE	COMFORT	  Packaging Dimension Length UnitCode
//	75  SCT_LINE	COMFORT	  Packaging Dimension Height
//	76  SCT_LINE	COMFORT	  Packaging Dimension Height UnitCode
	private SpatialDimensionType getPackagingSpatialDimension() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return 
		getSpecifiedTradeProduct().getApplicableSupplyChainPackaging()==null
		? null
		: getSpecifiedTradeProduct().getApplicableSupplyChainPackaging().getLinearSpatialDimension();
	}
	@Override
	public IQuantity getPackagingWidth() {
		SpatialDimensionType sd = getPackagingSpatialDimension();
		return sd==null ? null : Measure.create(sd.getWidthMeasure());
	}
	@Override
	public IQuantity getPackagingLength() {
		SpatialDimensionType sd = getPackagingSpatialDimension();
		return sd==null ? null : Measure.create(sd.getLengthMeasure());
	}
	@Override
	public IQuantity getPackagingHeight() {
		SpatialDimensionType sd = getPackagingSpatialDimension();
		return sd==null ? null : Measure.create(sd.getHeightMeasure());
	}

	// 77,78: BG-31.BT-159 0..1 Item country of origin
/*
                    <ram:OriginTradeCountry>
                         <ram:ID>FR</ram:ID>
                    </ram:OriginTradeCountry>
 */
	@Override
	public void setCountryOfOrigin(String code) {
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_specifiedTradeProduct, code);
		SCopyCtor.getInstance().newFieldInstance(getSpecifiedTradeProduct(), "originTradeCountry", code);		
		SCopyCtor.getInstance().set(getSpecifiedTradeProduct().getOriginTradeCountry(), FIELD_id, ISOTwoletterCountryCodeContentType.fromValue(code));
	}
	@Override
	public String getCountryOfOrigin() {
		if(super.getSpecifiedTradeProduct()==null) return null;
		return getSpecifiedTradeProduct().getOriginTradeCountry()==null ? null : getSpecifiedTradeProduct().getOriginTradeCountry().getID().getValue().value();
	}

	// 79: 0..n ADDITIONAL REFERENCED PRODUCT DOCUMENT
//	80  SCT_LINE	COMFORT	  Additional Referenced Product Document - ID
//	81  SCT_LINE	COMFORT	  Additional Referenced Product Document - External document location
//	82  SCT_LINE	COMFORT	  Additional Referenced Product Document - Type Code
//	83  SCT_LINE	COMFORT	  Additional Referenced Product Document - Description
//	84  SCT_LINE	COMFORT	  Additional Referenced Product Document - Attached document
//	85  SCT_LINE	COMFORT	  Additional Referenced Product Document - Attached document Mime code
//	86  SCT_LINE	COMFORT	  Additional Referenced Product Document- Attached document Filename
	// Name in BG-24 anders: ADDITIONAL SUPPORTING DOCUMENTS
	// factory für _79_AdditionalReferencedProductDocs und _141_AdditionalReferencedDocs
	@Override
	public SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description, Timestamp ts, String uri) {
		// delegieren
		ReferencedDocument rd = ReferencedDocument.create(docRefId, lineId, description);
		rd.setExternalDocumentLocation(uri);
		return rd;
	}
	@Override
	public SupportingDocument createSupportigDocument(String docRefId, Reference lineId, String description, Timestamp ts,
			byte[] content, String mimeCode, String filename) {
		// delegieren
		ReferencedDocument rd = ReferencedDocument.create(docRefId, lineId, description);
		rd.setAttachedDocument(content, mimeCode, filename);
		return rd;
	}
	@Override // implements interface _79_AdditionalReferencedProductDocs
	public void addReferencedProductDocument(String code, SupportingDocument supportigDocument) {
		SCopyCtor.getInstance().newFieldInstance(this, FIELD_specifiedTradeProduct, supportigDocument);
		supportigDocument.setDocumentCode(code);
		super.getSpecifiedTradeProduct().getAdditionalReferenceReferencedDocument().add((ReferencedDocument)supportigDocument);		
	}
	@Override
	public List<SupportingDocument> getReferencedProductDocuments() {
		List<SupportingDocument> res = new ArrayList<SupportingDocument>();
		if(super.getSpecifiedTradeProduct()==null) return res;
		List<ReferencedDocumentType> list = getSpecifiedTradeProduct().getAdditionalReferenceReferencedDocument();
		list.forEach(rd -> {
			ReferencedDocument referencedDocument = ReferencedDocument.create(rd);
			res.add(referencedDocument);
		});
		return res;
	}

	// 87: TODO
//rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem/
	//ram:SpecifiedTradeProduct/ram:IncludedReferencedProduct

//	87  SCT_LINE	EXTENDED  REFERENCED PRODUCT
//	88  SCT_LINE	EXTENDED  Referenced Product ID
//	89  SCT_LINE	EXTENDED  Referenced Product Global ID
//	90  SCT_LINE	EXTENDED  Referenced Product Global ID Scheme IDScheme
//	91  SCT_LINE	EXTENDED  Referenced Product Seller Assigned ID
//	92  SCT_LINE	EXTENDED  Referenced Product Buyer Assigned ID
//	93  SCT_LINE	EXTENDED  Referenced Product Industry Assigned ID
//	94  SCT_LINE	EXTENDED  Referenced Product Name
//	95  SCT_LINE	EXTENDED  Referenced Product Description
//	96  SCT_LINE	EXTENDED  Referenced Product Unit Quantity
//	97  SCT_LINE	EXTENDED  Referenced Product UnitCode for Unit Quantity

//	98  SCT_LINE	BASIC	  SUBSTITUTED PRODUCT / OOR only
	private boolean isOOR(Object o) {
		if(this.order.getDocumentCode()==DocumentNameCode.OrderResponse) return true;
		if(o!=null) {
			LOG.warning("Document Type is not OrderResponse, ignore Substituted Product:'"+o+"'.");
		}
		return false;
	}

//	99  SCT_LINE	EXTENDED  Substituted Product ID
	@Override
	public void setSubstitutedProductID(String id) {
		if(isOOR(id)) {
			SCopyCtor.getInstance().newFieldInstance(this, FIELD_substitutedReferencedProduct, id);
			SCopyCtor.getInstance().set(getSubstitutedReferencedProduct(), FIELD_id, id);
		}
	}
	@Override
	public String getSubstitutedProductID() {
		if(super.getSubstitutedReferencedProduct()==null) return null;
		return getSubstitutedReferencedProduct().getID().getValue();
	}
	
//	100 SCT_LINE	BASIC	  Substituted Product Global ID
//	101 SCT_LINE	BASIC	  Substituted Product Global ID Scheme IDScheme
	@Override
	public void addSubstitutedIdentifier(Identifier id) {
		if(isOOR(id)) {
			SCopyCtor.getInstance().newFieldInstance(this, FIELD_substitutedReferencedProduct, id);
			super.getSubstitutedReferencedProduct().getGlobalID().add((ID)id);
		}
	}
	@Override
	public List<Identifier> getSubstitutedIdentifier() {
		if(super.getSubstitutedReferencedProduct()==null) return null;
		List<IDType> list = getSubstitutedReferencedProduct().getGlobalID();
		List<Identifier> result = new ArrayList<Identifier>(list.size());
		list.forEach(id -> {
			result.add(new ID(id));
		});
		return result;
	}
	
//	102 SCT_LINE	BASIC	  Substituted Product Seller Assigned ID
	@Override
	public void setSubstitutedSellerAssignedID(String id) {
		if(isOOR(id)) {
			SCopyCtor.getInstance().newFieldInstance(this, FIELD_substitutedReferencedProduct, id);
			SCopyCtor.getInstance().set(getSubstitutedReferencedProduct(), FIELD_sellerAssignedID, id);
		}
	}
	@Override
	public String getSubstitutedSellerAssignedID() {
		if(super.getSubstitutedReferencedProduct()==null) return null;
		return new ID(getSubstitutedReferencedProduct().getSellerAssignedID()).getContent();
	}

//	103 SCT_LINE	BASIC	  Substituted Product Buyer Assigned ID
	@Override
	public void setSubstitutedBuyerAssignedID(String id) {
		if(isOOR(id)) {
			SCopyCtor.getInstance().newFieldInstance(this, FIELD_substitutedReferencedProduct, id);
			SCopyCtor.getInstance().set(getSubstitutedReferencedProduct(), FIELD_buyerAssignedID, id);
		}
	}
	@Override
	public String getSubstitutedBuyerAssignedID() {
		if(super.getSubstitutedReferencedProduct()==null) return null;
		return new ID(getSubstitutedReferencedProduct().getBuyerAssignedID()).getContent();
	}
	
//	104 SCT_LINE	EXTENDED  Substituted Product Industry Assigned ID
	
//	105 SCT_LINE	BASIC	  Substituted Product Name
	@Override
	public void setSubstitutedProductName(String text) {
		if(isOOR(text)) {
			SCopyCtor.getInstance().newFieldInstance(this, FIELD_substitutedReferencedProduct, text);
			SCopyCtor.getInstance().set(getSubstitutedReferencedProduct(), FIELD_name, text);
		}
	}
	@Override
	public String getSubstitutedProductName() {
		if(super.getSubstitutedReferencedProduct()==null) return null;
		return Text.create(super.getSubstitutedReferencedProduct().getName()).getValue();
	}
//	106 SCT_LINE	COMFORT	  Substituted Product Description
	@Override
	public void setSubstitutedProductDescription(String text) {
		if(isOOR(text)) {
			SCopyCtor.getInstance().newFieldInstance(this, FIELD_substitutedReferencedProduct, text);
			SCopyCtor.getInstance().set(getSubstitutedReferencedProduct(), FIELD_description, text);
		}
	}
	@Override
	public String getSubstitutedProductDescription() {
		if(super.getSubstitutedReferencedProduct()==null) return null;
		return Text.create(super.getSubstitutedReferencedProduct().getDescription()).getValue();
	}
	
//	107 SCT_LINE_TA BASIC	  LINE TRADE AGREEMENT
//	108 SCT_LINE_TA EXTENDED  Minimum Orderable Quantity
//	109 SCT_LINE_TA EXTENDED  Unit of Measure of Minimum Orderable Quantity
//	110 SCT_LINE_TA EXTENDED  Maximum Orderable Quantity
//	111 SCT_LINE_TA EXTENDED  Unit of Measure of Maximum Orderable Quantity
//	112 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator)
//	113 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner ID
//	114 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner Global ID
//	115 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner Global ID Shceme ID
//	116 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner Name
//	117 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator) - Contact
//	118 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator) - Person Name
//	119 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator) - Department Name
//	120 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator) Contact - Type
//	121 SCT_LINE_TA EXTENDED  (Line Buyer Requisitioner (originator) - telephone number)
//	122 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator) - telephone number
//	123 SCT_LINE_TA EXTENDED  (Line Buyer Requisitioner (originator) Contact -Fax number)
//	124 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator) Contact -Fax number
//	125 SCT_LINE_TA EXTENDED  (Line Buyer Requisitioner (originator) - email address)
//	126 SCT_LINE_TA EXTENDED  Line Buyer Requisitioner (originator) - email address
	
//	127 SCT_LINE_TA BASIC	  (REFERENCED BUYER ORDER LINE REFERENCE)
//	128 SCT_LINE_TA BASIC	  Referenced Buyer Order line reference
	private static final String WARN_ORDERLINEID = "An Order (Document Type Code BT-3 = 220) MUST NOT contain this business term: ";
	// 127: BT-132 0..1 Referenced purchase order line reference
	@Override
	public void setOrderLineID(String id) {
		if(id==null) return;
		if(this.order.getDocumentCode()==DocumentNameCode.Order) {
			LOG.warning(WARN_ORDERLINEID + "ignore purchase order line reference:'"+id+"'.");
			return;
		}
		SCopyCtor.getInstance().newFieldInstance(getSpecifiedLineTradeAgreement(), "buyerOrderReferencedDocument", id);
		SCopyCtor.getInstance().set(getSpecifiedLineTradeAgreement().getBuyerOrderReferencedDocument(), FIELD_lineID, id);
	}
	@Override
	public String getOrderLineID() {
		ReferencedDocumentType referencedDocument = super.getSpecifiedLineTradeAgreement()==null ? null 
				: getSpecifiedLineTradeAgreement().getBuyerOrderReferencedDocument();
		return referencedDocument==null ? null : new ID(referencedDocument.getLineID()).getName();		
	}

	// 129: 0..1 QUOTATION REFERENCE
	
	// 130: 0..1 Quotation Reference ID
	@Override
	public void setQuotationID(String id) {
		SCopyCtor.getInstance().newFieldInstance(getSpecifiedLineTradeAgreement(), "quotationReferencedDocument", id);
		SCopyCtor.getInstance().set(getSpecifiedLineTradeAgreement().getQuotationReferencedDocument(), "issuerAssignedID", id);
	}
	@Override
	public String getQuotationID() {
		ReferencedDocumentType referencedDocument = super.getSpecifiedLineTradeAgreement()==null ? null 
				: getSpecifiedLineTradeAgreement().getQuotationReferencedDocument();
		return referencedDocument==null ? null : new ID(referencedDocument.getIssuerAssignedID()).getName();		
	}
	@Override
	public void setQuotationLineID(String id) {
		SCopyCtor.getInstance().newFieldInstance(getSpecifiedLineTradeAgreement(), "quotationReferencedDocument", id);
		SCopyCtor.getInstance().set(getSpecifiedLineTradeAgreement().getQuotationReferencedDocument(), FIELD_lineID, id);
	}
	// 131: 0..1 Quotation Reference LineID
	@Override
	public String getQuotationLineID() {
		ReferencedDocumentType referencedDocument = super.getSpecifiedLineTradeAgreement()==null ? null 
				: getSpecifiedLineTradeAgreement().getQuotationReferencedDocument();
		return referencedDocument==null ? null : new ID(referencedDocument.getLineID()).getName();		
	}
//	132 SCT_LINE_TA EXTENDED  (Quotation Reference Date)             TODO
//	133 SCT_LINE_TA EXTENDED  Quotation Reference Date
//	134 SCT_LINE_TA EXTENDED  Date format

	
//	135 SCT_LINE_TA EXTENDED  CONTRACT REFERENCE
//	136 SCT_LINE_TA EXTENDED  Contract Reference ID
//	137 SCT_LINE_TA EXTENDED  Contract Reference LineID
//	138 SCT_LINE_TA EXTENDED  (Contract Reference Date)
//	139 SCT_LINE_TA EXTENDED  Contract Reference Date
//	140 SCT_LINE_TA EXTENDED  Date format
	
	// 141ff : 0..n ADDITIONAL REFERENCED DOCUMENT - An additional document referenced in SpecifiedLineTradeAgreement
//	142 SCT_LINE_TA COMFORT	  Additional Referenced Document - ID
//	143 SCT_LINE_TA COMFORT	  Additional Referenced Document - External document location
//	144 SCT_LINE_TA COMFORT	  Additional Referenced Document + Line ID
//	145 SCT_LINE_TA COMFORT	  Additional Referenced Document - Type Code
//	146 SCT_LINE_TA COMFORT	  Additional Referenced Document - Description
//	147 SCT_LINE_TA COMFORT	  Additional Referenced Document - Attached document
//	148 SCT_LINE_TA COMFORT	  Additional Referenced Document - Attached document Mime code
//	149 SCT_LINE_TA COMFORT	  Additional Referenced Document- Attached document Filename
//	150 SCT_LINE_TA COMFORT	  Additional Referenced Document - Reference Type Code
//	151 SCT_LINE_TA EXTENDED  (Additional Referenced Document +  Date)
//	152 SCT_LINE_TA EXTENDED  Additional Referenced Document -  Date
//	153 SCT_LINE_TA EXTENDED  Date format
	// Unterschied zu 79 ADDITIONAL REFERENCED PRODUCT DOCUMENT: 79 ist in SpecifiedTradeProduct
	// factory gemeinsam mit 79, mit LineID siehe #createSupportigDocument() 
	@Override // implements interface _141_AdditionalReferencedDocs
	public void addReferencedDocument(SupportingDocument supportigDocument) {
		super.getSpecifiedLineTradeAgreement().getAdditionalReferencedDocument().add((ReferencedDocument)supportigDocument);
	}
	public List<SupportingDocument> getReferencedDocuments() {
		List<SupportingDocument> res = new ArrayList<SupportingDocument>();
		if(super.getSpecifiedLineTradeAgreement()==null) return res;
		List<ReferencedDocumentType> list = getSpecifiedLineTradeAgreement().getAdditionalReferencedDocument();
		list.forEach(rd -> {
			ReferencedDocument referencedDocument = ReferencedDocument.create(rd);
			if(referencedDocument.isRelatedDocument()) res.add(referencedDocument);
		});
		return res;
	}
	
	// 154: BG.25.BT-128 0..1 Objektkennung // (OBJECT IDENTIFIER FOR INVOICE LINE)
//	155 SCT_LINE_TA COMFORT	  Order line object identifier
//	156 SCT_LINE_TA COMFORT	  Order line object identifier - Typecode
//	157 SCT_LINE_TA COMFORT	  Order line object identifier - TypecodeAd BR (
/*
                    <ram:AdditionalReferencedDocument>
                         <ram:IssuerAssignedID>ADD_REF_DOC_ID</ram:IssuerAssignedID>
                         <ram:URIID>ADD_REF_DOC_URIID</ram:URIID>
                         <ram:LineID>5</ram:LineID>
                         <ram:TypeCode>916</ram:TypeCode>
                         <ram:Name>ADD_REF_DOC_Desc</ram:Name>
                    </ram:AdditionalReferencedDocument>
                    <ram:AdditionalReferencedDocument>
                         <ram:IssuerAssignedID>OBJECT_125487</ram:IssuerAssignedID> <!-- id
                         <ram:TypeCode>130</ram:TypeCode>                           <!-- schemeID, TypeCode = "130" Rechnungsdatenblatt, UNTDID 1001 Untermenge
                         <ram:ReferenceTypeCode>AWV</ram:ReferenceTypeCode>         <!-- schemeCode, aus UNTDID 1153
                    </ram:AdditionalReferencedDocument>
 */
	@Override
	public void setLineObjectID(String id, String schemeID, String schemeCode) {
		if(id==null) return;
		ReferencedDocument rd = ReferencedDocument.create(id, schemeID, schemeCode);
		LineTradeAgreementType lta = super.getSpecifiedLineTradeAgreement(); 
		lta.getAdditionalReferencedDocument().add(rd);
	}
	@Override
	public Identifier getLineObjectIdentifier() {
		List<ReferencedDocumentType> rds = super.getSpecifiedLineTradeAgreement()==null ? null : getSpecifiedLineTradeAgreement().getAdditionalReferencedDocument();
		if(rds==null || rds.isEmpty()) return null;
		// A Line MUST NOT HAVE more than 1 Object Identifier BT-128
		ReferencedDocument rd = ReferencedDocument.create(rds.get(0));
		return new ID(rd.getIssuerAssignedID().getValue(), rd.getReferenceCode());
	}
	
//	158 SCT_LINE_TA COMFORT	  (Gross Price)
//	159 SCT_LINE_TA COMFORT	  Gross Price
//	160 SCT_LINE_TA COMFORT	  Gross Price Base quantity
//	161 SCT_LINE_TA COMFORT	  Gross Price Unit Code for base quantity
	// 159: BG-29.BT-148 0..1 Item gross price
	@Override
	public IAmount getGrossPrice() {
		TradePriceType grossPrice = super.getSpecifiedLineTradeAgreement()==null ? null 
				: getSpecifiedLineTradeAgreement().getGrossPriceProductTradePrice();
		return grossPrice==null ? null : Amount.create(grossPrice.getChargeAmount());
	}
	@Override
	public void setGrossPrice(IAmount grossPrice) {
		SCopyCtor.getInstance().newFieldInstance(getSpecifiedLineTradeAgreement(), "grossPriceProductTradePrice", grossPrice);
		SCopyCtor.getInstance().set(getSpecifiedLineTradeAgreement().getGrossPriceProductTradePrice(), "chargeAmount", grossPrice);
	}

	// 162: BG-29.BT-147 0..1 Item price discount
//	163 SCT_LINE_TA COMFORT	  ((Item price discount))
//	164 SCT_LINE_TA COMFORT	  (Item price discount)
//	165 SCT_LINE_TA EXTENDED  Item price discount CalculationPercent
//	166 SCT_LINE_TA EXTENDED  Item price discount BasisAmount
//	167 SCT_LINE_TA COMFORT	  Item price discount
//	168 SCT_LINE_TA COMFORT	  Item price discount Reason Code
//	169 SCT_LINE_TA COMFORT	  Item price discount Reason
	@Override
	public AllowancesAndCharges getPriceDiscount() {
		TradePriceType grossPrice = super.getSpecifiedLineTradeAgreement()==null ? null 
				: getSpecifiedLineTradeAgreement().getGrossPriceProductTradePrice();
		if(grossPrice==null) return null;
		if(grossPrice.getAppliedTradeAllowanceCharge().isEmpty()) return null;
		List<TradeAllowanceCharge> resCandidate = new ArrayList<TradeAllowanceCharge>();
		grossPrice.getAppliedTradeAllowanceCharge().forEach(e ->{
			TradeAllowanceCharge tac = TradeAllowanceCharge.create(e);
			// discount is ALLOWANCE!
			if(tac.isAllowance()) resCandidate.add(tac);
		});
		return resCandidate.isEmpty() ? null : resCandidate.get(0);
	}
	@Override
	public void setPriceDiscount(AllowancesAndCharges discount) {
		// discount is ALLOWANCE! ungeprüft: discount.isAllowance()
		SCopyCtor.getInstance().newFieldInstance(getSpecifiedLineTradeAgreement(), FIELD_grossPriceProductTradePrice, discount);
		getSpecifiedLineTradeAgreement().getGrossPriceProductTradePrice()
			.getAppliedTradeAllowanceCharge().add((TradeAllowanceCharge)discount);
	}

//	170 SCT_LINE_TA COMFORT	  (((Item price charge)))
	@Override
	public AllowancesAndCharges getPriceCharge() {
		TradePriceType grossPrice = super.getSpecifiedLineTradeAgreement()==null ? null 
				: getSpecifiedLineTradeAgreement().getGrossPriceProductTradePrice();
		if(grossPrice==null) return null;
		if(grossPrice.getAppliedTradeAllowanceCharge().isEmpty()) return null;
		List<TradeAllowanceCharge> resCandidate = new ArrayList<TradeAllowanceCharge>();
		grossPrice.getAppliedTradeAllowanceCharge().forEach(e ->{
			TradeAllowanceCharge tac = TradeAllowanceCharge.create(e);
			// only CHARGE is a candidate:
			if(tac.isCharge()) resCandidate.add(tac);
		});
		return resCandidate.isEmpty() ? null : resCandidate.get(0);
	}
//	171 SCT_LINE_TA COMFORT	  ((Item price charge))
//	172 SCT_LINE_TA COMFORT	  (Item price charge)
//	173 SCT_LINE_TA EXTENDED  Item price charge CalculationPercent
//	174 SCT_LINE_TA EXTENDED  Item price discount BasisAmount
//	175 SCT_LINE_TA COMFORT	  Item price charge amount
//	176 SCT_LINE_TA COMFORT	  Item price charge Reason Code
//	177 SCT_LINE_TA COMFORT	  Item price charge Reason
	/* Item price charge Reason Code:
	 * Use entries of the UNTDID 7161 code list. 
	 * The order line level item trade price discount reason code and 
	 * the order line level item trade price discount reason shall indicate the same item trade price charge reason. 
	 * Example AEW for WEEE.
	 */
	@Override
	public void setPriceCharge(AllowancesAndCharges charge) {
		SCopyCtor.getInstance().newFieldInstance(getSpecifiedLineTradeAgreement(), FIELD_grossPriceProductTradePrice, charge);
		getSpecifiedLineTradeAgreement().getGrossPriceProductTradePrice()
			.getAppliedTradeAllowanceCharge().add((TradeAllowanceCharge)charge);
	}

	
	/* ------------------------------------------------------------------------------
	 * BG-29 1..1 PRICE DETAILS
	 * 
	 * BT-146   1..1      Item net price   ==> NetPriceProductTradePrice
	 * BT-149-0 BT-150-0 UnitPriceQuantity ==> NetPriceProductTradePrice
	 */
	// 179: price after subracting === korrekt
	// 179: BG-29.BT-146 1..1 Item net price aka UnitPriceAmount
	@Override
	public IAmount getUnitPriceAmount() {
		TradePriceType tradePrice = super.getSpecifiedLineTradeAgreement()==null ? null : getSpecifiedLineTradeAgreement().getNetPriceProductTradePrice();
		return tradePrice==null ? null : Amount.create(tradePrice.getChargeAmount());
	}
	// tradePrice wird im ctor initialisiert, in CII ist chargeAmount List, in CIO field
	private void setUnitPriceAmount(UnitPriceAmount unitPriceAmount) {
		LOG.fine("unitPriceAmount:"+unitPriceAmount);
//		Object tradePrice = 
		SCopyCtor.getInstance().newFieldInstance(getSpecifiedLineTradeAgreement(), FIELD_netPriceProductTradePrice, unitPriceAmount);
		SCopyCtor.getInstance().set(getSpecifiedLineTradeAgreement().getNetPriceProductTradePrice(), "chargeAmount", unitPriceAmount);
	}

	// in CII gibt es: setUnitPriceAmountAndQuantity
	
	// 180+181: BG-29.BT-150 + BG-29.BT-149 0..1
	@Override
	public IQuantity getUnitPriceQuantity() {
		TradePriceType tradePrice = super.getSpecifiedLineTradeAgreement()==null ? null : getSpecifiedLineTradeAgreement().getNetPriceProductTradePrice();
		return tradePrice==null ? null : Quantity.create(tradePrice.getBasisQuantity());
	}
	@Override
	public void setUnitPriceQuantity(IQuantity basisQuantity) {
		SCopyCtor.getInstance().newFieldInstance(getSpecifiedLineTradeAgreement(), FIELD_netPriceProductTradePrice, basisQuantity);
		SCopyCtor.getInstance().set(getSpecifiedLineTradeAgreement().getNetPriceProductTradePrice(), "basisQuantity", basisQuantity);
	}

	// 182: Minimum Quantity for this Unit Price
	// 183: UnitCode for  Minimum Quantity
	// 184: Maximum Quantity for this Unit Price
	// 185: UnitCode for  Maximum Quantity
	
	/* 186: INCLUDED TRADE TAX
	// TODO INCLUDED TRADE TAX ist nicht BG-30
// ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:IncludedTradeTax
	 A tax included in this trade price 
	 (for instance in case of other Tax, or B2C Order with VAT)
*/
	// 187: Included Tade Tax Calculated Amount
	// 188: Included Tade Tax type code on line level
	// 189: Included Tade Tax Exemption Reason
	// 190: Included Tade Tax Type (category) Code
	// 191: Included Tade Tax Exemption Reason Code
	// 192: Included Tade Tax Type rate

	// 311: BG-30 LINE VAT INFORMATION
	// TODO 312: VAT Calculated Amount
	// TODO 313: VAT type code on line level
	// TODO 314: VAT Exemption Reason

//	193 SCT_LINE_TA COMFORT	  (CATALOGUE REFERENCED DOC)
//	194 SCT_LINE_TA COMFORT	  Catalogue Referenced Doc ID
//	195 SCT_LINE_TA COMFORT	  Catalogue Referenced Doc LineID
//	196 SCT_LINE_TA EXTENDED  (Catalog Reference Date)
//	197 SCT_LINE_TA EXTENDED  Catalog Reference Date
//	198 SCT_LINE_TA EXTENDED  Date format

//	199 SCT_LINE_TA BASIC	  (BLANKET ORDER REFERENCED LINE ID)
//	200 SCT_LINE_TA BASIC	  Blanket Order Referenced Line ID
	public void setBlanketOrderReference(String id) {
		SCopyCtor.getInstance().newFieldInstance(getSpecifiedLineTradeAgreement(), "blanketOrderReferencedDocument", id);
		SCopyCtor.getInstance().set(getSpecifiedLineTradeAgreement().getBlanketOrderReferencedDocument(), FIELD_lineID, id);
	}
	public String getBlanketOrderReference() {
		ReferencedDocumentType refDoc = super.getSpecifiedLineTradeAgreement()==null ? null : getSpecifiedLineTradeAgreement().getBlanketOrderReferencedDocument();
		return refDoc==null ? null : new ID(refDoc.getLineID()).getContent();
	}

//	201 SCT_LINE_TA EXTENDED  ULTIMATE CUSTOMER ORDER REFERENCED DOCUMENT
//	202 SCT_LINE_TA EXTENDED  Ultimate Customer Order Referenced Doc ID
//	203 SCT_LINE_TA EXTENDED  Ultimate Customer Order Referenced Doc LineID
//	204 SCT_LINE_TA EXTENDED  (Ultimate Customer Order Date)
//	205 SCT_LINE_TA EXTENDED  Ultimate Customer Order Date
//	206 SCT_LINE_TA EXTENDED  Date format
	
/*	207 SCT_LINE_TD BASIC	  LINE TRADE DELIVERY
    <ram:SpecifiedLineTradeDelivery>
         <ram:PartialDeliveryAllowedIndicator>
              <udt:Indicator>true</udt:Indicator>		<!-- 209: isPartialDeliveryAllowed
         </ram:PartialDeliveryAllowedIndicator>
         <ram:RequestedQuantity unitCode="C62">6</ram:RequestedQuantity> <!-- 210+211
         <ram:PackageQuantity unitCode="C62">3</ram:PackageQuantity>     <!-- 214+215
         <ram:PerPackageUnitQuantity unitCode="C62">2</ram:PerPackageUnitQuantity> <!-- 216+217
         <ram:RequestedDeliverySupplyChainEvent>		<!-- 297: BG-26 0..1
              <ram:OccurrenceSpecifiedPeriod>			<!-- 303:
                   <ram:StartDateTime>            		<!-- 304: BG-26.BT-134
                        <udt:DateTimeString format="102">20200415</udt:DateTimeString>
                   </ram:StartDateTime>
                   <ram:EndDateTime>              		<!-- 307: BG-26.BT-135
                        <udt:DateTimeString format="102">20200430</udt:DateTimeString>
                   </ram:EndDateTime>
              </ram:OccurrenceSpecifiedPeriod>
         </ram:RequestedDeliverySupplyChainEvent>
    </ram:SpecifiedLineTradeDelivery>
*/
	// 208: SCT_LINE_TD BASIC	  Partial Delivery Allowed Indicator
	// 209: SCT_LINE_TD BASIC	  Partial Delivery Allowed Indicator Value
	@Override
	public void setPartialDeliveryIndicator(boolean indicator) {
		SCopyCtor.getInstance().set(getSpecifiedLineTradeDelivery(), "partialDeliveryAllowedIndicator", indicator);		
	}
	@Override
	public boolean isPartialDeliveryAllowed() {
		IndicatorType indicator = super.getSpecifiedLineTradeDelivery().getPartialDeliveryAllowedIndicator();
		return indicator!=null && indicator.isIndicator().equals(YES);
	}
	
	// 210: BT-129 1..1 bestellte Menge / The quantity, at line level, requested for this trade delivery.
	// 211: Unit of measure Code for Requested quantity BT-129+BT-130
	void setQuantity(IQuantity quantity) { 
		SCopyCtor.getInstance().set(getSpecifiedLineTradeDelivery(), "requestedQuantity", (Quantity)quantity);
	}
	@Override
	public IQuantity getQuantity() {
		LineTradeDeliveryType ltd = super.getSpecifiedLineTradeDelivery();
		return ltd.getRequestedQuantity()==null ?  null : Quantity.create(ltd.getRequestedQuantity());
	}

	// 212: SCT_LINE_TD BASIC	  Agreed Quantity
	// 213: SCT_LINE_TD BASIC	  Unit of measure Code for Agreed quantity
	@Override
	public void setAgreedQuantity(IQuantity quantity) { 
		if(quantity==null) return;
		if(this.order.getDocumentCode()!=DocumentNameCode.OrderResponse) {
			LOG.warning(WARN_ORDERLINEID + "ignore Agreed Quantity:'"+quantity+"'.");
			return;
		}
		SCopyCtor.getInstance().set(getSpecifiedLineTradeDelivery(), "agreedQuantity", (Quantity)quantity);
	}
	@Override
	public IQuantity getAgreedQuantity() {
		LineTradeDeliveryType ltd = super.getSpecifiedLineTradeDelivery();
		return ltd.getAgreedQuantity()==null ?  null : Quantity.create(ltd.getAgreedQuantity());
	}

	// 214: SCT_LINE_TD COMFORT	  Package Quantity
	// 215: SCT_LINE_TD COMFORT	  Unit of measure Code for Package quantity
	@Override
	public void setPackageQuantity(IQuantity quantity) { 
		SCopyCtor.getInstance().set(getSpecifiedLineTradeDelivery(), "packageQuantity", (Quantity)quantity);
	}
	@Override
	public IQuantity getPackageQuantity() {
		LineTradeDeliveryType ltd = super.getSpecifiedLineTradeDelivery();
		return ltd.getPackageQuantity()==null ?  null : Quantity.create(ltd.getPackageQuantity());
	}
	
	// 216: SCT_LINE_TD COMFORT	  Per Package Quantity
	// 217: SCT_LINE_TD COMFORT	  Unit of measure Code for Per Package quantity
	@Override
	public void setPerPackageQuantity(IQuantity quantity) { 
		SCopyCtor.getInstance().set(getSpecifiedLineTradeDelivery(), "perPackageUnitQuantity", (Quantity)quantity);
	}
	@Override
	public IQuantity getPerPackageQuantity() {
		LineTradeDeliveryType ltd = super.getSpecifiedLineTradeDelivery();
		return ltd.getPerPackageUnitQuantity()==null ?  null : Quantity.create(ltd.getPerPackageUnitQuantity());
	}

	/* ram:SpecifiedLineTradeDelivery/ram:ShipToTradeParty : ltd.getShipToTradeParty()

A group of business terms providing information about where and when the goods and services ordered are delivered.

	 */
//	218 SCT_LINE_TD EXTENDED  SHIP TO PARTY
//	219 SCT_LINE_TD EXTENDED  Ship To ID
//	220 SCT_LINE_TD EXTENDED  Ship To Global ID
//	221 SCT_LINE_TD EXTENDED  SchemeID
//	222 SCT_LINE_TD EXTENDED  Ship To Name
//	223 SCT_LINE_TD EXTENDED  (Ship to Legal ID)
//	224 SCT_LINE_TD EXTENDED  Ship to Legal ID
//	225 SCT_LINE_TD EXTENDED  Ship to Legal ID scheme ID
//	226 SCT_LINE_TD EXTENDED  Ship to Trading Name
//	227 SCT_LINE_TD EXTENDED  SHIP TO CONTACT
//	228 SCT_LINE_TD EXTENDED  SHIP TO Contact - Person Name
//	229 SCT_LINE_TD EXTENDED  SHIP TO Contact - Department Name
//	230 SCT_LINE_TD EXTENDED  SHIP TO Contact - Type
//	231 SCT_LINE_TD EXTENDED  (SHIP TO Contact - telephone number)
//	232 SCT_LINE_TD EXTENDED  SHIP TO Contact - telephone number
//	233 SCT_LINE_TD EXTENDED  (SHIP TO Contact -Fax number)
//	234 SCT_LINE_TD EXTENDED  SHIP TO Contact -Fax number
//	235 SCT_LINE_TD EXTENDED  (SHIP TO Contact - email address)
//	236 SCT_LINE_TD EXTENDED  SHIP TO Contact - email address
//	237 SCT_LINE_TD EXTENDED  SHIP TO POSTAL ADDRESS
//	238 SCT_LINE_TD EXTENDED  Ship To Address / Postal Code
//	239 SCT_LINE_TD EXTENDED  Ship To Address / Line One
//	240 SCT_LINE_TD EXTENDED  Ship To Address / Line Two
//	241 SCT_LINE_TD EXTENDED  Ship To Address / Line Three
//	242 SCT_LINE_TD EXTENDED  Ship To Address / City Name
//	243 SCT_LINE_TD EXTENDED  Ship To Country Code
//	244 SCT_LINE_TD EXTENDED  Ship To Country Subdivision
//	245 SCT_LINE_TD EXTENDED  SHIP TO ELECTRONIC ADDRESS
//	246 SCT_LINE_TD EXTENDED  SHIP TO Electronic Address ID
//	247 SCT_LINE_TD EXTENDED  SHIP TO Electronic Address Scheme ID
//	248 SCT_LINE_TD EXTENDED  SHIP TO TAX REGISTRATION
//	249 SCT_LINE_TD EXTENDED  SHIP TO VAT Identifier
//	250 SCT_LINE_TD EXTENDED
	/*
A group of business terms providing information about where and when the goods and services ordered are delivered.
	 */
//	251 SCT_LINE_TD EXTENDED  ULTIMATE SHIP TO PARTY
//	252 SCT_LINE_TD EXTENDED  Ultimate Ship To ID
//	253 SCT_LINE_TD EXTENDED  Ultimate Ship To Global ID
//	254 SCT_LINE_TD EXTENDED  SchemeID
//	255 SCT_LINE_TD EXTENDED  Ultimate Ship To Name
//	256 SCT_LINE_TD EXTENDED  (ULTIMATE SHIP TO LEGAL)
//	257 SCT_LINE_TD EXTENDED  Ultimate Ship to Legal ID
//	258 SCT_LINE_TD EXTENDED  Ultimate Ship to Legal ID scheme ID
//	259 SCT_LINE_TD EXTENDED  Ultimate Ship to Trading Name
//	260 SCT_LINE_TD EXTENDED  ULTIMATE SHIP TO CONTACT
//	261 SCT_LINE_TD EXTENDED  Ultimate Ship TO Contact - Person Name
//	262 SCT_LINE_TD EXTENDED  Ultimate Ship TO Contact - Department Name
//	263 SCT_LINE_TD EXTENDED  Ultimate Ship TO Contact - Type
//	264 SCT_LINE_TD EXTENDED  (Ultimate Ship TO Contact - telephone number)
//	265 SCT_LINE_TD EXTENDED  Ultimate Ship TO Contact - telephone number
//	266 SCT_LINE_TD EXTENDED  (Ultimate Ship TO Contact -Fax number)
//	267 SCT_LINE_TD EXTENDED  Ultimate Ship TO Contact -Fax number
//	268 SCT_LINE_TD EXTENDED  (Ultimate Ship TO Contact - email address)
//	269 SCT_LINE_TD EXTENDED  Ultimate Ship TO Contact - email address
//	270 SCT_LINE_TD EXTENDED  ULTIMATE SHIP TO POSTAL ADDRESS
//	271 SCT_LINE_TD EXTENDED  Ultimate Ship To Address / Postal Code
//	272 SCT_LINE_TD EXTENDED  Ultimate Ship To Address / Line One
//	273 SCT_LINE_TD EXTENDED  Ultimate Ship To Address / Line Two
//	274 SCT_LINE_TD EXTENDED  Ultimate Ship To Address / Line Three
//	275 SCT_LINE_TD EXTENDED  Ultimate Ship To Address / City Name
//	276 SCT_LINE_TD EXTENDED  Ultimate Ship To Country Code
//	277 SCT_LINE_TD EXTENDED  Ultimate Ship To Country Subdivision
//	278 SCT_LINE_TD EXTENDED  ULTIMATE SHIP TO ELECTRONIC ADDRESS
//	279 SCT_LINE_TD EXTENDED  Ultimate Ship TO Electronic Address Scheme ID
//	280 SCT_LINE_TD EXTENDED  Ultimate Ship TO Electronic Address Scheme ID
//	281 SCT_LINE_TD EXTENDED  ULTIMATE SHIP TO TAX REGISTRATION
//	282 SCT_LINE_TD EXTENDED  Ultimate Ship TO VAT Identifier
//	283 SCT_LINE_TD EXTENDED
	
	// 284: LINE REQUESTED PICK UP DATE or PERIOD
	// 285: LINE REQUESTED PICK UP DATE
	@Override
	public void setPickupDate(Timestamp ts) {
		if(ts==null) return;
		DateTimeType dateTime = DateTimeFormatStrings.toDateTime(ts);
		if (getSpecifiedLineTradeDelivery().getRequestedDespatchSupplyChainEvent().isEmpty()) {
			getSpecifiedLineTradeDelivery().getRequestedDespatchSupplyChainEvent().add(new SupplyChainEventType());
		}
		getSpecifiedLineTradeDelivery().getRequestedDespatchSupplyChainEvent().get(0).setOccurrenceDateTime(dateTime);
	}
	@Override
	public Timestamp getPickupDateAsTimestamp() {
		List<SupplyChainEventType> list = super.getSpecifiedLineTradeDelivery().getRequestedDespatchSupplyChainEvent();
		if (list.isEmpty()) return null;
		DateTimeType dateTime = list.get(0).getOccurrenceDateTime();
		return dateTime == null ? null : DateTimeFormats.ymdToTs(dateTime.getDateTimeString().getValue());
	}

	// 290: LINE REQUESTED PICK UP PERIOD
	@Override
	public void setPickupPeriod(IPeriod period) {
		if(period==null) return;
		if(getSpecifiedLineTradeDelivery().getRequestedDespatchSupplyChainEvent().isEmpty()) {
			getSpecifiedLineTradeDelivery().getRequestedDespatchSupplyChainEvent().add(new SupplyChainEventType());
		}
		getSpecifiedLineTradeDelivery().getRequestedDespatchSupplyChainEvent().get(0).setOccurrenceSpecifiedPeriod((Period)period);
	}
	@Override
	public IPeriod getPickupPeriod() {
		List<SupplyChainEventType> list = super.getSpecifiedLineTradeDelivery().getRequestedDespatchSupplyChainEvent();
		if (list.isEmpty()) return null;
		SpecifiedPeriodType period = list.get(0).getOccurrenceSpecifiedPeriod();
		return period == null ? null : Period.create(period);
	}

// TODO	288 SCT_LINE_TD EXTENDED  Unit Quantity to be pick up in this event
// TODO	289 SCT_LINE_TD EXTENDED  Unit of measure Code for Unit Quantity to be pick up in this event
	
	// 291..393 + 294..296
	// 304..306 + 305..307
	@Override
	public IPeriod createPeriod(Timestamp start, Timestamp end) {
		return Period.create(start, end);
	}

	// 297: LINE REQUESTED DELIVERY DATE or PERIOD
/* TODO
288: Unit Quantity to be pick up in this event
//rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem
 //ram:SpecifiedLineTradeDelivery/ram:RequestedDespatchSupplyChainEvent/ram:UnitQuantity
	A number of units for this supply chain event.	
	To be used in case of multiple delivery date or period
289: Unit of measure Code for Unit Quantity to be pick up in this event

301: Unit Quantity to be delivered in this event
//rsm:SupplyChainTradeTransaction/ram:IncludedSupplyChainTradeLineItem
 //ram:SpecifiedLineTradeDelivery/ram:RequestedDeliverySupplyChainEvent/ram:UnitQuantity
//	@Override
	public void setLineDeliveryUnitQuantity(IQuantity quantity) {
		SCopyCtor.getInstance().newFieldInstance(getSpecifiedLineTradeDelivery(), "requestedDeliverySupplyChainEvent", quantity);
		SCopyCtor.getInstance().set(getSpecifiedLineTradeDelivery().getRequestedDeliverySupplyChainEvent(), "unitQuantity", quantity);
		// protected QuantityType unitQuantity;
	}

302: Unit of measure Code for Unit Quantity to be delivered
 */
	// 298: BG-26 0..1 Date on which Delivery is requested
	// wie HeaderTradeDelivery#setLineDeliveryDate
	public void setDeliveryDate(Timestamp ts) {
		DateTimeType dateTime = DateTimeFormatStrings.toDateTime(ts);
		if (getSpecifiedLineTradeDelivery().getRequestedDeliverySupplyChainEvent().isEmpty()) {
			getSpecifiedLineTradeDelivery().getRequestedDeliverySupplyChainEvent().add(new SupplyChainEventType());
		}
		getSpecifiedLineTradeDelivery().getRequestedDeliverySupplyChainEvent().get(0).setOccurrenceDateTime(dateTime);
	}
	public Timestamp getDeliveryDateAsTimestamp() {
		List<SupplyChainEventType> list = super.getSpecifiedLineTradeDelivery().getRequestedDeliverySupplyChainEvent();
		if (list.isEmpty()) return null;
		DateTimeType dateTime = list.get(0).getOccurrenceDateTime();
		return dateTime == null ? null : DateTimeFormats.ymdToTs(dateTime.getDateTimeString().getValue());
	}

	// 303: BG-26 0..1 Period on which Delivery is requested
	@Override
	public void setDeliveryPeriod(IPeriod period) {
		if(getSpecifiedLineTradeDelivery().getRequestedDeliverySupplyChainEvent().isEmpty()) {
			getSpecifiedLineTradeDelivery().getRequestedDeliverySupplyChainEvent().add(new SupplyChainEventType());
		}
		getSpecifiedLineTradeDelivery().getRequestedDeliverySupplyChainEvent().get(0).setOccurrenceSpecifiedPeriod((Period)period);
	}
	@Override
	public IPeriod getDeliveryPeriod() {
		List<SupplyChainEventType> list = super.getSpecifiedLineTradeDelivery().getRequestedDeliverySupplyChainEvent();
		if (list.isEmpty()) return null;
		SpecifiedPeriodType period = list.get(0).getOccurrenceSpecifiedPeriod();
		return period == null ? null : Period.create(period);
	}
	
//	310 SCT_LINE_TS BASIC	  LINE TRADE SETTLEMENT
//	311 SCT_LINE_TS COMFORT	  LINE VAT
//	312 SCT_LINE_TS EXTENDED  VAT Calculated Amount
//	313 SCT_LINE_TS COMFORT	  VAT type code on line level
//	314 SCT_LINE_TS EXTENDED  VAT Exemption Reason
//	315 SCT_LINE_TS COMFORT	  Tax Type (category) Code
//	316 SCT_LINE_TS EXTENDED  VAT Exemption Reason Code
//	317 SCT_LINE_TS COMFORT	  Tax Type (category) rate

	// 315: BG-30.BT-151 1..1 item VAT category code
	@Override
	public void setTaxCategory(TaxCategoryCode code) {
		if(tradeTax==null) {
			tradeTax = TradeTax.create(TaxTypeCode.ValueAddedTax, code, null);
			specifiedLineTradeSettlement.setApplicableTradeTax(tradeTax);
		} else {
			tradeTax.setTaxCategoryCode(code.getValue());
			specifiedLineTradeSettlement.setApplicableTradeTax(tradeTax);
		}
	}
	@Override
	public TaxCategoryCode getTaxCategory() {
		return tradeTax.getTaxCategoryCode();
	}

	// TODO 316: VAT Exemption Reason Code

	// 317: BG-30.BT-152 0..1 item VAT rate
	@Override
	public void setTaxRate(BigDecimal percent) {
		tradeTax.setTaxPercentage(percent);
		specifiedLineTradeSettlement.setApplicableTradeTax(tradeTax);
	}
	@Override
	public BigDecimal getTaxRate() {
		return tradeTax.getTaxPercentage();
	}

	/*
	 * 318: BG-27 0..n LINE ALLOWANCES
	319 SCT_LINE_TS COMFORT	  Charges and Allowances line Indicator
	320 SCT_LINE_TS COMFORT	  Line Allowances indicator value
	321 SCT_LINE_TS COMFORT	  line allowance percentage
	322 SCT_LINE_TS COMFORT	  line allowance base amount
	323 SCT_LINE_TS COMFORT	  line allowance amount
	324 SCT_LINE_TS COMFORT	  line allowance reason code
	325 SCT_LINE_TS COMFORT	  line allowance reason
	
	 * 326: BG-28 0..n LINE CHARGES
	327 SCT_LINE_TS COMFORT	  Charges and Allowances line Indicator
	328 SCT_LINE_TS COMFORT	  Line Charges indicator value
	329 SCT_LINE_TS COMFORT	  line charge percentage
	330 SCT_LINE_TS COMFORT	  line charge base amount
	331 SCT_LINE_TS COMFORT	  line charge amount
	332 SCT_LINE_TS COMFORT	  line charge reason code
	333 SCT_LINE_TS COMFORT	  line charge reason

                    <ram:SpecifiedTradeAllowanceCharge>
                         <ram:ChargeIndicator>
                              <udt:Indicator>false</udt:Indicator>
                         </ram:ChargeIndicator>
                         <ram:CalculationPercent>10.00</ram:CalculationPercent>
                         <ram:BasisAmount>60.00</ram:BasisAmount>
                         <ram:ActualAmount>6.00</ram:ActualAmount>
                         <ram:ReasonCode>64</ram:ReasonCode>
                         <ram:Reason>SPECIAL AGREEMENT</ram:Reason>
                    </ram:SpecifiedTradeAllowanceCharge>
                    <ram:SpecifiedTradeAllowanceCharge>
                         <ram:ChargeIndicator>
                              <udt:Indicator>true</udt:Indicator>
                         </ram:ChargeIndicator>
                         <ram:CalculationPercent>10.00</ram:CalculationPercent>
                         <ram:BasisAmount>60.00</ram:BasisAmount>
                         <ram:ActualAmount>6.00</ram:ActualAmount>
                         <ram:ReasonCode>64</ram:ReasonCode>
                         <ram:Reason>FREIGHT SERVICES</ram:Reason>
                    </ram:SpecifiedTradeAllowanceCharge>

	 */
	@Override
	public AllowancesAndCharges createAllowance(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		// delegieren:
		return TradeAllowanceCharge.create(AllowancesAndCharges.ALLOWANCE, amount, baseAmount, percentage);
	}
	@Override
	public AllowancesAndCharges createCharge(IAmount amount, IAmount baseAmount, BigDecimal percentage) {
		// delegieren:
		return TradeAllowanceCharge.create(AllowancesAndCharges.CHARGE, amount, baseAmount, percentage);
	}

	@Override
	public void addAllowanceCharge(AllowancesAndCharges allowanceOrCharge) {
		if(allowanceOrCharge==null) return; // optional
		super.getSpecifiedLineTradeSettlement().getSpecifiedTradeAllowanceCharge().add((TradeAllowanceCharge)allowanceOrCharge);
	}

	@Override
	public List<AllowancesAndCharges> getAllowancesAndCharges() {
		List<TradeAllowanceChargeType> allowanceChargeList = super.getSpecifiedLineTradeSettlement()==null ? null : getSpecifiedLineTradeSettlement().getSpecifiedTradeAllowanceCharge();
		List<AllowancesAndCharges> res = new ArrayList<AllowancesAndCharges>(allowanceChargeList.size());
		allowanceChargeList.forEach(allowanceOrCharge -> {
			res.add(TradeAllowanceCharge.create(allowanceOrCharge));
		});
		return res;
	}

//	334 SCT_LINE_TS BASIC	  LINE TOTAL AMOUNTS
//	335 SCT_LINE_TS BASIC	  Line Total Amount (without VAT)
//	336 SCT_LINE_TS EXTENDED  Line Total Charges (without VAT)
//	337 SCT_LINE_TS EXTENDED  Line Total Allowance Amount (without VAT)
//	338 SCT_LINE_TS EXTENDED  Line Total Tax amount
//	339 SCT_LINE_TS EXTENDED  Line Total Charges and Allowances Amount (without VAT)
//	340 SCT_LINE_TS COMFORT	  (IORDER LINE BUYER ACCOUNTING REFERENCE)
//	341 SCT_LINE_TS COMFORT	  Order line Buyer accounting reference
//	342 SCT_LINE_TS EXTENDED  TypeCode

	// 335: BT-131 1..1 Nettobetrag der Rechnungsposition
	void setLineTotalAmount(IAmount amount) {
		SCopyCtor.getInstance().newFieldInstance(getSpecifiedLineTradeSettlement(), "specifiedTradeSettlementLineMonetarySummation", amount);
		SCopyCtor.getInstance().set(getSpecifiedLineTradeSettlement().getSpecifiedTradeSettlementLineMonetarySummation(), "lineTotalAmount", (Amount)amount);
	}
	@Override
	public IAmount getLineTotalAmount() {
		TradeSettlementLineMonetarySummationType tsms = super.getSpecifiedLineTradeSettlement()==null ? null : getSpecifiedLineTradeSettlement().getSpecifiedTradeSettlementLineMonetarySummation();
		return tsms==null ? null : Amount.create(tsms.getLineTotalAmount());
	}

	// 340: BT-133 0..1 line Buyer accounting reference : ram:ReceivableSpecifiedTradeAccountingAccount
	public void setBuyerAccountingReference(String text) {
		SCopyCtor.getInstance().newFieldInstance(getSpecifiedLineTradeSettlement(), "receivableSpecifiedTradeAccountingAccount", text);
		SCopyCtor.getInstance().set(getSpecifiedLineTradeSettlement().getReceivableSpecifiedTradeAccountingAccount(), FIELD_id, text);
	}
	public String getBuyerAccountingReference() {
		TradeAccountingAccountType taa = super.getSpecifiedLineTradeSettlement()==null ? null : getSpecifiedLineTradeSettlement().getReceivableSpecifiedTradeAccountingAccount();
		return taa==null ? null : new ID(taa.getID()).getName();		
	}

}
