package pl.lps.entity;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Status entity class refers to current status of room reservation requests
 * except if reservation is accepted (when it turns into event) !!!!! UNDER
 * CONSTRUCTION !!!!!
 * 
 * @author kaz
 *
 */
@Entity
@Table(name = "statuses")
public class Status {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne(mappedBy = "status")
	private Request request;
	@NotNull
	private HashMap<Integer, String> statusLabels;
	@NotNull
	private HashMap<Integer, String> statusValues;

	public Status() {
		super();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public HashMap<Integer, String> getStatusLabels() {
		return statusLabels;
	}

	public void setStatusLabels(HashMap<Integer, String> statusLabels) {
		this.statusLabels = statusLabels;
	}

	public HashMap<Integer, String> getStatusValues() {
		return statusValues;
	}

	public void setStatusValues(HashMap<Integer, String> statusValues) {
		this.statusValues = statusValues;
	}

	@Override
	public String toString() {
		return "Status [id=" + id + ", request=" + request + ", statusLabels=" + statusLabels + ", statusValues="
				+ statusValues + "]";
	}

}
