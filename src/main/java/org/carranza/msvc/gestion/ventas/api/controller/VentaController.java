package org.carranza.msvc.gestion.ventas.api.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.carranza.msvc.gestion.ventas.api.dto.VentaDTO;
import org.carranza.msvc.gestion.ventas.api.service.VentaService;
import org.carranza.msvc.gestion.ventas.api.utils.CodeEnum;
import org.carranza.msvc.gestion.ventas.api.utils.ConstantesUtil;
import org.carranza.msvc.gestion.ventas.api.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(ConstantesUtil.API_VENTAS)
public class VentaController {
    private final VentaService VentaService;

    public VentaController(VentaService VentaService) {
        this.VentaService = VentaService;
    }

    @GetMapping("/findAllPage")
    @ResponseBody
    public ResponseEntity<CustomResponse> findAllPage(Pageable paginador){
        Page<VentaDTO> lstVentaDTO= this.VentaService.findAllPage(paginador);
        if (lstVentaDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), lstVentaDTO, "Información encontrada");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CustomResponse> findAll(){
        List<VentaDTO> lstVentaDTO= this.VentaService.findAll();
        if (lstVentaDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), lstVentaDTO, "Información encontrada");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomResponse> findById(@PathVariable UUID id) {
        var Venta = this.VentaService.findById(id);
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), Venta, null);
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody VentaDTO VentaDTO){
        VentaDTO Venta = this.VentaService.save(VentaDTO);
        CustomResponse r = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), Venta, "Venta registrado correctamente.");
        return new ResponseEntity<>(r, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    @ResponseBody
    public ResponseEntity<CustomResponse> update( @PathVariable UUID id,  @RequestBody VentaDTO VentaDTO) {
        VentaDTO result = this.VentaService.update(id, VentaDTO);
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), result, "Venta actualizada correctamente.");
        return new ResponseEntity<>(rpta, HttpStatus.OK);

    }

    @PutMapping("delete/{id}")
    public ResponseEntity<CustomResponse> delete( @PathVariable UUID id,  @RequestBody VentaDTO VentaDTO) {
        Boolean result = this.VentaService.delete(id, VentaDTO);
        CustomResponse rpta = new CustomResponse(Boolean.TRUE.equals(result)?"1":"0", Boolean.TRUE.equals(result) ? "Eliminado correctamente":"Error al eliminar");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

}
