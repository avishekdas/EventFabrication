package com.ibm.maersk.rest.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;

import com.ibm.maersk.rest.resource.event.Event;

/**
 * Podcast entity 
 * 
 * @author ama
 *
 */
@Entity
@Table(name="gemsevtfabrication")
public class EventEntity implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;

	/** id of the podcast */
	@Id
	@GeneratedValue
	@Column(name = "EVNTID")
	private Long evntId;

	/** title of the podcast */
	@Column(name = "STATUS")
	private String status;

	/** link of the podcast on Podcastpedia.org */
	@Column(name = "EVTTMST")
	private Timestamp evtTmst;

	/** url of the feed */
	@Column(name = "FKFQEVENTID")
	private Long fqEventId;

	/** description of the podcast */
	@Column(name = "SOURCE")
	private String source;

	/** insertion date in the database */
	@Column(name = "PTNRID")
	private String ptnrId;

	/** url of the feed */
	@Column(name = "MSGTYPE")
	private Long msgType;

	/** description of the podcast */
	@Column(name = "EQPTNO")
	private String eqptNo;

	/** insertion date in the database */
	@Column(name = "SHIPNO")
	private String shipNo;

	/** description of the podcast */
	@Column(name = "TPDOCNO")
	private String tpdocNo;

	/** insertion date in the database */
	@Column(name = "ACTVYLOC")
	private String actvtyLoc;

	/** description of the podcast */
	@Column(name = "ACTVDATE")
	private String actvtyDate;

	/** insertion date in the database */
	@Column(name = "ACTVTIME")
	private String actvtTime;

	public EventEntity() {
	}

	public EventEntity(Long evntId, String status,
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

	public EventEntity(Event eventFabrication) {
		try {
			BeanUtils.copyProperties(this, eventFabrication);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
