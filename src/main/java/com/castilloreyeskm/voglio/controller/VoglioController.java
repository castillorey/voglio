package com.castilloreyeskm.voglio.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castilloreyeskm.voglio.dto.VoglioDto;
import com.castilloreyeskm.voglio.exceptions.ResourceNotFoundException;
import com.castilloreyeskm.voglio.model.Voglio;
import com.castilloreyeskm.voglio.request.AddVoglioRequest;
import com.castilloreyeskm.voglio.request.UpdateVoglioRequest;
import com.castilloreyeskm.voglio.response.ApiResponse;
import com.castilloreyeskm.voglio.service.voglio.IVoglioService;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/voglios")
public class VoglioController {
	private final IVoglioService voglioService;

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllVoglios() {
		List<VoglioDto> voglios = voglioService.getAllVoglios();
		return ResponseEntity.ok(new ApiResponse("Voglios found", voglios));
	}

	@GetMapping("{id}/voglio")
	public ResponseEntity<ApiResponse> getVoglioById(@PathVariable Long id) {
		try {
			Voglio voglio = voglioService.getVoglioById(id);
			VoglioDto voglioDto = voglioService.convertToDto(voglio);
			return ResponseEntity.ok(new ApiResponse("Voglio found", voglioDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PostMapping("add")
	public ResponseEntity<ApiResponse> addVoglio(@RequestBody AddVoglioRequest request) {
		try {
			VoglioDto newVoglio = voglioService.addVoglio(request);
			return ResponseEntity.ok(new ApiResponse("Add successful", newVoglio));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("{id}/update")
	public ResponseEntity<ApiResponse> updateVoglio(@RequestBody UpdateVoglioRequest request, @PathVariable Long id) {
		try {
			VoglioDto updatedVoglio = voglioService.updateVoglio(request, id);
			return ResponseEntity.ok(new ApiResponse("Update successful", updatedVoglio));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteVoglio(@PathVariable Long id) {
        try {
            voglioService.deleteVoglioById(id);
            return ResponseEntity.ok(new ApiResponse("Delete successful", id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

	@GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse> findVogliosByCategory(@PathVariable String category) {
        try {
            List<VoglioDto> voglios = voglioService.getVogliosByCategory(category);
            if (voglios.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No voglios found ", null));
            }
            return  ResponseEntity.ok(new ApiResponse("success", voglios));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

	@GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> getVoglioByName(@PathVariable String name){
        try {
            List<VoglioDto> voglios = voglioService.getVogliosByName(name);
            if (voglios.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No voglios found ", null));
            }
            return  ResponseEntity.ok(new ApiResponse("success", voglios));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse( e.getMessage(), null));
        }
    }
	
}
