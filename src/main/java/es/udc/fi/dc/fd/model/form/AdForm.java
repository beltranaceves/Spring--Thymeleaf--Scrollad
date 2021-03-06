package es.udc.fi.dc.fd.model.form;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.validator.constraints.NotEmpty;

public final class AdForm implements Serializable {

	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 1328776989450853491L;

	@NotEmpty
	private String title;

	@NotEmpty
	private String description;

	public AdForm() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public void setTitle(final String title) {
		this.title = checkNotNull(title, "Received a null pointer as name");
	}

	public void setDescription(final String description) {
		this.description = checkNotNull(description, "Received a null pointer as name");
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof AdForm))
			return false;
		AdForm other = (AdForm) obj;
		return Objects.equals(description, other.description) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "AdForm [title=" + title + ", description=" + description + "]";
	}

}
