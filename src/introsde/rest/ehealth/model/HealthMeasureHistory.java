package introsde.rest.ehealth.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import introsde.rest.ehealth.dao.LifeCoachDao;

import java.util.Date;
import java.util.List;


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

	public static HealthMeasureHistory getHistoryId(int historyId) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        HealthMeasureHistory p = em.find(HealthMeasureHistory.class, historyId);
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }
	
    public static List<HealthMeasureHistory> getAll(int personId, String type) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        List<HealthMeasureHistory> list = em.createNamedQuery("HealthMeasureHistory.findAll", HealthMeasureHistory.class)
            .getResultList();
        
        LifeCoachDao.instance.closeConnections(em);
        for (int index = list.size()-1 ; index >= 0 ; index--){
        	if ((list.get(index).getPerson().getIdPerson()!=personId) || (!type.equals(list.get(index).getMeasureDefName())) ){
        		list.remove(index);
        	}
        }
        return list;
    }
    
    public static LifeStatus saveHistory(HealthMeasureHistory h, int id,String type) {
    	LifeStatus last = LifeStatus.getLastLifeStatus(id, type);
    	// create new History
    	if (last!= null){
	    	HealthMeasureHistory newHistory = new HealthMeasureHistory();
	    	newHistory.setMeasureDefinition(last.getMeasureDef());
	    	newHistory.setPerson(last.getPerson());
	    	newHistory.setTimestamp(new Date());
	    	newHistory.setValue(last.getValue());
	    	LifeStatus.removeLifeStatus(last);
	        EntityManager em = LifeCoachDao.instance.createEntityManager();
	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        em.persist(newHistory);
	        tx.commit();
	        LifeCoachDao.instance.closeConnections(em);
    	}
        // h is a new value in the Lifestatus
    	LifeStatus nuovo = new LifeStatus();
//    	Person p =Person.getPersonById(1);
//    	System.out.println("3");
//    	nuovo.setPerson(p);
    	System.out.println("4");
    	nuovo.setMeasureDef(MeasureDefinition.getByType(type));
    	System.out.println("5");
    	nuovo.setValue(h.getValue());
    	System.out.println("6");
    	return LifeStatus.saveLifeStatus(nuovo);
        //return h;
    } 
    
}