package com.castilloreyeskm.voglio.service.voglio;

import java.util.List;

import com.castilloreyeskm.voglio.dto.VoglioDto;
import com.castilloreyeskm.voglio.model.Voglio;
import com.castilloreyeskm.voglio.request.VoglioAddRequest;
import com.castilloreyeskm.voglio.request.VoglioUpdateRequest;

public interface IVoglioService {
	VoglioDto addVoglio(VoglioAddRequest voglio);

	Voglio getVoglioById(Long id);

	void deleteVoglioById(Long id);

	VoglioDto updateVoglio(VoglioUpdateRequest voglio, Long voglioId);

	List<VoglioDto> getAllVoglios();

	List<VoglioDto> getVogliosByCategory(String categoryName);

	List<VoglioDto> getVogliosByName(String voglioName);

	VoglioDto convertToDto(Voglio voglio);
}
