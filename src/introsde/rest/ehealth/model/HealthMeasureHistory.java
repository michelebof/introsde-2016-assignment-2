package introsde.rest.ehealth.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.Date;


/**
 * The persistent class for the "HealthMeasureHistory" database table.
 * 
 */
@Entity
@Table(name="\"HealthMeasureHistory\"")
@NamedQuery(name="HealthMeasureHistory.findAll", query="SELECT h FROM HealthMeasureHistory h")
@XmlRootElement
public class HealthMeasureHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_mhistory")
	@TableGenerator(name="sqlite_mhistory", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="HealthMeasureHistory")
	@Column(name="\"idMeasureHistory\"")
	private int idMeasureHistory;

	@Temporal(TemporalType.DATE)
	@Column(name="\"timestamp\"")
	private Date timestamp;

	@Column(name="\"value\"")
	private String value;

	//bi-directional many-to-one association to MeasureDefinition
	@ManyToOne
	@JoinColumn(name = "\"idMeasureDefinition\"", referencedColumnName = "\"idMeasureDef\"")
	private MeasureDefinition measureDefinition;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name = "\"idPerson\"", referencedColumnName = "\"idPerson\"")
	private Person person;

	public HealthMeasureHistory() {
	}

	// the GETTERS and SETTERS of all the private attributes

	@XmlTransient
	public int getIdMeasureHistory() {
		return this.idMeasureHistory;
	}

	public void setIdMeasureHistory(int idMeasureHistory) {
		this.idMeasureHistory = idMeasureHistory;
	}

	@XmlElement(name = "created")
	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlTransient
	public MeasureDefinition getMeasureDefinition() {
		return this.measureDefinition;
	}
	
	@XmlElement(name = "measureName")
	public String getMeasureDefName() {
		return measureDefinition.getMeasureName();
	}

	public void setMeasureDefinition(MeasureDefinition measureDefinition) {
		this.measureDefinition = measureDefinition;
	}

	@XmlTransient
	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}