package com.castilloreyeskm.voglio.service.voglio;

import java.util.List;

import com.castilloreyeskm.voglio.model.Voglio;
import com.castilloreyeskm.voglio.request.AddVoglioRequest;
import com.castilloreyeskm.voglio.request.UpdateVoglioRequest;

public interface IVoglioService {
	Voglio addVoglio(AddVoglioRequest voglio);

	Voglio getVoglioById(Long id);

	void deleteVoglioById(Long id);

	Voglio updateVoglio(UpdateVoglioRequest voglio, Long voglioId);

	List<Voglio> getAllVoglios();

	List<Voglio> getVogliosByCategory(String categoryName);

	List<Voglio> getVogliosByName(String voglioName);
}
