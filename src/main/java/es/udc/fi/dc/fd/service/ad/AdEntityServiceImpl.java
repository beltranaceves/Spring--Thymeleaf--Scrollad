package es.udc.fi.dc.fd.service.ad;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.udc.fi.dc.fd.model.Ad;
import es.udc.fi.dc.fd.model.form.AdForm;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.ImageEntity;
import es.udc.fi.dc.fd.repository.AdEntityRepository;

@Service
public class AdEntityServiceImpl implements AdEntityService {

	private final AdEntityRepository adEntityRepository;

	@Autowired
	public AdEntityServiceImpl(final AdEntityRepository repository) {
		super();

		adEntityRepository = checkNotNull(repository, "Received a null pointer as repository");
	}

	@Override
	public final Ad add(final AdEntity entity) {
		return adEntityRepository.save(entity);
	}

	@Override
	public final AdEntity findById(final Integer identifier) {
		final AdEntity entity;
		checkNotNull(identifier, "Received a null pointer as identifier");
		Optional<AdEntity> imageEntity = adEntityRepository.findById(identifier);
		if (imageEntity.isPresent()) {
			entity = imageEntity.get();
		} else {
			entity = new AdEntity();
		}

		return entity;	
	}

	@Override
	public final Iterable<AdEntity> getAllEntities() {
		Iterable<AdEntity> adEntities = adEntityRepository.findAll(Sort.by(Sort.Direction.DESC,"date"));
		adEntities.forEach((adEntity) -> {
			adEntity.convertAndLoadImageBase64();
		});
		return adEntities;
	}

	@Override
	public final Iterable<AdEntity> getEntities(final Pageable page) {
		return adEntityRepository.findAll(page);
	}

	@Override
	public final void remove(final AdEntity entity) {
		adEntityRepository.delete(entity);
	}

	@Override
	public boolean checkForm(final AdForm adForm) {
		checkNotNull(adForm.getTitle(), "Received a null pointer as a title");
		// checkNotNull(adForm.getImage(), "Received a null pointer as an Image");
		checkNotNull(adForm.getDescription(), "Received a null pointer as a Description");
		return false;
	}
}
