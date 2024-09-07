package com.castilloreyeskm.voglio.service.voglio;

import java.util.List;

import com.castilloreyeskm.voglio.dto.VoglioDto;
import com.castilloreyeskm.voglio.model.Voglio;
import com.castilloreyeskm.voglio.request.AddVoglioRequest;
import com.castilloreyeskm.voglio.request.UpdateVoglioRequest;

public interface IVoglioService {
	VoglioDto addVoglio(AddVoglioRequest voglio);

	VoglioDto getVoglioById(Long id);

	void deleteVoglioById(Long id);

	VoglioDto updateVoglio(UpdateVoglioRequest voglio, Long voglioId);

	List<VoglioDto> getAllVoglios();

	List<VoglioDto> getVogliosByCategory(String categoryName);

	List<VoglioDto> getVogliosByName(String voglioName);
}
