package es.udc.fi.dc.fd.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import es.udc.fi.dc.fd.model.persistence.ImageEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;

public interface Ad extends Serializable {

	public Integer getId();

	public String getTitle();

	public String getDescription();

	public LocalDateTime getDate();

	public UserEntity getUserA();

	public List<ImageEntity> getImages();

	public void setId(final Integer value);

	public void setTitle(final String value);

	public void setDescription(final String value);

	public void setDate(final LocalDateTime value);

	public void setUserA(final UserEntity value);

	public void setImages(List<ImageEntity> images);

}
