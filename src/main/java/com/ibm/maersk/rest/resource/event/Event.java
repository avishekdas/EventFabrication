package com.ibm.maersk.rest.resource.event;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.beanutils.BeanUtils;

import com.ibm.maersk.rest.dao.EventEntity;
import com.ibm.maersk.rest.helpers.DateISO8601Adapter;

/**
 * Event resource placeholder for json/xml representation 
 * 
 * @author ama
 *
 */
@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Event implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;

	/** id of the event */
	@XmlElement(name = "evntId")
	private Long evntId;

	/** title of the event */
	@XmlElement(name = "status")
	private String status;

	/** link of the event */
	@XmlElement(name = "evtTmst")
	@XmlJavaTypeAdapter(DateISO8601Adapter.class)
	@EventDetailedView
	private Timestamp evtTmst;

	/** url of the feed */
	@XmlElement(name = "fqEventId")
	private Long fqEventId;

	/** description of the event */
	@XmlElement(name = "source")
	private String source;

	/** insertion date in the database */
	@XmlElement(name = "ptnrId")
	private String ptnrId;

	/** description of the event */
	@XmlElement(name = "msgType")
	private Long msgType;

	/** insertion date in the database */
	@XmlElement(name = "eqptNo")
	private String eqptNo;

	/** description of the event */
	@XmlElement(name = "shipNo")
	private String shipNo;

	/** insertion date in the database */
	@XmlElement(name = "tpdocNo")
	private String tpdocNo;

	/** description of the event */
	@XmlElement(name = "actvtyLoc")
	private String actvtyLoc;

	/** insertion date in the database */
	@XmlElement(name = "actvtyDate")
	private String actvtyDate;

	/** insertion date in the database */
	@XmlElement(name = "actvtTime")
	private String actvtTime;

	public Event(EventEntity eventFabricationEntity) {
		try {
			BeanUtils.copyProperties(this, eventFabricationEntity);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Event(Long evntId, String status,
			Timestamp evtTmst, Long fqEventId, String source, String ptnrId,
			Long msgType, String eqptNo, String shipNo, String tpdocNo,
			String actvtyLoc, String actvtyDate, String actvtTime) {

		this.evntId = evntId;
		this.status = status;
		this.evtTmst = evtTmst;
		this.fqEventId = fqEventId;
		this.source = source;
		this.ptnrId = ptnrId;
		this.msgType = msgType;
		this.eqptNo = eqptNo;
		this.shipNo = shipNo;
		this.tpdocNo = tpdocNo;
		this.actvtyLoc = actvtyLoc;
		this.actvtyDate = actvtyDate;
		this.actvtTime = actvtTime;

	}

	public Event() {
	}

	public Long getEvntId() {
		return evntId;
	}

	public void setEvntId(Long evntId) {
		this.evntId = evntId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getEvtTmst() {
		return evtTmst;
	}

	public void setEvtTmst(Timestamp evtTmst) {
		this.evtTmst = evtTmst;
	}

	public Long getFqEventId() {
		return fqEventId;
	}

	public void setFqEventId(Long fqEventId) {
		this.fqEventId = fqEventId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPtnrId() {
		return ptnrId;
	}

	public void setPtnrId(String ptnrId) {
		this.ptnrId = ptnrId;
	}

	public Long getMsgType() {
		return msgType;
	}

	public void setMsgType(Long msgType) {
		this.msgType = msgType;
	}

	public String getEqptNo() {
		return eqptNo;
	}

	public void setEqptNo(String eqptNo) {
		this.eqptNo = eqptNo;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getTpdocNo() {
		return tpdocNo;
	}

	public void setTpdocNo(String tpdocNo) {
		this.tpdocNo = tpdocNo;
	}

	public String getActvtyLoc() {
		return actvtyLoc;
	}

	public void setActvtyLoc(String actvtyLoc) {
		this.actvtyLoc = actvtyLoc;
	}

	public String getActvtyDate() {
		return actvtyDate;
	}

	public void setActvtyDate(String actvtyDate) {
		this.actvtyDate = actvtyDate;
	}

	public String getActvtTime() {
		return actvtTime;
	}

	public void setActvtTime(String actvtTime) {
		this.actvtTime = actvtTime;
	}

}
